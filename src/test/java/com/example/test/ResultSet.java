/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class ResultSet {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private final ResultActions resultActions;

    private final MockHttpServletResponse response;

    private final String responceAsString;

    public ResultSet(ResultActions resultActions) {
        this.resultActions = resultActions;
        response = resultActions.andReturn().getResponse();
        try {
            responceAsString = response.getContentAsString();
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException(e);
        }
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public ResultSet andExpect(ResultMatcher matcher) throws Exception {
        resultActions.andExpect(matcher);
        return this;
    }

    @SuppressWarnings("unchecked")
    public <T extends Object> T getObjectFromResponce(Class<?> cl) {
        try {
            return (T) objectMapper.readValue(responceAsString, cl);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends Object> T getObjectFromResponce(TypeReference tr) {
        try {
            return (T) objectMapper.readValue(responceAsString, tr);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends Object> PageImpl<T> getObjectFromPageResponce(Class<?> cl) {
        try {
            PageImpl<T> page = ((PageImplBean<T>) objectMapper.readValue(responceAsString, new TypeReference<PageImplBean<T>>() {
            })).pageImpl();
            //page.content contains LinkedHashMap - so convert to POJO :
            JavaType jt = objectMapper.getTypeFactory().constructCollectionType(List.class, cl);
            List<T> content = objectMapper.convertValue(page.getContent(), jt);
            return new PageImpl(content);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public String getContentAsString() {
        return responceAsString;
    }

    public String getErrorCode() {
        try {
            return new JSONObject(responceAsString).getString("errorCode");
        } catch (JSONException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public String getErrorMessage() {
        try {
            return new JSONObject(responceAsString).getString("message");
        } catch (JSONException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public ResultActions getResultActions() {
        return resultActions;
    }
}

class PageImplBean<T> extends PageImpl<T> {

    private static final long serialVersionUID = 1L;
    private int number;
    private int size;
    private int totalPages;
    private int numberOfElements;
    private long totalElements;
    private boolean previousPage;
    private boolean first;
    private boolean next;
    private boolean last;
    private List<T> content;
    private Sort sort;

    public PageImplBean() {
        super(new ArrayList<T>());
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(int numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public boolean isPreviousPage() {
        return previousPage;
    }

    public void setPreviousPage(boolean previousPage) {
        this.previousPage = previousPage;
    }

    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    public boolean isNext() {
        return next;
    }

    public void setNext(boolean next) {
        this.next = next;
    }

    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public Sort getSort() {
        return sort;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }

    public PageImpl<T> pageImpl() {
        return new PageImpl<T>(getContent(), new PageRequest(getNumber(), getSize(), getSort()), getTotalElements());
    }
}
