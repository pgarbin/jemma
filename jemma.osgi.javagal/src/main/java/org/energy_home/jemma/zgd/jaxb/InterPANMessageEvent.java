/**
 * This file is part of JEMMA - http://jemma.energy-home.org
 * (C) Copyright 2013 Telecom Italia (http://www.telecomitalia.it)
 *
 * JEMMA is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License (LGPL) version 3
 * or later as published by the Free Software Foundation, which accompanies
 * this distribution and is available at http://www.gnu.org/licenses/lgpl.html
 *
 * JEMMA is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License (LGPL) for more details.
 *
 */
//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.1.9-03/31/2009 04:14 PM(snajper)-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.04.03 at 05:23:14 PM CEST 
//


package org.energy_home.jemma.zgd.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for InterPANMessageEvent complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InterPANMessageEvent">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CallbackIdentifier" type="{http://www.zigbee.org/GWGSchema}CallbackIdentifier" minOccurs="0"/>
 *         &lt;element name="SrcAddressMode" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="SrcAddress" type="{http://www.zigbee.org/GWGSchema}Address"/>
 *         &lt;element name="DstAddressMode" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="DstAddress" type="{http://www.zigbee.org/GWGSchema}Address"/>
 *         &lt;element name="SrcPANID" type="{http://www.zigbee.org/GWGSchema}unsigned16Bit"/>
 *         &lt;element name="DstPANID" type="{http://www.zigbee.org/GWGSchema}unsigned16Bit"/>
 *         &lt;element name="ProfileID" type="{http://www.zigbee.org/GWGSchema}ProfileIdentifier" minOccurs="0"/>
 *         &lt;element name="ClusterID" type="{http://www.zigbee.org/GWGSchema}ClusterIdentifier"/>
 *         &lt;element name="ASDULength" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="ASDU" type="{http://www.w3.org/2001/XMLSchema}hexBinary"/>
 *         &lt;element name="LinkQuality" type="{http://www.w3.org/2001/XMLSchema}unsignedByte" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InterPANMessageEvent", propOrder = {
    "callbackIdentifier",
    "srcAddressMode",
    "srcAddress",
    "dstAddressMode",
    "dstAddress",
    "srcPANID",
    "dstPANID",
    "profileID",
    "clusterID",
    "asduLength",
    "asdu",
    "linkQuality"
})
public class InterPANMessageEvent {

    @XmlElement(name = "CallbackIdentifier")
    protected Long callbackIdentifier;
    @XmlElement(name = "SrcAddressMode")
    @XmlSchemaType(name = "unsignedInt")
    protected long srcAddressMode;
    @XmlElement(name = "SrcAddress", required = true)
    protected Address srcAddress;
    @XmlElement(name = "DstAddressMode")
    @XmlSchemaType(name = "unsignedInt")
    protected long dstAddressMode;
    @XmlElement(name = "DstAddress", required = true)
    protected Address dstAddress;
    @XmlElement(name = "SrcPANID")
    protected int srcPANID;
    @XmlElement(name = "DstPANID")
    protected int dstPANID;
    @XmlElement(name = "ProfileID")
    protected Integer profileID;
    @XmlElement(name = "ClusterID")
    protected int clusterID;
    @XmlElement(name = "ASDULength")
    @XmlSchemaType(name = "unsignedInt")
    protected long asduLength;
    @XmlElement(name = "ASDU", required = true, type = String.class)
    @XmlJavaTypeAdapter(HexBinaryAdapter.class)
    @XmlSchemaType(name = "hexBinary")
    protected byte[] asdu;
    @XmlElement(name = "LinkQuality")
    @XmlSchemaType(name = "unsignedByte")
    protected Short linkQuality;

    /**
     * Gets the value of the callbackIdentifier property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCallbackIdentifier() {
        return callbackIdentifier;
    }

    /**
     * Sets the value of the callbackIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCallbackIdentifier(Long value) {
        this.callbackIdentifier = value;
    }

    /**
     * Gets the value of the srcAddressMode property.
     * 
     */
    public long getSrcAddressMode() {
        return srcAddressMode;
    }

    /**
     * Sets the value of the srcAddressMode property.
     * 
     */
    public void setSrcAddressMode(long value) {
        this.srcAddressMode = value;
    }

    /**
     * Gets the value of the srcAddress property.
     * 
     * @return
     *     possible object is
     *     {@link Address }
     *     
     */
    public Address getSrcAddress() {
        return srcAddress;
    }

    /**
     * Sets the value of the srcAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link Address }
     *     
     */
    public void setSrcAddress(Address value) {
        this.srcAddress = value;
    }

    /**
     * Gets the value of the dstAddressMode property.
     * 
     */
    public long getDstAddressMode() {
        return dstAddressMode;
    }

    /**
     * Sets the value of the dstAddressMode property.
     * 
     */
    public void setDstAddressMode(long value) {
        this.dstAddressMode = value;
    }

    /**
     * Gets the value of the dstAddress property.
     * 
     * @return
     *     possible object is
     *     {@link Address }
     *     
     */
    public Address getDstAddress() {
        return dstAddress;
    }

    /**
     * Sets the value of the dstAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link Address }
     *     
     */
    public void setDstAddress(Address value) {
        this.dstAddress = value;
    }

    /**
     * Gets the value of the srcPANID property.
     * 
     */
    public int getSrcPANID() {
        return srcPANID;
    }

    /**
     * Sets the value of the srcPANID property.
     * 
     */
    public void setSrcPANID(int value) {
        this.srcPANID = value;
    }

    /**
     * Gets the value of the dstPANID property.
     * 
     */
    public int getDstPANID() {
        return dstPANID;
    }

    /**
     * Sets the value of the dstPANID property.
     * 
     */
    public void setDstPANID(int value) {
        this.dstPANID = value;
    }

    /**
     * Gets the value of the profileID property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getProfileID() {
        return profileID;
    }

    /**
     * Sets the value of the profileID property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setProfileID(Integer value) {
        this.profileID = value;
    }

    /**
     * Gets the value of the clusterID property.
     * 
     */
    public int getClusterID() {
        return clusterID;
    }

    /**
     * Sets the value of the clusterID property.
     * 
     */
    public void setClusterID(int value) {
        this.clusterID = value;
    }

    /**
     * Gets the value of the asduLength property.
     * 
     */
    public long getASDULength() {
        return asduLength;
    }

    /**
     * Sets the value of the asduLength property.
     * 
     */
    public void setASDULength(long value) {
        this.asduLength = value;
    }

    /**
     * Gets the value of the asdu property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public byte[] getASDU() {
        return asdu;
    }

    /**
     * Sets the value of the asdu property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setASDU(byte[] value) {
        this.asdu = ((byte[]) value);
    }

    /**
     * Gets the value of the linkQuality property.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getLinkQuality() {
        return linkQuality;
    }

    /**
     * Sets the value of the linkQuality property.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setLinkQuality(Short value) {
        this.linkQuality = value;
    }

}
