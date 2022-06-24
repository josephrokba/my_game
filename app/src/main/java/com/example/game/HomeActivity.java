package com.example.game;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.pranavpandey.android.dynamic.toasts.DynamicToast;

import java.util.Calendar;

public class HomeActivity extends AppCompatActivity {

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    MediaPlayer success_raw, false_raw;
    int true_answered;
    EditText answered;
    TextView tv_age, tv_name, tv_score;
    TextView num_1, num_2, num_3, num_4, num_5, num_6, num_7, num_8, num_9;
    Button check, new_game;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.inflateMenu(R.menu.activity_setting);


        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if (item.getItemId() == R.id.setting) {
                    Intent intent = new Intent(getBaseContext(), SettingsActivity.class);
                      startActivity(intent);
                        } else if (item.getItemId() == R.id.logout) {
                            preferences.getString(Constant.cons_username, "");
                                preferences.getString(Constant.cons_password, "");
                                   preferences.getString(Constant.cons_score, "");
                                      editor.clear();
                                          editor.apply();
                                              Intent inten = new Intent(getBaseContext(), SignInActivity.class);
                                                 startActivity(inten);
                                                    finish();
                                                    }

                        return false;
            }
        });

        success_raw = MediaPlayer.create(getBaseContext(),R.raw.success);
          false_raw = MediaPlayer.create(getBaseContext(),R.raw.falsee);

        num_1 = findViewById(R.id.number1);
          num_2 = findViewById(R.id.number2);
             num_3 = findViewById(R.id.number3);
                num_4 = findViewById(R.id.number4);
                   num_5 = findViewById(R.id.number5);
                      num_6 = findViewById(R.id.number6);
                         num_7 = findViewById(R.id.number7);
                            num_8 = findViewById(R.id.number8);
                                num_9 = findViewById(R.id.number9);

        answered = findViewById(R.id.enter_number);
            tv_score = findViewById(R.id.result);
               tv_name = findViewById(R.id.user_name);
                  tv_age = findViewById(R.id.age);
                      check = findViewById(R.id.check_button);
                         new_game = findViewById(R.id.new_game_but);



        Database database = new Database(HomeActivity.this);


        fillData();


        preferences =  getSharedPreferences(Constant.preferences,MODE_PRIVATE);
        editor = preferences.edit();

        String user = preferences.getString(Constant.cons_username,"");
        tv_name.setText(user);
        int age = preferences.getInt(Constant.cons_age,0);
        tv_age.setText(""+age);
        String score = preferences.getString(Constant.cons_score,"");
        if(!score.isEmpty()) {
            tv_score.setText(score);
        }



        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int old_score;
                int new_score;

                if (answered.getText().toString().trim().isEmpty()){
                     Toast.makeText(HomeActivity.this, R.string.try_answered, Toast.LENGTH_SHORT).show();
                          }
                             else if (answered.getText().toString().equals(String.valueOf(true_answered))){
                                     DynamicToast.makeSuccess(HomeActivity.this, getString(R.string.great),Toast.LENGTH_SHORT).show();
                                       success_raw.start();
                                         old_score = Integer.parseInt(tv_score.getText().toString().trim());
                                           new_score = old_score +10;
                                             tv_score.setText(String.valueOf(new_score));
                                               fillData();
                                                  addOnDb();
                                                    answered.setText("");
                                                      editor.putString(Constant.cons_score,String.valueOf(new_score));
                                                          editor.apply();
                             }
                                    else {
                                          DynamicToast.makeError(HomeActivity.this, getString(R.string.good_luck),Toast.LENGTH_SHORT).show();
                                            old_score = Integer.parseInt(tv_score.getText().toString().trim());
                                               new_score = old_score-1;
                                                 tv_score.setText(String.valueOf(new_score));
                                                   false_raw.start();
                                                     answered.setText("");
                                                       addOnDb();
                                                         editor.putString(Constant.cons_score,String.valueOf(new_score));
                                                          editor.apply();
                                     }

            }
        });

        new_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillData();
            }
        });
    }




    public  void  fillData(){
        Util util = new Util();
        Question q =  util.generteQuestion();

        num_1.setText(q.getData()[0][0]);
        num_2.setText(q.getData()[0][1]);
        num_3.setText(q.getData()[0][2]);
        num_4.setText(q.getData()[1][0]);
        num_5.setText(q.getData()[1][1]);
        num_6.setText(q.getData()[1][2]);
        num_7.setText(q.getData()[2][0]);
        num_8.setText(q.getData()[2][1]);
        num_9.setText(q.getData()[2][2]);
        true_answered = q.getHiddenNumber();
    }



    public void addOnDb() {

        Calendar calendar = Calendar.getInstance();
        String date = calendar.get(Calendar.YEAR)+"/"+
                calendar.get(Calendar.MONTH)+"/"+calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH);
        String time = calendar.get(Calendar.HOUR)+":"+ calendar.get(Calendar.MINUTE);
        String time_date = date+" "+time;

        String fullName = preferences.getString(Constant.cons_full_name,"");
        int score=Integer.parseInt(tv_score.getText().toString());
            if(score!=-10000) {
                Users users = new Users(fullName, time_date, score);
                Database database = new Database(HomeActivity.this);
                boolean add = database.insertUser(users);
            }
        editor.putString(Constant.date,time_date);
        editor.apply();

    }


}