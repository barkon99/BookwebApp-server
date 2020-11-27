package com.konew.backend.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.apache.catalina.core.ApplicationPart;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

@Service
public class ImageUploader {

    Cloudinary cloudinary;

    public ImageUploader() {
        cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dxfih2idm",
                "api_key", "344648756689497",
                "api_secret", "ZmbW-s5TGODwAADymN2RJOuIo58"));
    }

    public String uploadFile(MultipartFile image) throws IOException {
        Map uploadParams = ObjectUtils.asMap("resource_type", "image");
        image.getInputStream();
        Map uploadResult = cloudinary.uploader().upload(image.getBytes(), uploadParams);

        return (String) uploadResult.get("url");
    }
}
