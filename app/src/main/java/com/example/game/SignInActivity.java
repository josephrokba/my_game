package com.example.game;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class SignInActivity extends AppCompatActivity {

    EditText user_name,password;
    Button sign_in;
    CheckBox remember;
    TextView sign_up;

    SharedPreferences preferences ;
    SharedPreferences.Editor editor;
    Context context = this;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        preferences = getSharedPreferences(Constant.preferences,MODE_PRIVATE);
        editor = preferences.edit();

        user_name = findViewById(R.id.user_name);
        password = findViewById(R.id.password);
        remember = findViewById(R.id.remember);
        sign_up = findViewById(R.id.sign_up);
        sign_in = findViewById(R.id.sign_in);


        remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean b) {

                if (remember.isChecked()){
                    editor.putString("remember","0");
                }else if (!remember.isChecked()){
                    editor.putString("remember","1");
                }
                editor.apply();

            }
        });


        String checkbox = preferences.getString("remember","");
             if (checkbox.equals("0")){
                 Intent intent = new Intent(context,HomeActivity.class);
                    startActivity(intent);
                       }else if (checkbox.equals("1")){
                         Toast.makeText(getBaseContext(), R.string.signin_again, Toast.LENGTH_SHORT).show();
                        }


        ActivityResultLauncher<Intent> launcher =
                registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        String name = result.getData().getStringExtra(Constant.cons_username);
                          String pass = result.getData().getStringExtra(Constant.cons_password);
                            user_name.setText(name);
                              password.setText(pass);
                    }
                });


        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,RegisterActivity.class);
                   launcher.launch(intent);
            }
        });



        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String userName = preferences.getString(Constant.cons_username, "");
                String user_password = preferences.getString(Constant.cons_password, "");

                if (user_name.getText().toString().isEmpty()) {
                    Toast.makeText(context, R.string.username_empty, Toast.LENGTH_SHORT).show();
                       } else if (password.getText().toString().isEmpty()) {
                          Toast.makeText(context, R.string.password_empty, Toast.LENGTH_SHORT).show();
                             } else if (!user_name.getText().toString().equals(userName) &&
                                 !password.getText().toString().equals(user_password)) {
                                     Toast.makeText(getBaseContext(), R.string.Not_found, Toast.LENGTH_SHORT).show();
                                         } else if (user_name.getText().toString().equals(userName) &&
                                              password.getText().toString().equals(user_password)) {
                                                 Intent intent = new Intent(getBaseContext(), HomeActivity.class);
                                                    String username = user_name.getText().toString();
                                                       intent.putExtra(Constant.cons_username, username);
                                                          startActivity(intent);
                                                              finish(); }
                                                                 else { Toast.makeText(getBaseContext(), R.string.Not_found, Toast.LENGTH_SHORT).show();
                }
            }

        });
    }
}

