package com.metsci.glimpse.worldwind.tile;

import static com.metsci.glimpse.util.logging.LoggerUtils.logWarning;
import gov.nasa.worldwind.View;
import gov.nasa.worldwind.geom.LatLon;
import gov.nasa.worldwind.layers.AbstractLayer;
import gov.nasa.worldwind.render.DrawContext;
import gov.nasa.worldwind.render.PreRenderable;
import gov.nasa.worldwind.render.Renderable;
import gov.nasa.worldwind.util.OGLStackHandler;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.media.opengl.GL;
import javax.media.opengl.GLContext;

import com.metsci.glimpse.axis.Axis2D;
import com.metsci.glimpse.canvas.GlimpseCanvas;
import com.metsci.glimpse.gl.GLSimpleFrameBufferObject;
import com.metsci.glimpse.layout.GlimpseLayout;
import com.metsci.glimpse.util.geo.LatLonGeo;
import com.metsci.glimpse.util.geo.projection.GeoProjection;
import com.metsci.glimpse.util.vector.Vector2d;
import com.metsci.glimpse.worldwind.canvas.SimpleOffscreenCanvas;

/**
 * GlimpseSurfaceTile uses TextureSurfaceTile to display the output of Glimpse
 * offscreen rendering onto the surface of the Worldwind globe.
 * 
 * @author ulman
 */
public class GlimpseDynamicSurfaceTile extends AbstractLayer implements Renderable, PreRenderable
{
    private static final Logger logger = Logger.getLogger( GlimpseDynamicSurfaceTile.class.getSimpleName( ) );

    protected GlimpseLayout layout;
    protected Axis2D axes;
    protected GeoProjection projection;
    protected int width, height;

    protected LatLonBounds maxBounds;
    protected List<LatLon> maxCorners;

    protected LatLonBounds bounds;
    protected List<LatLon> corners;

    protected SimpleOffscreenCanvas offscreenCanvas;
    protected TextureSurfaceTile tile;
    protected GLContext context;

    public GlimpseDynamicSurfaceTile( GlimpseLayout layout, Axis2D axes, GeoProjection projection, int width, int height, double minLat, double maxLat, double minLon, double maxLon )
    {
        this.axes = axes;
        this.projection = projection;
        this.layout = layout;

        this.width = width;
        this.height = height;

        this.maxBounds = new LatLonBounds( minLat, maxLat, minLon, maxLon );
        this.maxCorners = getCorners( this.maxBounds );
    }

    public GlimpseDynamicSurfaceTile( GlimpseLayout layout, Axis2D axes, GeoProjection projection, int width, int height, List<LatLon> corners )
    {
        this.axes = axes;
        this.projection = projection;
        this.layout = layout;

        this.width = width;
        this.height = height;

        this.maxBounds = getCorners( corners );
        this.maxCorners = getCorners( this.maxBounds );

        this.offscreenCanvas = new SimpleOffscreenCanvas( width, height, false, false, context );
    }

    public GlimpseCanvas getGlimpseCanvas( )
    {
        return this.offscreenCanvas;
    }

    @Override
    public void preRender( DrawContext dc )
    {
        if ( tile == null )
        {

            if ( context == null )
            {
                GLContext oldcontext = dc.getGLContext( );
                context = dc.getGLDrawable( ).createContext( oldcontext );
            }

            offscreenCanvas.initialize( context );
        }

        updateGeometry( dc );

        drawOffscreen( dc );

        if ( tile == null )
        {
            int textureHandle = offscreenCanvas.getFrameBuffer( ).getTextureId( );
            tile = newTextureSurfaceTile( textureHandle, corners );
        }
    }
    
    protected TextureSurfaceTile newTextureSurfaceTile( int textureHandle, Iterable<? extends LatLon> corners )
    {
        return new TextureSurfaceTile( textureHandle, corners );
    }

    protected void updateGeometry( DrawContext dc )
    {
        List<LatLon> screenCorners = getCorners( dc );

        if ( !isValid( screenCorners ) )
        {
            corners = maxCorners;
            bounds = maxBounds;
        }
        else
        {
            LatLonBounds screenBounds = bufferCorners( getCorners( screenCorners ), 0.5 );
            bounds = getIntersectedCorners( maxBounds, screenBounds );
            corners = getCorners( bounds );
        }

        if ( tile != null )
        {
            setAxes( axes, bounds );
            tile.setCorners( corners );
        }
    }

    protected void setAxes( Axis2D axes, LatLonBounds bounds )
    {
        Vector2d c1 = projection.project( LatLonGeo.fromDeg( bounds.minLat, bounds.minLon ) );
        Vector2d c2 = projection.project( LatLonGeo.fromDeg( bounds.maxLat, bounds.minLon ) );
        Vector2d c3 = projection.project( LatLonGeo.fromDeg( bounds.maxLat, bounds.maxLon ) );
        Vector2d c4 = projection.project( LatLonGeo.fromDeg( bounds.minLat, bounds.maxLon ) );

        double minX = minX( c1, c2, c3, c4 );
        double maxX = maxX( c1, c2, c3, c4 );
        double minY = minY( c1, c2, c3, c4 );
        double maxY = maxY( c1, c2, c3, c4 );

        axes.set( minX, maxX, minY, maxY );
        axes.getAxisX( ).validate( );
        axes.getAxisY( ).validate( );
    }

    protected double minX( Vector2d... corners )
    {
        double min = Double.POSITIVE_INFINITY;
        for ( Vector2d corner : corners )
        {
            if ( corner.getX( ) < min ) min = corner.getX( );
        }
        return min;
    }

    protected double minY( Vector2d... corners )
    {
        double min = Double.POSITIVE_INFINITY;
        for ( Vector2d corner : corners )
        {
            if ( corner.getY( ) < min ) min = corner.getY( );
        }
        return min;
    }

    protected double maxX( Vector2d... corners )
    {
        double max = Double.NEGATIVE_INFINITY;
        for ( Vector2d corner : corners )
        {
            if ( corner.getX( ) > max ) max = corner.getX( );
        }
        return max;
    }

    protected double maxY( Vector2d... corners )
    {
        double max = Double.NEGATIVE_INFINITY;
        for ( Vector2d corner : corners )
        {
            if ( corner.getY( ) > max ) max = corner.getY( );
        }
        return max;
    }

    protected boolean isValid( List<LatLon> screenCorners )
    {
        if ( screenCorners == null ) return false;

        for ( LatLon latlon : screenCorners )
        {
            if ( latlon == null ) return false;
        }

        return true;
    }

    protected LatLonBounds bufferCorners( LatLonBounds corners, double bufferFraction )
    {
        double diffLat = corners.maxLat - corners.minLat;
        double diffLon = corners.maxLon - corners.minLon;

        double buffMinLat = corners.minLat - diffLat * bufferFraction;
        double buffMaxLat = corners.maxLat + diffLat * bufferFraction;
        double buffMinLon = corners.minLon - diffLon * bufferFraction;
        double buffMaxLon = corners.maxLon + diffLon * bufferFraction;

        return new LatLonBounds( buffMinLat, buffMaxLat, buffMinLon, buffMaxLon );
    }

    protected LatLonBounds getCorners( List<LatLon> screenCorners )
    {
        double minLat = Double.POSITIVE_INFINITY;
        double minLon = Double.POSITIVE_INFINITY;
        double maxLat = Double.NEGATIVE_INFINITY;
        double maxLon = Double.NEGATIVE_INFINITY;
        for ( LatLon latlon : screenCorners )
        {
            double lat = latlon.getLatitude( ).getDegrees( );
            double lon = latlon.getLongitude( ).getDegrees( );

            if ( lat < minLat ) minLat = lat;
            if ( lat > maxLat ) maxLat = lat;
            if ( lon < minLon ) minLon = lon;
            if ( lon > maxLon ) maxLon = lon;
        }

        return new LatLonBounds( minLat, maxLat, minLon, maxLon );
    }

    protected LatLonBounds getIntersectedCorners( LatLonBounds corners1, LatLonBounds corners2 )
    {
        double minLat = Math.max( corners1.minLat, corners2.minLat );
        double minLon = Math.max( corners1.minLon, corners2.minLon );
        double maxLat = Math.min( corners1.maxLat, corners2.maxLat );
        double maxLon = Math.min( corners1.maxLon, corners2.maxLon );

        return new LatLonBounds( minLat, maxLat, minLon, maxLon );
    }

    protected List<LatLon> getCorners( LatLonBounds bounds )
    {
        List<LatLon> corners = new ArrayList<LatLon>( );

        corners.add( LatLon.fromDegrees( bounds.minLat, bounds.minLon ) );
        corners.add( LatLon.fromDegrees( bounds.minLat, bounds.maxLon ) );
        corners.add( LatLon.fromDegrees( bounds.maxLat, bounds.maxLon ) );
        corners.add( LatLon.fromDegrees( bounds.maxLat, bounds.minLon ) );

        return corners;
    }

    protected List<LatLon> getCorners( DrawContext dc )
    {
        View view = dc.getView( );
        Rectangle viewport = view.getViewport( );

        List<LatLon> corners = new ArrayList<LatLon>( 4 );
        corners.add( view.computePositionFromScreenPoint( viewport.getMinX( ), viewport.getMinY( ) ) );
        corners.add( view.computePositionFromScreenPoint( viewport.getMinX( ), viewport.getMaxY( ) ) );
        corners.add( view.computePositionFromScreenPoint( viewport.getMaxX( ), viewport.getMaxY( ) ) );
        corners.add( view.computePositionFromScreenPoint( viewport.getMaxX( ), viewport.getMinY( ) ) );

        return corners;
    }

    @Override
    protected void doRender( DrawContext dc )
    {
        tile.render( dc );

    }

    protected void drawOffscreen( DrawContext dc )
    {
        context.makeCurrent( );
        try
        {
            drawOffscreen( dc.getGLContext( ) );
        }
        finally
        {
            dc.getGLContext( ).makeCurrent( );
        }
    }

    protected void drawOffscreen( GLContext glContext )
    {
        GLSimpleFrameBufferObject fbo = offscreenCanvas.getFrameBuffer( );
        OGLStackHandler stack = new OGLStackHandler( );
        GL gl = glContext.getGL( );

        stack.pushAttrib( gl, GL.GL_ALL_ATTRIB_BITS );
        stack.pushClientAttrib( gl, ( int ) GL.GL_ALL_CLIENT_ATTRIB_BITS );
        stack.pushTexture( gl );
        stack.pushModelview( gl );
        stack.pushProjection( gl );

        fbo.bind( glContext );
        try
        {
            layout.paintTo( offscreenCanvas.getGlimpseContext( ) );
        }
        catch ( Exception e )
        {
            logWarning( logger, "Trouble drawing to offscreen buffer", e );
        }
        finally
        {
            fbo.unbind( glContext );
            stack.pop( gl );
        }
    }

    protected static class LatLonBounds
    {
        public double minLat, maxLat, minLon, maxLon;

        public LatLonBounds( double minLat, double maxLat, double minLon, double maxLon )
        {
            this.minLat = minLat;
            this.maxLat = maxLat;
            this.minLon = minLon;
            this.maxLon = maxLon;
        }
    }
}
