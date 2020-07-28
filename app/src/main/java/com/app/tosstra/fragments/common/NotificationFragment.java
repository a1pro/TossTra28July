package com.app.tosstra.fragments.common;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.tosstra.adapters.RVnotificationAdapter;
import com.app.tosstra.R;


public class NotificationFragment extends Fragment {
    RecyclerView rvNotification;
    RVnotificationAdapter rVnotificationAdapter;



    public NotificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_notification, container, false);;
        setAdapter(view);
        return view;
    }

    private void setAdapter(View view) {
        rvNotification=view.findViewById(R.id.rvNotification);
        rVnotificationAdapter=new RVnotificationAdapter();
        rvNotification.setLayoutManager(new LinearLayoutManager(getContext()));
        rvNotification.setAdapter(rVnotificationAdapter);

    }
}