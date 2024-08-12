package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

// Simple web spider class, fetch a page, and use found links to further spider
public class WebSpider {
    private final Queue<String> queue = new LinkedList<>();
    private final List<String> visitedURLs = new LinkedList<>();
    private final int maxPages;
    private int count = 0;

    // Spider Constructor
    public WebSpider(String baseURL, int maxPages) {
        this.maxPages = maxPages;
        queue.add(baseURL);
    }

    // Crawling Logic
    public void beginCrawl() {
        while (!queue.isEmpty() && count < maxPages) {
            String url = queue.poll();
            if (url != null && addVisitedURL(url)) {
                parsePage(url);
            }
        }
    }

    // Logic for parsing page
    private void parsePage(String url)   {
        try {
            Document doc = Jsoup.connect(url).get();
            System.out.println("Crawled (" + count++ + "): " + doc.title() + " - " + url);
            enqueueLinks(doc);
        } catch (IOException e) {
            System.err.println("Error crawling " + url + ": " + e.getMessage());
        }
    }

    // Extract and enqueue links from the document
    private void enqueueLinks(Document doc) {
        Elements links = doc.select("a[href]");
        for (Element link : links) {
            String absUrl = link.attr("abs:href");
            if (visitedURLs.size() < maxPages && !containsVisitedURL(absUrl)) {
                queue.add(absUrl);
            }
        }
    }

    // Method to add visited site
    private boolean addVisitedURL(String url) {
        if (visitedURLs.contains(url))
            return false;
        return visitedURLs.add(url);
    }

    // Method to check if a URL has already been visited
    private boolean containsVisitedURL(String url)
    {
        return visitedURLs.contains(url);
    }

}
