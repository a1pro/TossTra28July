package com.app.tosstra.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.tosstra.R;
import com.app.tosstra.models.AllJobsToDriver;
import com.app.tosstra.models.GenricModel;
import com.app.tosstra.services.Interface;
import com.app.tosstra.utils.CommonUtils;
import com.app.tosstra.utils.PreferenceHandler;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JobOfferActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView iv_back_offer;
    AllJobsToDriver allJobsToDriver;
    TextView tvCompanyName, tvPickAddval, tvCity, tvState, tvZip, tvDropAddval, tvCityDrop,
            tvStateDrop, tvZipDrop, tvDateRange, tvToRange, tvStartTime, tvEndTime;
    EditText et_add_info;
    Button btn_reject, btn_accept;
    int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_offer);
        allJobsToDriver = (AllJobsToDriver) getIntent().getSerializableExtra("job_offer"); //Obtaining data
        pos= (int) getIntent().getSerializableExtra("job_offer_pos");
        initUI();
        setData();
    }

    private void setData() {
        tvCompanyName.setText(allJobsToDriver.getData().get(pos).getRate());
        tvPickAddval.setText(allJobsToDriver.getData().get(pos).getPupStreet());
        tvCity.setText(allJobsToDriver.getData().get(pos).getPupCity());
        tvState.setText(allJobsToDriver.getData().get(pos).getPupState());
        tvZip.setText(allJobsToDriver.getData().get(pos).getPupZipcode());
        tvDropAddval.setText(allJobsToDriver.getData().get(pos).getDrpStreet());
        tvCityDrop.setText(allJobsToDriver.getData().get(pos).getDrpCity());
        tvStateDrop.setText(allJobsToDriver.getData().get(pos).getDrpState());
        tvZipDrop.setText(allJobsToDriver.getData().get(pos).getDrpZipcode());
        tvDateRange.setText("Start From " + allJobsToDriver.getData().get(pos).getDateFrom());
        tvToRange.setText("To " + allJobsToDriver.getData().get(pos).getDateTo());
        tvStartTime.setText("Start Time " + allJobsToDriver.getData().get(pos).getStartTime());
        tvEndTime.setText("End Time " + allJobsToDriver.getData().get(pos).getEndTime());
        et_add_info.setText(allJobsToDriver.getData().get(pos).getAdditinalInstructions());
    }

    private void initUI() {
        et_add_info = findViewById(R.id.et_add_info);
        iv_back_offer = findViewById(R.id.iv_back_offer);
        tvCompanyName = findViewById(R.id.tvCompanyName);
        tvPickAddval = findViewById(R.id.tvPickAddval);
        tvCity = findViewById(R.id.tvCity);
        tvState = findViewById(R.id.tvState);
        tvZip = findViewById(R.id.tvZip);
        tvDropAddval = findViewById(R.id.tvDropAddval);
        tvCityDrop = findViewById(R.id.tvCityDrop);
        tvStateDrop = findViewById(R.id.tvStateDrop);
        tvZipDrop = findViewById(R.id.tvZipDrop);
        tvDateRange = findViewById(R.id.tvDateRange);
        tvToRange = findViewById(R.id.tvToRange);
        tvStartTime = findViewById(R.id.tvStartTime);
        tvEndTime = findViewById(R.id.tvEndTime);
        btn_reject = findViewById(R.id.btn_reject);
        btn_accept = findViewById(R.id.btn_accept);
        btn_accept.setOnClickListener(this);
        btn_reject.setOnClickListener(this);
        iv_back_offer.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back_offer:
                finish();
                break;
            case R.id.btn_accept:
                hitAcceptReject("1");
                break;
            case R.id.btn_reject:
                hitAcceptReject("0");
                break;
        }
    }

    private void hitAcceptReject(String status) {
        final Dialog dialog = AppUtil.showProgress(JobOfferActivity.this);
        Interface service = CommonUtils.retroInit();
        Call<GenricModel> call = service.acceptReject(PreferenceHandler.readString(JobOfferActivity.this,
                PreferenceHandler.USER_ID,""), allJobsToDriver.getData().get(pos).getJobId(),
                status, allJobsToDriver.getData().get(pos).getDispatcherId());
        call.enqueue(new Callback<GenricModel>() {
            @Override
            public void onResponse(Call<GenricModel> call, Response<GenricModel> response) {
                GenricModel data = response.body();
                assert data != null;
                if (data.getCode().equalsIgnoreCase("201")) {
                    dialog.dismiss();
                    CommonUtils.showLongToast(JobOfferActivity.this, data.getMessage());
                    finish();
                } else {
                    dialog.dismiss();
                    CommonUtils.showLongToast(JobOfferActivity.this, data.getMessage());
                }
            }

            @Override
            public void onFailure(Call<GenricModel> call, Throwable t) {
                dialog.dismiss();
                CommonUtils.showSmallToast(JobOfferActivity.this, t.getMessage());
            }
        });
    }
}