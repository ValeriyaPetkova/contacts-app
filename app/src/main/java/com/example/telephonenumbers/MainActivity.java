package com.example.telephonenumbers;

import androidx.annotation.CallSuper;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.telephonenumbers.DatabaseHelper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDB;
    protected android.widget.ListView simpleList;
    Button btnAddContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDB = new DatabaseHelper(this);


        btnAddContact = findViewById(R.id.addButton);
        btnAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, activity_insertData.class));
            }
        });

        simpleList = findViewById(R.id.listView);
        simpleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selected = "";
                TextView clickedText = view.findViewById(R.id.textview);
                selected = clickedText.getText().toString();
                String[] elements = selected.split("\t", -1);//to get empty values
                String ID = elements[0];
                String name = elements[1];
                String phone = elements[2];
                String category = elements[3];
                String descr = elements[4];

                Intent intent = new Intent(MainActivity.this, update.class);
                Bundle bundle = new Bundle();

                bundle.putString("ID", ID);
                bundle.putString("Name", name);
                bundle.putString("Phone", phone);
                bundle.putString("Category", category);
                bundle.putString("Description", descr);

                intent.putExtras(bundle);
                startActivityForResult(intent, 200, bundle);

            }
        });

    }

    @Override
    @CallSuper
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            ViewData();
        } catch (Exception e) {

        }
    }

    protected void onStart() {
        ViewData();
        super.onStart();

    }

    @Override
    protected void onRestart() {
        ViewData();
        super.onRestart();

    }

    public void ViewData() {
        simpleList = findViewById(R.id.listView);
        ArrayList<String> listResults = new ArrayList<String>();
        Cursor c = myDB.getAllData();
        simpleList.clearChoices();
        while (c.moveToNext()) {
            String ID = c.getString(0);
            String name = c.getString(1);
            String number = c.getString(2);
            String category = c.getString(3);
            String description = c.getString(4);
            listResults.add(ID + "\t" + name + "\t" + number + "\t" + category + "\t" + description);
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                getApplicationContext(),
                R.layout.activity_listview,
                R.id.textview,
                listResults
        );

        simpleList.setAdapter(arrayAdapter);
    }
}
