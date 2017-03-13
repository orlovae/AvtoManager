package com.example.alex.avtomanager.layout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.alex.avtomanager.MyApplication;
import com.example.alex.avtomanager.Parameter;
import com.example.alex.avtomanager.R;
import com.example.alex.avtomanager.adapter.RecyclerViewAdapterParameter;

import java.util.ArrayList;

/**
 * Created by alex on 22.09.16.
 */
public class LayoutParameterView extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<Parameter> parameters = MyApplication.getInstance().getParameters();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layuot_parameters_view);

        initViews();

        setRecyclerView();
    }

    private void initViews(){

        recyclerView = (RecyclerView)findViewById(R.id.recycler_view_parameter);
    }

    private void setRecyclerView(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new RecyclerViewAdapterParameter(parameters, this));
    }

}