package com.example.easyhelp.RegistrationAndLogin.Registration.Item;

import com.google.gson.annotations.SerializedName;

public class Report {
    @SerializedName("Name")
    private String name;
    @SerializedName("Thana_ID")
    private String thanaId;
    @SerializedName("District_ID")
    private String districtId;
    @SerializedName("Division_ID")
    private String divisionId;
    @SerializedName("Country_ID")
    private String countryId;

    public Report(String name, String countryId) //for country
    {
        this.name = name;
        this.countryId = countryId;
    }

    public Report(String name, String countryId, String divisionId) //for country to division
    {
        this.name = name;
        this.divisionId = divisionId;
        this.countryId = countryId;
    }

    public Report(String name, String countryId, String divisionId, String districtId)
    {
        this.name = name;
        this.districtId = districtId;
        this.divisionId = divisionId;
        this.countryId = countryId;
    }

    public Report(String name, String countryId, String divisionId, String districtId, String thanaId)
    {
        this.name = name;
        this.thanaId = thanaId;
        this.districtId = districtId;
        this.divisionId = divisionId;
        this.countryId = countryId;
    }

    public String getName() {
        return name;
    }

    public String getThanaId() {
        return thanaId;
    }

    public String getDistrictId() {
        return districtId;
    }

    public String getDivisionId() {
        return divisionId;
    }

    public String getCountryId() {
        return countryId;
    }
}
