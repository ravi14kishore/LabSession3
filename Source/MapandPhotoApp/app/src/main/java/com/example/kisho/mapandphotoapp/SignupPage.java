package com.example.kisho.mapandphotoapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class SignupPage extends AppCompatActivity {

    EditText SignupPageActivity_FullName,SignupPageActivity_EmailId,SignupPageActivity_Username,SignupPageActivity_Password;
    Button SignupPageActivity_SignupButton;
    ImageView SignupPageActivity_ImageView;
    TextView SignupPageActivity_ErrorTextView;
    int SignupPageActivity_PhotoCode = 0;
    Bitmap photo;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == RESULT_OK) {
            photo = (Bitmap) data.getExtras().get("data");
            SignupPageActivity_ImageView.setImageBitmap(photo);
            Log.d("CameraDemo", "Pic saved");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_page);

        SignupPageActivity_FullName = (EditText)findViewById(R.id.SignupPageContent_FullName);
        SignupPageActivity_EmailId = (EditText)findViewById(R.id.SignupPageContent_EmailId);
        SignupPageActivity_Username = (EditText)findViewById(R.id.SignupPageContent_Username);
        SignupPageActivity_Password = (EditText)findViewById(R.id.SignupPageContent_Password);
        SignupPageActivity_SignupButton = (Button)findViewById(R.id.SignupPageContent_SignupButton);
        SignupPageActivity_ErrorTextView = (TextView)findViewById(R.id.SignupPageContent_errorTextView);
        SignupPageActivity_ImageView = (ImageView)findViewById(R.id.SignupPageContent_ImageView);

        SignupPageActivity_ImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, SignupPageActivity_PhotoCode);


            }
        });





        SignupPageActivity_SignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(SignupPageActivity_FullName.getText().toString().isEmpty()) && !(SignupPageActivity_EmailId.getText().toString().isEmpty()) && !(SignupPageActivity_Username.getText().toString().isEmpty()) && !(SignupPageActivity_Password.getText().toString().isEmpty()) && photo!=null) {
                    Intent newIntent = new Intent(SignupPage.this,HomeMapsActivity.class);
                    LoginDetails NewLogin = new LoginDetails(SignupPageActivity_FullName.toString(), SignupPageActivity_EmailId.toString(), SignupPageActivity_Username.toString(), SignupPageActivity_Password.toString(), photo);
                    newIntent.putExtra("New Login",NewLogin);
                    startActivity(newIntent);
                } else {
                    SignupPageActivity_ErrorTextView.setVisibility(View.VISIBLE);
                    SignupPageActivity_ErrorTextView.setText("Fill all the details");
                }
            }
        });


    }


}
