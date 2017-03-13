package com.example.alex.avtomanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

/**
 * Created by alex on 12.11.16.
 */

public class LayoutPartEditEdit extends AppCompatActivity {
    private EditText editTextNamePart, editTextCatalogueNumber;
    private RadioGroup radioGroup;
    private RadioButton replace, control;
    private Button buttonAdd, buttonComplete;
    private Part part;

    public final static String PART = "com.example.alex.avtomanager.PART";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layuot_part_edit);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        // скрытие клавиатуры на activity

        initView();

        setIntent();

        setButtonAddBehavoir();

        setRadioGroupBehavior();

    }

    private void initView(){
        editTextNamePart = (EditText)findViewById(R.id.name_part);
        editTextCatalogueNumber = (EditText)findViewById(R.id.catalogue_number);
        radioGroup = (RadioGroup) findViewById(R.id.select);
        replace = (RadioButton)findViewById(R.id.replace);
        control =(RadioButton)findViewById(R.id.control);
        buttonAdd = (Button)findViewById(R.id.add);
        buttonComplete = (Button)findViewById(R.id.complete);
    }

    public void onClick(View view) {
        //Обработка нажатий complete
        switch (view.getId()) {
            case R.id.complete:
                startLayoutGo(LayoutPartView.class);
                finish();
                break;
        }
    }

    private void startLayoutGo(Class activity){
        startActivity(new Intent(LayoutPartEditEdit.this, activity));
    }

    private String getEditText(EditText editText){
        //возврат String из Edittext
        String text = editText.getText().toString();
        if (text.length() == 0) text = "";
        return text;
    }

    private void setRadioGroupBehavior(){
        //обработка нажатий радиобаттен. Сетим значения.
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.replace:
                        MyApplication.getInstance().setVariablePartTypeOfWork(true);
                        break;
                    case R.id.control:
                        MyApplication.getInstance().setVariablePartTypeOfWork(false);
                        break;
                }
            }
        });
    }

    private void fieldValidation(String string, EditText editText){
        //Проверка заполненности поля
        if (string.equals("")) setToast(editText);
    }

    private void setToast(EditText editText){
        //Вывод предупреждения о необходимости заполнить поле
        String tmp1 = getResources().getString(R.string.field_valid_1);
        String tmp2 = getResources().getString(R.string.field_valid_2);

        String field = tmp1 + " " + editText.getHint().toString() + " " + tmp2;
        Toast.makeText(getApplicationContext(), field, Toast.LENGTH_SHORT).show();
    }

    private void checkedRadioGroup(RadioButton radioButtonReplace){
        //сетим значения если не нажаты радиобаттаны
        boolean b = radioButtonReplace.isChecked();
        if (b) {
            MyApplication.getInstance().setVariablePartTypeOfWork(true);
        }
        else {
            MyApplication.getInstance().setVariablePartTypeOfWork(false);
        }
    }

    private void setIntent(){
        part = (Part)getIntent().getParcelableExtra(PART);

        String s1 = part.getNamePart();
        String s2 = part.getCatalogueNumber();
        boolean b = part.isPartTypeOfWork();

        setEditText(s1, s2);

        setRadioGroup(b);
    }

    private void setRadioGroup(boolean b){
        //сетим замену/контроль, в радиобаттен
        if (b) {
            replace.setChecked(true);
        }
        else {
            control.setChecked(true);
        }
    }

    private void setEditText(String s1, String s2){
        //сетим имя и каталожный номер редактируемой детали.
        editTextNamePart.setText(s1);
        editTextCatalogueNumber.setText(s2);
    }

    private void setButtonAddBehavoir(){
        //обработка кнопки добавить
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tmp1 = getEditText(editTextNamePart);
                fieldValidation(tmp1,editTextNamePart);

                String tmp2 = getEditText(editTextCatalogueNumber);
                fieldValidation(tmp2, editTextCatalogueNumber);

                checkedRadioGroup(replace);
                boolean tmp3 = MyApplication.getInstance().isVariablePartTypeOfWork();

                editParts(tmp1, tmp2, tmp3);
            }
        });
    }

    private void editParts(String s1, String s2, boolean b){
        //Изменяем Part и ArrayList
        if (s1.length()!=0 && s2.length()!=0){
            part.setNamePart(s1);
            part.setCatalogueNumber(s2);
            part.setPartTypeOfWork(b);
            MyApplication.getInstance().editArrayList(part);}
    }

    @Override
    public void onBackPressed() {
        //переходит после редактирования на LayoutPartView
        super.onBackPressed();
        Intent intent = new Intent(LayoutPartEditEdit.this, LayoutPartView.class);
        startActivity(intent);
    }
}
