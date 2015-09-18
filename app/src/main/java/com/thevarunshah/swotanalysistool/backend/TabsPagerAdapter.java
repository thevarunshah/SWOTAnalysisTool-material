package com.thevarunshah.swotanalysistool.backend;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.thevarunshah.swotanalysistool.OpportunitiesScreen;
import com.thevarunshah.swotanalysistool.R;
import com.thevarunshah.swotanalysistool.StrengthsScreen;
import com.thevarunshah.swotanalysistool.ThreatsScreen;
import com.thevarunshah.swotanalysistool.WeaknessesScreen;

public class TabsPagerAdapter extends FragmentPagerAdapter {

    CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created

    // Build a Constructor and assign the passed Values to appropriate values in the class
    public TabsPagerAdapter(FragmentManager fm,CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);

        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;


    }
	@Override
    public Fragment getItem(int position) {
        // Inflate a new layout from our resources
//		View view=null;
        SWOTObject so = Database.getCurrentSWOT();

        switch (position) {
            case 0:
                return StrengthsScreen.newInstance(so.getStrengths());
            case 1:
                return WeaknessesScreen.newInstance(so.getWeaknesses());
            case 2:
                return OpportunitiesScreen.newInstance(so.getOpportunities());
            case 3:
                return ThreatsScreen.newInstance(so.getThreats());
        }
        return null;
    }
    // This method return the titles for the Tasbs in the Tab Strip

    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

    // This method return the Number of tabs for the tabs Strip

    @Override
    public int getCount() {
        return NumbOfTabs;
    }


}
