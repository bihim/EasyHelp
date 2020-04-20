package com.example.easyhelp.RegistrationAndLogin.Registration.Item;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CountryDivisionDistrictThana {

    @SerializedName("report")
    private List<Report> report;
    @SerializedName("error_report")
    private String errorReport;
    @SerializedName("error")
    private int error;

    public List<Report> getReport() {
        return report;
    }

    public String getErrorReport() {
        return errorReport;
    }

    public int getError() {
        return error;
    }
}
