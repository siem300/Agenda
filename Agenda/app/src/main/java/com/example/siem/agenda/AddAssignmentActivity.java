package com.example.siem.agenda;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class AddAssignmentActivity extends AppCompatActivity {

    private DataSource datasource;

    EditText editText;
    EditText titleText;
    DatePicker datePicker;
    String date;

    private int day;
    private int month;
    private int year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assignment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Add Homework");
        setSupportActionBar(toolbar);

        editText = (EditText)findViewById(R.id.editText);
        datePicker = (DatePicker)findViewById(R.id.datePicker);
        titleText = (EditText)findViewById(R.id.titleText);

        datasource = new DataSource(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(editText.getText()) && !TextUtils.isEmpty(titleText.getText())) {

                    day = datePicker.getDayOfMonth();
                    month = datePicker.getMonth() + 1;
                    year = datePicker.getYear();

                    date = day + "-" + month + "-" + year;
                    long assignmentId = datasource.createAssignment(editText.getText().toString(), date, titleText.getText().toString());
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra(MainActivity.EXTRA_ASSIGNMENT_ID, assignmentId);
                    setResult(Activity.RESULT_OK, resultIntent);
                    finish();
                } else {
                    //Show a message to the user
                    Toast.makeText(AddAssignmentActivity.this, "Please enter some text in the title and description fields", Toast.LENGTH_LONG).show();

                }
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
