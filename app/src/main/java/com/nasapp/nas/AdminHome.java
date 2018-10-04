package com.nasapp.nas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * @author Syed_Muhammad_Ali_Raza_zaidi
 * @discription Class for the activity in the Admin home page
 * @Date_Last_altered 17_07_2018
 */
public class AdminHome extends AppCompatActivity implements View.OnClickListener{

    private Button button;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState)          ;
        setContentView(R.layout.activity_admin_home);

        button = (Button) findViewById(R.id.button2);
        button.setOnClickListener(this)             ;

        progressDialog = new ProgressDialog(this);
    }

    public void onClick(View view) {
        if(view == button){
            startActivity(new Intent(this,LogOut.class));
        }
    }

   /* @Override
    public void onBackPressed() {
        Toast.makeText(this,"Do you really want to exit?",Toast.LENGTH_LONG).show();

    }
    */
}

