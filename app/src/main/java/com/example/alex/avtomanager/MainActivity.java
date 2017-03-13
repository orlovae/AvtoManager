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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {

    private TextView textViewRemain1; // Показывает сколько км осталось до ближайшего ТО
    private TextView textViewRemain2; // Показывает на каком километраже ближайшее ТО
    private TextView textViewRemain3; // Показывает сколько времени (месяцев и дней) осталось до ближайшего ТО
    private TextView textViewRemain4; // Показывает крайний срок ближайшего ТО ДД.ММ.ГГГГ

    private EditText editTextManually; //Ввод текущих показаний спидометра вручную
    private Button buttonAdd; //Добавление текущих показаний спидометра вручную
    private Button buttonPhotograph; //Ввод текущих показаний спидометра с помощью камеры

    private Button buttonViewParts; //Просмотр списка расходников
    private Button buttonEditParts; //Изменение списка расходников
    private Button buttonViewParameters; //Просмотр списка контролируемых параметров
    private Button buttonEditParameters; //Изменение списка контролируемых параметров
    private Button buttonAlert; //Изменение оповещения
    private Button buttonEnterConfiguration; //Ввод начальных настроек

    private static final String SERIALISE_FILE_NAME = "/savedData.ser";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);// скрытие клавиатуры на activity
        initView();
    }

    private void initView(){
        textViewRemain1 = (TextView)findViewById(R.id.remain_1);
        setTextViewRemain1();
        textViewRemain2 = (TextView)findViewById(R.id.remain_2);
        setTextViewRemain2();
        textViewRemain3 = (TextView)findViewById(R.id.remain_3);
        setTextViewRemain3();
        textViewRemain4 = (TextView)findViewById(R.id.remain_4);
        setTextViewRemain4();

        editTextManually = (EditText)findViewById(R.id.manual_input_layout_enter);
        buttonAdd = (Button)findViewById(R.id.manual_input_layout_enter2);
        buttonPhotograph = (Button)findViewById(R.id.foto_input_layout_enter);

        buttonViewParts = (Button)findViewById(R.id.view_parts);
        buttonEditParts = (Button)findViewById(R.id.edit_parts);
        buttonViewParameters = (Button)findViewById(R.id.view_parameters);
        buttonEditParameters = (Button)findViewById(R.id.edit_parameters);
        buttonAlert = (Button)findViewById(R.id.change_alert);
        buttonEnterConfiguration = (Button)findViewById(R.id.enter_configuration);
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

    private void setTextViewRemain1(){
        int tmp = MyApplication.getInstance().proirInspectionKm();
        String tmp1 = Integer.toString(tmp);
        String tmp2 = getString(R.string.proir_inspection);
        String tmp3 = getString(R.string.kilometr);
        if (tmp == 0) textViewRemain1.setText(R.string.erorr1);
        else textViewRemain1.setText(tmp2 + " " + tmp1 + " " + tmp3 + ".");
    }

    private void setTextViewRemain2(){
        int tmp = MyApplication.getInstance().nextInspectionKm();
        String tmp1 = Integer.toString(tmp);
        String tmp2 = getString(R.string.inspectoin_achievement);
        String tmp3 = getString(R.string.kilometr);
        if (tmp == 0) textViewRemain2.setText(R.string.erorr1);
        else  textViewRemain2.setText(tmp2 + " " + tmp1 + " " + tmp3 + ".");
    }

    private void setTextViewRemain3(){
        Calendar variableNextInspection = MyApplication.getInstance().proirInspectionTime();
        if (variableNextInspection != null){
            int monthNextInspection = variableNextInspection.MONTH;
            int daynextInspectoin = variableNextInspection.DAY_OF_MONTH;
            String tmp2 = getString(R.string.proir_inspection);
            String tmp3 = getString(R.string.month);
            String tmp4 = getString(R.string.day);
            textViewRemain3.setText(tmp2 + " " + monthNextInspection + " " + tmp3 + " " +
                daynextInspectoin + " " + tmp4 + ".");
        }
        else textViewRemain3.setText(R.string.erorr1);
    }

    private void setTextViewRemain4(){
        Calendar variableNextInspection = MyApplication.getInstance().nextInspectionTime();
        if (variableNextInspection != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
            String dateNextInspection = sdf.format(variableNextInspection.getTime());
            String tmp = getString(R.string.daedline_inspection);
            textViewRemain4.setText(tmp + " - " + dateNextInspection);
        }
        else textViewRemain4.setText(R.string.erorr1);
    }

    public void onClick(View view) { //Обработка нажатий кнопок
        switch (view.getId()) {

            case R.id.manual_input_layout_enter2:
                int tmp = getEditText(editTextManually);
                MyApplication.getInstance().setVariableSpeedometer(tmp);;
                break;
            case R.id.foto_input_layout_enter:
                ;
                break;
            case R.id.view_parts:
                startLayoutGo(LayoutPartView.class);
                break;
            case R.id.edit_parts:
                startLayoutGo(LayoutPartEdit.class);
                break;
            case R.id.view_parameters:
                startLayoutGo(LayuotParameterView.class);
                break;
            case R.id.edit_parameters:
                startLayoutGo(LayoutParameterEdit.class);
                break;
            case R.id.change_alert:
                ;
                break;
            case R.id.enter_configuration:
                startLayoutGo(LayoutEnter1.class);
                break;
        }
    }

    private void startLayoutGo(Class activity ){
        startActivity(new Intent(MainActivity.this, activity));
    }

    @SuppressWarnings("all")
    private boolean serializeDataBase(DataBase dataBase) {
        File file;
        try {
            file = new File(getFilesDir() + SERIALISE_FILE_NAME);
            FileOutputStream fileOut;
            ObjectOutputStream out;
            if(!file.exists()){
                file.createNewFile();
            }

            fileOut = new FileOutputStream(file, false);
            out = new ObjectOutputStream(fileOut);
            out.writeObject(dataBase);
            out.close();
            fileOut.close();

            return true;
        } catch (Exception exc) {
            Toast.makeText(this, exc.toString(), Toast.LENGTH_LONG).show();
            return false;
        }
    }

    private DataBase deserializeDataBase () {
        FileInputStream fileIn = null;
        ObjectInputStream in = null;
        DataBase dataBase;
        try {

            fileIn = new FileInputStream(getFilesDir() + SERIALISE_FILE_NAME);
            in = new ObjectInputStream(fileIn);
            dataBase = (DataBase) in.readObject();

            in.close();
            fileIn.close();

            return dataBase;
        } catch (FileNotFoundException exc){
            return new DataBase();
        } catch (Exception exc) {
            Toast.makeText(this, exc.toString(), Toast.LENGTH_LONG).show();
            return new DataBase();
        }
    }

    private int getEditText(EditText editText){//Возвращает значение из EditText в формате int
        int tmp = 0;
        String text = editText.getText().toString();
        if (!(text.length() == 0))
            return Integer.parseInt(text);
        else return tmp;
    }
}
