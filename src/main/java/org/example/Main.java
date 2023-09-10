package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Main {
        public static void main(String[] args) {
            String respJson = get();
            Document document = Jsoup.parse(respJson);
            String h1 = document.select("#message").toString();
            String code = h1.substring((h1.indexOf("encoded\":") + 11), h1.indexOf("\", \"offset")).trim();
            String count = h1.substring((h1.indexOf(", \"offset\":") + 13), h1.indexOf("\"}</s")).trim();
            String result = decrypt(code, Integer.parseInt(count));
            String newJson = String.format("{\"decoded\": \"%s\"}", result);
            System.out.println(post(newJson));
        }

    private static String get() {
        URI uri = URI.create("http://ya.praktikum.fvds.ru:8080/dev-day/task/2");
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(uri)
                .header("Content-Type", "application/json")
                .header("AUTH_TOKEN", "a09dc786-2d73-479d-8a3f-d7ef514dc3f8")
                .build();
        HttpClient client = HttpClient.newHttpClient();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                return null;
            } else {
                return response.body();
            }
        } catch (IOException | InterruptedException e) {
            return null;
        }
    }

    private static String post(String json) {
        URI uri = URI.create("http://ya.praktikum.fvds.ru:8080/dev-day/task/2");
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .uri(uri)
                .header("Content-Type", "application/json")
                .header("AUTH_TOKEN", "a09dc786-2d73-479d-8a3f-d7ef514dc3f8")
                .build();
        HttpClient client = HttpClient.newHttpClient();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                System.out.println(response.body());
                System.out.println("Something went wrong. Status code is: " + response.statusCode());
                return null;
            } else {
                System.out.println(response.body());
                System.out.println(response.statusCode());
                return response.body();
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("There is a problem\n" +
                    "Check the address and try again");
            return null;
        }
    }

    public static String encrypt(String message, int shift) {
        message = message.toLowerCase();
        StringBuilder encryptedMessage = new StringBuilder();

        for (int i = 0; i < message.length(); i++) {
            char ch = message.charAt(i);

            if (Character.isLetter(ch)) {
                char shiftedCh = (char) (((ch - 'a' + shift) % 26) + 'a');
                encryptedMessage.append(shiftedCh);
            } else {
                encryptedMessage.append(ch);
            }
        }

        return encryptedMessage.toString();
    }

    public static String decrypt(String encryptedMessage, int shift) {
        return encrypt(encryptedMessage, 26 - shift);
    }
}