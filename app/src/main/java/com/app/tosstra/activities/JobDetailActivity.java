package com.app.tosstra.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.tosstra.R;
import com.app.tosstra.adapters.RVMyJobAdapter;
import com.app.tosstra.models.AllDrivers;
import com.app.tosstra.models.AllJobsToDriver;
import com.app.tosstra.services.Interface;
import com.app.tosstra.utils.CommonUtils;
import com.app.tosstra.utils.PreferenceHandler;

import java.io.Serializable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JobDetailActivity extends AppCompatActivity implements View.OnClickListener {
    TextView tvCompanyValue, tvPickAddval, tvDropAddval, tvDriverNameVal, tvDriverEmailVal,
            startTimetVal, dateToVal, dateStartVal, tomeToVal;
    ImageView iv_back;
    private AllJobsToDriver allJobsToDriver;
    private Button btn_end_job,btn_dirction;
    private double cur_lat,cur_lon,dr_lat,dr_lon;
    int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_detail);
        pos= getIntent().getIntExtra("position_my",0);
        initUI();
    }

    private void initUI() {
        btn_dirction=findViewById(R.id.btn_dirction);
        allJobsToDriver = (AllJobsToDriver) getIntent().getSerializableExtra("our_job"); //Obtaining data
        tvCompanyValue = findViewById(R.id.tvCompanyValue);
        iv_back = findViewById(R.id.iv_back);
        tvPickAddval = findViewById(R.id.tvPickAddval);
        tvDropAddval = findViewById(R.id.tvDropAddval);
        tvDriverNameVal = findViewById(R.id.tvDriverNameVal);
        tvDriverEmailVal = findViewById(R.id.tvDriverEmailVal);
        tomeToVal = findViewById(R.id.tomeToVal);
        dateStartVal = findViewById(R.id.dateStartVal);
        dateToVal = findViewById(R.id.dateToVal);
        startTimetVal = findViewById(R.id.startTimetVal);
        btn_end_job = findViewById(R.id.btn_end_job);
        btn_end_job.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        btn_dirction.setOnClickListener(this);

        tvCompanyValue.setText(allJobsToDriver.getData().get(pos).getCompanyName());
        tvPickAddval.setText(allJobsToDriver.getData().get(pos).getPupStreet());
        tvDropAddval.setText(allJobsToDriver.getData().get(pos).getDrpStreet());
        tvDriverNameVal.setText(allJobsToDriver.getData().get(pos).getFirstName() + " " + allJobsToDriver.getData().get(pos).getLastName());
        tvDriverEmailVal.setText(allJobsToDriver.getData().get(pos).getEmail());
        dateStartVal.setText(allJobsToDriver.getData().get(pos).getDateFrom());
        dateToVal.setText(allJobsToDriver.getData().get(pos).getDateTo());
        startTimetVal.setText(allJobsToDriver.getData().get(pos).getEndTime());
        tomeToVal.setText(allJobsToDriver.getData().get(pos).getEndTime());
        cur_lat=allJobsToDriver.getData().get(pos).getPuplatitude();
        cur_lon=allJobsToDriver.getData().get(pos).getPuplongitude();
        dr_lat=allJobsToDriver.getData().get(pos).getDrplatitude();
        dr_lon=allJobsToDriver.getData().get(pos).getDrplongitude();

        Log.e("posss", cur_lat+","+cur_lon);
    }


    @Override

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_end_job:
                hitJobComplete();
                break;
            case R.id.btn_dirction:
                Intent i=new Intent(JobDetailActivity.this,SeeDirectionActivity.class);
                i.putExtra("cur_lat",cur_lat);
                i.putExtra("cur_lon",cur_lon);
                i.putExtra("dr_lat",dr_lat);
                i.putExtra("dr_lon",dr_lon);
                startActivity(i);
                break;
        }
    }

    private void hitJobComplete() {
        final Dialog dialog = AppUtil.showProgress(JobDetailActivity.this);
        Interface service = CommonUtils.retroInit();
        Call<AllJobsToDriver> call = service.job_complete(PreferenceHandler.readString(JobDetailActivity.this, PreferenceHandler.USER_ID, ""), allJobsToDriver.getData().get(0).getJobId());
        call.enqueue(new Callback<AllJobsToDriver>() {
            @Override
            public void onResponse(Call<AllJobsToDriver> call, Response<AllJobsToDriver> response) {
                AllJobsToDriver data = response.body();
                assert data != null;
                if (data.getCode().equalsIgnoreCase("201")) {
                    dialog.dismiss();
                    CommonUtils.showSmallToast(JobDetailActivity.this, data.getMessage());
                    finish();

                } else {
                    dialog.dismiss();
                    CommonUtils.showSmallToast(JobDetailActivity.this, data.getMessage());
                }
            }

            @Override
            public void onFailure(Call<AllJobsToDriver> call, Throwable t) {
                dialog.dismiss();
                CommonUtils.showSmallToast(JobDetailActivity.this, t.getMessage());
            }
        });
    }
}