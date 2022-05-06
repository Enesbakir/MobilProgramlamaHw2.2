package com.example.application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.Toast;

public class registerPage extends AppCompatActivity {


    EditText password,password2,phoneNumber,email,name,surname;
    Button register;
    DBHelper dbhelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);
        register = (Button)findViewById(R.id.register);
        phoneNumber = (EditText)findViewById(R.id.phoneNumber);
        password = (EditText)findViewById(R.id.password);
        password2 = (EditText)findViewById(R.id.password2);
        email = (EditText)findViewById(R.id.email);
        name = (EditText)findViewById(R.id.name);
        surname = (EditText)findViewById(R.id.surname);
        dbhelper= new DBHelper(registerPage.this);
    }
    public void register(View view){
        String emailAdress= ""+email.getText();
        String text = "Name: " +name.getText()+"\nSurname: "+ surname.getText()+"\n Phone Number: "+phoneNumber.getText()+"\nPassword: "+password.getText();
        if(checkPasswords(password.getText().toString(),password2.getText().toString())){
            if(addUser()){
                Context context = view.getContext();
                Intent intent = new Intent (context,MainActivity.class);
                context.startActivity(intent);
                composeEmail(emailAdress,"Register",text);
            }
        }
    }

    public void composeEmail(String address, String subject,String text) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, new String[] {address});
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT,text);
        intent.setType("message/rfc822");
        try{
            startActivity(Intent.createChooser(intent,"Send mail..."));
        }
        catch(android.content.ActivityNotFoundException ex){
            Toast.makeText(registerPage.this,"Error occur to sending mail...",Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(registerPage.this,"Sign up Succeed,",Toast.LENGTH_SHORT).show();
        finish();
    }

    public boolean addUser(){
        if(dbhelper.checkUser(phoneNumber.getText().toString())){
            User user =new User(name.getText().toString(),surname.getText().toString(),phoneNumber.getText().toString(),email.getText().toString(),password.getText().toString());
            dbhelper.addUser(user);
            Toast.makeText(this, "Succesfully Registered",Toast.LENGTH_LONG).show();
            return true;
        }else{
            Toast.makeText(this, "This phone number has already registered",Toast.LENGTH_LONG).show();
            return false;
        }
    }
    private boolean checkPasswords(String password,String password2){
        if(!password.equals(password2)){
            Toast.makeText(this, "Passwords are not Matching",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }




}