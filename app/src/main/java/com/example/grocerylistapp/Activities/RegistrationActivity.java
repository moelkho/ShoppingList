package com.example.grocerylistapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.grocerylistapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private TextView signin;
    private Button btnRegistration;
    private Button btnForgotPassword;
    private FirebaseAuth c_Auth;
    private ProgressDialog c_Dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setWidgets();
        setListners();


    }

    private void setWidgets()
    {
        c_Auth = FirebaseAuth.getInstance();

        c_Dialog = new ProgressDialog(this);
        email = findViewById(R.id.email_registration);
        password = findViewById(R.id.password_registration);
        btnRegistration = findViewById(R.id.btn_registration);
        signin = findViewById(R.id.signin_text);
        btnForgotPassword = findViewById(R.id.btn_forgotPassword_registration);
    }
    private void setListners() {
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);

            }
        });

        btnRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String c_email = email.getText().toString().trim();
                String c_password = password.getText().toString().trim();

                if (TextUtils.isEmpty(c_email))
                { email.setError("Requiered Field ");
                    return;
                } else if (TextUtils.isEmpty(c_password))
                {
                    password.setError("Requiered Field");
                    return;
                }
                c_Dialog.setMessage("Processing");
                c_Dialog.show();
                c_Auth.createUserWithEmailAndPassword(c_email,c_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {

                            Toast.makeText(getApplicationContext(),"Successful",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                            startActivity(intent);
                            c_Dialog.dismiss();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_SHORT).show();
                            c_Dialog.dismiss();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        btnForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), ForgotActivity.class);
                startActivity(intent);
            }
        });
    }
}