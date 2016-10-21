/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.model;

import java.util.Date;
import lombok.Data;

/**
 *
 * @author User
 */
@Data
public class AttachmentResponse {

    public AttachmentResponse() {
    }

    public AttachmentResponse(Long id, User user, String status, String filename, String createdBy, Date createdAt, Date lastStatusChange) {
        this.id = id;
        this.status = status;
        this.lastStatusChange = lastStatusChange.getTime();
        this.filename = filename;
        this.createdAt = createdAt.getTime();
        this.createdBy = createdBy;
        imageUrl = new StringBuilder()
                .append("/user/")
                .append(user.getId())
                .append("/attachment/")
                .append(id)
                .toString();
    }

    private Long id;
    private Long createdAt;
    private Long lastStatusChange;
    private String createdBy;
    private String status;
    private String imageUrl;
    private String filename;
}
