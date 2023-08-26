package com.example.financesupermarket;

import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.nodes.Element;
import org.jsoup.select.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GetProducts {
    private String url;
    private String stringJSON;
    private List<String> allProducts = new ArrayList<>();
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

    public List<String> getData() {
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

            Elements query = framePage.select("table[class='NFCCabecalho']");
            Element items_major = query.get(3);
            Element tBody = items_major.child(0);
            Elements items = tBody.children();

            for (Element item : items) {
                String prodCode = item.child(0).text();
                String prodName = item.child(1).text();
                String amount = item.child(2).text();
                String value = item.child(4).text();
                String totalValue = item.child(5).text();

                this.stringJSON = toJSON(prodCode, prodName, amount, value, totalValue);
                this.allProducts.add(this.stringJSON);
            }

            return allProducts;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
