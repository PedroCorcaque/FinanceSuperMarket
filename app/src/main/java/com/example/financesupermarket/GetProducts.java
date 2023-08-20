package com.example.financesupermarket;

import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.nodes.Element;
import org.jsoup.select.*;

import java.io.IOException;

public class GetProducts {
    private String url;
    private String stringJSON;
    public GetProducts(String url) {
        this.url = url;
    }

    private String toJSON(String... params) {
        return "{ \"productCode\": " + params[0] + "\", "
                + " \"productName\": " + params[1] + "\", "
                + " \"amount\": " + params[2] + "\", "
                + " \"value\": " + params[3] + "\", "
                + " \"totalValue\": " + params[4] + "\" }";
     }

    public String getData() {
        Document mainPage, framePage;

        try {
            mainPage = Jsoup
                    .connect(this.url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36")
                    .get();

            this.url = mainPage.select("iframe.iframe").attr("src");

            framePage = Jsoup
                    .connect(this.url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36")
                    .get();

            for (Element row : framePage.select("tr[id='Item + 1']")) {
                String prodCode = row.child(0).text();
                String prodName = row.child(1).text();
                String amount = row.child(2).text();
                String value = row.child(4).text();
                String totalValue = row.child(5).text();

                this.stringJSON = toJSON(prodCode, prodName, amount, value, totalValue);
            }

            return this.stringJSON;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
