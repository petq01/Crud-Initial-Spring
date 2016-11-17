/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.exceptions;

/**
 *
 * @author Petya
 */
public class EmptyFileException extends BaseException {

    public EmptyFileException() {
        super(new ErrorCode("1005", "Either no file has been chosen in the multipart form or the chosen file has no content."));
    }
}
