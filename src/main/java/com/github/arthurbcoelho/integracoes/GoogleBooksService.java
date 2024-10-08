package com.github.arthurbcoelho.integracoes;

import com.github.arthurbcoelho.domain.entity.Livro;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class GoogleBooksService {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    @Value("${google.books.api_key}")
    private String apiKey;
    @Value("${google.books.base_url}")
    private String baseUrl;

    public String getLivrosByTitulo(String rawTitulo) throws IOException, InterruptedException {
        if (StringUtils.isNotBlank(rawTitulo)) {
            String[] splittedTitulo = rawTitulo.split("\\s+");
            final String URL = this.baseUrl + "volumes?q=intitle:" + StringUtils.join(splittedTitulo, '+')
                    + "&key=" + this.apiKey;

            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create(URL)).GET().build();

            return httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString()).body();
        }
        return null;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void buscaInfoByISBN10() throws IOException, InterruptedException {
        Livro livro = Livro.builder().isbn("8532511015").build();
        if (livro != null) {
            final String URL = this.baseUrl + "volumes?q=isbn:" + livro.getIsbn() + "&key=" + this.apiKey;

            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create(URL)).GET().build();

            String rawResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString()).body();

            JsonObject jsonObject = new Gson().fromJson(rawResponse, JsonObject.class);

            if (jsonObject.get("items") != null) {
                JsonArray items = jsonObject.get("items").getAsJsonArray();

                if (items.get(0) != null) {
                    JsonObject volumeInfo = items.get(0).getAsJsonObject().get("volumeInfo").getAsJsonObject();

//                    livro.setDataPublicacao(LocalDate.parse(volumeInfo.get("publishedDate").getAsString(),
//                            DATE_TIME_FORMATTER));
                    livro.setAutor(volumeInfo.get("authors").getAsJsonArray().get(0).getAsString());
                    livro.setTitulo(volumeInfo.get("title").getAsString());
                }
            }
        }
    }
}
