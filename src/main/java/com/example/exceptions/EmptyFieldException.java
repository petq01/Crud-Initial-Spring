/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.exceptions;

/**
 *
 * @author User
 */
public class EmptyFieldException extends BaseException {

    public EmptyFieldException(String field) {
        super(new ErrorCode("1056", "There is no stored value for field " + field));
    }

}
