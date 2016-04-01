/*
 * Copyright (c) 2016, Metron, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of Metron, Inc. nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL METRON, INC. BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2.3-3-
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
// Generated on: 2011.12.05 at 11:09:09 AM EST
//


package com.metsci.glimpse.dspl.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.namespace.QName;


/**
 *
 *         A property of a concept (e.g., the country of a city).
 *
 *
 * <p>Java class for ConceptProperty complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="ConceptProperty">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="info" type="{http://schemas.google.com/dspl/2010}Info" minOccurs="0"/>
 *         &lt;element name="type" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="ref" use="required" type="{http://schemas.google.com/dspl/2010}DataType" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="id" type="{http://schemas.google.com/dspl/2010}LocalId" />
 *       &lt;attribute name="concept" type="{http://www.w3.org/2001/XMLSchema}QName" />
 *       &lt;attribute name="isParent" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *       &lt;attribute name="isMapping" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *       &lt;attribute name="isRequired" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ConceptProperty", propOrder = {
    "info",
    "type"
})
public class ConceptProperty {

    protected Info info;
    protected ConceptProperty.Type type;
    @XmlAttribute(name = "id")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String id;
    @XmlAttribute(name = "concept")
    protected QName conceptRef;
    @XmlAttribute(name = "isParent")
    protected Boolean isParent;
    @XmlAttribute(name = "isMapping")
    protected Boolean isMapping;
    @XmlAttribute(name = "isRequired")
    protected Boolean isRequired;

    /**
     * Gets the value of the info property.
     *
     * @return
     *     possible object is
     *     {@link Info }
     *
     */
    public Info getInfo() {
        return info;
    }

    /**
     * Sets the value of the info property.
     *
     * @param value
     *     allowed object is
     *     {@link Info }
     *
     */
    public void setInfo(Info value) {
        this.info = value;
    }

    /**
     * Gets the value of the type property.
     *
     * @return
     *     possible object is
     *     {@link ConceptProperty.Type }
     *
     */
    public ConceptProperty.Type getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     *
     * @param value
     *     allowed object is
     *     {@link ConceptProperty.Type }
     *
     */
    public void setType(ConceptProperty.Type value) {
        this.type = value;
    }

    /**
     * Gets the value of the id property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the conceptRef property.
     *
     * @return
     *     possible object is
     *     {@link QName }
     *
     */
    public QName getConceptRef() {
        return conceptRef;
    }

    /**
     * Sets the value of the conceptRef property.
     *
     * @param value
     *     allowed object is
     *     {@link QName }
     *
     */
    public void setConceptRef(QName value) {
        this.conceptRef = value;
    }

    /**
     * Gets the value of the isParent property.
     *
     * @return
     *     possible object is
     *     {@link Boolean }
     *
     */
    public boolean isIsParent() {
        if (isParent == null) {
            return false;
        } else {
            return isParent;
        }
    }

    /**
     * Sets the value of the isParent property.
     *
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *
     */
    public void setIsParent(Boolean value) {
        this.isParent = value;
    }

    /**
     * Gets the value of the isMapping property.
     *
     * @return
     *     possible object is
     *     {@link Boolean }
     *
     */
    public boolean isIsMapping() {
        if (isMapping == null) {
            return false;
        } else {
            return isMapping;
        }
    }

    /**
     * Sets the value of the isMapping property.
     *
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *
     */
    public void setIsMapping(Boolean value) {
        this.isMapping = value;
    }

    /**
     * Gets the value of the isRequired property.
     *
     * @return
     *     possible object is
     *     {@link Boolean }
     *
     */
    public boolean isIsRequired() {
        if (isRequired == null) {
            return false;
        } else {
            return isRequired;
        }
    }

    /**
     * Sets the value of the isRequired property.
     *
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *
     */
    public void setIsRequired(Boolean value) {
        this.isRequired = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     *
     * <p>The following schema fragment specifies the expected content contained within this class.
     *
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;attribute name="ref" use="required" type="{http://schemas.google.com/dspl/2010}DataType" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     *
     *
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class Type {

        @XmlAttribute(name = "ref", required = true)
        protected DataType ref;

        /**
         * Gets the value of the ref property.
         *
         * @return
         *     possible object is
         *     {@link DataType }
         *
         */
        public DataType getRef() {
            return ref;
        }

        /**
         * Sets the value of the ref property.
         *
         * @param value
         *     allowed object is
         *     {@link DataType }
         *
         */
        public void setRef(DataType value) {
            this.ref = value;
        }

    }


    @javax.xml.bind.annotation.XmlTransient
    protected Concept parentConcept;

    public Concept getParentConcept( )
    {
        return parentConcept;
    }

    public void setParentConcept( Concept parentConcept )
    {
        this.parentConcept = parentConcept;
    }

    public Concept getConcept( ) throws javax.xml.bind.JAXBException, java.io.IOException, com.metsci.glimpse.dspl.util.DsplException
    {
        DataSet dataset = this.parentConcept.getDataSet( );
        return dataset.getConcept( this.conceptRef );
    }


}
