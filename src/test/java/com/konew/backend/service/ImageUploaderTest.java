package com.konew.backend.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.io.FileInputStream;
import java.io.IOException;

class ImageUploaderTest {


    @Test
    void shouldReturnCorrectUrl() throws IOException {
        ImageUploader imageUploader = new ImageUploader();
        FileInputStream inputFile = new FileInputStream( "C:\\Users\\barte\\OneDrive\\Obrazy\\zdjeciaDoCV.jpg");
        MockMultipartFile file = new MockMultipartFile("file", "zdjeciaDoCV", "multipart/form-data", inputFile);

        String url = imageUploader.uploadFile(file);

        Assertions.assertNotNull(url);

    }
}
