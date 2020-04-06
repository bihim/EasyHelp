package com.example.easyhelp.ChangePasswordThings;

public class ChangePasswordItems
{
    private int error;

    public ChangePasswordItems(int error) {
        this.error = error;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    /*Only getting error value from change password. 0 is success and 1 is failure*/
}
