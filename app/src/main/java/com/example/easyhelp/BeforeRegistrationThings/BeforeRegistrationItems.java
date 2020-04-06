package com.example.easyhelp.BeforeRegistrationThings;

public class BeforeRegistrationItems
{
    private int iconId;
    private String professionName;

    public BeforeRegistrationItems(int iconId, String professionName)
    {
        this.iconId = iconId;
        this.professionName = professionName;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public String getProfessionName() {
        return professionName;
    }

    public void setProfessionName(String professionName) {
        this.professionName = professionName;
    }


    /*
    * This is for registration professions with icons*/
}
