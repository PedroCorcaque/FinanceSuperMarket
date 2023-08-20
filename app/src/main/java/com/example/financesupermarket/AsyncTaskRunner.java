package com.example.financesupermarket;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class AsyncTaskRunner extends AsyncTask<String, Void, String> {
    private String result;
    private String resp;
    ProgressDialog progressDialog;

    protected String doInBackground(String... url) {
        try {
            GetProducts getProducts = new GetProducts(url[0]);

            Thread thread_one = new Thread(new Runnable() {
                @Override
                public void run() {
                    result = getProducts.getData();
                }
            });
            thread_one.start();
            thread_one.join();

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return resp;
    }
}
