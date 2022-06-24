package com.example.game;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ChangePassActivity extends AppCompatActivity {

    SharedPreferences.Editor editor;
    SharedPreferences preferences;
    EditText new_password, old_password;
    Button change;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);

        preferences = getSharedPreferences(Constant.preferences, MODE_PRIVATE);
        editor = preferences.edit();


        old_password = findViewById(R.id.old_password);
        new_password = findViewById(R.id.new_password);
        change = findViewById(R.id.change);


        String oldPass = preferences.getString(Constant.cons_password, "");

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String old_pass = old_password.getText().toString();
                String new_pass = new_password.getText().toString();

           if (old_pass.isEmpty()) {
                Toast.makeText(ChangePassActivity.this, R.string.enter_old_password, Toast.LENGTH_SHORT).show(); }
                   else if (new_pass.isEmpty()) {
                       Toast.makeText(ChangePassActivity.this, R.string.enter_new_password, Toast.LENGTH_SHORT).show(); }
                           else if (!oldPass.equals(old_pass)){
                                Toast.makeText(ChangePassActivity.this, R.string.check_old_password, Toast.LENGTH_SHORT).show();
                                    old_password.setText("");}
                                        else if (oldPass.equals(new_pass)){
                                            Toast.makeText(ChangePassActivity.this, R.string.another_password, Toast.LENGTH_SHORT).show();
                                               new_password.setText("");}
                                                 else {
                                                      editor.putString(Constant.cons_password, new_pass);
                                                      editor.apply();
                                                      Toast.makeText(ChangePassActivity.this, R.string.Success, Toast.LENGTH_SHORT).show();
                                                      finish(); }

       }

        });
    }


}