package org.example;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Main {
        public static void main(String[] args) throws IOException, InterruptedException {
            String json = "{\"name\":\"КЕС\",\"gitHubUrl\":\"https://github.com/Chernosmaga/developer-day\"," +
                    "\"participants\":[{\"email\":\"sergeychernosmaga1997@ya.ru\",\"cohort\":\"java_25\",\"firstName\"" +
                    ":\"Сергей\",\"lastName\":\"Черносмага\"},{\"email\":\"kirill.mikhailov.d@yandex.ru\",\"cohort\":" +
                    "\"java_26\",\"firstName\":\"Кирилл\",\"lastName\":\"Михайлов\"},{\"email\":\"gks-08@mail.ru\"," +
                    "\"cohort\":\"java_21\",\"firstName\":\"Евгений\",\"lastName\":\"Семененко\"}]}";

            URI uri = URI.create("http://ya.praktikum.fvds.ru:8080/dev-day/register");
            HttpRequest request = HttpRequest.newBuilder()
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .uri(uri)
                    .header("Content-Type", "application/json")
                    .header("MAIN_ANSWER", "42")
                    .build();
            HttpClient client = HttpClient.newHttpClient();
            try {
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                if (response.statusCode() != 200) {
                    System.out.println(response.body());
                    System.out.println("Something went wrong. Status code is: " + response.statusCode());
                } else {
                    System.out.println(response.statusCode());
                }
            } catch (IOException | InterruptedException e) {
                System.out.println("There is a problem\n" +
                        "Check the address and try again");
            }
        }
}