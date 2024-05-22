package org.example;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class HttpUtils {

    // Створення інстансу клієнта OkHttp для виконання HTTP запитів
    private static final OkHttpClient client = new OkHttpClient();

    public static Response executeGetRequest(String url) throws IOException {
        // Створення запиту
        Request request = new Request.Builder().url(url).build();
        // Виконання запиту та повернення відповіді
        return client.newCall(request).execute();
    }

    public static void checkResponseStatus(Response response, int code) throws Exception {
        // Перевірка успішності відповіді
        if (!response.isSuccessful()) {
            if (response.code() == 404) {
                // Викидання виключення, якщо ресурс не знайдено
                throw new Exception("Image not found for status code: " + code);
            } else {
                // Викидання виключення для інших помилок
                throw new Exception("Failed to fetch image for status code: " + code);
            }
        }
    }

    public static void downloadImage(String imageUrl, int code) throws IOException {
        // Створення запиту для завантаження зображення
        Request request = new Request.Builder().url(imageUrl).build();

        // Виконання запиту
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Failed to download image for status code: " + code);
            }

            // Збереження зображення у файл
            try (InputStream inputStream = response.body().byteStream();
                 FileOutputStream outputStream = new FileOutputStream(code + ".jpg")) {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    // Записання прочитаних байтів у файл
                    outputStream.write(buffer, 0, bytesRead);
                }
            }
        }
    }
}
