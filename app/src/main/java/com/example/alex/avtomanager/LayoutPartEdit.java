package com.example.alex.avtomanager;

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
 * Created by alex on 21.09.16.
 */
public class LayoutPartEdit extends AppCompatActivity {

    private EditText editTextNamePart, editTextCatalogueNumber;
    private RadioGroup radioGroup;
    private RadioButton radioButtonReplace, radioButtonControl;
    private Button buttonAdd, buttonComplete;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layuot_part_edit);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        // скрытие клавиатуры на activity

        initView();

        setButtonAddBehavoir();

        setRadioGroupBehavior();

    }

    private void initView(){
        editTextNamePart = (EditText)findViewById(R.id.name_part);
        editTextCatalogueNumber = (EditText)findViewById(R.id.catalogue_number);
        radioGroup = (RadioGroup) findViewById(R.id.select);
        radioButtonReplace = (RadioButton)findViewById(R.id.replace);
        radioButtonControl =(RadioButton)findViewById(R.id.control);
        buttonAdd = (Button)findViewById(R.id.add);
        buttonComplete = (Button)findViewById(R.id.complete);
    }

    public void onClick(View view) { //Обработка нажатий complete
        switch (view.getId()) {
            case R.id.complete:
                finish();
                break;
        }
    }

    private String getEditText(EditText editText){//возврат String из Edittext
        String text = editText.getText().toString();
        if (text.length() == 0) text = "";
        return text;
    }

    private void setRadioGroupBehavior(){
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

    private void setButtonAddBehavoir(){
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tmp1 = getEditText(editTextNamePart);
                fieldValidation(tmp1,editTextNamePart);

                String tmp2 = getEditText(editTextCatalogueNumber);
                fieldValidation(tmp2, editTextCatalogueNumber);

                checkedRadioGroup(radioButtonReplace);
                boolean tmp3 = MyApplication.getInstance().isVariablePartTypeOfWork();

                addPart(tmp1, tmp2, tmp3);
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

    private void addPart(String s1, String s2, boolean b){
        //Добавление Part к ArrayList если поля заполнены
        if (s1.length()!=0 && s2.length()!=0){
            MyApplication.getInstance().setVariableNamePart(s1);
            MyApplication.getInstance().setVariableCatalogueNumber(s2);
            MyApplication.getInstance().addArrayList(s1, s2, b);}
    }

    private void checkedRadioGroup(RadioButton radioButton){
        //сетим значения если не нажаты радиобаттаны
        boolean b = radioButton.isChecked();
        if (b) {
            MyApplication.getInstance().setVariablePartTypeOfWork(true);
        }
        else {
            MyApplication.getInstance().setVariablePartTypeOfWork(false);
        }
    }
}
