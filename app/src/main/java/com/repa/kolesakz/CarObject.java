package com.repa.kolesakz;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by repa on 26.05.2015.
 */
public class CarObject {
    public String title;
    public String link;
    public String city;
    public String price;
    public ArrayList<String> images = new ArrayList();
    public ArrayList<Bitmap> bitmaps = new ArrayList();

    public CarObject(String title, String link, String city, String price) {
        this.title = title;
        this.link = link;
        this.city = city;
        this.price = price;
    }

    public void AddImage (String im, Bitmap bitmap) {

        images.add(im);
        bitmaps.add(bitmap);
    }
    public String getMainImage() {
        String t="";
        if (images.size()>0)
        {
          t=images.get(0);
        }
        return t;
    }
    public Bitmap getMainImageBitmap() {
        Bitmap b;
        if (bitmaps.size()>0)
        {
            b=bitmaps.get(0);
        } else {b=null;}
        return b;
    }

    public int getImagesCount() {
        return images.size();
    }

    public String getImage(int index) {
        String t="";
        if (images.size()>index)
        {
            t=images.get(index);
        }
        return t;
    }

    public Bitmap getImageBitmap(int index) {
        Bitmap b;
        if (bitmaps.size()>index)
        {
            b=bitmaps.get(index);
        } else {b=null;}
        return b;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
