package com.example.simpleinterest;

import static java.time.temporal.ChronoUnit.DAYS;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    EditText e1,e2,e3,e4;


    TextView etResult;
    Button button;


    int day,month,year;

    private DatePickerDialog datePickerDialog;

    private Button dateButton;

    String selectedDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        e1=findViewById(R.id.etAmount);
        e2=findViewById(R.id.etRate);

        button=findViewById(R.id.etButton);

        etResult=findViewById(R.id.etResult);

        initDatePicker();
        dateButton = findViewById(R.id.datePickerButton);
        dateButton.setText(todaysDate());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean isAmtValid=e1.getText().toString().isBlank();
                boolean isRateValid= e2.getText().toString().isEmpty();

                int amt=(!isAmtValid)?Integer.parseInt(e1.getText().toString()):0;
                double rate=(!isRateValid)?Double.parseDouble(e2.getText().toString()):0;
                boolean callFun=true;
                String result="";
                  if(amt<=0){
                      result= "Muldhan Enter karen";
                      callFun=false;
                }
                else if(rate<=0){
                      result= "Rate Enter karen";
                      callFun=false;
                }
                System.out.println(callFun);
                System.out.println(result);
                selectedDate=(selectedDate!=null)?selectedDate:todaysDate();
                System.out.println(selectedDate);

                if(callFun){
                    result=initCalculations(selectedDate,amt,rate);
                }
                etResult.setText(result);

            }
        });






    }

    private String initCalculations(String selectedDate, int amt, double rate) {

        Date initialDate;
        try {
            initialDate=new SimpleDateFormat("dd/MM/yyyy").parse(selectedDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        Date currentDate=Calendar.getInstance().getTime();
        long diffInMillies = Math.abs(currentDate.getTime() - initialDate.getTime());
        long days = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);


       boolean isBeforeToday= initialDate.before(currentDate);

         if(!isBeforeToday){
        return "Aaj se pahle ka Date Dalen (Jis Din Paise diye the us din ka Date Dalen)";
       }

       double month=days/30;

       long totalAmt=0;
       totalAmt= (long) ((amt*rate/100)*month);

       long grandTotal= (long) (totalAmt+amt);


        String yrsStr=(int)(month/12)+" Yrs, "+(int)(month%12)+" Months";

        String StringResult = "Amount : "+amt+"\n" +
                //"Total Time(Months) : "+month+"\n" +
                "Total Time : "+yrsStr+"\n"+
                "Total Interest : "+totalAmt+"\n" +
                "Grand Total : "+grandTotal;






return StringResult;



    }

    private String todaysDate() {
        final Calendar calendar=Calendar.getInstance();
        day=calendar.get(Calendar.DAY_OF_MONTH);
        month=calendar.get(Calendar.MONTH)+1;
        year=calendar.get(Calendar.YEAR);
        String date=day+"/"+month+"/"+year;
        return date;
    }




    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;
                String date = makeDateString(day, month, year);
                dateButton.setText(date);
                selectedDate=date;
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int style = AlertDialog.THEME_HOLO_LIGHT;
        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
    }

    private String makeDateString(int day, int month, int year)
    {
        return day+"/"+month+"/"+year;
    }
    private String getMonthFormat(int month)
    {
        if(month == 1)
            return "JAN";
        if(month == 2)
            return "FEB";
        if(month == 3)
            return "MAR";
        if(month == 4)
            return "APR";
        if(month == 5)
            return "MAY";
        if(month == 6)
            return "JUN";
        if(month == 7)
            return "JUL";
        if(month == 8)
            return "AUG";
        if(month == 9)
            return "SEP";
        if(month == 10)
            return "OCT";
        if(month == 11)
            return "NOV";
        if(month == 12)
            return "DEC";

        //default should never happen
        return "JAN";
    }

    public void openDatePicker(View view)
    {
        datePickerDialog.show();
    }
}