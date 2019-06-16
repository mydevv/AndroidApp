package com.example.testowanie.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class TaskDbHelper extends SQLiteOpenHelper{

    public static final String DB_NAME = "com.example.testowanie.db";
    public static final int DB_VERSION = 1;
    private Context context;

    //Konstruktor
    public TaskDbHelper(Context context) {
        super(context, TaskDbHelper.DB_NAME, null, 1);
        this.context = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) { //SQLiteDatabase db code here creates an instance of the SQLiteDatabase class via which database methods are going to be implemented
        //on creation of the SQLite database execute following
        String createTableMeeting = "CREATE TABLE " + TaskContract.TaskMeetingEntry.TABLE + " ( " +
                TaskContract.TaskMeetingEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TaskContract.TaskMeetingEntry.COL_TASK_DATETIME + " INTEGER NOT NULL, " +
                TaskContract.TaskMeetingEntry.COL_TASK_TITLE + " TEXT NOT NULL, " +
                TaskContract.TaskMeetingEntry.COL_TASK_PLACE + " TEXT NOT NULL, " +
                TaskContract.TaskMeetingEntry.COL_TASK_PARTICIPANTS + " TEXT NOT NULL);"; // this is SQL command to create the table and is stored in the string createTable
        /*the equivalent sql query of the above would be CREATE TABLE tasks (
        _id INTEGER PRIMARY KEY AUTOINCREMENT,
                title TEXT NOT NULL
        );*/

        String createTablePhone = "CREATE TABLE " + TaskContract.TaskPhoneEntry.TABLE + " ( " +
                TaskContract.TaskPhoneEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TaskContract.TaskPhoneEntry.COL_TASK_DATETIME + " INTEGER NOT NULL, " +
                TaskContract.TaskPhoneEntry.COL_TASK_TITLE + " TEXT NOT NULL, " +
                TaskContract.TaskPhoneEntry.COL_TASK_PERSON + " TEXT NOT NULL, " +
                TaskContract.TaskPhoneEntry.COL_TASK_PHONENUMBER + " INT NOT NULL);";

        String createTableEmail = "CREATE TABLE " + TaskContract.TaskEmailEntry.TABLE + " ( " +
                TaskContract.TaskEmailEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TaskContract.TaskEmailEntry.COL_TASK_DATETIME + " INTEGER NOT NULL, " +
                TaskContract.TaskEmailEntry.COL_TASK_TITLE + " TEXT NOT NULL, " +
                TaskContract.TaskEmailEntry.COL_TASK_PERSON + " TEXT NOT NULL, " +
                TaskContract.TaskEmailEntry.COL_TASK_EMAILADDRESS + " TEXT NOT NULL);";

        String createTableNote = "CREATE TABLE " + TaskContract.TaskNoteEntry.TABLE + " ( " +
                TaskContract.TaskNoteEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TaskContract.TaskNoteEntry.COL_TASK_DATETIME + " INTEGER NOT NULL, " +
                TaskContract.TaskNoteEntry.COL_TASK_TITLE + " TEXT NOT NULL, " +
                TaskContract.TaskNoteEntry.COL_TASK_NOTE + " TEXT NOT NULL);";

        db.execSQL(createTableMeeting); // the createTable string is passed to the db.execSQL instead of the long SQL command
        db.execSQL(createTablePhone);
        db.execSQL(createTableEmail);
        db.execSQL(createTableNote);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TaskContract.TaskMeetingEntry.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TaskContract.TaskPhoneEntry.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TaskContract.TaskEmailEntry.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TaskContract.TaskNoteEntry.TABLE);
        onCreate(db);
    }

    //Usunięcie elementu z bazy
    public void deleteListItem(int id)
    {
        try {
            //Poranie bazy do zapisu
            SQLiteDatabase db = this.getWritableDatabase();

            //Sprawdzenie czy baza nie jest tylko do odczytu
            if(!db.isReadOnly())
            {
                //Usunięcie elementu w bazie dla id = ? <- item.getId()
                db.delete(
                        TaskContract.TaskMeetingEntry.TABLE,
                        TaskContract.TaskMeetingEntry._ID + " = ?",
                        new String[]{String.valueOf(id)}
                );
                db.close();
            }

        }catch(Exception e)
        {
            Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
