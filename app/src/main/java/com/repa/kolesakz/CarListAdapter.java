package com.repa.kolesakz;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by repa on 26.05.2015.
 */
public class CarListAdapter extends ArrayAdapter<CarObject> {

    private final Activity context;
    //int layoutResourceId;
    //List<CarObject> list;
    public CarListAdapter(Activity context, List<CarObject> list) {
        super(context, R.layout.cars_list_row, list);
        this.context = context;
        //this.list = list;
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return MainActivity.Cars.size();
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        TextView TitleView;
        TextView PriceView;
        TextView CityView;
        ImageView imageView;

        LayoutInflater inflator = context.getLayoutInflater();
        view = inflator.inflate(R.layout.cars_list_row, null);
            TitleView = (TextView) view.findViewById(R.id.textViewTitle);
            PriceView = (TextView) view.findViewById(R.id.textViewPrice);
            CityView = (TextView) view.findViewById(R.id.textViewCity);
            imageView = (ImageView) view.findViewById(R.id.imageView);
        if (TitleView.getText().equals("Title")) {
            TitleView.setText(MainActivity.Cars.get(position).getTitle());
            PriceView.setText(MainActivity.Cars.get(position).getPrice());
            CityView.setText(MainActivity.Cars.get(position).getCity());

            String url;
            CarObject c = MainActivity.Cars.get(position);
            url = c.getImage(0);

            ImageLoader im = new ImageLoader();
            im.SetImageView(imageView);
            im.SetPostBitmap(MainActivity.Cars.get(position).getImageBitmap(0));
            im.SetCarObject(MainActivity.Cars.get(position), 0);
            im.execute(url);
        }
        return view;
    }
}
