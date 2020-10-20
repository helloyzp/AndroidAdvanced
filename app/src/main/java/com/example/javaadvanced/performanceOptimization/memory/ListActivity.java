package com.example.javaadvanced.performanceOptimization.memory;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.javaadvanced.R;

/**
 * 演示内存抖动的Activity
 */
public class ListActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        RecyclerView rv = findViewById(R.id.rv);
        rv.setAdapter(new MyAdapter(this));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("a","a");
    }


    static class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

        private LayoutInflater layoutInflater;

        public MyAdapter(Context context) {
            layoutInflater = LayoutInflater.from(context);
        }

        @NonNull
        @Override
        public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = layoutInflater.inflate(R.layout.rv_item, viewGroup, false);
            return new MyAdapter.MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder myViewHolder, int i) {

        }

        @Override
        public int getItemCount() {
            return 100;
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
            }
        }
    }


}
