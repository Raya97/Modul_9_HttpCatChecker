package org.example;

public class HttpStatusImageDownloader {

    private final HttpStatusChecker checker;
    private final HttpImageDownloader imageDownloader;

    public HttpStatusImageDownloader(HttpStatusChecker checker, HttpImageDownloader imageDownloader) {
        this.checker = checker;
        this.imageDownloader = imageDownloader;
    }

    public HttpStatusImageDownloader() {
        this(new HttpStatusChecker(), new HttpImageDownloader());
    }

    // Метод для завантаження зображення за кодом статусу.
    public void downloadStatusImage(int code) throws Exception {
        // Отримання URL зображення за кодом статусу.
        String imageUrl = checker.getStatusImage(code);
        // Завантаження зображення.
        imageDownloader.downloadImage(imageUrl, code);
    }
}
