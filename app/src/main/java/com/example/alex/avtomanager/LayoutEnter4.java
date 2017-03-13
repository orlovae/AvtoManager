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
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by alex on 21.09.16.
 */
public class LayoutEnter4 extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private EditText editTextEnterToKm;
    private Spinner spinnerEnterToSend;
    private TextView textViewDateRecall, textViewTimeRecall, textViewSetRepeat;
    private Button buttonNext;
    private Calendar dateLastInspecton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_enter_4);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);// скрытие клавиатуры на activity

        initView();

        setTextViewDateRecallBehavior();

        setTextViewTimeRecallBehavior();

        setTextViewSetRepeatBehavior();

        setSpinner();

        setButtonBehavior();
    }



    private void initView(){
        editTextEnterToKm = (EditText) findViewById(R.id.enter_to_km);

        textViewDateRecall = (TextView) findViewById(R.id.date_recall);

        textViewTimeRecall = (TextView) findViewById(R.id.time_recall);

        textViewSetRepeat = (TextView) findViewById(R.id.set_repeat);

        spinnerEnterToSend = (Spinner)findViewById(R.id.enter_to_send);

        buttonNext = (Button)findViewById(R.id.next);
    }

    private void setTextViewSetRepeatBehavior() {

    }

    private void setTextViewTimeRecallBehavior() {
//        Calendar timeRecall = MyApplication.getInstance().getVariableTimeRecall();
//        timeRecall.set(Calendar.HOUR_OF_DAY, 8);
//        timeRecall.set(Calendar.MINUTE, 0);
//        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
//        String stringTimeRecall = sdf.format(timeRecall.getTime());
//        textViewTimeRecall.setText(stringTimeRecall);
    }

    private void setTextViewDateRecallBehavior() {
        dateLastInspecton = MyApplication.getInstance().getVariableLastInspecton();
        int intervalMonth = MyApplication.getInstance().getVariableIntervalMonth();
        dateLastInspecton.add(Calendar.MONTH, intervalMonth);
        int setHour = new Integer(getResources().getString(R.string.hour));
        int setMinute = new Integer(getResources().getString(R.string.minute));
        dateLastInspecton.set(Calendar.HOUR_OF_DAY, setHour);
        dateLastInspecton.set(Calendar.MINUTE, setMinute);
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        SimpleDateFormat sdfHoirMinute = new SimpleDateFormat("hh:mm");
        String dateNextInspection = sdf.format(dateLastInspecton.getTime());
        String hourNextInspection = sdfHoirMinute.format(dateLastInspecton.getTime());
        textViewDateRecall.setText(dateNextInspection);
        textViewTimeRecall.setText(hourNextInspection);
    }

    private void setButtonBehavior(){
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tmp = getEditText(editTextEnterToKm);

                checkingKMToZero(tmp);

                MyApplication.getInstance().setVariableReminderKm(tmp);
                startLayoutGo(LayoutEnter5.class);
            }
        });
    }

    private void startLayoutGo(Class activity ){
        startActivity(new Intent(LayoutEnter4.this, activity));
    }

    private int getEditText(EditText editText){//Возвращает значение из EditText в формате int
        int tmp;
        String text = editText.getText().toString();
        if (text.isEmpty()) tmp = 0;
        else tmp = Integer.parseInt(editText.getText().toString());

        return tmp;
    }

    private void setSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.to_send,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinnerEnterToSend.setAdapter(adapter);
        spinnerEnterToSend.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
        String[] tmp = getResources().getStringArray(R.array.to_send);
        MyApplication.getInstance().setToSend(tmp[i]);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private int checkingKMToZero(int a) {
        //Если километраж не заполнен, то возвращается его последнее значение
        if (a == 0) return MyApplication.getInstance().getVariableReminderKm();
        else return a;
    }
}
