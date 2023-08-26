package com.example.financesupermarket;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.List;

public class AsyncTaskRunner extends AsyncTask<String, Void, List<String>> {
    private List<String> products;

    protected List<String> doInBackground(String... url) {
        try {
//            GetProducts getProducts = new GetProducts(url[0]);
            GetProducts getProducts = new GetProducts("https://www.sefaz.rs.gov.br/NFCE/NFCE-COM.aspx?p=43230887810735000169651020000536471195752639%7C2%7C1%7C1%7CB2C045B13E83966F776BE72EE9027B4756DED0B0");
            Thread thread_one = new Thread(new Runnable() {
                @Override
                public void run() {
                    products = getProducts.getData();
                }
            });
            thread_one.start();
            thread_one.join();

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return products;
    }
}
