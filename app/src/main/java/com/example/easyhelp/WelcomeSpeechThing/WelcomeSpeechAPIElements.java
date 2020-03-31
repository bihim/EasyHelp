package com.example.easyhelp.WelcomeSpeechThing;

public class WelcomeSpeechAPIElements
{
    private int user_id;
    private int error;
    private String error_report;
    private String my_speech;

    public WelcomeSpeechAPIElements(int user_id) {
        this.user_id = user_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getError() {
        return error;
    }

    public String getError_report() {
        return error_report;
    }

    public String getMy_speech() {
        return my_speech;
    }
}
