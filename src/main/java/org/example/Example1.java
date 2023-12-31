package org.example;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Example1 {
    public static void main(String[] args) throws IOException {
        List<Post> posts = new ArrayList<>();
        Document doc = Jsoup.connect("https://4pda.to/").get();
        Elements postTitleElements = doc.getElementsByAttributeValue("itemprop", "url");
        for (Element postTitleElement : postTitleElements) {
            String detailsLink = postTitleElement.attr("href");
            Post post = new Post();
            post.setDetailsLink(detailsLink);
            post.setTitle(postTitleElement.attr("title"));
            Document postDetailsDoc = Jsoup.connect(detailsLink).get();
            try {
                Element authorElement = postDetailsDoc.getElementsByClass("name").first().child(0);
                post.setAuthor(authorElement.text());
                post.setAuthorDetailsLink(authorElement.attr("href"));
                post.setDateOfCreated(postDetailsDoc.getElementsByClass("date").first().text());
            } catch (NullPointerException e) {
                post.setAuthor("Автора нет");
                post.setAuthorDetailsLink("Ссылки нет");
                post.setDateOfCreated("Даты создания нет");
            }
            posts.add(post);
        }
        posts.forEach(System.out::println);
    }

    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class Post {
        String title;
        String detailsLink;
        String author;
        String authorDetailsLink;
        String dateOfCreated;
    }
}
