package com.repa.kolesakz;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
/**
 * Created by repa on 26.05.2015.
 */
public class PartsFragment extends Fragment {
    String LOG_TAG = "";


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LOG_TAG = getString(R.string.log_tag);
        Log.d(LOG_TAG, "Parts Fragment onCreateView");
        return inflater.inflate(R.layout.parts_fragment, null);
    }

}
