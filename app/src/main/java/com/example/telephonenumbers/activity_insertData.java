package com.example.telephonenumbers;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class activity_insertData extends AppCompatActivity {
    DatabaseHelper myDB;
    EditText editName, editPhone, editCategory, editDescription;
    protected android.widget.ListView simpleList;
    Button btnInsert;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_data);
        myDB = new DatabaseHelper(this);


        editName = findViewById(R.id.editName);
        editPhone = findViewById(R.id.editPhone);
        editCategory = findViewById(R.id.editCategory);
        editDescription = findViewById(R.id.editText);

        btnInsert = findViewById(R.id.insertButton);
        AddData();
    }
    public void AddData() {
        btnInsert.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name=editName.getText().toString();
                        String phone=editPhone.getText().toString();
                        String category=editCategory.getText().toString();
                        String description=editDescription.getText().toString();

                        if(name.trim().length()==0 || phone.trim().length()==0){
                            Toast.makeText(getApplicationContext(), "Error!Please fill name and phone number!",
                                    Toast.LENGTH_LONG).show();
                        }
                        else{
                            boolean isInserted = myDB.insertData(name,phone,category,description);
                            if (isInserted == true) {
                                Toast.makeText(getApplicationContext(), "Inserted number",
                                        Toast.LENGTH_LONG).show();
                                editName.getText().clear();
                                editPhone.getText().clear();
                                editCategory.getText().clear();
                                editDescription.getText().clear();

                            } else {
                                Toast.makeText(getApplicationContext(), "Not inserted",
                                        Toast.LENGTH_LONG).show();
                            }

                        }
                        finish();

                    }

                }
        );

    }

}
