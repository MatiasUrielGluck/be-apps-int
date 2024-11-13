package com.uade.beappsint.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.uade.beappsint.service.CloudinaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class CloudinaryServiceImpl implements CloudinaryService {
    private final Cloudinary cloudinary;

    @Override
    public String uploadImage(MultipartFile imgUrl) {
        try {
            File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + imgUrl.getOriginalFilename());
            FileOutputStream fos = new FileOutputStream(convFile);
            fos.write(imgUrl.getBytes());
            fos.close();

            var pic = cloudinary.uploader().upload(convFile, ObjectUtils.asMap("folder", "/blacknuster/"));
            return pic.get("url").toString();
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Failed to upload the file.");
        }
    }
}
