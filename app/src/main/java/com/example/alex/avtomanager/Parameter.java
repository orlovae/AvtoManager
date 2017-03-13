package com.example.alex.avtomanager;

import android.app.Activity;
import android.content.Context;
import java.io.Serializable;

/**
 * Created by alex on 30.09.16.
 */
public class Parameter extends Activity implements Serializable {

    private String nameParameter; //Наименование контролируемого узла
    private boolean parameterTypeOfWork; // Контроль-false или замена-true контролируемого узла
    Context context;

    public Parameter(String nameParameter, boolean parameterTypeOfWork, Context context) {
        this.nameParameter = nameParameter;
        this.parameterTypeOfWork = parameterTypeOfWork;
        this.context = context;
    }

    public String getNameParameter() {
        return nameParameter;
    }

    public void setNameParameter(String nameParameter) {
        this.nameParameter = nameParameter;
    }

    public boolean isParameterTypeOfWork() {
        return parameterTypeOfWork;
    }

    public void setParameterTypeOfWork(boolean parameterTypeOfWork) {
        this.parameterTypeOfWork = parameterTypeOfWork;
    }

    public String getParameterTypeOfWork(boolean b){
        //возврат string значений из boolean parameterTypeOfWork
        String tmp1 = context.getResources().getString(R.string.replace);
        String tmp2 = context.getResources().getString(R.string.control);

        if (b) return tmp1;
        return tmp2;

    }

}
