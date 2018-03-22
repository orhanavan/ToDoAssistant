package com.todo2.orhan.todoassistant;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DBconnection extends AppCompatActivity {

    Button button;
    EditText editText;
    static SQLiteDatabase sqLiteDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbconnection);

        button = findViewById(R.id.btn_content);
        editText = findViewById(R.id.et_content);

    }

    public void save(View view) {
        String toDoItem = editText.getText().toString();

        try {
            sqLiteDatabase = this.openOrCreateDatabase("Todolist", MODE_PRIVATE, null);
            sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS todolist (name VARCHAR)");
            String sqlString = "INSERT INTO todolist (name) VALUES(?)";
            SQLiteStatement statement = sqLiteDatabase.compileStatement(sqlString);
            statement.bindString(1, toDoItem);
            statement.execute();
            //sqLiteDatabase.execSQL("DROP TABLE todolist");



        } catch (Exception e) {
            e.printStackTrace();
        }
        setResult(RESULT_OK);
        finish();
    }
}
