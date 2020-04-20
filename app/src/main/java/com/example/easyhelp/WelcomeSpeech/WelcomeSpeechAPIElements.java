package com.example.easyhelp.WelcomeSpeech;

import com.google.gson.annotations.SerializedName;

public class WelcomeSpeechAPIElements {

    @SerializedName("error_report")
    private String errorReport;
    @SerializedName("error")
    private int error;
    @SerializedName("established")
    private int established;
    @SerializedName("about_us")
    private String aboutUs;
    @SerializedName("head_image")
    private String headImage;
    @SerializedName("head_of_institute")
    private String headOfInstitute;
    @SerializedName("institute_website")
    private String instituteWebsite;
    @SerializedName("institute_email")
    private String instituteEmail;
    @SerializedName("institute_phone")
    private String institutePhone;
    @SerializedName("institute_address")
    private String instituteAddress;
    @SerializedName("institute_logo")
    private String instituteLogo;
    @SerializedName("institute_name")
    private String instituteName;

    public String getErrorReport() {
        return errorReport;
    }

    public int getError() {
        return error;
    }

    public int getEstablished() {
        return established;
    }

    public String getAboutUs() {
        return aboutUs;
    }

    public String getHeadImage() {
        return headImage;
    }

    public String getHeadOfInstitute() {
        return headOfInstitute;
    }

    public String getInstituteWebsite() {
        return instituteWebsite;
    }

    public String getInstituteEmail() {
        return instituteEmail;
    }

    public String getInstitutePhone() {
        return institutePhone;
    }

    public String getInstituteAddress() {
        return instituteAddress;
    }

    public String getInstituteLogo() {
        return instituteLogo;
    }

    public String getInstituteName() {
        return instituteName;
    }
}
