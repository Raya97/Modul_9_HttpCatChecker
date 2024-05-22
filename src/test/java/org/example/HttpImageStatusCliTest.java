package org.example;

import com.github.stefanbirkner.systemlambda.SystemLambda;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HttpImageStatusCliTest {

    private ByteArrayOutputStream outputStreamCaptor; // Потік для зчитування виводу в консоль
    private HttpStatusImageDownloader downloader; // Об'єкт класу, який тестується

    @BeforeEach
    void setUp() {
        downloader = mock(HttpStatusImageDownloader.class); // Ініціалізація mock-об'єкта HttpStatusImageDownloader
        outputStreamCaptor = new ByteArrayOutputStream(); // Створення потоку для захоплення виводу
        System.setOut(new PrintStream(outputStreamCaptor)); // Перенаправлення системного виходу в потік
    }

    @Test
    void askStatus_validCode_shouldDownloadImage() throws Exception {
        // Імітація введення користувачем коду "200"
        SystemLambda.withTextFromSystemIn("200\n").execute(() -> {
            HttpImageStatusCli cli = new HttpImageStatusCli(downloader); // Створення об'єкта CLI
            cli.askStatus(); // Виклик методу для запиту статусу
        });

        verify(downloader).downloadStatusImage(200); // Перевірка виклику методу завантаження зображення з кодом 200
        assertTrue(outputStreamCaptor.toString().contains("Image downloaded for status code: 200")); // Перевірка виводу в консоль
    }

    @Test
    void askStatus_invalidCode_shouldPrintErrorMessage() throws Exception {
        // Імітація винятку при спробі завантажити зображення для неіснуючого коду
        doThrow(new Exception("No image")).when(downloader).downloadStatusImage(10000);

        // Імітація введення користувачем коду "10000"
        SystemLambda.withTextFromSystemIn("10000\n").execute(() -> {
            HttpImageStatusCli cli = new HttpImageStatusCli(downloader); // Створення об'єкта CLI
            cli.askStatus(); // Виклик методу для запиту статусу
        });

        verify(downloader).downloadStatusImage(10000); // Перевірка виклику методу завантаження зображення з кодом 10000
        assertTrue(outputStreamCaptor.toString().contains("There is no image for HTTP status 10000")); // Перевірка виводу в консоль
    }

    @Test
    void askStatus_invalidInput_shouldPrintErrorMessage() throws Exception {
        // Імітація введення некоректного значення "test"
        SystemLambda.withTextFromSystemIn("test\n").execute(() -> {
            HttpImageStatusCli cli = new HttpImageStatusCli(downloader); // Створення об'єкта CLI
            cli.askStatus(); // Виклик методу для запиту статусу
        });

        assertTrue(outputStreamCaptor.toString().contains("Please enter a valid number")); // Перевірка виводу в консоль
    }
}
