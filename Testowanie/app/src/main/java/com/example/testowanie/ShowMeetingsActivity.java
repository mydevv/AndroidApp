package com.example.testowanie;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.testowanie.db.MeetingItem;
import com.example.testowanie.db.TaskContract;
import com.example.testowanie.db.TaskDbHelper;

import java.util.ArrayList;
import java.util.List;

public class ShowMeetingsActivity extends AppCompatActivity {

    private TaskDbHelper mHelper;
    private ListView mTaskListView;
    private ArrayAdapter<String> mAdapter;
    public List<MeetingItem> meetingList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_meeting_todos);

        mHelper = new TaskDbHelper(this);
        mTaskListView = findViewById(R.id.list_meeting_todos);
        meetingList = new SQLListItems(this).getListItems();




        mTaskListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long rowId) {

                //Cursor c = (Cursor) parent.getAdapter().getItem(position);

                MeetingItem item = meetingList.get(position);

                AlertDialog.Builder adb = new AlertDialog.Builder(
                        ShowMeetingsActivity.this);
                adb.setTitle("Spotkanie");
               /* adb.setMessage(" selected Item is="
                        +item.getId());*/
               adb.setMessage("Temat:   \t" + item.getTitle() +
                        "\nMiejsce:\t" + item.getPlace() +
                        "\nOsoby:   \t" + item.getParticipants() + "\n" +
                        item.getTime());
                adb.setPositiveButton("Ok", null);
                adb.show();

            }
        });
        updateUI();


    /*    //show meeting details onclick
        mTaskListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //>>>> position is the position of the clicked item, 0 for the first item.
                //>>>> position will also be the respective offset into matches
                //>>>> position SHOULD NOT BE USED to try to perform arithmetic
                //>>>> calculation of rowid/id of the row in the table
                //>>>> So get rowid via matches.get(position).
                Intent intent = new Intent(this,MeetingDetailsActivity.class);
                intent.putExtra("KEYFORDATA",row_id_to_so_selected_item_can_be_edited);
                startActivity(intent);
            }
        });*/

        //mTaskListView.setOnItemClickListener(new ItemDetailsListener());
/*
        Button btn = (Button)findViewById(R.id.button5);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShowMeetingsActivity.this, MeetingDetailsActivity.class));
            }
        });*/
    }

 /*   private class ItemDetailsListener implements ListView.OnItemClickListener
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            try
            {
                if (list != null && list.size() > position) {
                    MainListItem item = list.get(position);

                    Intent detailsActivity = new Intent();
                    detailsActivity.putExtra(DetailsActivity.DETAILS_LIST_ITEM_EXTRA, item.getId());
                }
            } catch (Exception e)
            {
                Toast.makeText(getBaseContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }*/


    private void updateUI() {
        ArrayList<String> taskList = new ArrayList<>();

        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.query(TaskContract.TaskMeetingEntry.TABLE, new String[]{TaskContract.TaskMeetingEntry._ID,TaskContract.TaskMeetingEntry.COL_TASK_TITLE},null,null,null,null,null);
        while(cursor.moveToNext()){
            int title= cursor.getColumnIndex(TaskContract.TaskMeetingEntry.COL_TASK_TITLE);
            //int id = cursor.getColumnIndex(TaskContract.TaskMeetingEntry._ID);
            taskList.add(cursor.getString(title));

        }
        // jakos przekazac id spotkania do wiersza (i zrobic invisible)
        if(mAdapter == null){
            mAdapter = new ArrayAdapter<>(this,
                    R.layout.item_todo, // what view to use for the items
                    R.id.task_title, // where to put the strings of data
                    taskList); // where to get all the data

            mTaskListView.setAdapter(mAdapter); //set it as the adapter for the ListView instance
            //mTaskListView.setTag(id);
        }else{
            mAdapter.clear();
            mAdapter.addAll(taskList);
            mAdapter.notifyDataSetChanged();
        }
        cursor.close();
        db.close();
    }



    public void deleteTask(View view) {

        //Pobranie elementów listy z bazy danych
        meetingList = new SQLListItems(this).getListItems();
        //Utworzenie instancji własnego adaptera

        CustomAdapter listAdapter = new CustomAdapter(
                this,
                R.layout.item_todo,
                meetingList
        );

        //Znajdujemy widok listowy
        ListView listView = findViewById(R.id.list_meeting_todos);
        listView.setAdapter(listAdapter);

        //MeetingItem item = meetingList.get();
        //listAdapter.getItemId(MeetingItem);
        //new SQLListItems(getBaseContext()).deleteListItem(itemId);
        updateUI();
    }
    /*    listView.setAdapter(listAdapter);

        //meetingList = new SQLListItems(this).getListItems();

        ListView listView = findViewById(R.id.list_meeting_todos);

        int position = listView.getPositionForView((View) view.getParent());

        //String tag = view.getTag().toString();
        Toast.makeText(this, tag, Toast.LENGTH_SHORT).show();
*/

        //int position = Integer.parseInt(tag);
        //MeetingItem item = meetingList.get()
        //new SQLListItems(getBaseContext()).deleteListItem(position);
        //updateUI();

      /*  int position = listView.getPositionForView((View) view.getParent());
        MeetingItem item = meetingList.get(position);
        int itemId = item.getId();
        Toast.makeText(this, Integer.toString(itemId), Toast.LENGTH_SHORT).show();*/

        //new SQLListItems(getBaseContext()).deleteListItem(itemId);
        //updateUI();



        //String item = (meetingList.get(position)).toString();

        //listAdapter.getPo
        //listView.removeViewAt(position);
        //String item = listView.getItemAtPosition(position).toString();
  /*      Object someItemInsideList = listAdapter.getItemId(position);
        meetingList.remove(someItemInsideList);
        updateUI();*/
        //long pos = listAdapter.getItemId(position);

        //Toast.makeText(this, Long.toString(pos), Toast.LENGTH_SHORT).show();

        //meetingList.remove(position);
        //listView.removeViewAt(Integer.parseInt(position).toString());
        //((CustomAdapter) listAdapter).notifyDataSetChanged();

     /*   int position = mTaskListView.getPositionForView((View) view.getParent());
        //Toast.makeText(this, Integer.toString(position), Toast.LENGTH_SHORT).show();
        mTaskListView.removeViewAt(position);
        updateUI();*/

}
