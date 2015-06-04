package com.repa.kolesakz;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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


    ListView listview;
    //FragmentTransaction fTrans;
    void ShowCar(CarObject car)
    {
        String actress = car.getTitle();
        //Toast.makeText(getActivity().getApplicationContext(), actress + " selected", Toast.LENGTH_LONG).show();
        MainActivity.SelectedCar=car;
        ShowCarFragment CarFrag = new ShowCarFragment();
        //CarFrag.car=car;
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, CarFrag,"CarFrag")
                .addToBackStack("CarFrag")
                .commit();

    }

    void InitTempCars() {

        if (MainActivity.Cars.size()==0) {
            MainActivity.Cars.clear();
            MainActivity.Cars.add(new CarObject("Загрузка...", "", "", ""));

            new GetHotCarsParser().execute("http://kolesa.kz/");
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setRetainInstance(true);
        LOG_TAG = getString(R.string.log_tag);
        Log.d(LOG_TAG, "Main Fragment onCreateView");
        InitTempCars();

        return inflater.inflate(R.layout.main_fragment, null);
    }

    public void SetAdapter() {
        listview = (ListView) getView().findViewById(R.id.CarsList);
        CarListAdapter adapter = new CarListAdapter(getActivity(), MainActivity.Cars);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                ShowCar(MainActivity.Cars.get(position));
                //Log.d(LOG_TAG, MainActivity.Cars.get(position).getImage(0));

            }
        });

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
        @Override
        protected String doInBackground(String... links) {
            Document doc = null;
            MainActivity.Cars.clear();

            try {
                doc = Jsoup.connect(links[0]).userAgent("Mozilla Firefox").get();
                Element sec = doc.select("section#hot-big").first();
                //if (sec!=null) {Log.d(TAG, ">> "+sec.html());} else {Log.d(TAG, ">> Sec is null");}

                Elements qs = sec.select("div.hover");
                if (qs==null) {Log.d(LOG_TAG, ">> QS is null");}
                  else {Log.d(LOG_TAG, ">> QS size:"+qs.size());}
                int i=0;
                String im;

                for (Element div : qs) {
                    //Log.d(TAG, ">> div id:"+div.className());
                    i++;
                    //if (i>3) {break;}
                    CarObject car = new CarObject("","","","");
                    Element a =div.select("a").first();
                    car.setLink("http://kolesa.kz"+a.attr("href"));

                    Element img =div.select("img").first();
                    im=img.attr("src");
                    Log.d("MyLog", ">>"+im);
                    div.select("img").first().html("");
                    //Bitmap bit=getBitmapFromURL(im);
                    //im="";
                    car.AddImage(im,null);
                    Element top =div.select("span.top").first();
                    top.select("span.hot-top-text").html("");
                    car.setCity(top.text());
                    Element tit =div.select("span.bot").first();
                    car.setPrice(tit.select("nobr").first().text());
                    tit.select("nobr").first().html("");
                    car.setTitle(tit.text());

                    MainActivity.Cars.add(car);
                    Log.d(LOG_TAG, ">>"+car.title+" "+car.getImage(0));
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
            MainActivity.ShowCarsInLog();
            SetAdapter();
            Log.d(LOG_TAG, "Parser done:"+rez);
            if (result != "OK") {
                Log.d(LOG_TAG, "Parser done:"+rez);
            } else {

            }
        }
    }

}
