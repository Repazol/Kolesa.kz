package com.repa.kolesakz;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
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
    int layoutResourceId;
    List<CarObject> list;
    public CarListAdapter(Activity context, List<CarObject> list) {
        super(context, R.layout.cars_list_row, list);
        this.context = context;
        this.list = list;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        TextView TitleView;
        TextView PriceView;
        TextView CityView;
        ImageView imageView;

        //if (convertView == null) {
            LayoutInflater inflator = context.getLayoutInflater();
            view = inflator.inflate(R.layout.cars_list_row, null);

            TitleView = (TextView) view.findViewById(R.id.textViewTitle);
            PriceView = (TextView) view.findViewById(R.id.textViewPrice);
            CityView = (TextView) view.findViewById(R.id.textViewCity);
            imageView = (ImageView) view.findViewById(R.id.imageView);
            TitleView.setText(list.get(position).getTitle());
            PriceView.setText(list.get(position).getPrice());
            CityView.setText(list.get(position).getCity());
            Bitmap b = list.get(position).getMainImageBitmap();
            if (b!=null) {
                imageView.setImageBitmap(b);
            }
        //} else {            view = convertView;        }
        return view;
    }
}
