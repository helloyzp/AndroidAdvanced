package com.enjoy02.enjoyfragmentdemo02.activity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import com.enjoy02.enjoyfragmentdemo02.R;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * TODO: Fragment重叠异常
 */
public class SaveDataActivity extends ListActivity {

    private static final String TAG = "Zero";
    private ListAdapter mAdapter;
    private ArrayList<String> mDatas;
    private DialogFragment mLoadingDialog;
    private LoadDataAsyncTask mLoadDataAsyncTask;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate");
        initData(savedInstanceState);
    }

    /**
     * 初始化数据
     */
    private void initData(Bundle savedInstanceState)
    {
        if (savedInstanceState != null)
            mDatas = savedInstanceState.getStringArrayList("mDatas");

        if (mDatas == null)
        {
//            mLoadingDialog = new LoadingDialog();
//            mLoadingDialog.show(getFragmentManager(), "LoadingDialog");
            mLoadDataAsyncTask = new LoadDataAsyncTask();
            mLoadDataAsyncTask.execute();

        } else
        {
            initAdapter();
        }

    }

    /**
     * 初始化适配器
     */
    private void initAdapter()
    {
        mAdapter = new ArrayAdapter<String>(
                SaveDataActivity.this,
                android.R.layout.simple_list_item_1, mDatas);
        setListAdapter(mAdapter);
    }

    @Override
    protected void onRestoreInstanceState(Bundle state)
    {
        super.onRestoreInstanceState(state);
        Log.e(TAG, "onRestoreInstanceState");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        Log.e(TAG, "onSaveInstanceState");
        outState.putSerializable("mDatas", mDatas);

    }

    /**
     * 模拟耗时操作
     *
     * @return
     */
    private ArrayList<String> generateTimeConsumingDatas()
    {
        try
        {
            Thread.sleep(2000);
        } catch (InterruptedException e)
        {
        }
        return new ArrayList<String>(Arrays.asList("通过Fragment保存大量数据",
                "onSaveInstanceState保存数据",
                "getLastNonConfigurationInstance已经被弃用", "RabbitMQ", "Hadoop",
                "Spark"));
    }

    private class LoadDataAsyncTask extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... params)
        {
            mDatas = generateTimeConsumingDatas();
            return null;
        }

        @Override
        protected void onPostExecute(Void result)
        {
            mLoadingDialog.dismiss();
            initAdapter();
        }
    }

    @Override
    protected void onDestroy()
    {
        Log.e(TAG, "onDestroy");
        super.onDestroy();
    }

}
