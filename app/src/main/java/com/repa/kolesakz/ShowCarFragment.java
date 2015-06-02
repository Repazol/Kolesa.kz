package com.repa.kolesakz;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.ImageView;
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
 * Created by repa on 27.05.2015.
 */
public class ShowCarFragment extends Fragment {
    String LOG_TAG = "";
    String Description="";
    Spanned DescriptionSpanned;
    Bitmap MainImage;
    List<Bitmap>  Images = new ArrayList();
    public CarObject car;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        Log.d(LOG_TAG, "Show car Fragment onCreate");
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LOG_TAG = getString(R.string.log_tag);
        Log.d(LOG_TAG, "Show Car Fragment onCreateView");
        return inflater.inflate(R.layout.showcar_fragment, null);
    }
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(LOG_TAG, "Car >>"+car.getTitle());
        TextView title = (TextView) getView().findViewById(R.id.textViewCarTitle);
        title.setText(car.getTitle()+" "+car.getPrice());
        ImageView mImage =(ImageView) getView().findViewById(R.id.imageViewCar);
        mImage.setImageBitmap(car.getMainImageBitmap());
        TextView note = (TextView) getView().findViewById(R.id.textViewAbout);
        note.setText("Загрузка...");

        new CarParser().execute(car.link);

        Log.d(LOG_TAG, "Show Car Fragment onActivityCreated");
    }

    class CarParser extends AsyncTask<String, Void, String> {
        String rez="OK";
        //public List<CarObject> Cars = new ArrayList();
        public Bitmap getBitmapFromURL(String src) {
            try {
                //Log.e("src",src);
                //Log.d(LOG_TAG, "getBitmapFromURL:"+src);
                URL url = new URL(src);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                //Log.e("Bitmap","returned");
                // TO DO Load all images

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
            try {
                doc = Jsoup.connect(links[0]).userAgent("Mozilla").get();
                Element desc = doc.select("div.description-body").first();
                Element tel = doc.select("span#ya_share1").first();
                String t = tel.attr("data-desc");
                desc.select("dt").before("<b>");
                desc.select("dt").append(":</b>");
                desc.select("dd").append("<br>");
                desc.select("div.phones-box").html("Телефон: "+t+"<br>");

                DescriptionSpanned=Html.fromHtml(desc.html(), null, null);
                Log.d(LOG_TAG, "Description:"+Description);
                Element mimage = doc.select("a#bigPicLink").first();
                //car.images.add(mimage.attr("href"));
                MainImage=getBitmapFromURL(mimage.attr("href"));
                //Images.add(MainImage);
                int n=0;

                Elements img = doc.select("img[itemprop=image]");
                for (Element im : img) {
                    if (n>0) {
                        Images.add(getBitmapFromURL(im.attr("src")));
                    }
                    n++;

                }
                n=0;
                Elements linksel = doc.select("a[class^=photo]");
                for (Element l : linksel) {
                    if (n>0) {
                        car.images.add(l.attr("href"));
                    }
                    n++;
                    Log.d(LOG_TAG, " mImage:" + l.attr("href"));
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
            ImageView mImage =(ImageView) getView().findViewById(R.id.imageViewCar);
            if (MainImage!=null) {
                mImage.setImageBitmap(MainImage);
            }

            TextView note = (TextView) getView().findViewById(R.id.textViewAbout);
            note.setText(DescriptionSpanned);

            Gallery gal = (Gallery) getView().findViewById(R.id.gallery);
            gal.setAdapter(new ImageAdapter(getActivity().getApplicationContext(),Images));
            gal.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View v,
                                        int position, long id) {

                    ImageView mImag =(ImageView) getView().findViewById(R.id.imageViewCar);
                    String fn = car.getImage(position);
                    Log.d(LOG_TAG, "Try load image:"+fn);
                    if (!fn.equals("")) {
                        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                        StrictMode.setThreadPolicy(policy);
                        mImag.setImageBitmap(getBitmapFromURL(fn));
                    }

                }
            });


            if (result != "OK") {
                Log.d(LOG_TAG, "Parser done:"+rez);
            } else {

            }
        }
    }

}
