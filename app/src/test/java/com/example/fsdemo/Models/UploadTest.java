package com.example.fsdemo.Models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UploadTest {

    @Test
    void getName() {
        Upload upload = new Upload("17/08/2020", "www.google.com");
        assertEquals("17/08/2020", upload.getName());
    }

    @Test
    void setName() {
        Upload upload = new Upload("", "www.google.com");
        upload.setName("17/08/2020");
        assertEquals("17/08/2020", "17/08/2020");
    }

    @Test
    void getImageUrl() {
        Upload upload = new Upload("17/08/2020", "www.google.com");
        assertEquals("www.google.com", upload.getImageUrl());
    }

    @Test
    void setImageUrl() {
        Upload upload = new Upload("17/08/2020", "");
        upload.setImageUrl("www.google.com");
        assertEquals("www.google.com", upload.getImageUrl());
    }


}//class