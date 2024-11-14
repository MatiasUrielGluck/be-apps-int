package com.uade.beappsint.service.impl;

import com.uade.beappsint.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    private final ResourceLoader resourceLoader;

    @Override
    public String readFileFromResources(String filename) throws IOException {
        Resource resource = resourceLoader.getResource("classpath:" + filename);
        InputStream inputStream = resource.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }

        reader.close();
        return stringBuilder.toString();
    }
}
