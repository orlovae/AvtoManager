package com.example.alex.avtomanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alex.avtomanager.layout.setup.LayoutEnter1;
import com.example.alex.avtomanager.layout.LayoutParameterEdit;
import com.example.alex.avtomanager.layout.LayoutParameterView;
import com.example.alex.avtomanager.layout.LayoutPartEdit;
import com.example.alex.avtomanager.layout.LayoutPartView;
import com.example.alex.avtomanager.managers.ManagerMainLayout;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvRemain1; // Показывает сколько км осталось до ближайшего ТО
    private TextView tvRemain2; // Показывает на каком километраже ближайшее ТО
    private TextView tvRemain3; // Показывает сколько времени (месяцев и дней) осталось до ближайшего ТО
    private TextView tvRemain4; // Показывает крайний срок ближайшего ТО ДД.ММ.ГГГГ

    private EditText etManually; //Ввод текущих показаний спидометра вручную
    private Button bAdd; //Добавление текущих показаний спидометра вручную
    private Button bPhoto; //Ввод текущих показаний спидометра с помощью камеры

    private Button bViewParts; //Просмотр списка расходников
    private Button bEditParts; //Изменение списка расходников
    private Button bViewParameters; //Просмотр списка контролируемых параметров
    private Button bEditParameters; //Изменение списка контролируемых параметров
    private Button bChangeAlert; //Изменение оповещения
    private Button bEnterConfiguration; //Ввод начальных настроек

    private ManagerMainLayout manager = new ManagerMainLayout(getBaseContext());
    private MyApplication myApp = new MyApplication();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);// скрытие клавиатуры на activity
        initView();
        buttonBehavior();
    }

    private void initView(){
        tvRemain1 = (TextView)findViewById(R.id.remain_1);
        setTVRemain1();
        tvRemain2 = (TextView)findViewById(R.id.remain_2);
        setTVRemain2();
        tvRemain3 = (TextView)findViewById(R.id.remain_3);
//        setTVRemain3();
        tvRemain4 = (TextView)findViewById(R.id.remain_4);
//        setTVRemain4();

        etManually = (EditText)findViewById(R.id.edit_text_manual_input);

        bAdd = (Button)findViewById(R.id.button_manual_input);
        bPhoto = (Button)findViewById(R.id.button_foto_input);
        bViewParts = (Button)findViewById(R.id.button_view_parts);
        bEditParts = (Button)findViewById(R.id.button_edit_parts);
        bViewParameters = (Button)findViewById(R.id.button_view_parameters);
        bEditParameters = (Button)findViewById(R.id.button_edit_parameters);
        bChangeAlert = (Button)findViewById(R.id.button_change_alert);
        bEnterConfiguration = (Button)findViewById(R.id.button_enter_configuration);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);// скрытие клавиатуры на activity
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);// скрытие клавиатуры на activity
    }

    private void setTVRemain1(){
        int tmp = MyApplication.getInstance().proirInspectionKm();
        String tmp1 = Integer.toString(tmp);
        String tmp2 = getString(R.string.proir_inspection);
        String tmp3 = getString(R.string.kilometr);
        if (tmp == 0) tvRemain1.setText(R.string.erorr1);
        else tvRemain1.setText(tmp2 + " " + tmp1 + " " + tmp3 + ".");
    }

    private void setTVRemain2(){
        int tmp = MyApplication.getInstance().nextInspectionKm();
        String tmp1 = Integer.toString(tmp);
        String tmp2 = getString(R.string.inspectoin_achievement);
        String tmp3 = getString(R.string.kilometr);
        if (tmp == 0) tvRemain2.setText(R.string.erorr1);
        else  tvRemain2.setText(tmp2 + " " + tmp1 + " " + tmp3 + ".");
    }

//    private void setTVRemain3(){
//        Calendar variableNextInspection = MyApplication.getInstance().proirInspectionTime();
//        if (variableNextInspection != null){
//            int monthNextInspection = variableNextInspection.MONTH;
//            int daynextInspectoin = variableNextInspection.DAY_OF_MONTH;
//            String tmp2 = getString(R.string.proir_inspection);
//            String tmp3 = getString(R.string.month);
//            String tmp4 = getString(R.string.day);
//            textViewRemain3.setText(tmp2 + " " + monthNextInspection + " " + tmp3 + " " +
//                daynextInspectoin + " " + tmp4 + ".");
//        }
//        else tvRemain3.setText(R.string.erorr1);
//    }

//    private void setTVRemain4(){
//        Calendar variableNextInspection = MyApplication.getInstance().nextInspectionTime();
//        if (variableNextInspection != null) {
//            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
//            String dateNextInspection = sdf.format(variableNextInspection.getTime());
//            String tmp = getString(R.string.daedline_inspection);
//            textViewRemain4.setText(tmp + " - " + dateNextInspection);
//        }
//        else tvRemain4.setText(R.string.erorr1);
//    }
    private void buttonBehavior(){
        bAdd.setOnClickListener(this);
        bPhoto.setOnClickListener(this);
        bViewParts.setOnClickListener(this);
        bEditParts.setOnClickListener(this);
        bViewParameters.setOnClickListener(this);
        bEditParameters.setOnClickListener(this);
        bChangeAlert.setOnClickListener(this);
        bEnterConfiguration.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.button_manual_input:
                int manualInputKilometr = manager.getEditText(etManually);
                myApp.setVariableSpeedometer(manualInputKilometr);
                break;
            case R.id.button_foto_input:
                ;
                break;
            case R.id.button_view_parts:
                startLayoutGo(LayoutPartView.class);
                break;
            case R.id.button_edit_parts:
                startLayoutGo(LayoutPartEdit.class);
                break;
            case R.id.button_view_parameters:
                startLayoutGo(LayoutParameterView.class);
                break;
            case R.id.button_edit_parameters:
                startLayoutGo(LayoutParameterEdit.class);
                break;
            case R.id.button_change_alert:
                ;
                break;
            case R.id.button_enter_configuration:
                startLayoutGo(LayoutEnter1.class);
                break;
        }
    }

    private void startLayoutGo(Class activity ){
        startActivity(new Intent(MainActivity.this, activity));
    }

//    @SuppressWarnings("all")
//    private boolean serializeDataBase(DataBase dataBase) {
//        File file;
//        try {
//            file = new File(getFilesDir() + SERIALISE_FILE_NAME);
//            FileOutputStream fileOut;
//            ObjectOutputStream out;
//            if(!file.exists()){
//                file.createNewFile();
//            }
//
//            fileOut = new FileOutputStream(file, false);
//            out = new ObjectOutputStream(fileOut);
//            out.writeObject(dataBase);
//            out.close();
//            fileOut.close();
//
//            return true;
//        } catch (Exception exc) {
//            Toast.makeText(this, exc.toString(), Toast.LENGTH_LONG).show();
//            return false;
//        }
//    }
//
//    private DataBase deserializeDataBase () {
//        FileInputStream fileIn = null;
//        ObjectInputStream in = null;
//        DataBase dataBase;
//        try {
//
//            fileIn = new FileInputStream(getFilesDir() + SERIALISE_FILE_NAME);
//            in = new ObjectInputStream(fileIn);
//            dataBase = (DataBase) in.readObject();
//
//            in.close();
//            fileIn.close();
//
//            return dataBase;
//        } catch (FileNotFoundException exc){
//            return new DataBase();
//        } catch (Exception exc) {
//            Toast.makeText(this, exc.toString(), Toast.LENGTH_LONG).show();
//            return new DataBase();
//        }
//    }
}
