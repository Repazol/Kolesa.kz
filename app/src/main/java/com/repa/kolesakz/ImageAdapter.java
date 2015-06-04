package com.repa.kolesakz;

import android.graphics.Bitmap;
import android.media.Image;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by repa on 01.06.2015.
 */

public class ImageAdapter extends BaseAdapter {
    private int mGalleryItemBackground;
    private Context mContext;
    List<Bitmap> Images = new ArrayList();
    List<String> urls = new ArrayList();

    public ImageAdapter(Context сontext, List<String> data) {
        mContext = сontext;
        urls=data;
        //MainActivity.SelectedCar.images
        //Images=data;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return MainActivity.SelectedCar.images.size()-1;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return MainActivity.SelectedCar.images.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ImageView view = new ImageView(mContext);
        if (view.getPaddingTop()!=20) {
            ImageLoader im = new ImageLoader();
            im.SetImageView(view);
            im.SetPostBitmap(MainActivity.SelectedCar.getImageBitmap(position+1));
            im.SetCarObject(MainActivity.SelectedCar, position+1);
            im.execute(MainActivity.SelectedCar.getImage(position+1));

            view.setPadding(20, 20, 20, 20);
            //view.setLayoutParams(new Gallery.LayoutParams(300, 225));

            view.setLayoutParams(new Gallery.LayoutParams(
                    Gallery.LayoutParams.WRAP_CONTENT, Gallery.LayoutParams.WRAP_CONTENT));

            view.setScaleType(ImageView.ScaleType.FIT_CENTER);
        }
        return view;
    }
}

