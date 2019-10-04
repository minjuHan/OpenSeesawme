package com.example.openseesawme;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class TaskMethod extends AsyncTask<String, Void, String> {

    private final String url;
    private final String sId;
    private final String encodeType;

    /**
     *
     * @param url url for request
     * @param sId key and value (ex. "key=1234";)
     * @param encodeType ex. UTF-8, EUC-KR
     */
    public TaskMethod(String url, String sId, String encodeType) {
        this.url = url;
        this.sId = sId;
        this.encodeType = encodeType;
    }

    private String sResult;

    @Override
    protected String doInBackground(String[] sId) {
        String str;
        try {
            URL url = new URL(this.url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestMethod("POST");
            conn.connect();
            OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream(), encodeType);
            osw.write(this.sId);
            osw.flush();
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), encodeType);
                BufferedReader reader = new BufferedReader(tmp);
                StringBuilder stringBuilder = new StringBuilder();
                while ((str = reader.readLine()) != null) {
                    stringBuilder.append(str);
                }
                sResult = stringBuilder.toString();

            } else {
                Log.i("통신결과", conn.getResponseMessage() + conn.getResponseCode() + "/" + conn.getErrorStream());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sResult;
    }
}

