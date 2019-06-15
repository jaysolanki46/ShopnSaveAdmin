package com.example.jayso.shopnsaveadmin.com.example.jayso.shopnsaveadmin.model;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Base64;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class UploadImage extends AsyncTask<Void, Void, Void> {

    Bitmap image;
    String name;
    private static final String SERVER_ADDRESS = "http://www.solankiinfosolutions.com/";

    public UploadImage(Bitmap image, String name) {
        this.image = image;
        this.name = name;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        //Toast
    }

    @Override
    protected Void doInBackground(Void... voids) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);

        ArrayList<NameValuePair> dataSend = new ArrayList<>();
        dataSend.add(new BasicNameValuePair("image", encodedImage));
        dataSend.add(new BasicNameValuePair("name", name));

        HttpParams httpParams = getHttpRequestParams();
        HttpClient client = new DefaultHttpClient(httpParams);
        HttpPost post = new HttpPost(SERVER_ADDRESS + "SavePicture.php");

        try{
            post.setEntity(new UrlEncodedFormEntity(dataSend));
            client.execute(post);
        }catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private HttpParams getHttpRequestParams() {
        HttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, 1000* 30);
        HttpConnectionParams.setSoTimeout(httpParams, 1000 * 30);
        return httpParams;
    }
}
