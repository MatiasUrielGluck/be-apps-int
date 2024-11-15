package com.uade.beappsint.service;

import java.io.IOException;

public interface FileService {
    String readFileFromResources(String filename) throws IOException;
}
