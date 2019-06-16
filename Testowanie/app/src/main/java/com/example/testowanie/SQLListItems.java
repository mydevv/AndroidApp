package com.example.testowanie;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

import com.example.testowanie.db.MeetingItem;
import com.example.testowanie.db.TaskContract;
import com.example.testowanie.db.TaskDbHelper;

import java.util.ArrayList;


//Rozszerzamy podstawowy obiekt do obsługi bazy danych o funkcjonalności związane z listami
public class SQLListItems extends TaskDbHelper {

    private Context context;

    //Konstruktor
    public SQLListItems(Context context) {
        super(context);
        this.context = context;
    }


    //Metoda zapisu elementu w bazie
    public void insertListItem(MeetingItem item)
    {
        try {
            //Poranie bazy do zapisu
            SQLiteDatabase db = this.getWritableDatabase();
            //Tworzene obiektu z parametrami do zapisu do bazy
            ContentValues values = new ContentValues();
            values.put(TaskContract.TaskMeetingEntry.COL_TASK_TITLE, item.getTitle());
            values.put(TaskContract.TaskMeetingEntry.COL_TASK_DATETIME, item.getTime());
            values.put(TaskContract.TaskMeetingEntry.COL_TASK_PLACE, item.getPlace());
            values.put(TaskContract.TaskMeetingEntry.COL_TASK_PARTICIPANTS, item.getParticipants());


            //Sprawdzenie czy baza nie jest tylko do odczytu
            if (!db.isReadOnly()) {
                //Zapisanie elementu do bazy
                db.insert(TaskContract.TaskMeetingEntry.TABLE, null, values);
            }

        }catch(Exception e)
        {
            //Wyświetlenie komunikatu z błędem
            Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_LONG);
        }
    }

  /*  public void updateListItem(MainListItem item)
    {
        try {
            //Poranie bazy do zapisu
            SQLiteDatabase db = this.getWritableDatabase();
            //Tworzene obiektu z parametrami do zapisu do bazy
            ContentValues values = new ContentValues();
            values.put(SQLDataBaseHelper.TABLE_LIST_ITEMS_NAME, item.getTitle());
            values.put(SQLDataBaseHelper.TABLE_LIST_ITEMS_DESCRIPTION, item.getDescription());
            values.put(SQLDataBaseHelper.TABLE_LIST_ITEMS_IMAGE, item.getThumbPath());

            //Sprawdzenie czy baza nie jest tylko do odczytu
            if (!db.isReadOnly()) {
                //Uaktualnienie elementu w bazie dla id = ? <- item.getId()
                db.update(
                        SQLDataBaseHelper.TABLE_LIST_ITEMS,
                        values,
                        SQLDataBaseHelper.TABLE_LIST_ITEMS_ID + " = ?",
                        new String[]{String.valueOf(item.getId())});
            }

        }catch(Exception e)
        {
            Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_LONG);
        }
    }
*/
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
            }

        }catch(Exception e)
        {
            Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public Cursor getListItemsCursor()
    {
        try {
            //Poranie bazy do odczytu
            SQLiteDatabase db = this.getReadableDatabase();

        //Zdefiniowanie kolumn, które zostaną odczytane
        String[] cols = new String[]{
                TaskContract.TaskMeetingEntry._ID,
                TaskContract.TaskMeetingEntry.COL_TASK_TITLE,
                TaskContract.TaskMeetingEntry.COL_TASK_DATETIME,
                TaskContract.TaskMeetingEntry.COL_TASK_PLACE,
                TaskContract.TaskMeetingEntry.COL_TASK_PARTICIPANTS
        };
        //Odczytanie danych z bazy
        Cursor cursor = db.query(
                TaskContract.TaskMeetingEntry.TABLE,
                cols,
                null,
                null,
                null,
                null,
                null);
        //Zwrócenie kursora (elementu przechowującego dane)
        return cursor;

        }catch(Exception e)
        {
            Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_LONG);
        }

        return null;
    }


    //Utworzenie listy na podstawie kursora !!!!!!
    public ArrayList<MeetingItem> getListItems()
    {
        //Pobranie kursora
        Cursor cursor = getListItemsCursor();
        //Utworzenie tymczasowej listy
        ArrayList<MeetingItem> list = new ArrayList<MeetingItem>();

        try
        {
            //jeżeli kursor istnieje
            if(cursor != null)
            {
                //dopóki kursor odczytuje kolejne wiersze
                while (cursor.moveToNext())
                {
                    //pobranie danych z kursora
                    int id = cursor.getInt(cursor.getColumnIndex(TaskContract.TaskMeetingEntry._ID));
                    String title = cursor.getString(cursor.getColumnIndex(TaskContract.TaskMeetingEntry.COL_TASK_TITLE));
                    String time = cursor.getString(cursor.getColumnIndex(TaskContract.TaskMeetingEntry.COL_TASK_DATETIME));
                    String place = cursor.getString(cursor.getColumnIndex(TaskContract.TaskMeetingEntry.COL_TASK_PLACE));
                    String participants = cursor.getString(cursor.getColumnIndex(TaskContract.TaskMeetingEntry.COL_TASK_PARTICIPANTS));


                    //Utworzenie obiektu elementu listy
                    MeetingItem model = new MeetingItem(id, title, time, place, participants);
                    //Dodanie obiektu do listy tymczasowej
                    list.add(model);
                }
            }
        }
        catch(Exception e)
        {
            Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
        }

        //Zwrócenie listy
        return list;
    }

    public Cursor getListItemCursor(int id)
    {
        try
        {
            SQLiteDatabase db = this.getReadableDatabase();

            String[] cols = new String[]{
                    TaskContract.TaskMeetingEntry._ID,
                    TaskContract.TaskMeetingEntry.COL_TASK_TITLE,
                    TaskContract.TaskMeetingEntry.COL_TASK_DATETIME,
                    TaskContract.TaskMeetingEntry.COL_TASK_PLACE,
                    TaskContract.TaskMeetingEntry.COL_TASK_PARTICIPANTS};

            Cursor cursor = db.query(
                    TaskContract.TaskMeetingEntry.TABLE,
                    cols,
                    TaskContract.TaskMeetingEntry._ID + " = ?",
                    new String[]{ String.valueOf(id)},
                    null,
                    null,
                    null);

            return cursor;

        }
        catch(Exception e)
        {
            Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_LONG);
        }

        return null;
    }

    //Utworzenie listy na podstawie kursora
    public MeetingItem getListItem(int id)
    {
        //Pobranie kursora
        Cursor cursor = getListItemCursor(id);

        try
        {
            //jeżeli kursor istnieje
            if(cursor != null)
            {
                //dopóki kursor odczytuje kolejne wiersze
                if (cursor.moveToFirst())
                {
                    //pobranie danych z kursora

                    String title = cursor.getString(cursor.getColumnIndex(TaskContract.TaskMeetingEntry.COL_TASK_TITLE));
                    String time = cursor.getString(cursor.getColumnIndex(TaskContract.TaskMeetingEntry.COL_TASK_DATETIME));
                    String place = cursor.getString(cursor.getColumnIndex(TaskContract.TaskMeetingEntry.COL_TASK_PLACE));
                    String participants = cursor.getString(cursor.getColumnIndex(TaskContract.TaskMeetingEntry.COL_TASK_PARTICIPANTS));

                    //Utworzenie obiektu elementu listy
                    MeetingItem model = new MeetingItem(id, title, time, place, participants);

                    return model;
                }
            }
        }
        catch(Exception e)
        {
            Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_LONG);
        }

        //Zwrócenie listy
        return null;
    }
}
