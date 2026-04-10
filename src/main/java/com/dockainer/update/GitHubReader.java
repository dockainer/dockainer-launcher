package com.dockainer.update;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class GitHubReader {

    private static final String GITHUB_API = "https://api.github.com/repos/dockainer/dockainer-launcher/tags";

    public static List<String> getLatestTags() {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(GITHUB_API))
                .header("Accept", "application/vnd.github.v3+json")
                .header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/147.0.0.0 Safari/537.36")
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                System.out.println("Failed to fetch tags from GitHub: " + response.statusCode());
                return List.of();
            }

            var type = new TypeToken<List<GithubTag>>() {}.getType();
            List<GithubTag> tags = new Gson().fromJson(response.body(), type);

            return tags.stream().map(GithubTag::name).toList();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace(System.err);
            return List.of();
        }
    }
}
