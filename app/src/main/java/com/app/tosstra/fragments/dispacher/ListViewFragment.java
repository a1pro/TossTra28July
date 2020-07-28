package com.app.tosstra.fragments.dispacher;

import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import com.app.tosstra.activities.AppUtil;
import com.app.tosstra.activities.SigninActivity;
import com.app.tosstra.adapters.ChildActieTruckAdapter;
import com.app.tosstra.adapters.ListViewAdapter;
import com.app.tosstra.R;
import com.app.tosstra.interfaces.PassDriverIds;
import com.app.tosstra.models.AllDrivers;
import com.app.tosstra.models.GenricModel;
import com.app.tosstra.services.Interface;
import com.app.tosstra.utils.CommonUtils;
import com.app.tosstra.utils.PreferenceHandler;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.app.tosstra.adapters.ChildActieTruckAdapter.interestList;
import static com.app.tosstra.fragments.dispacher.SeniorityTruckFragment.new_interestList_seniority;

public class ListViewFragment extends Fragment implements View.OnClickListener {
    ListViewAdapter listViewAdapter;
    RecyclerView rvList;
    Button btn_end_job;
    public static List<String> new_interestList_LV = new ArrayList<>();
    String lst = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_view, container, false);
        initUI(view);
        hitActiveDriverAPI();
        return view;
    }

    private void initUI(View view) {
        rvList = view.findViewById(R.id.rvList);
        btn_end_job = view.findViewById(R.id.btn_end_job);
        btn_end_job.setOnClickListener(this);


    }

    private void hitActiveDriverAPI() {
        final Dialog dialog = AppUtil.showProgress(getActivity());
        Interface service = CommonUtils.retroInit();
        Call<AllDrivers> call = service.active_drivers(PreferenceHandler.readString(getContext(), PreferenceHandler.USER_ID, ""));
        call.enqueue(new Callback<AllDrivers>() {
            @Override
            public void onResponse(Call<AllDrivers> call, Response<AllDrivers> response) {
                AllDrivers data = response.body();
                assert data != null;
                if (data.getCode().equalsIgnoreCase("201")) {
                    dialog.dismiss();
                    CommonUtils.showLongToast(getContext(), data.getMessage());
                    listViewAdapter = new ListViewAdapter(getContext(), data, passDriverIds);
                    rvList.setLayoutManager(new LinearLayoutManager(getContext()));
                    rvList.setAdapter(listViewAdapter);
                        btn_end_job.setVisibility(View.VISIBLE);
                } else {
                    btn_end_job.setVisibility(View.GONE);
                    dialog.dismiss();
                    CommonUtils.showLongToast(getContext(), data.getMessage());
                }
            }

            @Override
            public void onFailure(Call<AllDrivers> call, Throwable t) {
                dialog.dismiss();
                CommonUtils.showSmallToast(getContext(), t.getMessage());
            }
        });
    }

    PassDriverIds passDriverIds = new PassDriverIds() {
        @Override
        public void selectedDriverIdList(List<String> interestList) {
            new_interestList_LV = interestList;
            CommonUtils.showSmallToast(getContext(),String.valueOf(new_interestList_LV.toString()));
         //   String s = String.valueOf(new_interestList_LV.size());
            // tvSelected.setText("Total "+s+" Selected");
        }
    };



    private void hitEndJobAPI() {
        String mul_driver_ids;
        lst=new_interestList_LV.toString();
        String StringAdd = lst.toString();
        int comma = StringAdd.lastIndexOf(',');
        String finalStr = lst.replaceAll("[\\[\\](){}(,)*$]", "");
        mul_driver_ids =finalStr.replace(" ",",");
        Log.e("active_dri_id",mul_driver_ids);

        final Dialog dialog = AppUtil.showProgress(getActivity());
        Interface service = CommonUtils.retroInit();
        Call<GenricModel> call = service.end_driver_job(PreferenceHandler.readString(getContext(), PreferenceHandler.USER_ID, ""),
                mul_driver_ids);
        call.enqueue(new Callback<GenricModel>() {
            @Override
            public void onResponse(Call<GenricModel> call, Response<GenricModel> response) {
                GenricModel data = response.body();
                assert data != null;
                if (data.getCode().equalsIgnoreCase("201")) {
                    new_interestList_LV.clear();
                    dialog.dismiss();
                    hitActiveDriverAPI();

                } else {
                    dialog.dismiss();
                    // CommonUtils.showLongToast(context, data.getMessage() + ChildActieTruckAdapter.this.driver_id);
                }
            }

            @Override
            public void onFailure(Call<GenricModel> call, Throwable t) {
                dialog.dismiss();
                CommonUtils.showSmallToast(getActivity(), t.getMessage());
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_end_job:
                hitEndJobAPI();
                break;

        }
    }
}
