/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.Data;

/**
 *
 * @author Petya
 */
@Data
@Entity
//@Table(name = "USERATTACHMENT")
public class UserAttachment extends Attachment {

    @Id
    private String id;
    @JsonIgnore
    @ManyToOne
    private User user;

    public UserAttachment() {
    }

}
