package com.thevarunshah.swotanalysistool;

import java.util.ArrayList;
import java.util.Collections;

import android.view.Gravity;
import android.graphics.Color;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.view.Menu;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;
import android.content.Intent;

import com.thevarunshah.swotanalysistool.backend.Database;
import com.thevarunshah.swotanalysistool.backend.SWOTObject;
import com.thevarunshah.swotanalysistool.backend.RecyclerViewAdapter;

public class SavedSWOTsScreen extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private StaggeredGridLayoutManager mGridLayoutManager;
    private RecyclerViewAdapter mAdapter;
    private ArrayList<SWOTObject> swotList;
    private FloatingActionButton floatingButton;
    private Toolbar tbar;
    private String m_Text= "";
    private Context ctx;
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
        ctx = getApplicationContext();

		setContentView(R.layout.saved_swots);
        tbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(tbar);

	}

	@Override
	protected void onResume(){

		super.onResume();
		//final ListView swotLV = (ListView) findViewById(R.id.swots_list);
		swotList = new ArrayList<SWOTObject>();
		for(SWOTObject so : Database.getSWOTs().values()){
			swotList.add(so);
		}
		Collections.sort(swotList);

        createRecyclerView();
        createFABListener();

	}
    public void createRecyclerView(){

        mRecyclerView = (RecyclerView) findViewById(R.id.swots_list);
        mGridLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mAdapter = new RecyclerViewAdapter(this, swotList);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void createFABListener(){
        floatingButton = (FloatingActionButton) findViewById(R.id.fab);
        floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               getInput();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.saved_swots, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case android.R.id.home:
//                Intent i = new Intent(getApplicationContext(), HomeScreen.class);
//                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                getApplicationContext().startActivity(i);
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.about_settings:
                Intent i = new Intent(getApplicationContext(), AboutScreen.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(i);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void getInput(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
// Set up the input
        final EditText input = new EditText(this);
        SWOTObject so = new SWOTObject(Database.getId()+1);
        input.setText(so.getName());
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        TextView myMsg = new TextView(this);
        myMsg.setText("Title");
        myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
        myMsg.setTextSize(20);
        myMsg.setTextColor(Color.WHITE);
        builder.setCustomTitle(myMsg);
        builder.setView(input);


// Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                m_Text = input.getText().toString();
                SWOTObject so = new SWOTObject(Database.getId());
                Database.setId(Database.getId() + 1);
                so = new SWOTObject(Database.getId());
                Database.getSWOTs().put(so.getId(), so);
                Database.setCurrentSWOT(so);
                Bundle extra = new Bundle();
                extra.putInt("objectId", so.getId());
                Intent i = new Intent(ctx, SWOTScreen.class);
                i.putExtra("bundle", extra);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if(m_Text.length() > 0)
                    so.setName(m_Text);
                else{
                    String s = "Saved as " + Database.getCurrentSWOT().getName();
                    Toast.makeText(ctx, s, Toast.LENGTH_SHORT).show();
                }
                startActivity(i);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }


}
