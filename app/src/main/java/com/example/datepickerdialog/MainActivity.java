package com.example.datepickerdialog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ListAdapter;
import android.widget.TimePicker;

import com.jaredrummler.materialspinner.MaterialSpinner;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private ConstraintLayout consDateSelector, consTimeSelector;
    private MaterialSpinner materialDateSpinner, materialTimeSpinner;

    private Calendar calendar;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        consDateSelector = findViewById(R.id.cons_layoutA);
        consTimeSelector = findViewById(R.id.cons_layoutB);


        calendar = Calendar.getInstance();


        materialDateSpinner = findViewById(R.id.spinnet_order_filter_start_time);
        materialDateSpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("hank", "consDateSelector => OnClick");
                datePicker();
            }
        });
        materialTimeSpinner = findViewById(R.id.spinnet_order_filter_spinner_end_timeB);
        materialTimeSpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePicker();
                materialTimeSpinner.setSelected(true);
            }
        });

        initDateSpinner();

    }


    //DatePickerDialog(Context context,//1.這個Context
    //OnDateSetListener listener,//2.當使用者選擇完按下OK日期的監聽
    //int year,//3.預設開始年分
    //int month,//4.預設開始月份
    //int dayOfMonth)//5.預設開始日子

    //updateDate(int year, int month, int dayOfMonth)://更新現在日期


    //選擇日期
    private void datePicker() {

        int year =  calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);


        datePickerDialog = new DatePickerDialog(this,//1.這個Context
                new DatePickerDialog.OnDateSetListener()//2.當使用者選擇完日期的監聽
                {
                    @Override
                    public void onDateSet(DatePicker view,
                                          int year, //1.選擇的年份
                                          int month, //2.選擇的月份
                                          int dayOfMonth //3.選擇的日
                    ) {

                        //取得int值轉成04-05的效果,前面加0的方法
                        String newMonth = getDoubleDigitMonth(month);
                        String newDay = getDoubleDigitday(dayOfMonth);


                        String dateTime = String.valueOf(year) + "-" + newMonth + "-" + newDay;
                        materialDateSpinner.setText(dateTime);
                        String materialSpinnerText = materialDateSpinner.getText().toString();
                        view.updateDate(year, month, dayOfMonth);//更新現在日期
                        Log.v("hank", "year:" + year + "/month:" + month + "/dayOfMonth:" + dayOfMonth + "/materialSpinnerText:" + materialSpinnerText);

                    }
                }, year,//3.預設開始年分
                month,//4.預設開始月份
                day);//5.預設開始日子

        datePickerDialog.show();
        materialDateSpinner.setOnNothingSelectedListener(new MaterialSpinner.OnNothingSelectedListener() {
            @Override
            public void onNothingSelected(MaterialSpinner spinner) {
                datePickerDialog.show();
            }
        });
        Log.v("hank", "datePicker");
    }

    //選擇時間
    private void timePicker() {


        timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                //小數格式 00:00設定
                DecimalFormat decimalFormat = new DecimalFormat("00");
                materialTimeSpinner.setText(decimalFormat.format(hourOfDay) + ":" + decimalFormat.format(minute));
                Log.v("hank", "hourOfDay:" + hourOfDay + "/minute:" + minute);
            }
        }, 0, 0, false);
        timePickerDialog.show();

        //當Picker朝上,沒被選到時也繼續跳出Picker
        materialTimeSpinner.setOnNothingSelectedListener(new MaterialSpinner.OnNothingSelectedListener() {
            @Override
            public void onNothingSelected(MaterialSpinner spinner) {
                timePickerDialog.show();
            }
        });

    }

    public void date(View view) {
        datePicker();
    }

    //初始化一開始顯示的日期設定Spinner
    private void initDateSpinner() {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);


        //取得int值轉成04-05的效果,前面加0的方法
        String newMonth = getDoubleDigitMonth(month);
        String newDay = getDoubleDigitday(day);


        String dateTime = String.valueOf(year) + "-" + newMonth + "-" + newDay;
        materialDateSpinner.setText(dateTime);
    }

    public void simpleDateFormat(View view) {

        // TODO Auto-generated method stub
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");//Date指定格式：yyyy-MM-dd HH:mm:ss:SSS
        Date date = new Date();//创建一个date对象保存当前时间
        Log.v("hank", "new Date():" + date.toString());
        String dateStr = simpleDateFormat.format(date);//format()方法将Date转换成指定格式的String
        Log.v("hank", "dateStr:" +dateStr);//2018-08-24 15:37:47:033


        String string = "2018-8-24 12:50:20:545";
        Date date2 = null;//调用parse()方法时 注意 传入的格式必须符合simpleDateFormat对象的格式，即"yyyy-MM-dd HH:mm:ss:SSS" 否则会报错！！
        try {
            date2 = simpleDateFormat.parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.v("hank", "date2:" + date2.toString());//2018-08-24 15:37:47:033

    }


    //月份自動判斷是否前面+0方法
    private String getDoubleDigitMonth(int month) {
        String newMonth = null;
        if (month < 10) {
            newMonth = "0" + (month + 1);
        } else {
            newMonth = String.valueOf(month);
        }

        return newMonth;
    }

    //日期自動判斷是否前面+0方法
    private String getDoubleDigitday(int day) {
        String newDay = null;
        if (day < 10) {
            newDay = "0" + day;
        } else {
            newDay = String.valueOf(day);
        }

        return newDay;
    }

}
