package org.example;

import java.util.Scanner;

public class HttpImageStatusCli {

    private final HttpStatusImageDownloader downloader;

    // Конструктор за замовчуванням, який ініціалізує downloader новим екземпляром HttpStatusImageDownloader
    public HttpImageStatusCli() {
        this.downloader = new HttpStatusImageDownloader();
    }

    // Конструктор, який приймає downloader як параметр, що дозволяє використовувати мок об'єктів у тестах
    public HttpImageStatusCli(HttpStatusImageDownloader downloader) {
        this.downloader = downloader;
    }

    // Метод для виведення привітання користувача
    private void greetUser() {
        System.out.println("Welcome to the HTTP Status Image Downloader!");
    }

    // Метод для виведення прощання з користувачем
    private void farewellUser() {
        System.out.println("Thank you for using the HTTP Status Image Downloader. Goodbye!");
    }

    // Метод для запиту коду статусу у користувача та завантаження відповідного зображення
    public void askStatus() {
        greetUser(); // Виклик привітання

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter HTTP status code: ");
        String input = scanner.nextLine();  // Зчитування введення користувача
        try {
            int code = Integer.parseInt(input); // Перетворення введеного рядка в ціле число
            try {
                downloader.downloadStatusImage(code); // Спроба завантажити зображення для введеного коду статусу
                System.out.println("Image downloaded for status code: " + code);
            } catch (Exception e) {
                // Виведення повідомлення, якщо зображення для введеного коду статусу не знайдено
                System.out.println("There is no image for HTTP status " + code);
            }
        } catch (NumberFormatException e) {
            // Виведення повідомлення, якщо введене значення не є числом
            System.out.println("Please enter a valid number");
        }

        farewellUser(); // Виклик прощання
    }

    // Головний метод для запуску CLI
    public static void main(String[] args) {
        HttpImageStatusCli cli = new HttpImageStatusCli();
        cli.askStatus();
    }
}
