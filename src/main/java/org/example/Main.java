package org.example;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Main {
        public static void main(String[] args) throws IOException, InterruptedException {
            // укажите URL-адрес ресурса
            URI uri = URI.create("https://ya.ru");

            HttpRequest.Builder requestBuilder = HttpRequest.newBuilder();
            // создайте объект, описывающий HTTP-запрос
            HttpRequest request = requestBuilder
                    .GET()    // указываем HTTP-метод запроса
                    .uri(uri) // указываем адрес ресурса
                    .version(HttpClient.Version.HTTP_1_1) // указываем версию протокола HTTP
                    .header("Accept", "text/html") // указываем заголовок Accept
                    .build(); // заканчиваем настройку и создаём ("строим") HTTP-запрос ;

            // создайте HTTP-клиент с настройками по умолчанию
            HttpClient client = HttpClient.newHttpClient();
            ;

            // получите стандартный обработчик тела запроса
            // с конвертацией содержимого в строку
            HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
            ;

            // отправьте запрос
            HttpResponse<String> response = client.send(request, handler);

            System.out.println("Код состояния : " + response.statusCode()); // выведите код состояния
        }
}