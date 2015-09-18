package com.thevarunshah.swotanalysistool;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import android.graphics.Color;
import android.os.Bundle;
import android.app.AlertDialog;
import android.text.InputType;
import android.content.DialogInterface;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;

import com.thevarunshah.swotanalysistool.backend.Database;
import com.thevarunshah.swotanalysistool.backend.SWOTObject;
import com.thevarunshah.swotanalysistool.backend.TabsPagerAdapter;
import com.thevarunshah.swotanalysistool.backend.SlidingTabLayout;


public class SWOTScreen extends AppCompatActivity {

    ViewPager pager;
    TabsPagerAdapter adapter;
    SlidingTabLayout tabs;
    CharSequence Titles[]={"S","W", "O", "T"};
    int Numboftabs =4;
    Toolbar toolbar;
    private String m_Text = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		int swotId = getIntent().getBundleExtra("bundle").getInt("objectId");
		SWOTObject so = Database.getSWOTs().get(swotId);

		Database.setCurrentSWOT(so);

        setContentView(R.layout.tab_screen);
        toolbar = (Toolbar) findViewById(R.id.swot_tool_bar);
        toolbar.setTitle(Database.getCurrentSWOT().getName());
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_48dp);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().setWindowAnimations(0);
        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        adapter =  new TabsPagerAdapter(getSupportFragmentManager(),Titles,Numboftabs);

        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);

        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tabsScrollColor);
            }
        });

        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(pager);


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_save:
                //openSearch();
                    String title = (String) toolbar.getTitle();
			        Database.getCurrentSWOT().setName(title);
			        Toast.makeText(getApplicationContext(), "Saved!", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_settings:
                getInput();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.swot_screen, menu);
        return true;
    }

    public void getInput(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Title");

// Set up the input
        final EditText input = new EditText(this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setText(Database.getCurrentSWOT().getName());
        builder.setView(input);

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
                if(m_Text.length() > 0) {
                    Intent in = getIntent();
                    Database.getCurrentSWOT().setName(m_Text);
                    finish();
                    startActivity(in);
                }
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

	@Override
	public void onPause(){
		super.onPause();

		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try {
			fos = openFileOutput("swot_backup.ser", MODE_PRIVATE);
			oos = new ObjectOutputStream(fos);
			oos.writeInt(Database.getId());
			oos.writeObject(Database.getSWOTs());
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try{
				oos.close();
				fos.close();
			} catch (Exception e){
				e.printStackTrace();
			}
		}
	}
}
