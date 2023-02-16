package com.example.mnrhbsensor;

import android.util.Patterns;
import android.widget.EditText;

public class Validator {
    public static boolean emailValidator(EditText etMail) {

        // extract the entered data from the EditText
        String emailToText = etMail.getText().toString();

        return !emailToText.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailToText).matches();
    }
    public static boolean phoneValidator(EditText phone) {

        // extract the entered data from the EditText
        String phoneToText = phone.getText().toString();

        return !phoneToText.isEmpty() && Patterns.PHONE.matcher(phoneToText).matches();
    }
}
