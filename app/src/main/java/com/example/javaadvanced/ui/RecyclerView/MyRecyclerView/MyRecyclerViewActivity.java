package com.example.javaadvanced.ui.RecyclerView.MyRecyclerView;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.load.engine.Resource;
import com.example.javaadvanced.R;

public class MyRecyclerViewActivity extends AppCompatActivity {

    private int mCount = 500000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myrecyclerview);

    }

    public class MyAdapter extends RecyclerView.Adapter {

        private LayoutInflater layoutInflater;
        private int height;

        public MyAdapter(Context context) {
            Resources resource = context.getResources();
            layoutInflater = LayoutInflater.from(context);
            height = resource.getDimensionPixelSize(R.dimen.table_height);

        }

        @Override
        public View onCreateViewHolder(int position, View convertView, ViewGroup parent) {
            convertView = layoutInflater.inflate(R.layout.item_text, parent, false);
            return convertView;
        }

        @Override
        public View onBindViewHolder(int position, View convertView, ViewGroup parent) {
            TextView textView = convertView.findViewById(R.id.text);
            textView.setText("第" + position + "行");
            return convertView;
        }

        @Override
        public int getItemViewType(int position) {
            return 0;
        }

        @Override
        public int getViewTypeCount() {
            return 1;//一种类型的item
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
