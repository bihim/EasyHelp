package com.example.easyhelp.BeforeRegistrationThings;

public class BeforeRegistrationItems
{
    private int iconId;
    private String professionName;
    private String professionId;

    public BeforeRegistrationItems(int iconId, String professionName, String professionId)
    {
        this.iconId = iconId;
        this.professionName = professionName;
        this.professionId = professionId;
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

    public String getProfessionId() {
        return professionId;
    }

    public void setProfessionId(String professionId) {
        this.professionId = professionId;
    }

    /*
    * This is for registration professions with icons*/
}
