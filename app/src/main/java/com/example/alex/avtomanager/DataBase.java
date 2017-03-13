package com.example.alex.avtomanager;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by alex on 30.09.16.
 */
public class DataBase implements Serializable {

    private String carBrand = ""; //Бренд авто
    private String carModel = ""; //Модель авто
    private int speedometer; //Показания спидометра
    private int day; //Дата последнего ТО, день
    private int mount; //Дата последнего ТО, месяц
    private int year; //Дата последнего ТО, год
    private int intervalKm; //Интервал ТО в км
    private int intervalMount; //Интервал ТО в месяцах
    private int reminderKm; //Напоминание в км пробега
    private int reminderMount; //Напоминание в месяцах пробега
    private int reminder; //Количество напоминаний
    private int reminderDay; //Напоминание в днях
//    private int reminderHour; //Установка напоминания в часах
//    private int reminderMinute; //Установка напоминания в минутах
//
//    private Map<String,Part> partMap; //Коллекция деталей
//
//    private Map<String,Parameter> parameterMap; //Коллекция узлов

    public DataBase() {
    }

    public DataBase(String carBrand, String carModel, int speedometer, int day, int mount, int year, int intervalKm, int intervalMount, int reminderKm, int reminderMount, int reminder, int reminderDay) {
        this.carBrand = carBrand;
        this.carModel = carModel;
        this.speedometer = speedometer;
        this.day = day;
        this.mount = mount;
        this.year = year;
        this.intervalKm = intervalKm;
        this.intervalMount = intervalMount;
        this.reminderKm = reminderKm;
        this.reminderMount = reminderMount;
        this.reminder = reminder;
        this.reminderDay = reminderDay;
    }

    public DataBase(String carBrand, String carModel, int speedometer, int intervalKm, int intervalMount, int reminderKm, int reminderMount, int reminder, int reminderDay) {
        this.carBrand = carBrand;
        this.carModel = carModel;
        this.speedometer = speedometer;
        this.intervalKm = intervalKm;
        this.intervalMount = intervalMount;
        this.reminderKm = reminderKm;
        this.reminderMount = reminderMount;
        this.reminder = reminder;
        this.reminderDay = reminderDay;
    }

    public String getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public int getSpeedometer() {
        return speedometer;
    }

    public void setSpeedometer(int speedometer) {
        this.speedometer = speedometer;
    }

    public int getIntervalKm() {
        return intervalKm;
    }

    public void setIntervalKm(int intervalKm) {
        this.intervalKm = intervalKm;
    }

    public int getIntervalMount() {
        return intervalMount;
    }

    public void setIntervalMount(int intervalMount) {
        this.intervalMount = intervalMount;
    }

    public int getReminderKm() {
        return reminderKm;
    }

    public void setReminderKm(int reminderKm) {
        this.reminderKm = reminderKm;
    }

    public int getReminderMount() {
        return reminderMount;
    }

    public void setReminderMount(int reminderMount) {
        this.reminderMount = reminderMount;
    }

    public int getReminder() {
        return reminder;
    }

    public void setReminder(int reminder) {
        this.reminder = reminder;
    }

    public int getReminderDay() {
        return reminderDay;
    }

    public void setReminderDay(int reminderDay) {
        this.reminderDay = reminderDay;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMount() {
        return mount;
    }

    public void setMount(int mount) {
        this.mount = mount;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
