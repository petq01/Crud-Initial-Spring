/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.controller;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author User
 */
public interface MultipartService {

    public void downloadByteArrayData(
            byte[] content,
            String contentType,
            String filename,
            HttpServletResponse response
    ) throws IOException;
}
