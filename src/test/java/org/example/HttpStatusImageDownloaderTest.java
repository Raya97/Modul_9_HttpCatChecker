package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HttpStatusImageDownloaderTest {

    // Тест для перевірки завантаження зображення для валідного статус коду
    @Test
    void downloadStatusImage_validCode_shouldDownloadImage() throws Exception {
        HttpStatusChecker checker = mock(HttpStatusChecker.class); // Мок об'єкта HttpStatusChecker
        HttpImageDownloader imageDownloader = mock(HttpImageDownloader.class); // Мок об'єкта HttpImageDownloader

        // Налаштування поведінки мока для повернення URL при виклику методу getStatusImage з кодом 200
        when(checker.getStatusImage(200)).thenReturn("https://http.cat/200.jpg");

        // Створення об'єкта HttpStatusImageDownloader з моками
        HttpStatusImageDownloader statusImageDownloader = new HttpStatusImageDownloader(checker, imageDownloader);
        // Виклик методу для завантаження зображення зі статус кодом 200
        statusImageDownloader.downloadStatusImage(200);

        // Перевірка, що метод downloadImage був викликаний з правильними параметрами
        verify(imageDownloader).downloadImage("https://http.cat/200.jpg", 200);
    }

    // Тест для перевірки, чи кидається виключення для невалідного статус коду
    @Test
    void downloadStatusImage_invalidCode_shouldThrowException() throws Exception {
        HttpStatusChecker checker = mock(HttpStatusChecker.class); // Мок об'єкта HttpStatusChecker
        HttpImageDownloader imageDownloader = mock(HttpImageDownloader.class); // Мок об'єкта HttpImageDownloader

        // Налаштування поведінки мока для кидання виключення при виклику методу getStatusImage з кодом 10000
        when(checker.getStatusImage(10000)).thenThrow(new Exception("Image not found for status code: 10000"));

        // Створення об'єкта HttpStatusImageDownloader з моками
        HttpStatusImageDownloader statusImageDownloader = new HttpStatusImageDownloader(checker, imageDownloader);
        // Виклик методу для завантаження зображення з невалідним статус кодом і перевірка, що кидається виключення
        Exception exception = assertThrows(Exception.class, () -> {
            statusImageDownloader.downloadStatusImage(10000);
        });

        // Перевірка повідомлення виключення, яке повинно відповідати очікуваному значенню
        assertEquals("Image not found for status code: 10000", exception.getMessage());
    }
}
