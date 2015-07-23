package com.example.administrador.myapplication.util;


import android.app.Activity;
import android.widget.EditText;

import com.example.administrador.myapplication.R;

public final class FormHelper {

    private FormHelper(){
        super();
    }

    public static boolean requireValidate(Activity context, EditText... editTexts){

        boolean valid = true;

        for(EditText editText : editTexts){
            String value = editText.getText() == null ? null : editText.getText().toString();

            if(value == null || value.trim().isEmpty()){
                String errorMessage = context.getString(R.string.required_field);
                editText.setError(errorMessage);
                valid = false;
            }
        }

        return valid;
    }
}
