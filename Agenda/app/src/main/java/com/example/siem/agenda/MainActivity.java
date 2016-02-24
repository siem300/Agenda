package com.example.siem.agenda;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //public static final String EXTRA_ASSIGNMENT = "extraAssignment";

    public static final String EXTRA_ASSIGNMENT_ID = "extraAssignmentId";

    private ListView listView;
    private ArrayAdapter<Assignment> adapter;

    private DataSource datasource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView = (ListView)findViewById(R.id.main_list);
        TextView emptyView = (TextView) findViewById(R.id.main_list_empty);
        listView.setEmptyView(emptyView);

        datasource = new DataSource(this);

        List<Assignment> assignments = datasource.getAllAssignments();
        adapter = new ArrayAdapter<Assignment>(this, android.R.layout.simple_list_item_1, assignments);
        listView.setAdapter(adapter);

        registerForContextMenu(listView);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddAssignmentActivity.class);

                startActivityForResult(intent, 1);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                intent.putExtra(EXTRA_ASSIGNMENT_ID, adapter.getItem(position).getId());
                startActivityForResult(intent, 2);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void updateAssignmentListView(){
        List<Assignment> assignments = datasource.getAllAssignments();
        adapter = new ArrayAdapter<Assignment>(this, android.R.layout.simple_list_item_1, assignments);
        listView.setAdapter(adapter);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == 1){
            if(resultCode == RESULT_OK) {
                long assignmentId = data.getLongExtra(EXTRA_ASSIGNMENT_ID, -1);
                if(assignmentId != -1) {
                    Assignment assignment = datasource.getAssignment(assignmentId);
                    adapter.add(assignment);

                    updateAssignmentListView();
                }
            }
        }
        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                updateAssignmentListView();
            }
        }


    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Select The Action");
        menu.add(0, v.getId(), 0, "Delete");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        if (item.getTitle() == "Delete") {
            Toast.makeText(getApplicationContext(), "Assignment deleted", Toast.LENGTH_LONG).show();
            Assignment assignment = adapter.getItem(info.position);
            adapter.remove(assignment);
            datasource.deleteAssignment(assignment);
        } else {
            return false;
        }
        return true;
    }


}
