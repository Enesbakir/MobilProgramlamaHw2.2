package com.example.application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText username,password;
    Button login,register;
    DBHelper dbhelper;
    private int tryNum=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = (Button)findViewById(R.id.login);
        register = (Button)findViewById(R.id.register);
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        dbhelper = new DBHelper(MainActivity.this);
    }

    public void register(View view){
        Intent intent = new Intent(this, registerPage.class);
        startActivity(intent);
    }

    public void login(View view){
        if(checkLogin(username.getText().toString(),password.getText().toString())){
            Toast.makeText(this, "Succesfully Logined",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, mediaPlayer.class);
            startActivity(intent);
        }
        if(tryNum==3){
            Toast.makeText(this, "Redirecting register Page",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, registerPage.class);
            startActivity(intent);
            tryNum=0;
        }
    }

    private boolean checkLogin(String username,String password){
        User user = dbhelper.getUser(username);
        if(user!=null){
            if(user.getPassword().equals(password)){
                tryNum=0;
                return true;
            }
        }
        Toast.makeText(this, "Username or password is Not Correct",Toast.LENGTH_SHORT).show();
        tryNum+=1;

        return false;
    }

}