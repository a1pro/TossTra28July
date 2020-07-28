package com.app.tosstra.activities;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.app.tosstra.R;
import com.app.tosstra.fragments.driver.AllJobsFragment;
import com.app.tosstra.googleMaps.FetchURL;
import com.app.tosstra.googleMaps.TaskLoadedCallback;
import com.app.tosstra.models.AllJobsToDriver;
import com.app.tosstra.services.Interface;
import com.app.tosstra.utils.CommonUtils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.app.tosstra.fragments.driver.AllJobsFragment.currentLatitude;
import static com.app.tosstra.fragments.driver.AllJobsFragment.currentLongitude;

public class SeeDirectionActivity extends AppCompatActivity implements OnMapReadyCallback, TaskLoadedCallback, View.OnClickListener {
    private GoogleMap mMap;
    private MarkerOptions place1, place2, place3;
    private Polyline currentPolyline;
    ImageView iv_back_addJob;
    private double cur_lat;
    private double cur_lon;
    private double dr_lat;
    private double dr_lon;
    private double dri_lat, dri_lon;
    String type;
    int pos=0;
    String dis_id,job_id;
    private double cur_lat_api,cur_lon_api,dr_lat_api,dr_lon_api,dri_lat_api,dri_lon_api;
    MapFragment mapFragment;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        initUI();
         mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.mapNearBy);
        if (mapFragment != null)
            mapFragment.getMapAsync(this);
        type = getIntent().getStringExtra("type");
        dis_id = getIntent().getStringExtra("dis_id");
        job_id = getIntent().getStringExtra("job_id");
        cur_lat = getIntent().getDoubleExtra("cur_lat", cur_lat);
        cur_lon = getIntent().getDoubleExtra("cur_lon", cur_lon);
        dr_lat = getIntent().getDoubleExtra("dr_lat", dr_lat);
        dr_lon = getIntent().getDoubleExtra("dr_lon", dr_lon);
        dri_lat = getIntent().getDoubleExtra("dri_lat", dri_lat);
        dri_lon = getIntent().getDoubleExtra("dri_lon", dri_lon);



        Log.e("dr_lat", String.valueOf(dr_lat_api));
        Log.e("dr_lon", String.valueOf(dr_lon_api));
        Log.e("jobid", String.valueOf(job_id));
        Log.e("disid", String.valueOf(dis_id));



        if (type != null && type.equalsIgnoreCase("ActiveJobs")) {
            job_detail();
            place1 = new MarkerOptions().position(new LatLng(cur_lat_api, cur_lon_api));
            place2 = new MarkerOptions().position(new LatLng(dr_lat_api, dr_lon_api));
        }else {
            place1 = new MarkerOptions().position(new LatLng(cur_lat, cur_lon));
            place2 = new MarkerOptions().position(new LatLng(dr_lat, dr_lon));
        }


        //    btnGetDirection.performClick();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                new FetchURL(SeeDirectionActivity.this).execute(getUrl(place1.getPosition(), place2.getPosition()), "driving");
            }
        }, 1000);


        /*btnGetDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FetchURL(SeeDirectionActivity.this).execute(getUrl(place1.getPosition(), place2.getPosition()), "driving");
            }
        });
*/


       /* SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapNearBy);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }*/
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.mapNearBy);
        if (mapFragment != null)
            mapFragment.getMapAsync(this);

    }

    private void initUI() {
        iv_back_addJob = findViewById(R.id.iv_back_addJob);
        iv_back_addJob.setOnClickListener(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        boolean success = googleMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                        SeeDirectionActivity.this, R.raw.style_json));


        if (!success) {
            Log.e("ooo", "Style parsing failed.");
        }
        Log.d("mylog", "Added Markers");


        mMap.addMarker(place1);
        mMap.addMarker(place2);

        if (type != null && type.equalsIgnoreCase("ActiveJobs")) {
            place3 = new MarkerOptions().position(new LatLng(cur_lat_api, cur_lon_api));
            mMap.addMarker(place3);
            CameraPosition googlePlex = CameraPosition.builder()
                    .target(new LatLng(cur_lat_api, cur_lon_api))
                    .zoom(15)
                    .bearing(0)
                    .tilt(45)
                    .build();
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex));
        }else {
            CameraPosition googlePlex = CameraPosition.builder()
                    .target(new LatLng(cur_lat, cur_lon))
                    .zoom(15)
                    .bearing(0)
                    .tilt(45)
                    .build();
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex));
        }



    }

    private String getUrl(LatLng origin, LatLng dest) {
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        String mode = "mode=" + "driving";
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        String output = "json";
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + getString(R.string.google_maps_key);
        return url;
    }

    @Override
    public void onTaskDone(Object... values) {
        if (currentPolyline != null)
            currentPolyline.remove();
        currentPolyline = mMap.addPolyline((PolylineOptions) values[0]);
    }

    @Override
    public void onClick(View v) {
        finish();
    }

    private void job_detail() {
        final Dialog dialog = AppUtil.showProgress(SeeDirectionActivity.this);
        Interface service = CommonUtils.retroInit();
        Call<AllJobsToDriver> call = service.single_job_detail(job_id,dis_id);
        call.enqueue(new Callback<AllJobsToDriver>() {
            @Override
            public void onResponse(Call<AllJobsToDriver> call, Response<AllJobsToDriver> response) {
                AllJobsToDriver data = response.body();
                assert data != null;
                if (data.getCode().equalsIgnoreCase("201")) {
                    dialog.dismiss();
                    cur_lat_api=data.getData().get(pos).getPuplatitude();
                    cur_lon_api=data.getData().get(pos).getPuplongitude();
                    dr_lat_api=data.getData().get(pos).getDrplatitude();
                    dr_lon_api=data.getData().get(pos).getDrplongitude();
                    dri_lat_api=data.getData().get(pos).getDriverlatitude();
                    dri_lon_api=data.getData().get(pos).getDriverlongitude();
                    CommonUtils.showLongToast(SeeDirectionActivity.this, data.getMessage());
                    Log.e("qqq", String.valueOf(dr_lat_api));
                }
                else {
                    dialog.dismiss();
                    CommonUtils.showLongToast(SeeDirectionActivity.this, data.getMessage());
                }
            }

            @Override
            public void onFailure(Call<AllJobsToDriver> call, Throwable t) {
                dialog.dismiss();
                CommonUtils.showSmallToast(SeeDirectionActivity.this, t.getMessage());
            }
        });
    }
}