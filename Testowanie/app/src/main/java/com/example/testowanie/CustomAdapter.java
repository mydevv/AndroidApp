package com.example.testowanie;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testowanie.db.MeetingItem;
import com.example.testowanie.db.TaskDbHelper;

import java.util.List;

public class CustomAdapter extends ArrayAdapter<MeetingItem> {

    //Zmienne dla klasy
    int resource;
    Context context;
    List<MeetingItem> objects;
    TaskDbHelper myDB;

    //Konstruktor
    public CustomAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);

        //Przypisanie wartości do zmiennych
        this.resource = resource;
        this.context = context;
        this.objects = objects;
    }

    //Obiekt do przechowywania widoku
    class ListItemTag
    {
        TextView textViewTitle;
    }


    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        try
        {
            //Obiekt do przechowywania widoku
            ListItemTag listItemTag;

            //Sprawdzamy czy widok dla elementu listy istnieje i czy ustawiony został tag
            //Sprawdzenie ma na celu poprawę wydajności. Wykorzystanie LayoutInflatera jest bardzo czasochłonne.
            if(convertView == null || convertView.getTag() == null) {

                //Jeżeli nie, wykorzystamy  LayoutInflater do pobrania i przetworzenia layoutu na obiekt JAVA
                //LayoutInflater pobieramy z kontekstu z usług systemowych
                LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                //Tworzymy obiekt JAVA na podstawie layoutu
                convertView = layoutInflater.inflate(R.layout.item_todo, null);
                //View row = layoutInflater.inflate(R.layout.item_todo, parent, false);
                Button btn = (Button) convertView.findViewById(R.id.task_delete);
                btn.setTag(position);

                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                       /* MeetingItem item = objects.get(position);
                        int itemId = item.getId();
                        myDB = new TaskDbHelper(getContext());
                        myDB.deleteListItem(itemId);
                        objects.remove(item);
                        notifyDataSetChanged();*/

                        int deleted;
                        deleted = objects.get(position).getId();
                        myDB = new TaskDbHelper(getContext());
                        //SQLiteDatabase db = myDB.getReadableDatabase();
                        myDB.deleteListItem(deleted);
                        myDB.close();
                        objects.remove(deleted);
                    }
                });

                //Tworzymy nową instancję obiektu do przechowywania widoku
                listItemTag = new ListItemTag();

                //Znajdujemy i zapisujemy do ListItemTag elementy z widoku
                listItemTag.textViewTitle = convertView.findViewById(R.id.task_title);

              }
            else
            {
                //Jeżli widok istieje i ma ustawiony tag, pobieramy obiektu do przechowywania widoku stamtąd
                //Pozwala to oszczędzić czas potrzebny na operację zamiany layoutu na obiekt dla już istniejącego elementu listy
                listItemTag = (ListItemTag)convertView.getTag();
            }

            //Ustawiamy obiekt do przechowywania elementów widoku w tagu widoku
            convertView.setTag(listItemTag);


            //Pobieramy model danych z listy obiektów
            MeetingItem item = objects.get(position);


            //Dla pierwszego obiektu ustawiamy inny wygląd
            if(position == 0)
            {

            }
            //Dla pozostałych
            else
            {
                //Ustawiamy tekst
                listItemTag.textViewTitle.setText(item.getTitle());


            }
            //Zwracamy przetworzony widok dla elementu na liście
            return convertView;
        }
        catch(Exception e)
        {
            //Wyświetlenie błędu
            Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
        }

        //Ponieważ metoda jest typu View, każda ścieżka kodu musi zwracać jakąś wartość
        return null;
    }
}
