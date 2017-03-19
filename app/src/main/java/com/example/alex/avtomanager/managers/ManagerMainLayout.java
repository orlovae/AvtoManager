package com.example.alex.avtomanager.managers;

import android.content.Context;
import android.widget.EditText;

/**
 * Created by alex on 19.03.17.
 */

public class ManagerMainLayout {

    public ManagerMainLayout(Context context) {
    }

    public int getEditText(EditText editText){//Возвращает значение из EditText в формате int
        int tmp = -1;
        String text = editText.getText().toString();
        if (!(text.length() == 0))
            return Integer.parseInt(text);
        else return tmp;
    }
}
