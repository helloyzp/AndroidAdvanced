package com.enjoy02.enjoyfragmentdemo02.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import com.enjoy02.enjoyfragmentdemo02.BaseFragment;
import com.enjoy02.enjoyfragmentdemo02.R;
import com.enjoy02.enjoyfragmentdemo02.arch.FragmentObserver;

/**
 * TODO: getActivity==null
 */
public class MyDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_login, null);
        builder.setView(view)
                .setPositiveButton("登录",
                        new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int id)
                            {
                            }
                        }).setNegativeButton("取消", null);
        return builder.create();
    }


    public static DialogFragment newIntance() {
        MyDialogFragment fragment = new MyDialogFragment();
        return fragment;
    }


}
