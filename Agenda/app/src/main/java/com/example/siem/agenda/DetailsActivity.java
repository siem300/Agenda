package com.example.siem.agenda;

import android.app.Activity;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class DetailsActivity extends AppCompatActivity {

    private DataSource datasource;
    private Assignment assignment;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        datasource = new DataSource(this);
        long assignmentId = getIntent().getLongExtra(MainActivity.EXTRA_ASSIGNMENT_ID, -1);
        assignment = datasource.getAssignment(assignmentId);

        TextView textView = (TextView) findViewById(R.id.details_textview);
        textView.setText(assignment.getAssignment());

        editText = (EditText) findViewById(R.id.details_updateText);

        Button updateButton = (Button) findViewById(R.id.details_updateButton);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assignment.setAssignment(editText.getText().toString());
                datasource.updateAssignment(assignment);
                Toast.makeText(DetailsActivity.this, "Assignment Updated", Toast.LENGTH_SHORT).show();

                Intent resultIntent = new Intent();
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }
}