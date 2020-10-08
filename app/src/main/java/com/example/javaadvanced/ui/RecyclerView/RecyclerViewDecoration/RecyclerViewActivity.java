package com.example.javaadvanced.ui.RecyclerView.RecyclerViewDecoration;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.javaadvanced.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends AppCompatActivity {

    private List<Star> starList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);

        init();

        RecyclerView recyclerView = findViewById(R.id.rv_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // 自定义分割线
        recyclerView.addItemDecoration(new StarDecoration(this));
        recyclerView.setAdapter(new StarAdapter(this, starList));
    }

    private void init() {
        starList = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 20; j++) {
                if (i % 2 == 0) {
                    starList.add(new Star("何炅" + j, "快乐家族" + i));
                } else {
                    starList.add(new Star("汪涵" + j, "天天兄弟" + i));
                }
            }
        }
    }
}