package com.example.telephonenumbers;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class update extends AppCompatActivity {
    DatabaseHelper myDB;
    protected String id;
    protected EditText updateName, updatePhone, updateCategory, updateDescription;
    protected Button updateButton, deleteButton;
    protected android.widget.ListView simpleList;
    boolean answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        myDB = new DatabaseHelper(this);


        simpleList = findViewById(R.id.listView);
        updateName = findViewById(R.id.updateName);
        updatePhone = findViewById(R.id.updatePhone);
        updateCategory = findViewById(R.id.updateCategory);
        updateDescription = findViewById(R.id.updateText);

        updateButton = findViewById(R.id.updateButton);
        deleteButton = findViewById(R.id.deleteButton);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            id = b.getString("ID");
            updateName.setText(b.getString("Name"));
            updatePhone.setText(b.getString("Phone"));
            updateCategory.setText(b.getString("Category"));
            updateDescription.setText(b.getString("Description"));

        }
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = updateName.getText().toString();
                String phone = updatePhone.getText().toString();
                String category = updateCategory.getText().toString();
                String description = updateDescription.getText().toString();

                if (name.trim().length() == 0 || phone.trim().length() == 0) {
                    Toast.makeText(getApplicationContext(), "Error!Please fill name and phone number!",
                            Toast.LENGTH_LONG).show();
                } else {
                    boolean isUpdated = myDB.updateData(id, name, phone, category, description);
                    if (isUpdated == true) {
                        Toast.makeText(getApplicationContext(), "Successfully updated!",
                                Toast.LENGTH_LONG).show();
                        updateName.getText().clear();
                        updatePhone.getText().clear();
                        updateCategory.getText().clear();
                        updateDescription.getText().clear();
                        finish();


                    } else {
                        Toast.makeText(getApplicationContext(), "Error!Something went wrong!The number has not been updated.",
                                Toast.LENGTH_LONG).show();
                    }

                }

            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showMessage("Delete", "Are you sure?");

            }
        });

    }

    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                myDB.deleteData(id);
                Toast.makeText(getApplicationContext(), "You've choosen to delete the record!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "You've changed your mind to delete all records", Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();
    }

}
