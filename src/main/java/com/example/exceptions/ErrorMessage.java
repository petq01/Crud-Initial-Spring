/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.exceptions;

import org.joda.time.DateTime;

/**
 *
 * @author Petya
 */
public class ErrorMessage {

    Long timestamp;
    Integer HttpStatusCode;
    String errorCode;
    String message;

    public ErrorMessage() {
        this.timestamp = new DateTime().getMillis();
    }

    public ErrorMessage(int status, ErrorCode errorCode) {
        this.timestamp = new DateTime().getMillis();
        this.HttpStatusCode = status;
        this.errorCode = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getHttpStatusCode() {
        return HttpStatusCode;
    }

    public void setHttpStatusCode(Integer status) {
        this.HttpStatusCode = status;
    }

    public String getMessage() {
        return message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode.getCode();
        this.message = errorCode.getMessage();
    }
}
