package com.enjoy02.enjoyfragmentdemo02.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.enjoy02.enjoyfragmentdemo02.R;


public class RetainedFragment extends Fragment
{
    // data object we want to retain
    private Object data;
    // this method is only called once for this fragment
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        // retain this fragment
        setRetainInstance(true);
    }

    public void setData(Bitmap data)
    {
        this.data = data;
    }

    public Object getData()
    {
        return data;
    }
}
