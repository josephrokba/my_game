package com.example.game;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;



public class RegisterActivity extends AppCompatActivity {


    EditText birth_date,password,user_name,email,full_name;
    Spinner country;
    Button signup;
    SharedPreferences preferences ;
    SharedPreferences.Editor editor ;
    int years,months,days;
    ImageView src;
    int age;
    String country_select,country_name,f_name,u_email,u_name,pass,bd;
    RadioButton male,female;
    ArrayList<String> array = new ArrayList<>();
    Context context = this;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        birth_date = findViewById(R.id.birth_date);
        country = findViewById(R.id.country);
        password = findViewById(R.id.password);
        user_name = findViewById(R.id.user_name);
        full_name = findViewById(R.id.full_name);
        email = findViewById(R.id.email);
        src = findViewById(R.id.src);
        signup = findViewById(R.id.signup);
        male = findViewById(R.id.male);
        female = findViewById(R.id.female);

        preferences =  getSharedPreferences(Constant.preferences,MODE_PRIVATE);
        editor = preferences.edit();



        array = new ArrayList<>();
                array.add("palestine");
                array.add("another country");

        ArrayAdapter<String> adapter = new ArrayAdapter<>( context,
                android.R.layout.simple_spinner_item,array );
             adapter.setDropDownViewResource(
                     android.R.layout.simple_spinner_dropdown_item );

         country.setAdapter( adapter );

             country.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
                  @Override
                       public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                           Object item = adapterView.getItemAtPosition( i );
                              country_select = item.toString();
                                if (adapterView.getItemAtPosition( i ) == country_select) {
                                    country_name = country_select;
                      }
                  }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        } );



        src.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                studio.launch("image/*");
            }
        });


        birth_date.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                days = calendar.get( Calendar.DAY_OF_MONTH );
                months = calendar.get( Calendar.MONTH );
                years = calendar.get( Calendar.YEAR );

                DatePickerDialog datePickerDialog =
                        new DatePickerDialog( RegisterActivity.this,
                                android.R.style.Theme_DeviceDefault_Dialog
                                , new DatePickerDialog.OnDateSetListener() {
                       @Override
                       public void onDateSet(DatePicker datePicker
                               , int year, int month, int day) {

                        birth_date.setText( day + "-" +(month + 1) + "-" + year );

                             String dateString = day + "/" + (month + 1) + "/" + year;
                                DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                                   Date date = null;
                                       try {
                                                date = df.parse(dateString);
                                                Date now_time = Calendar.getInstance().getTime();
                                                age = getAge(date,now_time);

                                       } catch (ParseException e) {
                                              e.printStackTrace();
                                           }
                       }
                }, years, months, days );
                datePickerDialog.show();
            }
        } );


        signup.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                f_name = full_name.getText().toString();
                pass = password.getText().toString();
                bd = birth_date.getText().toString();
                u_email =email.getText().toString();
                u_name = user_name.getText().toString();




                if (f_name.isEmpty()){
                    Toast.makeText(RegisterActivity.this, R.string.enter_your_name, Toast.LENGTH_SHORT).show(); }
                    else if (u_email.isEmpty()){
                        Toast.makeText(RegisterActivity.this, R.string.enter_your_email, Toast.LENGTH_SHORT).show(); }
                         else if (u_name.isEmpty()){
                             Toast.makeText(RegisterActivity.this, R.string.enter_the_user_name, Toast.LENGTH_SHORT).show(); }
                             else if (pass.isEmpty()){
                                 Toast.makeText(RegisterActivity.this, R.string.enter_password, Toast.LENGTH_SHORT).show(); }
                                     else if (bd.isEmpty()) {
                                        Toast.makeText(RegisterActivity.this, R.string.enter_birth_date, Toast.LENGTH_SHORT).show(); }
                                            else if (!male.isChecked()&&!female.isChecked()) {
                                               Toast.makeText(RegisterActivity.this, R.string.choose_gender, Toast.LENGTH_SHORT).show(); }
                                                  else {

                    editor.putString(Constant.cons_full_name,f_name);
                    editor.putString(Constant.cons_email,u_email);
                    editor.putString(Constant.cons_username,u_name);
                    editor.putString(Constant.cons_password,pass);
                    editor.putString(Constant.cons_date,bd);
                    editor.putInt(Constant.cons_age,age);
                    editor.apply();

                          Intent intent = new Intent(getBaseContext(), SignInActivity.class);
                              intent.putExtra(Constant.cons_username,u_name);
                                  intent.putExtra(Constant.cons_password,pass);
                                      setResult(RESULT_OK,intent);
                                           finish();

                           }
            }
        } );



    }

    public int getAge(Date startDate, Date endDate) {
        long different = endDate.getTime() - startDate.getTime();
        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;
        long yearsInMilli = daysInMilli * 365;
        int elapsedYears = (int) (different / yearsInMilli);
        return elapsedYears;
    }

    ActivityResultLauncher<String> studio
            = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri result) {
                    src.setImageURI(result);
                }
            });

}