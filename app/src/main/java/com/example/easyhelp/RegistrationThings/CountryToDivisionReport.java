package com.example.easyhelp.RegistrationThings;

import com.google.gson.annotations.SerializedName;

public class CountryToDivisionReport {
    @SerializedName("Name")
    private String name;
    @SerializedName("Division_ID")
    private String divisionId;
    @SerializedName("Country_ID")
    private String countryId;

    public CountryToDivisionReport(String name, String divisionId, String countryId) {
        this.name = name;
        this.divisionId = divisionId;
        this.countryId = countryId;
    }

    public String getName() {
        return name;
    }

    public String getDivisionId() {
        return divisionId;
    }

    public String getCountryId() {
        return countryId;
    }
}
