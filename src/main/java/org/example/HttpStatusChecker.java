package org.example;

import okhttp3.Response;

public class HttpStatusChecker {

    private static final String BASE_URL = "https://http.cat/";

    // Метод для отримання посилання на зображення за кодом статусу HTTP.
    public String getStatusImage(int code) throws Exception {
        String url = BASE_URL + code + ".jpg";
        Response response = HttpUtils.executeGetRequest(url);

        try (response) {
            HttpUtils.checkResponseStatus(response, code);
            return url;
        }
    }

    public static void main(String[] args) {
        HttpStatusChecker checker = new HttpStatusChecker();
        try {
            System.out.println(checker.getStatusImage(200)); // повинно вивести URL
            System.out.println(checker.getStatusImage(10000)); // повинно викинути виключення
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
