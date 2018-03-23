package com.todo2.orhan.todoassistant;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button button;
    ListView listView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        listView = findViewById(R.id.listView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DBconnection.class);
                intent.putExtra("info","new");
                startActivity(intent);

            }
        });


    }

    public void deleteTask(View view) {
        View parent = (View) view.getParent();
        TextView taskTextView = parent.findViewById(R.id.task_title);
        String s = String.valueOf(taskTextView.getText());

        String sqlString = "DELETE FROM todolist WHERE (name) LIKE (?)";
        DBconnection.sqLiteDatabase = this.openOrCreateDatabase("Todolist", MODE_PRIVATE, null);
        SQLiteStatement statement = DBconnection.sqLiteDatabase.compileStatement(sqlString);
        statement.bindString(1, s);
        statement.execute();

        Toast.makeText(getApplicationContext(),s, Toast.LENGTH_SHORT).show();
        onStart();

    }

    @Override
    protected void onStart() {
        super.onStart();



        final ArrayList<String> taskList = new ArrayList<>();
        ArrayAdapter arrayAdapter = new ArrayAdapter<>(this, R.layout.task_item, R.id.task_title, taskList);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), DBconnection.class);
                intent.putExtra("info", "old");
                intent.putExtra("name", taskList.get(i));
                startActivity(intent);
            }
        });



        try {
            DBconnection.sqLiteDatabase = this.openOrCreateDatabase("Todolist", MODE_PRIVATE, null);
            DBconnection.sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS todolist (name VARCHAR)");

            // okuma
            Cursor cursor = DBconnection.sqLiteDatabase.rawQuery("SELECT * FROM todolist", null);

            int nameIx = cursor.getColumnIndex("name");
            cursor.moveToFirst();

            while(cursor != null) {
                taskList.add(cursor.getString(nameIx));

                cursor.moveToNext();
                arrayAdapter.notifyDataSetChanged();

            }


        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}