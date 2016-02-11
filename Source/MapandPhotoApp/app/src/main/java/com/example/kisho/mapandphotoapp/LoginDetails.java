package com.example.kisho.mapandphotoapp;

import android.graphics.Bitmap;
import android.widget.ImageView;

import java.io.Serializable;

/**
 * Created by kisho on 2/8/2016.
 */
public class LoginDetails implements Serializable{
    String FullName,EmailId,Username,Password;
    Bitmap DisplayPicture;
    private static final long serialVersionUID=1L;

    public LoginDetails(String fullName, String emailId, String username, String password, Bitmap displayPicture) {
        FullName = fullName;
        EmailId = emailId;
        Username = username;
        Password = password;
        DisplayPicture = displayPicture;
    }
}
