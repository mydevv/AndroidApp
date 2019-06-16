package com.example.testowanie;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
//import android.support.v7.app.AlertDialog;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.testowanie.R;
import com.example.testowanie.db.TaskContract;
import com.example.testowanie.db.TaskDbHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //private static final String TAG = "MainActivity";//TAG constant with the name of the class for logging
    //private TaskDbHelper mHelper; // private instance of TaskDbHelper
    //private ListView mTaskListView; // list view to display the todos created by user
    //private ArrayAdapter<String> mAdapter; // this array adapter will help to poulate the list view

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //mHelper = new TaskDbHelper(this);
        //mTaskListView = (ListView) findViewById(R.id.list_todo); //listview to id bata reference gareko

        //updateUI(); // update UI with the tasks on start

        Button btn = (Button)findViewById(R.id.button5);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ShowMeetingsActivity.class));
            }
        });
    }

    /*private void updateUI() {
        ArrayList<String> taskList = new ArrayList<>();

        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.query(TaskContract.TaskMeetingEntry.TABLE, new String[]{TaskContract.TaskMeetingEntry._ID,TaskContract.TaskMeetingEntry.COL_TASK_TITLE},null,null,null,null,null);
        while(cursor.moveToNext()){
            int idx= cursor.getColumnIndex(TaskContract.TaskMeetingEntry.COL_TASK_TITLE);
            taskList.add(cursor.getString(idx));
        }

        if(mAdapter == null){
            mAdapter = new ArrayAdapter<>(this,
                    R.layout.item_todo, // what view to use for the items
                    R.id.task_title, // where to put the strings of data
                    taskList); // where to get all the data

            mTaskListView.setAdapter(mAdapter); //set it as the adapter for the ListView instance
        }else{
            mAdapter.clear();
            mAdapter.addAll(taskList);
            mAdapter.notifyDataSetChanged();
        }
        cursor.close();
        db.close();
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) { // to create the menu
        //mHelper = new TaskDbHelper(this); // initialising the mHelper instance of TaskDbHelper
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //when the add a task button is selected
        switch(item.getItemId()){
            case R.id.action_add_task:
                //Log.d(TAG, "Add a new task");
                //return true;
                //final EditText taskEditText = new EditText(this); // EditText to add new task text

                String[] categories = {"meeting", "phone", "email", "note"};

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Pick a todo category");
                builder.setItems(categories, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                openAddMeeting();
                                //updateUI();
                                break;
                        }
                    }
                })
                        .setNegativeButton("Cancel",null) // Cancel daabdaa
                        .create();
                builder.show();
                return true;


                /*AlertDialog dialog = new AlertDialog.Builder(this) // adding a new task functionality in AlertDialog
                        .setTitle("Add a new Task")
                        .setMessage("What do you want to do next?")
                        .setView(taskEditText)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() { //Add button daabdaa
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String task = String.valueOf(taskEditText.getText());
                                //Log.d(TAG,"Task to add: "+task); // This code was for testing purpose with Logcat
                                SQLiteDatabase db = mHelper.getWritableDatabase(); // mHelper vaneko TaskDbHelper ko instance ho, tyaha bata sqlite database lyayeko
                                ContentValues values= new ContentValues();
                                values.put(TaskContract.TaskEntry.COL_TASK_TITLE,task);
                                db.insertWithOnConflict(TaskContract.TaskEntry.TABLE,
                                        null,
                                        values,
                                        SQLiteDatabase.CONFLICT_REPLACE);
                                db.close();
                                updateUI();
                            }
                        })
                        .setNegativeButton("Cancel",null) // Cancel daabdaa
                        .create();
                dialog.show();
                return true;*//*AlertDialog dialog = new AlertDialog.Builder(this) // adding a new task functionality in AlertDialog
                        .setTitle("Add a new Task")
                        .setMessage("What do you want to do next?")
                        .setView(taskEditText)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() { //Add button daabdaa
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String task = String.valueOf(taskEditText.getText());
                                //Log.d(TAG,"Task to add: "+task); // This code was for testing purpose with Logcat
                                SQLiteDatabase db = mHelper.getWritableDatabase(); // mHelper vaneko TaskDbHelper ko instance ho, tyaha bata sqlite database lyayeko
                                ContentValues values= new ContentValues();
                                values.put(TaskContract.TaskEntry.COL_TASK_TITLE,task);
                                db.insertWithOnConflict(TaskContract.TaskEntry.TABLE,
                                        null,
                                        values,
                                        SQLiteDatabase.CONFLICT_REPLACE);
                                db.close();
                                updateUI();
                            }
                        })
                        .setNegativeButton("Cancel",null) // Cancel daabdaa
                        .create();
                dialog.show();
                return true;*/

            default:
                return super.onOptionsItemSelected(item);
        }

    }



    public void openAddMeeting() {
        Intent intent = new Intent(this, AddMeetingActivity.class);
        startActivity(intent);
    }

    public void showMeetingTodos() {
        Intent intent = new Intent(this, ShowMeetingsActivity.class);
        startActivity(intent);
    }



}
