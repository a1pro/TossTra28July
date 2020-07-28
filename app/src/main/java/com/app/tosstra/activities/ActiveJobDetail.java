package com.app.tosstra.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.tosstra.R;
import com.app.tosstra.models.AllDrivers;
import com.app.tosstra.models.AllJobsToDriver;
import com.app.tosstra.services.Interface;
import com.app.tosstra.utils.CommonUtils;
import com.app.tosstra.utils.PreferenceHandler;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActiveJobDetail extends AppCompatActivity implements View.OnClickListener {
    TextView tvCompanyValue, tvPickAddval, tvDropAddval, tvDriverNameVal, tvDriverEmailVal,
            startTimetVal, dateToVal, dateStartVal, tomeToVal;
    ImageView iv_back;
   // private AllJobsToDriver allJobsToDriver;
    private Button btn_end_job, btn_dirction;
    private double cur_lat;
    private double cur_lon;
    private double dr_lat;
    private double dr_lon;
    private double dri_lat;
    private double dri_lon;
    int pos;
    String job_id;
    private String dis_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_job_detail);
        initUI();
        job_detail();
    }

    private void initUI() {
        pos= 0;
        job_id=getIntent().getStringExtra("job_id1");
        dis_id=getIntent().getStringExtra("dis_id1");

        btn_dirction = findViewById(R.id.btn_dirction);
        //allJobsToDriver = (AllJobsToDriver) getIntent().getSerializableExtra("our_job"); //Obtaining data
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
                Intent i = new Intent(ActiveJobDetail.this, SeeDriverLocation.class);
                i.putExtra("dis_id", dis_id);
                i.putExtra("job_id", job_id);
                i.putExtra("pup_lat",cur_lat);
                i.putExtra("pup_lon",cur_lon);
                i.putExtra("dr_lat",dr_lat);
                i.putExtra("dr_lon",dr_lon);
                startActivity(i);
                break;
        }
    }


    private void job_detail() {
        final Dialog dialog = AppUtil.showProgress(ActiveJobDetail.this);
        Interface service = CommonUtils.retroInit();
        Call<AllJobsToDriver> call = service.single_job_detail(job_id,dis_id);
        call.enqueue(new Callback<AllJobsToDriver>() {
            @Override
            public void onResponse(Call<AllJobsToDriver> call, Response<AllJobsToDriver> response) {
                AllJobsToDriver data = response.body();
                assert data != null;
                if (data.getCode().equalsIgnoreCase("201")) {
                    dialog.dismiss();
                    tvCompanyValue.setText(data.getData().get(pos).getCompanyName());
                    tvPickAddval.setText(data.getData().get(pos).getPupStreet());
                    tvDropAddval.setText(data.getData().get(pos).getDrpStreet());
                    tvDriverNameVal.setText(data.getData().get(pos).getFirstName() + " " + data.getData().get(pos).getLastName());
                    tvDriverEmailVal.setText(data.getData().get(pos).getEmail());
                    dateStartVal.setText(data.getData().get(pos).getDateFrom());
                    dateToVal.setText(data.getData().get(pos).getDateTo());
                    startTimetVal.setText(data.getData().get(pos).getEndTime());
                    tomeToVal.setText(data.getData().get(pos).getEndTime());
                    cur_lat=data.getData().get(pos).getPuplatitude();
                    cur_lon=data.getData().get(pos).getPuplongitude();
                    dr_lat=data.getData().get(pos).getDrplatitude();
                    dr_lon=data.getData().get(pos).getDrplongitude();
                    dri_lat=data.getData().get(pos).getDriverlatitude();
                    dri_lon=data.getData().get(pos).getDriverlongitude();
                    CommonUtils.showLongToast(ActiveJobDetail.this, data.getMessage());
                    Log.e("qwww", String.valueOf(dri_lat));
                }
                else {
                    dialog.dismiss();
                    CommonUtils.showLongToast(ActiveJobDetail.this, data.getMessage());
                }
            }

            @Override
            public void onFailure(Call<AllJobsToDriver> call, Throwable t) {
                dialog.dismiss();
                CommonUtils.showSmallToast(ActiveJobDetail.this, t.getMessage());
            }
        });
    }
    private void hitJobComplete() {
        final Dialog dialog = AppUtil.showProgress(ActiveJobDetail.this);
        Interface service = CommonUtils.retroInit();
        Call<AllJobsToDriver> call = service.job_complete(PreferenceHandler.readString(ActiveJobDetail.this,
                PreferenceHandler.USER_ID, ""), job_id);
        call.enqueue(new Callback<AllJobsToDriver>() {
            @Override
            public void onResponse(Call<AllJobsToDriver> call, Response<AllJobsToDriver> response) {
                AllJobsToDriver data = response.body();
                assert data != null;
                if (data.getCode().equalsIgnoreCase("201")) {
                    dialog.dismiss();
                    CommonUtils.showSmallToast(ActiveJobDetail.this, data.getMessage());
                    finish();
                } else {
                    dialog.dismiss();
                    CommonUtils.showSmallToast(ActiveJobDetail.this, data.getMessage());
                }
            }

            @Override
            public void onFailure(Call<AllJobsToDriver> call, Throwable t) {
                dialog.dismiss();
                CommonUtils.showSmallToast(ActiveJobDetail.this, t.getMessage());
            }
        });
    }
}