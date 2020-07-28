package com.app.tosstra.fragments.dispacher;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.tosstra.activities.AddANewJobActivity;
import com.app.tosstra.activities.AppUtil;
import com.app.tosstra.adapters.SeniorityAdapter;
import com.app.tosstra.R;
import com.app.tosstra.interfaces.PassDriverIds;
import com.app.tosstra.interfaces.RefreshDriverList;
import com.app.tosstra.models.AllDrivers;
import com.app.tosstra.services.Interface;
import com.app.tosstra.utils.CommonUtils;
import com.app.tosstra.utils.PreferenceHandler;
import com.github.clans.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SeniorityTruckFragment extends Fragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    RecyclerView rvSeniority;
    SeniorityAdapter seniorityAdapter;
    FloatingActionButton fab;
    private SwipeRefreshLayout swiperefresh;
    private TextView tvEmptyView, tvSelected;
    public static List<String> new_interestList_seniority = new ArrayList<>();
    Dialog dialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_seniority_truck, container, false);
        init(view);
        hitFavAPI(refreshDriverList, "onCreate");
        return view;
    }


    private void init(View view) {
        fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(this);
        rvSeniority = view.findViewById(R.id.rvSeniority);
        swiperefresh = view.findViewById(R.id.swiperefresh);
        tvEmptyView = view.findViewById(R.id.empty_view);
        swiperefresh.setOnRefreshListener(this);
        tvSelected = view.findViewById(R.id.tvSelected);
        String s = String.valueOf(new_interestList_seniority.size());
        tvSelected.setText("Total " + s + " Selected");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                if (new_interestList_seniority != null)
                    if (new_interestList_seniority.size() == 0) {
                        CommonUtils.showSmallToast(getContext(), "Please select at least one driver");
                    } else {
                        Intent i = new Intent(getContext(), AddANewJobActivity.class);
                        i.putExtra("f_type", "sen");
                        startActivity(i);
                    }

                /*Intent i = new Intent(getContext(), AddANewJobActivity.class);
                startActivity(i);*/
        }
    }

    private void hitFavAPI(final RefreshDriverList refreshDriverList, String key) {
        if (key.equalsIgnoreCase("onCreate")) {
            dialog = AppUtil.showProgress(getActivity());
        }

        Interface service = CommonUtils.retroInit();
        Call<AllDrivers> call = service.onlyFav(PreferenceHandler.readString(getContext(), PreferenceHandler.USER_ID, ""));
        call.enqueue(new Callback<AllDrivers>() {
            @Override
            public void onResponse(Call<AllDrivers> call, Response<AllDrivers> response) {
                AllDrivers data = response.body();
                assert data != null;
                if (data.getCode().equalsIgnoreCase("201")) {
                    swiperefresh.setRefreshing(false);
                    dialog.dismiss();
                    //    CommonUtils.showLongToast(getContext(), data.getMessage());
                    seniorityAdapter = new SeniorityAdapter(getActivity(), data, refreshDriverList, passDriverIds);
                    rvSeniority.setLayoutManager(new LinearLayoutManager(getContext()));
                    rvSeniority.setAdapter(seniorityAdapter);

                } else {
                    dialog.dismiss();
                    swiperefresh.setRefreshing(false);

                    // CommonUtils.showLongToast(getContext(), data.getMessage());
                }
            }

            @Override
            public void onFailure(Call<AllDrivers> call, Throwable t) {
                dialog.dismiss();
                swiperefresh.setRefreshing(false);
                CommonUtils.showSmallToast(getContext(), t.getMessage());
            }
        });
    }

    @Override
    public void onRefresh() {
        hitFavAPI(refreshDriverList, "swipeToRefresh");
    }

    PassDriverIds passDriverIds = new PassDriverIds() {
        @Override
        public void selectedDriverIdList(List<String> interestList) {
            new_interestList_seniority = interestList;
            String s = String.valueOf(new_interestList_seniority.size());
            tvSelected.setText("Total " + s + " Selected");
        }
    };


    RefreshDriverList refreshDriverList = new RefreshDriverList() {
        @Override
        public void favClick() {
            hitFavAPI(refreshDriverList, "fav");
        }
    };

}