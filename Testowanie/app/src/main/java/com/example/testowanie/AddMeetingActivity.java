package com.example.testowanie;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.testowanie.db.TaskContract;
import com.example.testowanie.db.TaskDbHelper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class AddMeetingActivity extends AppCompatActivity {
    private TaskDbHelper mHelper;
    private ArrayAdapter<String> mAdapter;
    private ListView mMeetingsListView;

    private String  date_time;
    private int     mYear;
    private int     mMonth;
    private int     mDay;
    private int     mHour;
    private int     mMinute;
    private Button  date_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_meeting);
        mHelper = new TaskDbHelper(this);
        mMeetingsListView = (ListView) findViewById(R.id.list_meeting_todos);
        TimeZone.setDefault(TimeZone.getTimeZone("Paris"));
        date_button = (Button)findViewById(R.id.dateButton);

        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("Paris"));
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);


        // Init button at current time()
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm");
        date_button.setText(sdf.format(new Date()));

        date_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker();
            }
        });

        Button addListItemButton = findViewById(R.id.add_meeting);
        addListItemButton.setOnClickListener(new AddNewItemListener());


    }

    private class AddNewItemListener implements View.OnClickListener
    {
        @Override
        public void onClick(View v) {
            try
            {
                //String time = ((EditText)findViewById(R.id.editText10)).getText().toString();
                String title = ((EditText)findViewById(R.id.editText12)).getText().toString();
                String place = ((EditText)findViewById(R.id.editText13)).getText().toString();
                String participants = ((EditText)findViewById(R.id.editText14)).getText().toString();

                SQLiteDatabase db = mHelper.getWritableDatabase(); // mHelper vaneko TaskDbHelper ko instance ho, tyaha bata sqlite database lyayeko
                ContentValues values= new ContentValues();
                //values.put(TaskContract.TaskMeetingEntry.COL_TASK_DATETIME,time);
                values.put(TaskContract.TaskMeetingEntry.COL_TASK_TITLE,title);
                values.put(TaskContract.TaskMeetingEntry.COL_TASK_PLACE,place);
                values.put(TaskContract.TaskMeetingEntry.COL_TASK_PARTICIPANTS,participants);

                DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy H:mm");
                try {
                    Date date = formatter.parse(date_time);
                    values.put(TaskContract.TaskMeetingEntry.COL_TASK_DATETIME, date.getTime() / 1000);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                db.insertWithOnConflict(TaskContract.TaskMeetingEntry.TABLE,
                        null,
                        values,
                        SQLiteDatabase.CONFLICT_REPLACE);
                db.close();
                finish();


            }
            catch (Exception e)
            {
                Toast.makeText(getBaseContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    void updateDateButton() {

        // Build date string from Custom dialog
        date_time =  mDay + "/" +(mMonth + 1)  + "/" + mYear + " "
                + mHour + ":" + mMinute;

        String toUpdate;
        toUpdate = CustomDateFactory("dd/MM/yyyy H:mm",
                "EEE, d MMM yyyy HH:mm",
                date_time);

        date_button.setText(toUpdate);
    }

    String CustomDateFactory(String expectedPattern, String outputPattern, String input) {

        SimpleDateFormat formatter = new SimpleDateFormat(expectedPattern);

        try {
            SimpleDateFormat sdf = new SimpleDateFormat(outputPattern);
            Date date = formatter.parse(input);
            String output = sdf.format(date);
            return (output);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return (null);
    }

    private void datePicker(){

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        if (view.isShown()) {
                            mYear = year;
                            mMonth = monthOfYear;
                            mDay = dayOfMonth;

                            updateDateButton();
                            timePicker();
                        }
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.setTitle("Select a day");
        datePickerDialog.show();
    }
    private void timePicker(){

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        if (view.isShown()) {
                            mHour = hourOfDay;
                            mMinute = minute;

                            updateDateButton();
                        }
                    }
                }, mHour, mMinute, false);
        timePickerDialog.setTitle("Select an hour");
        timePickerDialog.show();
    }

}
