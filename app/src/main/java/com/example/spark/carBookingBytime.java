package com.example.spark;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.service.chooser.ChooserTarget;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class carBookingBytime extends AppCompatActivity {

    private TextView textViewStartTime,textViewEndTime,TextViewStartDate,TextViewEndDate,textViewFinalBook;
    DatePickerDialog.OnDateSetListener setListener;
    TimePickerDialog.OnTimeSetListener onTimeSetListener;
    public String date1,date2,time1,time2,date3,date4;
    String AmPm;
    public int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_booking_bytime);
        Objects.requireNonNull(getSupportActionBar()).hide();


        textViewStartTime = (TextView) findViewById(R.id.textViewSetStartTime);
        textViewEndTime = (TextView) findViewById(R.id.textViewSetEndTime);
        TextViewStartDate = (TextView) findViewById(R.id.textViewSetStartDate);
        TextViewEndDate = (TextView) findViewById(R.id.textViewSetEndDate);
        textViewFinalBook = (TextView) findViewById(R.id.textViewFinalBook);
        Bundle bundle = getIntent().getExtras();
        position= bundle.getInt("position");
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        final int hour = calendar.get(Calendar.HOUR_OF_DAY);
        final int minute = calendar.get(Calendar.MINUTE);

        textViewStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        carBookingBytime.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minutes) {
                        if(hourOfDay >= 12){
                            AmPm = "pm";
                        } else {
                            AmPm = "am";
                        }
                        time1 = hourOfDay+":"+minutes;
                        Log.v("abc","time1:"+time1);
                        textViewStartTime.setText(String.format("%2d:%2d",hourOfDay,minutes));
                    }
                },hour,minute,false);
                timePickerDialog.show();
            }
        });

        textViewEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        carBookingBytime.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minutes) {
                        if(hourOfDay >= 12){
                            AmPm = "pm";
                        } else {
                            AmPm = "am";
                        }
                        time2 = hourOfDay+":"+minutes;
                        Log.v("abc","time2:"+time2);
                        textViewEndTime.setText(String.format("%2d:%2d",hourOfDay,minutes));
                    }
                },hour,minute,false);
                timePickerDialog.show();
            }
        });

        TextViewStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        carBookingBytime.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month+1;
                        date1= month+"/"+ day +"/" +year;
                        date3= day+"/"+ month +"/" +year;
                        Log.v("abc","date1:"+date1);
                        TextViewStartDate.setText(date3);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        TextViewEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        carBookingBytime.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month+1;
                        date2= month+"/"+ day +"/" +year;
                        date4 =day+"/"+ month +"/" +year;
                        Log.v("abc","date2:"+date2);
                        TextViewEndDate.setText(date4);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        textViewFinalBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
//                    String format_date1 = (date1+" "+time1);
//                    String format_date2 = (date2+" "+time2);
//                    Log.v("abc","dateformat1:"+format_date1);
//                    Log.v("abc","datefomate2:"+format_date2);
                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm");
                    Date dateObj1 = sdf.parse(date1 + " " + time1);
                    Date dateObj2 = sdf.parse(date2 + " " + time2);
//                    Date dateObj1 = sdf.parse(format_date1);
//                    Date dateObj2 = sdf.parse(format_date2);
                    System.out.println(dateObj1);
                    System.out.println(dateObj2 + "\n");

                    DecimalFormat crunchifyFormatter = new DecimalFormat("###,###");

                    // getTime() returns the number of milliseconds since January 1, 1970, 00:00:00 GMT represented by this Date object
                    long diff = dateObj2.getTime() - dateObj1.getTime();

                    int diffDays = (int) (diff / (24 * 60 * 60 * 1000));
                    int diffhours = (int) (diff / (60 * 60 * 1000));
                    String bookHourDiff = crunchifyFormatter.format(diffhours);
                    int diffmin = (int) (diff / (60 * 1000));
                    String bookMinuteDiff = crunchifyFormatter.format(diffmin);
                    Intent intent = new Intent(carBookingBytime.this, PaymentActivity.class);
                    Bundle bundle = new Bundle();
                    Log.v("abc","price:"+((diffhours * 60) + diffmin)*2);
                    bundle.putInt("time",diffmin);
                    bundle.putInt("position",position);
                    bundle.putInt("Amount",((diffhours * 60) + diffmin)*2);
                    intent.putExtras(bundle);
                    startActivity(intent);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
