package com.example.alex.avtomanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by alex on 21.09.16.
 */
public class LayoutEnter3 extends AppCompatActivity  {

    private EditText editTextIntervalKm;
    private Spinner spinnerIntervalMount;
    private Button buttonNext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_enter_3);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);// скрытие клавиатуры на activity

        initView();

        setSpinner();

        setButtonNextBehavoir();
    }

    private void initView(){
        editTextIntervalKm = (EditText)findViewById(R.id.interval_km);
        spinnerIntervalMount = (Spinner)findViewById(R.id.interval_mount);
        buttonNext = (Button)findViewById(R.id.next);
    }

    private void setButtonNextBehavoir(){
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tmp = getEditText(editTextIntervalKm);

                checkingKMToZero(tmp);

                MyApplication.getInstance().setVariableIntervalKm(tmp);

                int tmpMount = MyApplication.getInstance().getVariableIntervalMonth();

                if (tmpMount == 0){
                    Toast.makeText(getApplicationContext(), warning(), Toast.LENGTH_SHORT).show();
                }
                else startLayoutGo(LayoutEnter4.class);
            }
        });
    }

    private void startLayoutGo(Class activity){
        startActivity(new Intent(LayoutEnter3.this, activity));
        finish();
    }

    private int getEditText(EditText editText){//Возвращает значение из EditText в формате int
        int tmp = 0;
        String text = editText.getText().toString();
        if (!(text.length() == 0))
            return Integer.parseInt(text);
        else return tmp;
    }

    private int checkingKMToZero(int a){//Если километраж не заполнен, то возвращается его последнее значение
        if (a == 0) return MyApplication.getInstance().getVariableIntervalKm();
        else return a;
    }

    private void setSpinner(){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.the_number_day,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        spinnerIntervalMount.setAdapter(adapter);
        spinnerIntervalMount.post(new Runnable() {
            @Override
            public void run() {
                spinnerIntervalMount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    String[] tmp = getResources().getStringArray(R.array.the_number_day);
                    MyApplication.getInstance().setVariableIntervalMonth(Integer.parseInt(tmp[i]));
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    MyApplication.getInstance().setVariableIntervalMonth(0);
                    }
                });
            }
        });
    }

    private String warning(){
        return getResources().getString(R.string.warning_layout_enter_3);
    }
}
