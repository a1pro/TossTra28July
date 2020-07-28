package com.app.tosstra.fragments.driver;

import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.tosstra.activities.AppUtil;
import com.app.tosstra.adapters.RVMyJobAdapter;
import com.app.tosstra.R;
import com.app.tosstra.models.AllJobsToDriver;
import com.app.tosstra.services.Interface;
import com.app.tosstra.utils.CommonUtils;
import com.app.tosstra.utils.PreferenceHandler;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyJobFragment extends Fragment {
    RecyclerView rvMyJob;
    RVMyJobAdapter rvMyJobAdapter;
    TextView tvEmptyView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_job, container, false);
        initUI(view);
        hitAllJobsToDriverAPI();
        return view;
    }

    private void initUI(View view) {
        rvMyJob = view.findViewById(R.id.rvMyJob);
        tvEmptyView = view.findViewById(R.id.empty_view);
    }


    private void hitAllJobsToDriverAPI() {
        final Dialog dialog = AppUtil.showProgress(getActivity());
        Interface service = CommonUtils.retroInit();
        Call<AllJobsToDriver> call = service.ourJobs(PreferenceHandler.readString(getContext(), PreferenceHandler.USER_ID, ""));
        call.enqueue(new Callback<AllJobsToDriver>() {
            @Override
            public void onResponse(Call<AllJobsToDriver> call, Response<AllJobsToDriver> response) {
                AllJobsToDriver data = response.body();
                assert data != null;
                if (data.getCode().equalsIgnoreCase("201")) {
                    dialog.dismiss();
                    rvMyJobAdapter = new RVMyJobAdapter(getActivity(), data);
                    rvMyJob.setLayoutManager(new LinearLayoutManager(getContext()));
                    rvMyJob.setAdapter(rvMyJobAdapter);
                } else {
                    dialog.dismiss();
                    tvEmptyView.setVisibility(View.VISIBLE);
                    rvMyJob.setVisibility(View.GONE);
                  //  CommonUtils.showSmallToast(getContext(), data.getMessage());
                }
            }

            @Override
            public void onFailure(Call<AllJobsToDriver> call, Throwable t) {
                dialog.dismiss();
                CommonUtils.showSmallToast(getContext(), t.getMessage());
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        hitAllJobsToDriverAPI();
    }
}