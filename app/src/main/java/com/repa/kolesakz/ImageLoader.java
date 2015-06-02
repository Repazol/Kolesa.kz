package com.repa.kolesakz;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by repa on 02.06.2015.
 */
public class ImageLoader extends AsyncTask<String, Void, String> {


    String rez="OK";
    String LOG_TAG="MyLog";
    Bitmap PostBitmap;
    ImageView imageView;
    CarObject car;
    int imageIndex;

    public void SetImageView (ImageView iV)
    {
        imageView=iV;
    }
    public void SetPostBitmap (Bitmap b)
    {
        PostBitmap=b;
    }
    public void SetCarObject (CarObject c, int index)
    {
        car=c;
        imageIndex=index;
    }

    //public List<CarObject> Cars = new ArrayList();
    public Bitmap getBitmapFromURL(String src) {
        Bitmap myBitmap=null;
        try {
            if (!src.equals("")) {
                URL url = new URL(src);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                myBitmap = BitmapFactory.decodeStream(input);
            }
            //Log.e("Bitmap","returned");
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Exception", e.getMessage());
            Log.e("URL:", src);
            return null;
        }
    }
    @Override
    protected String doInBackground(String... links) {


        rez="OK";
        try {
            if (PostBitmap==null) {
                PostBitmap = getBitmapFromURL(links[0]);
            }

        } catch (Exception e) {
            e.printStackTrace();
            rez=e.getMessage();
            Log.d(LOG_TAG, "Post error:"+rez);
        }
        Log.d(LOG_TAG, "Parser done");
        return rez;
    }

    @Override
    protected void onPostExecute(String result) {
        Log.d(LOG_TAG, "Parser done:"+rez);
        if (result == "OK") {
            if (imageView!=null) {
                imageView.setImageBitmap(PostBitmap);
            }
            if (car!=null&&car.bitmaps.size()>imageIndex) {
                car.bitmaps.set(imageIndex, PostBitmap);
            }
        }
    }
}


