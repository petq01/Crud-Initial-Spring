/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.multipart;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.servlet.http.HttpServletResponse;
import static org.apache.commons.io.IOUtils.copy;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

/**
 *
 * @author Petya
 */
@Service
public class MultipartServiceImpl implements MultipartService {

    @Override
    public void downloadByteArrayData(
            byte[] content,
            String contentType,
            String filename,
            HttpServletResponse response
    ) throws IOException {

        InputStream is = new ByteArrayInputStream(content);

        response.setContentType(contentType);
        response.addHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + filename + "\"");
        copy(is, response.getOutputStream());
        response.flushBuffer();
    }
}
