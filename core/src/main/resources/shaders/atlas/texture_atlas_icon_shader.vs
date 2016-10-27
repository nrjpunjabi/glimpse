//
// Copyright (c) 2016, Metron, Inc.
// All rights reserved.
//
// Redistribution and use in source and binary forms, with or without
// modification, are permitted provided that the following conditions are met:
//     * Redistributions of source code must retain the above copyright
//       notice, this list of conditions and the following disclaimer.
//     * Redistributions in binary form must reproduce the above copyright
//       notice, this list of conditions and the following disclaimer in the
//       documentation and/or other materials provided with the distribution.
//     * Neither the name of Metron, Inc. nor the
//       names of its contributors may be used to endorse or promote products
//       derived from this software without specific prior written permission.
//
// THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
// ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
// WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
// DISCLAIMED. IN NO EVENT SHALL METRON, INC. BE LIABLE FOR ANY
// DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
// (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
// LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
// ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
// (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
// SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
//

#version 150

in vec4 a_position;

in vec4 pixelCoords;
in vec4 texCoords;
in vec3 pickColor;

out VertexData {
    vec4 vpixelCoords;
    vec4 vtexCoords;
    vec3 vpickColor; 
} VertexOut;

uniform mat4 mvpMatrix;

void main( )
{
    // pass through icon widths and heights and center to geometry shader
    // order in vector: width, height, offsetX, offsetY
    VertexOut.vpixelCoords = pixelCoords;

    // pass through icon texture coordinates to geometry shader
    // order in vector: minX, maxX, minY, maxY
    VertexOut.vtexCoords = texCoords;
    
    // pass through picking color
    VertexOut.vpickColor = pickColor;

    // transform vertex (this will have to change with later OpenGL versions)
    gl_Position = mvpMatrix * vec4( a_position.xy, 0, 1 );
    
    // replace the scale and rotation so they make it to the geometry shader    
    gl_Position.z = a_position.z;
    gl_Position.w = a_position.w;
}