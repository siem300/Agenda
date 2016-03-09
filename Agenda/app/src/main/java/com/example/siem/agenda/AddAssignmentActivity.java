package com.example.siem.agenda;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Calendar;

public class AddAssignmentActivity extends FragmentActivity {

    private DataSource datasource;

    EditText editText;
    EditText titleText;
    TextView dateText;
    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assignment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Add Homework");

        editText = (EditText)findViewById(R.id.editText);
        titleText = (EditText)findViewById(R.id.titleText);
        dateText = (TextView)findViewById(R.id.date);

        datasource = new DataSource(this);

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(editText.getText()) && !TextUtils.isEmpty(titleText.getText())) {

                    date = getIntent().getStringExtra("date");
                    if (date != null) {
                        long assignmentId = datasource.createAssignment(editText.getText().toString(), date, titleText.getText().toString());
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra(MainActivity.EXTRA_ASSIGNMENT_ID, assignmentId);
                        setResult(Activity.RESULT_OK, resultIntent);
                        finish();
                    } else {
                        Toast.makeText(AddAssignmentActivity.this, "Please enter a date", Toast.LENGTH_LONG).show();
                    }
                } else {
                    //Show a message to the user
                    Toast.makeText(AddAssignmentActivity.this, "Please enter some text in the title and description fields", Toast.LENGTH_LONG).show();

                }
            }
        });
    }


    public void showDatePickerDialog(View v){
        DialogFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.show(getFragmentManager(), "date");
    }
}
