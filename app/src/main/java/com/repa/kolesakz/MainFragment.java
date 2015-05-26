package com.repa.kolesakz;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by repa on 26.05.2015.
 */
public class MainFragment extends Fragment {
    String LOG_TAG = "";
    List<CarObject>  Cars = new ArrayList();

    ListView listview;

    void InitTempCars() {
        Cars.clear();
        Cars.add(new CarObject("Загрузка...","","",""));

        new GetHotCarsParser().execute("http://kolesa.kz/");
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LOG_TAG = getString(R.string.log_tag);
        Log.d(LOG_TAG, "Main Fragment onCreateView");
        InitTempCars();

        return inflater.inflate(R.layout.main_fragment, null);
    }
    public void SetAdapter() {
        listview = (ListView) getView().findViewById(R.id.CarsList);
        CarListAdapter adapter = new CarListAdapter(getActivity(), Cars);
        listview.setAdapter(adapter);

    }
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        TextView textView =(TextView) getView().findViewById(R.id.textViewHeader);
        textView.setText("Горячие предложения ");
        SetAdapter();
        Log.d(LOG_TAG, "onActivityCreated");
    }


    class GetHotCarsParser extends AsyncTask<String, Void, String> {
        String rez="OK";
        //public List<CarObject> Cars = new ArrayList();
        public Bitmap getBitmapFromURL(String src) {
            try {
                Log.e("src",src);
                URL url = new URL(src);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                //Log.e("Bitmap","returned");
                return myBitmap;
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("Exception",e.getMessage());
                return null;
            }
        }
        @Override
        protected String doInBackground(String... links) {
            Document doc = null;
            Cars.clear();

            try {
                doc = Jsoup.connect(links[0]).userAgent("Mozilla").get();
                Element sec = doc.select("section#hot-big").first();
                //if (sec!=null) {Log.d(TAG, ">> "+sec.html());} else {Log.d(TAG, ">> Sec is null");}

                Elements qs = sec.select("div.hover");
                if (qs==null) {Log.d(LOG_TAG, ">> QS is null");}
                  else {Log.d(LOG_TAG, ">> QS size:"+qs.size());}

                for (Element div : qs) {
                    //Log.d(TAG, ">> div id:"+div.className());
                    CarObject car = new CarObject("","","","");
                    Element a =div.select("a").first();
                    car.setLink(a.attr("href"));
                    Element img =div.select("img").first();
                    String im=img.attr("src");
                    Bitmap bit=getBitmapFromURL(im);
                    car.AddImage(im,bit);
                    Element top =div.select("span.top").first();
                    top.select("span.hot-top-text").html("");
                    car.setCity(top.text());
                    Element tit =div.select("span.bot").first();
                    car.setPrice(tit.select("nobr").first().text());
                    tit.select("nobr").first().html("");
                    car.setTitle(tit.text());
                    Cars.add(car);
                    Log.d(LOG_TAG, ">>"+car.title+" "+car.price+" "+car.city);
                    //Log.d(LOG_TAG, ">>"+car.getMainImage());
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
            SetAdapter();
            Log.d(LOG_TAG, "Parser done:"+rez);
            if (result != "OK") {
                Log.d(LOG_TAG, "Parser done:"+rez);
            } else {

            }
        }
    }

}