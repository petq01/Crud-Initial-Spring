/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;

/**
 *
 * @author Petya
 */
@MappedSuperclass
public abstract class Attachment {

    @Id
    private String id; //set in service

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dateOfUpload;

    private String contentType;

    private String filename;
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long version;

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Lob
    @JsonIgnore
    @Basic(fetch = FetchType.LAZY)
    private byte[] content;

    public Attachment() {
    }

    public Date getDateOfUpload() {
        return dateOfUpload;
    }

    public void setDateOfUpload(Date dateOfUpload) {
        this.dateOfUpload = dateOfUpload;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] binarContent) {
        this.content = binarContent;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}
