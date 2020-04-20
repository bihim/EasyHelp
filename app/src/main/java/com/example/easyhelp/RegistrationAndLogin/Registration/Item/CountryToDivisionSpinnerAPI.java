package com.example.easyhelp.RegistrationAndLogin.Registration.Item;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CountryToDivisionSpinnerAPI {

    @SerializedName("report")
    private List<CountryToDivisionReport> countryToDivisionReport;
    @SerializedName("error_report")
    private String errorReport;
    @SerializedName("error")
    private int error;

    public List<CountryToDivisionReport> getCountryToDivisionReport() {
        return countryToDivisionReport;
    }

    public String getErrorReport() {
        return errorReport;
    }

    public int getError() {
        return error;
    }
}
