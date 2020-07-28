package com.app.tosstra.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.app.tosstra.fragments.dispacher.ListViewFragment;
import com.app.tosstra.fragments.dispacher.MapViewFragment;

public class VPActiveDriver extends FragmentStatePagerAdapter {
    public VPActiveDriver(FragmentManager fm) {
        super(fm);

    }

    @NonNull
    @Override
    public Fragment getItem(int position)
    {
        Fragment fragment = null;
        if (position == 0)
            fragment = new ListViewFragment();
        else if (position == 1)
            fragment = new MapViewFragment();
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        String title = null;
        if (position == 0)
            title = "List View";
        else if (position == 1)
            title = "Map View";

        return title;
    }
}
