package com.app.tosstra.fragments.dispacher;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.tosstra.adapters.VPActiveDriver;
import com.app.tosstra.R;
import com.google.android.material.tabs.TabLayout;


public class ActiverDriverFragments extends Fragment {

    private ViewPager view_pager;
    private VPActiveDriver viewPagerAdapter;
    private TabLayout tablayout;

    public ActiverDriverFragments() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_activer_driver_fragments, container, false);
        view_pager = view.findViewById(R.id.view_pager);
        viewPagerAdapter = new VPActiveDriver(getFragmentManager());
        view_pager.setAdapter(viewPagerAdapter);
        tablayout =view.findViewById(R.id.tablayout);
        tablayout.setupWithViewPager(view_pager);
        return view;
    }
}