package com.example.easyhelp.RegistrationAndLogin.Registration.Item;


public class CountryReport {
    private String Name;
    private String Country_ID;

    public CountryReport(String Name, String Country_ID)
    {
        this.Name = Name;
        this.Country_ID = Country_ID;
    }

    public String getName() {
        return Name;
    }

    public String getCountryId() {
        return Country_ID;
    }
}
