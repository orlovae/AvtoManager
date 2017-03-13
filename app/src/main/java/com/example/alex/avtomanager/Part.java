package com.example.alex.avtomanager;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by alex on 30.09.16.
 */
public class Part implements Parcelable {

    private String namePart; //Наименование детали
    private String catalogueNumber; //Каталожный номер детали
    private boolean partTypeOfWork; //Контроль-false или замена-true детали
    private float pricePartExist; //Цена детали на Exist.ru
    private float pricePartAutodoc; //Цена детали на Autodoc.ru
    private float pricePartEmex; //Цена детали на Emex.ru
    private int id;//индекс Part в массиве

    public Part(Parcel parcel){
        String[] data = new String[2];
        parcel.readStringArray(data);
        namePart = data[0];
        catalogueNumber = data[1];

        boolean[] booleanArray = new boolean[1];
        parcel.readBooleanArray(booleanArray);
        partTypeOfWork = booleanArray[0];

        float[] data1 = new float[3];
        parcel.readFloatArray(data1);
        pricePartExist = data1[0];
        pricePartAutodoc = data1[1];
        pricePartEmex = data1[2];

        id = parcel.readInt();
    }

    public Part(){}
    //TODO для запроса цен с сайтов, необходимо будет делать новый поток. Приложение
    //погода за 1 час, 2 часть 12 мин. + стартандроид 80 урок.

    public Part(String namePart, String catalogueNumber, boolean partTypeOfWork,
                float pricePartExist, float pricePartAutodoc, float pricePartEmex, int id) {
        this.namePart = namePart;
        this.catalogueNumber = catalogueNumber;
        this.partTypeOfWork = partTypeOfWork;
        this.pricePartExist = pricePartExist;
        this.pricePartAutodoc = pricePartAutodoc;
        this.pricePartEmex = pricePartEmex;
        this.id = id;
    }

    public String getNamePart() {
        return namePart;
    }

    public void setNamePart(String namePart) {
        this.namePart = namePart;
    }

    public String getCatalogueNumber() {
        return catalogueNumber;
    }

    public void setCatalogueNumber(String catalogueNumber) {
        this.catalogueNumber = catalogueNumber;
    }

    public boolean isPartTypeOfWork() {
        return partTypeOfWork;
    }

    public void setPartTypeOfWork(boolean partTypeOfWork) {
        this.partTypeOfWork = partTypeOfWork;
    }

    public float getPricePartExist() {
        return pricePartExist;
    }

    public void setPricePartExist(float pricePartExist) {
        this.pricePartExist = pricePartExist;
    }

    public float getPricePartAutodoc() {
        return pricePartAutodoc;
    }

    public void setPricePartAutodoc(float pricePartAutodoc) {
        this.pricePartAutodoc = pricePartAutodoc;
    }

    public float getPricePartEmex() {
        return pricePartEmex;
    }

    public void setPricePartEmex(float pricePartEmex) {
        this.pricePartEmex = pricePartEmex;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {namePart, catalogueNumber});
        dest.writeBooleanArray(new boolean[] {partTypeOfWork});
        dest.writeFloatArray(new float[]{pricePartExist, pricePartAutodoc, pricePartEmex});
        dest.writeInt(id);
    }

    public static final Parcelable.Creator<Part> CREATOR = new Parcelable.Creator<Part>() {
        @Override
        public Part createFromParcel(Parcel source) {
            return new Part(source);
        }

        @Override
        public Part[] newArray(int size) {
            return new Part[size];
        }
    };

}
