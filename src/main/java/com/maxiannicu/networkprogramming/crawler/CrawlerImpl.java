package com.maxiannicu.networkprogramming.crawler;

import com.maxiannicu.networkprogramming.http.WebClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * Created by Nicu Maxian on 3/30/2017.
 */
public class CrawlerImpl implements Crawler {
    private final WebClient webClient;
    private String baseUrl;

    @Inject
    public CrawlerImpl(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public Set<String> crawle(String baseUrl, int depth) {
        this.baseUrl = baseUrl;

        Set<String> paths = new HashSet<>();
        Queue<String> queue = new LinkedList<>();
        queue.add(baseUrl);

        for (int i = 0; i < depth; i++) {
            System.out.printf("Scanning level %d\n",i);
            Set<String> scan = scan(queue);
            queue.addAll(scan);
            paths.addAll(scan);
            System.out.printf("Crawler found %d on level %d\n",scan.size(),i);
        }

        return paths;
    }

    public Set<String> scan(Queue<String> queue) {
        Set<String> set = new HashSet<>();
        while (!queue.isEmpty()) {
            String url = queue.poll();
            try {
                String content = webClient.get(url);
                crawle(url,set, content);
            } catch (IOException e) {

            }
        }

        return set;
    }

    private void crawle(String url,Set<String> set, String content) {
        Document parse = Jsoup.parse(content);
        Elements aElements = parse.select("a[href]");
        for (Element element : aElements) {
            String cleanUrl = getCleanUrl(url,element);
            if (cleanUrl != null && cleanUrl.startsWith(baseUrl)) {
                set.add(cleanUrl);
            }
        }
    }

    private String getCleanUrl(String url,Element element) {
        String href = element.attr("href").toLowerCase();
        try {
            if (!href.startsWith("http")) {
                if (href.contains(":"))
                    throw new URISyntaxException(href, "Bad");
                if (href.startsWith("/"))
                    href = String.format("%s%s", baseUrl, href);
                else
                    href = String.format("%s%s",url,href);
            }
        } catch (URISyntaxException e) {
            return null;
        }
        return getUrlWithoutQuery(href);
    }

    private String getUrlWithoutQuery(String url){
        try {
            URI uri = new URI(url);
            return String.format("%s://%s/%s",uri.getScheme(),uri.getHost(),uri.getPath());
        } catch (URISyntaxException e) {
            return null;
        }
    }
}
