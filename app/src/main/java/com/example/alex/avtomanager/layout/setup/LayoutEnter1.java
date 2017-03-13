package com.example.alex.avtomanager.layout.setup;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.alex.avtomanager.MyApplication;
import com.example.alex.avtomanager.R;

/**
 * Created by alex on 21.09.16.
 */
public class LayoutEnter1 extends AppCompatActivity {

    private EditText editTextCarBrand; //Ввод Марки Авто. На будущее, нужно реализовывать Спиннер.
    private EditText editTextCarModel; //Ввод Модели Авто

    private Button buttonCancel; //Возврат на главный экран
    private Button buttonNext; //Переход к следующему layout

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_enter_1);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);// скрытие клавиатуры на activity

        initView();

    }

    private void initView(){
        editTextCarBrand = (EditText)findViewById(R.id.car_brand);
        editTextCarModel = (EditText)findViewById(R.id.car_model);

        buttonCancel = (Button)findViewById(R.id.cancel);
        buttonNext = (Button)findViewById(R.id.next);

    }

    public void onClick(View view) { //Обработка нажатий кнопок
        switch (view.getId()) {
            case R.id.cancel:
                startLayoutGo(LayoutEnter2.class);
                break;

            case R.id.next:
                String carBrand = getEditText(editTextCarBrand);
                String carModel = getEditText(editTextCarModel);
                MyApplication.getInstance().setVariableCarBrand(carBrand);
                MyApplication.getInstance().setVariableCarModel(carModel);
                startLayoutGo(LayoutEnter2.class);

                break;
        }

    }

    private void startLayoutGo(Class activity){
        startActivity(new Intent(LayoutEnter1.this, activity));
    }

    private String getEditText(EditText editText){
        String text = editText.getText().toString();
        if (text.length() == 0) text = "";
        return text;

    }

}
