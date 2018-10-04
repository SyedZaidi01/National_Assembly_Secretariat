package com.nasapp.nas;

/**
 * Author @Syed_Muhammad_Ali_Raza_Zaidi
 * Date_Last_Coded_July_05_2018
 * Description = This class is the main Sign in
 * page for the Application.
 */

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Timer;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    //defining views
    private Button   buttonSignIn    ;
    private EditText editTextEmail   ;
    private EditText editTextPassword;
    private TextView textViewSignup  ;

    //Firebase object
    private FirebaseAuth firebaseAuth;

    //progress dialog
    private ProgressDialog progressDialog;
    //intent for switching user between admin and client
    Intent intentForUserSwitch;

    //boolean needed to acquire if the user is admin
    Boolean isAdmin;

    //a string var to store the user's email
    String emailTemp;


    /** The method is a default method in every class
     *  in order to open the xml view.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in);

        isAdmin = null;

        //getting firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

        FirebaseUser userTemp = FirebaseAuth.getInstance().getCurrentUser();

        //if the objects getcurrentuser method is not null
        //means user is already logged in
        if(firebaseAuth.getCurrentUser() != null){
            //close this activity
            finish();
            emailTemp = userTemp .getEmail()    ;
            emailTemp = emailTemp.substring(0,5);
            //opening profile activity
            if ( emailTemp == null){
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
            else if ( emailTemp.equals("admin")) {
                System.out.println("in top admin clause *******************BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB****************************************************");
                startActivity(new Intent(getApplicationContext(),AdminHome.class ));
            }
             else{
                System.out.println("in top user clause *******************BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB****************************************************");
                startActivity(new Intent(getApplicationContext(),UserHome.class));
            }
        }

        //initializing views
        editTextEmail    = (EditText) findViewById(R.id.editTextEmail)   ;
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        buttonSignIn     = (Button)   findViewById(R.id.buttonSignin)    ;
        textViewSignup   = (TextView) findViewById(R.id.textViewSignUp)  ;

        progressDialog   = new ProgressDialog(this);

        //attaching click listener
        buttonSignIn  .setOnClickListener(this);
        textViewSignup.setOnClickListener(this);


    }

    //method for user login
    private void userLogin(){
        String email     = editTextEmail   .getText().toString().trim();
        String password  = editTextPassword.getText().toString().trim();


        //checking if email and passwords are empty
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }

        final String cut = email.substring(0,5);

        //if the email and password are not empty
        //displaying a progress dialog

        progressDialog.setMessage("Logging In...");
        progressDialog.show()                     ;

        //intentForUserSwitch = null;
        if(cut.equals("admin")){
            //finish();
            intentForUserSwitch = new Intent(getApplicationContext(),AdminHome.class);
            System.out.println(cut + "***************************************************************************");
            isAdmin = true;
        }
        else {
            //debugging
            System.out.println(cut + "***************************8888888888888888888888888***************************");
            //debugging
            intentForUserSwitch = new Intent(getApplicationContext(), UserHome.class);
            isAdmin = false;
        }
        //logging in the user

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        //if the task is successfull
                        if ( task.isSuccessful() && intentForUserSwitch!=null){
                            startActivity(intentForUserSwitch);
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Please Try Again",Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }

    @Override
    public void onClick(View view) {
        if(view == buttonSignIn){
            userLogin();
        }
        if(view == textViewSignup ){
            startActivity( new Intent(getApplicationContext(),SignUp.class));
        }
    }
}
