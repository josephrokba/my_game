package com.example.game;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SettingsActivity extends AppCompatActivity {

    SharedPreferences preferences;
    Context context =this;
    SharedPreferences.Editor editor ;
    Database database = new Database(context);


    Button all_games,last_game,change_password,clear_history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        all_games = findViewById(R.id.all_games);
        last_game = findViewById(R.id.last_game);
        change_password = findViewById(R.id.change_password);
        clear_history = findViewById(R.id.clear_history);

        preferences =  getSharedPreferences(Constant.preferences,MODE_PRIVATE);
        editor = preferences.edit();




        all_games.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(),ShowAllActivity.class);
                startActivity(intent);
            }
        });

        clear_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
                builder.setTitle(R.string.Clear).setMessage(R.string.history).
                        setPositiveButton(R.string.continu, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                database.deleteData();
                                preferences.getString(Constant.cons_score, "");
                                editor.clear();

                                Toast.makeText(getBaseContext(), R.string.Success, Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton(R.string.back, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                AlertDialog dialog = builder.create();
                dialog.show();            }
        });

        last_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<Users> users=database.getAllData();
                String text="";

                for (Users i:users){
                    text=i.getDate();
                }
                if (!text.equals("")){
                    Toast.makeText(getBaseContext(), ""+text, Toast.LENGTH_SHORT).show();


                }else
                    Toast.makeText(getBaseContext(), R.string.no_data, Toast.LENGTH_SHORT).show();

            }
        });

        change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(),ChangePassActivity.class);
                startActivity(intent);
            }
        });


    }

}