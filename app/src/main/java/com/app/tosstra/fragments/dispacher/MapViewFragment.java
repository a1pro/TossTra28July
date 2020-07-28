package com.app.tosstra.fragments.dispacher;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.app.tosstra.R;
import com.app.tosstra.activities.AppUtil;
import com.app.tosstra.adapters.ListViewAdapter;
import com.app.tosstra.interfaces.PassDriverIds;
import com.app.tosstra.models.AllDrivers;
import com.app.tosstra.services.Interface;
import com.app.tosstra.utils.CommonUtils;
import com.app.tosstra.utils.PreferenceHandler;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.app.tosstra.fragments.driver.AllJobsFragment.currentLatitude;
import static com.app.tosstra.fragments.driver.AllJobsFragment.currentLongitude;

public class MapViewFragment extends Fragment implements View.OnClickListener {
    private GoogleMap mMap;

    ArrayList<Double> lat = new ArrayList<Double>();
    ArrayList<Double> lon = new ArrayList<Double>();
    ArrayList<String> address = new ArrayList<String>();
    private MarkerOptions place1, place2;
    LocationManager locationManager;
    GoogleApiClient mGoogleApiClient;
    private static final int REQUEST_LOCATION = 1;
    private static final String TAG = "MainActivity";
    public double currentLatitude, currentLongitude;
    private Button btnGetDirection;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map_view, container, false);
        btnGetDirection=view.findViewById(R.id.btnGetDirection);
        btnGetDirection.setOnClickListener(this);
        hitActiveDriverAPI();

        return view;
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

                    for (int i = 0; i < data.getData().size(); i++) {
                        lat.addAll(Collections.singleton(data.getData().get(i).getPuplatitude()));
                        lon.addAll(Collections.singleton(data.getData().get(i).getPuplongitude()));
                        address.addAll(Collections.singleton(data.getData().get(i).getCompanyName()));
                    }
                    SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
                    mapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(GoogleMap googleMap) {
                            int i;
                            for (i = 0; i < lat.size(); i++) {
                                LatLng cur = new LatLng(lat.get(i), lon.get(i));
                                place1 = new MarkerOptions().position(cur).
                                        title(address.get(i)).icon(BitmapDescriptorFactory.fromResource(R.mipmap.marker));
                                googleMap.addMarker(place1);
                                CameraPosition googlePlex = CameraPosition.builder()
                                        .target(cur)
                                        .zoom(12)
                                        .bearing(0)
                                        .tilt(45)
                                        .build();
                                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex));
                                googleMap.getUiSettings().isMyLocationButtonEnabled();
                                googleMap.getUiSettings().isZoomControlsEnabled();
                                googleMap.getUiSettings().setMyLocationButtonEnabled(true);
                            }
                        }
                    });
                    dialog.dismiss();
                    CommonUtils.showLongToast(getContext(), data.getMessage());

                } else {
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

    @Override
    public void onClick(View v) {

        hitActiveDriverAPI();
    }
}