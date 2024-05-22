package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HttpStatusCheckerTest {

    // Тест для перевірки, чи повертає метод правильний URL для валідного статус коду
    @Test
    void getStatusImage_validCode_shouldReturnUrl() {
        HttpStatusChecker checker = new HttpStatusChecker(); // Створення об'єкта HttpStatusChecker
        try {
            // Виклик методу з валідним статус кодом 200 і перевірка результату
            String url = checker.getStatusImage(200);
            assertNotNull(url); // Перевірка, що URL не є null
            assertEquals("https://http.cat/200.jpg", url); // Перевірка, що URL відповідає очікуваному
        } catch (Exception e) {
            fail("Exception should not be thrown for valid status code 200"); // Якщо виникла помилка, тест провалюється
        }
    }

    // Тест для перевірки, чи кидає метод виключення для невалідного статус коду
    @Test
    void getStatusImage_invalidCode_shouldThrowException() {
        HttpStatusChecker checker = new HttpStatusChecker(); // Створення об'єкта HttpStatusChecker
        // Виклик методу з невалідним статус кодом 10000 і перевірка, що кидається виключення
        Exception exception = assertThrows(Exception.class, () -> {
            checker.getStatusImage(10000);
        });
        // Перевірка повідомлення виключення, яке повинно відповідати очікуваному
        assertEquals("Image not found for status code: 10000", exception.getMessage());
    }
}
