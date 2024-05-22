package org.example;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class HttpImageDownloaderTest {

    private MockWebServer mockWebServer; // Сервер для імітації HTTP-запитів
    private HttpImageDownloader imageDownloader; // Об'єкт класу, який тестується

    @BeforeEach
    void setUp() throws IOException {
        mockWebServer = new MockWebServer(); // Створення MockWebServer
        mockWebServer.start(); // Запуск MockWebServer
        imageDownloader = new HttpImageDownloader(); // Ініціалізація HttpImageDownloader
    }

    @AfterEach
    void tearDown() throws IOException {
        mockWebServer.shutdown(); // Зупинка MockWebServer після кожного тесту
    }

    @Test
    void downloadImage_validUrl_shouldDownloadImage() throws IOException {
        // Імітація успішної відповіді від сервера з кодом 200 та тілом "image data"
        mockWebServer.enqueue(new MockResponse().setResponseCode(200).setBody("image data"));

        // Отримання URL з MockWebServer для завантаження зображення
        String url = mockWebServer.url("/200.jpg").toString();
        imageDownloader.downloadImage(url, 200); // Виклик методу завантаження зображення

        // Перевірка існування завантаженого файлу
        File file = new File("200.jpg");
        assertTrue(file.exists());

        // Видалення завантаженого файлу після тесту
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void downloadImage_invalidUrl_shouldThrowException() {
        // Імітація відповіді від сервера з кодом 404 для неіснуючого зображення
        mockWebServer.enqueue(new MockResponse().setResponseCode(404));

        // Отримання URL з MockWebServer для неіснуючого зображення
        String url = mockWebServer.url("/10000.jpg").toString();
        Exception exception = assertThrows(IOException.class, () -> {
            imageDownloader.downloadImage(url, 10000); // Виклик методу завантаження зображення
        });
        // Перевірка повідомлення винятку
        assertEquals("java.lang.Exception: Image not found for status code: 10000", exception.getMessage());
    }
}
