package org.example;

import java.util.Scanner;
import org.example.WebSpider;

public class Main {

    // Simple web spider app, provide a URL and the program will obtain new links until the limit has been reached.
    public static void main(String[] args) {
        String exampleURL = "https://en.wikipedia.org/wiki/Game_of_Thrones";

        System.out.println("Enter a URL to spider, the following will be used: " + exampleURL);
        Scanner scannerOBJ = new Scanner (System.in);
        String userURL = scannerOBJ.nextLine();

        if (userURL != "")
            exampleURL = userURL;

        WebSpider spider = new WebSpider(exampleURL, 50);
        spider.beginCrawl();
    }
}