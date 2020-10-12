package com.example.javaadvanced.ui.RecyclerView.MyRecyclerView;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.javaadvanced.R;

public class MyRecyclerViewActivity extends AppCompatActivity {
    private String TAG = "MyRecyclerViewActivity";

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myrecyclerview);
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setAdapter(new MyAdapter(this));

    }

    public class MyAdapter extends RecyclerView.Adapter {
        private int mCount = 500000;

        private int ItemViewType_COUNT = 2;//共有的itemview类型
        private int ItemViewType_FIRST = 0;
        private int ItemViewType_SECOND = 1;

        private LayoutInflater layoutInflater;
        private int height;

        public MyAdapter(Context context) {
            Resources resource = context.getResources();
            layoutInflater = LayoutInflater.from(context);
            //只是简易版的RecyclerView，为了方便，每个itemveiw的高度固定
            height = resource.getDimensionPixelSize(R.dimen.table_height);

        }

        @Override
        public View onCreateViewHolder(int position, View convertView, ViewGroup parent) {
            if (getItemViewType(position) == ItemViewType_FIRST) {
                convertView = layoutInflater.inflate(R.layout.item_image, parent, false);
            } else {
                convertView = layoutInflater.inflate(R.layout.item_text, parent, false);
            }
            Log.i(TAG, "onCreateViewHolder(), position=" + position + " ,convertView=" + convertView);
            TextView textView = convertView.findViewById(R.id.tv);
            textView.setText("第" + position + "行");
            return convertView;
        }

        @Override
        public View onBindViewHolder(int position, View convertView, ViewGroup parent) {
            Log.i(TAG, "onBindViewHolder(), position=" + position + " ,convertView=" + convertView);
            TextView textView = convertView.findViewById(R.id.tv);
            textView.setText("第" + position + "行");
            return convertView;
        }

        @Override
        public int getItemViewType(int position) {
            if (position == 0) {//第一条数据是单独的类型，比如朋友圈的第一条数据是背景墙
                return ItemViewType_FIRST;
            }
            return ItemViewType_SECOND;
        }

        /**
         * 几种类型的ItemView
         *
         * @return
         */
        @Override
        public int getViewTypeCount() {
            return ItemViewType_COUNT;
        }

        @Override
        public int getCount() {
            return mCount;
        }

        @Override
        public int getHeight(int position) {
            return height;
        }
    }


}
