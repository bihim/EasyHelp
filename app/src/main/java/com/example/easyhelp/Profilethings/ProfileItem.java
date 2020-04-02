package com.example.easyhelp.Profilethings;

public class ProfileItem
{
    private String title;
    private String details;


    public ProfileItem(String title, String details)
    {
        this.title = title;
        this.details = details;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
