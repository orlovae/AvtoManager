package com.example.alex.avtomanager;

import android.app.Application;
import android.content.Context;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;

/**
 * Created by alex on 04.10.16.
 */

public class MyApplication extends Application implements Closeable{

    private String variableCarBrand; //Бренд авто +
    private String variableCarModel; //Модель авто +
    private int variableSpeedometer; //Показания спидометра +
    private Calendar variableLastInspecton; //Дата последнего техосмотра
    private int variableIntervalKm; //Интервал ТО в км +
    private int variableIntervalMonth; //Интервал ТО в месяцах +
    private int variableReminderKm; //Напоминание в км пробега +
    private Calendar variableTimeRecall; //Напоминание в часа и минутах
    private Calendar today; //сегодняшняя дата
    private int variableReminderHour; //Установка напоминания в часах +
    private int variableReminderMinute; //Установка напоминания в минутах +
    private String toSend;

    private String variableNamePart; //Наименование детали +
    private String variableCatalogueNumber; //Каталожный номер детали +
    private boolean variablePartTypeOfWork; //Контроль-false или замена-true детали
    private float variablePricePartExist; //Цена детали на Exist.ru
    private float variablePricePartAutodoc; //Цена детали на Autodoc.ru
    private float variablePricePartEmex; //Цена детали на Emex.ru
    private int variableId;

    private String variableParameter; //Наименование контролируемого узла
    private boolean variableParameterTypeOfWork; // Контроль-false или замена-true контролируемого узла

    private Part part;
    private ArrayList<Part> parts;
    private Parameter parameter;
    private ArrayList<Parameter> parameters;

    private static MyApplication sInstance;

    final Context context = this;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public ArrayList<Part> initArrayListPart(ArrayList<Part> parts){
        //Инициализация ArrayList<Part>
        if (parts == null) {
            parts = new ArrayList<>();
            return parts;
        }
        return parts;
    }

    public ArrayList<Parameter> initArrayListParameter(ArrayList<Parameter> parameters){
        //Инициализация ArrayList<Paramater>
        if (parameters == null) {
            parameters = new ArrayList<>();
            return parameters;
        }
        return parameters;
    }

    public int proirInspectionKm(){
        //расчёт сколько осталось до очередного ТО, км.
        int tmp1 = 0;
        int sVariableSpeedometer = getVariableSpeedometer();
        int sVariableIntervalKm = getVariableIntervalKm();

        try {
            int tmp2 = sVariableSpeedometer/sVariableIntervalKm;
            return (sVariableSpeedometer - tmp2 * sVariableIntervalKm);
        } catch (ArithmeticException e){
            return tmp1;
        }
    }

    public int nextInspectionKm(){
        //расчёт на каком километраже ближайшее ТО, км.
        int tmp1 = 0;
        int sVariableSpeedometer = getVariableSpeedometer();
        int sVariableIntervalKm = getVariableIntervalKm();

        try {
            int tmp2 = sVariableSpeedometer/sVariableIntervalKm;
            if (tmp2 == 0) return sVariableIntervalKm;
            else return (tmp2 + 1) * sVariableIntervalKm;

        } catch (ArithmeticException e){
            return tmp1;
        }
    }

    public Calendar proirInspectionTime(){
        //расчёт сколько осталось до очередного ТО, месяцы и дни.
        Calendar sVariableLastInspection; // = (Calendar) getVariableLastInspecton().clone();
        if (sVariableLastInspection != null){
            int sVariableIntervalMonth = getVariableIntervalMonth();
            today = Calendar.getInstance();
            sVariableLastInspection.add(Calendar.MONTH, sVariableIntervalMonth);
            sVariableLastInspection.add(Calendar.MONTH, -today.MONTH);
            return sVariableLastInspection;
        }
            else return sVariableLastInspection;
    }

    public Calendar nextInspectionTime(){
        //расчёт крайнего срока ТО.
        Calendar sVariableLastInspection = (Calendar) getVariableLastInspecton().clone();
        if (sVariableLastInspection != null){
            int sVariableIntervalMonth = getVariableIntervalMonth();
            sVariableLastInspection.add(Calendar.MONTH, sVariableIntervalMonth);
            return sVariableLastInspection;
        }
        else return sVariableLastInspection;
    }

//    public void addArrayList(String variableNamePart, String variableCatalogueNumber,
//                             boolean variablePartTypeOfWork, float variablePricePartExist,
//                             float variablePricePartAutodoc, float variablePricePartEmex){
//        Part part = new Part(variableNamePart, variableCatalogueNumber, variablePartTypeOfWork,
//                variablePricePartExist, variablePricePartAutodoc, variablePricePartEmex);
//        initArrayList(part);
//    }

    public void addArrayList(String namePart, String catalogueNumber, boolean partTypeOfWork){
        //Добавление к ArrayList нового Part
        float variablePricePartExist = getVariablePricePartExist();
        float variablePricePartAutodoc = getVariablePricePartAutodoc();
        float variablePricePartEmex = getVariablePricePartEmex();

        parts = initArrayListPart(parts);

        variableId = countId(parts);

        Part part = new Part(namePart, catalogueNumber, partTypeOfWork,
                variablePricePartExist, variablePricePartAutodoc, variablePricePartEmex, variableId);

        if (checkingDoubleNewPart(parts, part)) {
            toastCheckingDoublePart(part);
        }
        else {
            parts.add(part);
        }
    }

    public void editArrayList(Part part){
        //изменяет в массиве редактируемый элемент
        int id = part.getId();

        if (checkingDoubleEditPart(parts, part)) {
            toastCheckingDoublePart(part);
        }
        else {
            parts.set(id, part);
        }
    }

    private int countId(ArrayList<Part> parts){
        //возврат ID Part
        if (parts.isEmpty()){
            return 0;
        }else {
            int tmp = parts.size();
            part = parts.get(tmp - 1);
            return part.getId() + 1;
        }
    }

    public boolean checkingDoubleNewPart(ArrayList<Part> parts, Part part){
        //проверка дублирования каталожного номера при добавлении новой Part
        String tmp1 = part.getCatalogueNumber();
        boolean a = false;

        for (int i = 0; i < parts.size(); i++) {
            String tmp2 = parts.get(i).getCatalogueNumber();
            if (tmp2.equals(tmp1)) a = tmp2.equals(tmp1);

        }
        return a;
    }

    public boolean checkingDoubleEditPart(ArrayList<Part> parts, Part part){
        //проверка дублирования каталожного номера при изменении Part
        String tmp1 = part.getCatalogueNumber();
        boolean a = false;
        int id = part.getId();

        for (int i = 0; i < id; i++) {
            String tmp2 = parts.get(i).getCatalogueNumber();
            if (tmp2.equals(tmp1)) a = tmp2.equals(tmp1);
        }
        for (int i = id + 1; i < parts.size(); i++) {
            String tmp2 = parts.get(i).getCatalogueNumber();
            if (tmp2.equals(tmp1)) a = tmp2.equals(tmp1);
        }
        return a;
    }

    public void toastCheckingDoublePart(Part part){
        //вывод предупреждения о дублировании
        Toast.makeText(getApplicationContext(), warning(part), Toast.LENGTH_SHORT).show();
    }

    private String warning(Part part){
        //формирование строки предупреждения
        String tmp1 = getResources().getString(R.string.part);
        String tmp2 = part.getNamePart();
        String tmp3 = getResources().getString(R.string.with);
        String tmp4 = getResources().getString(R.string.warning_my_application_part);

        return tmp1 + " " + tmp2 + " " + tmp3 + " " + tmp4;
    }

    public void addArrayList(String nameParameter, boolean parameterTypeOfWork){
        //Добавление к ArrayList нового Parameter
        Parameter parameter = new Parameter(nameParameter, parameterTypeOfWork, this);
        parameters = initArrayListParameter(parameters);

        if (checkingDoubleParameter(parameters, parameter)) {
            toastCheckingDoublePart(parameter);
        }
        else {
            parameters.add(parameter);
        }

    }

    public boolean checkingDoubleParameter(ArrayList<Parameter> parameters, Parameter parameter){
        //проверка дублирования параметра
        String tmp1 = parameter.getNameParameter();
        boolean a = false;

        for (int i = 0; i < parameters.size(); i++) {
            String tmp2 = parameters.get(i).getNameParameter();
            boolean b = tmp2.equals(tmp1);
            if (b) a = b;

        }
        return a;
    }

    public void toastCheckingDoublePart(Parameter parameter){
        //вывод предупреждения о дублировании
        Toast.makeText(getApplicationContext(), warning(parameter), Toast.LENGTH_SHORT).show();
    }

    private String warning(Parameter parameter){
        //формирование строки предупреждения
        String tmp1 = getResources().getString(R.string.parameter);
        String tmp2 = parameter.getNameParameter();
        String tmp3 = getResources().getString(R.string.with);
        String tmp4 = getResources().getString(R.string.warning_my_application_parameter);

        return tmp1 + " " + tmp2 + " " + tmp3 + " " + tmp4;
    }

    public Part getArrayList(int i){
        return parts.get(i);
    }

    public ArrayList<Part> getParts(){
        return parts;
    }

    public ArrayList<Parameter> getParameters(){
        return parameters;
    }

    public String getEditText(EditText editText){
        String text = editText.getText().toString();
        if (text.length() == 0) text = "";
        return text;
    }

    public static MyApplication getInstance() {return sInstance;}

    public String getVariableCarBrand() {
        return variableCarBrand;
    }

    public void setVariableCarBrand(String variableCarBrand) {
        this.variableCarBrand = variableCarBrand;
    }

    public String getVariableCarModel() {
        return variableCarModel;
    }

    public void setVariableCarModel(String variableCarModel) {
        this.variableCarModel = variableCarModel;
    }

    public int getVariableSpeedometer() {
        return variableSpeedometer;
    }

    public void setVariableSpeedometer(int variableSpeedometer) {
        this.variableSpeedometer = variableSpeedometer;
    }

    public int getVariableIntervalKm() {
        return variableIntervalKm;
    }

    public void setVariableIntervalKm(int variableIntervalKm) {
        this.variableIntervalKm = variableIntervalKm;
    }

    public int getVariableIntervalMonth() {
        return variableIntervalMonth;
    }

    public void setVariableIntervalMonth(int variableIntervalMonth) {
        this.variableIntervalMonth = variableIntervalMonth;
    }

    public int getVariableReminderKm() {
        return variableReminderKm;
    }

    public void setVariableReminderKm(int variableReminderKm) {
        this.variableReminderKm = variableReminderKm;
    }

    public int getVariableReminderHour() {
        return variableReminderHour;
    }

    public void setVariableReminderHour(int variableReminderHour) {
        this.variableReminderHour = variableReminderHour;
    }

    public int getVariableReminderMinute() {
        return variableReminderMinute;
    }

    public void setVariableReminderMinute(int variableReminderMinute) {
        this.variableReminderMinute = variableReminderMinute;
    }

    public String getVariableNamePart() {
        return variableNamePart;
    }

    public void setVariableNamePart(String variableNamePart) {
        this.variableNamePart = variableNamePart;
    }

    public String getVariableCatalogueNumber() {
        return variableCatalogueNumber;
    }

    public void setVariableCatalogueNumber(String variableCatalogueNumber) {
        this.variableCatalogueNumber = variableCatalogueNumber;
    }

    public boolean isVariablePartTypeOfWork() {
        return variablePartTypeOfWork;
    }

    public void setVariablePartTypeOfWork(boolean variablePartTypeOfWork) {
        this.variablePartTypeOfWork = variablePartTypeOfWork;
    }

    public float getVariablePricePartExist() {
        return variablePricePartExist;
    }

    public void setVariablePricePartExist(float variablePricePartExist) {
        this.variablePricePartExist = variablePricePartExist;
    }

    public float getVariablePricePartAutodoc() {
        return variablePricePartAutodoc;
    }

    public void setVariablePricePartAutodoc(float variablePricePartAutodoc) {
        this.variablePricePartAutodoc = variablePricePartAutodoc;
    }

    public float getVariablePricePartEmex() {
        return variablePricePartEmex;
    }

    public void setVariablePricePartEmex(float variablePricePartEmex) {
        this.variablePricePartEmex = variablePricePartEmex;
    }

    public String getVariableParameter() {
        return variableParameter;
    }

    public void setVariableParameter(String variableParameter) {
        this.variableParameter = variableParameter;
    }

    public boolean isVariableParameterTypeOfWork() {
        return variableParameterTypeOfWork;
    }

    public void setVariableParameterTypeOfWork(boolean variableParameterTypeOfWork) {
        this.variableParameterTypeOfWork = variableParameterTypeOfWork;
    }

    public Calendar getVariableLastInspecton() {
        return variableLastInspecton;
    }

    public void setVariableLastInspecton(Calendar variableLastInspecton) {
        this.variableLastInspecton = variableLastInspecton;
    }

    public Calendar getVariableTimeRecall() {
        return variableTimeRecall;
    }

    public void setVariableTimeRecall(Calendar variableTimeRecall) {
        this.variableTimeRecall = variableTimeRecall;
    }

    public String getToSend() {
        return toSend;
    }

    public void setToSend(String toSend) {
        this.toSend = toSend;
    }

    @Override
    public void close() throws IOException {

    }
}
