package com.example.easyhelp.BloodThings;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BloodReportListAPI
{
    private int error;
    @SerializedName("blood_list")
    private List<BloodList> blood_list;

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public List<BloodList> getBlood_list() {
        return blood_list;
    }

    public void setBlood_list(List<BloodList> blood_list) {
        this.blood_list = blood_list;
    }
}
