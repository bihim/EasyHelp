package com.example.easyhelp.BloodThings;

public class BloodReportListItem
{
    private String id;
    private String category;
    private String title;
    private String name;
    private String mobile;

    public BloodReportListItem(String id, String category, String title, String name, String mobile)
    {
        this.id = id;
        this.category = category;
        this.title = title;
        this.name = name;
        this.mobile = mobile;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
