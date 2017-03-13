package com.example.alex.avtomanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by alex on 21.09.16.
 */
public class LayoutEnter5 extends AppCompatActivity {

    private Button buttonParameters;
    private Button buttonPart;
    private Button buttonCancel;
    private Button buttonNext;

    private ArrayList<Part> parts;
    private ArrayList<Parameter> parameters;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_enter_5);

        initView();
    }

    private void initView(){
        buttonParameters = (Button)findViewById(R.id.parameters);
        buttonPart = (Button)findViewById(R.id.part);
        buttonCancel = (Button)findViewById(R.id.cancel);
        buttonNext = (Button)findViewById(R.id.next);

    }

    public void onClick(View view) { //Обработка нажатий кнопок
        switch (view.getId()) {
            case R.id.parameters:
                startLayoutGo(LayoutParameterEdit.class);
                break;

            case R.id.part:
                startLayoutGo(LayoutPartEdit.class);
                break;

            case R.id.cancel://TODO повесить проверку, если Part && Parameter == null, выводить Popup с предупреждением о необходимости заполнить
                startLayoutGo(MainActivity.class);
                break;

            case R.id.next:
                checkingButtonNext();
                break;
        }
    }

    private void startLayoutGo(Class activity ){
        startActivity(new Intent(LayoutEnter5.this, activity));
    }

    private void checkingButtonCancel(){
        parts = MyApplication.getInstance().getParts();
        parameters = MyApplication.getInstance().getParameters();

        if (parts == null && parameters == null){

        }
        else startLayoutGo(MainActivity.class);
    }

    private void checkingButtonNext(){
        parts = MyApplication.getInstance().getParts();
        parameters = MyApplication.getInstance().getParameters();

        if ((parts != null) & (parameters != null)){
            startLayoutGo(MainActivity.class);
        }
        else Toast.makeText(getApplicationContext(), warning(), Toast.LENGTH_SHORT).show();
    }

    private String warning(){
        return getResources().getString(R.string.warning_layout_enter_5);
    }
}
