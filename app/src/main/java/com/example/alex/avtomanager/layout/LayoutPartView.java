package com.example.alex.avtomanager.layout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.alex.avtomanager.MyApplication;
import com.example.alex.avtomanager.Part;
import com.example.alex.avtomanager.R;
import com.example.alex.avtomanager.adapter.RecyclerViewAdapterPart;

import java.util.ArrayList;

/**
 * Created by alex on 21.09.16.
 */
public class LayoutPartView extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<Part> parts = MyApplication.getInstance().getParts();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_part_view);

        initViews();

        setRecyclerView();
    }

    private void initViews(){
        recyclerView = (RecyclerView)findViewById(R.id.recycler_view_part);
    }

    private void setRecyclerView(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new RecyclerViewAdapterPart(parts, this));
    }
}

//TODO Если список будет очень большой, то при его просмотре и переходе на следущее активити, и возврату обратно. список будет прокручиваться в самое начало. Для того, что бы при возврате список оставался на том же месте необходимо написать методы onSaveInstanseState и  onRestoreInstanseState. 7 урок 00:15