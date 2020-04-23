package com.example.easyhelp.RegistrationAndLogin.ForgetPassword.Items;

import com.google.gson.annotations.SerializedName;

public class ForgetPasswordItems {

    @SerializedName("error_report")
    private String errorReport;
    @SerializedName("error")
    private int error;

    public String getErrorReport() {
        return errorReport;
    }

    public int getError() {
        return error;
    }
}
