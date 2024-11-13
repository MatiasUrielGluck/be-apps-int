package com.uade.beappsint.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.uade.beappsint.exception.BadRequestException;
import com.uade.beappsint.service.CloudinaryService;
import com.uade.beappsint.utils.CommonUtilities;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class CloudinaryServiceImpl implements CloudinaryService {
    private final Cloudinary cloudinary;

    @Override
    public String uploadImageBase64(String base64) {
        String imageFormat = CommonUtilities.extractImageFormat(base64);
        if (!CommonUtilities.isValidImageFormat(imageFormat)) throw new BadRequestException("File format is not supported");

        try {
            var pic = cloudinary.uploader().upload(base64, ObjectUtils.asMap("folder", "/blacknuster/"));
            return pic.get("url").toString();
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Failed to upload the file.");
        }
    }
}
