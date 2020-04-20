package com.example.easyhelp.RegistrationAndLogin.Registration.Item;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CountrySpinnerAPI {

    @SerializedName("report")
    private List<Report> countryReport;
    private String errorReport;
    private int error;

    private String country_id;
    private String name;

    public List<Report> getCountryReport() {
        return countryReport;
    }

    public String getErrorReport() {
        return errorReport;
    }

    public int getError() {
        return error;
    }
}
