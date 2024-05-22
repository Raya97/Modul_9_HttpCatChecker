package org.example;

import java.io.IOException;

public class HttpImageDownloader {

    // Метод для завантаження зображення за URL.
    public void downloadImage(String imageUrl, int code) throws IOException {
        try {
            HttpUtils.downloadImage(imageUrl, code);
        } catch (Exception e) {
            throw new IOException(e.getMessage(), e);
        }
    }
}
