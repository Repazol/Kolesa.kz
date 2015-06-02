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

    public ImageAdapter(Context сontext, List<Bitmap> data) {
        mContext = сontext;
        Images=data;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return Images.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return Images.get(position);
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
        view.setImageBitmap(Images.get(position));
        view.setPadding(20, 20, 20, 20);
        view.setLayoutParams(new Gallery.LayoutParams(300, 225));
        view.setScaleType(ImageView.ScaleType.FIT_CENTER);
        return view;
    }
}

