package com.example.ti6a006161utsga;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TitikLokasi#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TitikLokasi extends Fragment implements
        TaskAddress.OnTaskCompleted {

    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private static final String TRACKING_LOCATION_KEY = "Lacak";

    private Button btn_dapat;
    private ImageView mAndroidImageView;

    private boolean mTrackingLocation;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationCallback mLocationCallback;

    EditText latitude, longitude, nm_lengkap, no_wa;
    TextView lokasi_saat_ini;

    LottieAnimationView fire;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TitikLokasi() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TitikLokasi.
     */
    // TODO: Rename and change types and number of parameters
    public static TitikLokasi newInstance(String param1, String param2) {
        TitikLokasi fragment = new TitikLokasi();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View a = inflater.inflate(R.layout.fragment_titik_lokasi, container, false);

        btn_dapat = (Button) a.findViewById(R.id.btn_dapat);
        lokasi_saat_ini = (TextView) a.findViewById(R.id.lokasi_saat_ini);
        latitude = (EditText) a.findViewById(R.id.latitude);
        longitude = (EditText) a.findViewById(R.id.longitude);
        nm_lengkap = (EditText) a.findViewById(R.id.nm_Lengkap);
        no_wa = (EditText) a.findViewById(R.id.no_wa);
        fire = (LottieAnimationView) a.findViewById(R.id.fire);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(
                getActivity());

        if (savedInstanceState != null) {
            mTrackingLocation = savedInstanceState.getBoolean(
                    TRACKING_LOCATION_KEY);
        }

        btn_dapat.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(nm_lengkap.getText().toString().isEmpty()){
                    Toast.makeText(getActivity(), "Masukkan Nama", Toast.LENGTH_LONG).show();
                } else if (no_wa.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Masukkan No. WhatsApp", Toast.LENGTH_LONG).show();
                } else {
                    if (!mTrackingLocation) {
                        startTrackingLocation();
                    } else {
                        stopTrackingLocation();
                    }
                }
                /*if (ActivityCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.ACCESS_FINE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(getActivity(),
                                Manifest.permission.ACCESS_COARSE_LOCATION) !=
                                PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION}, 10);
                }
                mFusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    }
                });*/
            }
        });

        mLocationCallback = new LocationCallback() {

            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (mTrackingLocation) {
                    new TaskAddress(TitikLokasi.this, TitikLokasi.this)
                            .execute(locationResult.getLastLocation());
                }
            }
        };

        return a;
    }

    private void startTrackingLocation() {
        if (ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]
                            {Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
        } else {
            mFusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if(!(location == null)) {
                        latitude.setText(String.valueOf(location.getLatitude()));
                        latitude.setEnabled(false);
                        longitude.setText(String.valueOf(location.getLongitude()));
                        longitude.setEnabled(false);
                        nm_lengkap.setEnabled(false);
                        no_wa.setEnabled(false);
                        fire.playAnimation();
                    } else {
                        Toast.makeText(getActivity(), "Aktifkan Lokasi", Toast.LENGTH_LONG).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getActivity(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
            });
            mTrackingLocation = true;
            mFusedLocationClient.requestLocationUpdates
                    (getLocationRequest(),
                            mLocationCallback,
                            null /* Looper */);
            btn_dapat.setText("STOP");
        }
    }

    private void stopTrackingLocation() {
        if (mTrackingLocation) {
            mTrackingLocation = false;
            btn_dapat.setText("DAPATKAN LOKASI");
            lokasi_saat_ini.setText(R.string.home);
            nm_lengkap.setEnabled(true);
            nm_lengkap.setText("");
            no_wa.setText("");
            no_wa.setEnabled(true);
            latitude.setEnabled(true);
            latitude.setText("");
            longitude.setEnabled(true);
            longitude.setText("");
            fire.pauseAnimation();
        }
    }

    private LocationRequest getLocationRequest() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return locationRequest;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(TRACKING_LOCATION_KEY, mTrackingLocation);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_LOCATION_PERMISSION:

                if (grantResults.length > 0
                        && grantResults[0]
                        == PackageManager.PERMISSION_GRANTED) {
                    startTrackingLocation();
                } else {
                    Toast.makeText(getActivity(),
                            R.string.izin_ditolak,
                            Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
    @Override
    public void onTaskCompleted(String result){
        if (mTrackingLocation) {
            lokasi_saat_ini.setText(result);

        }
    }

    @Override
    public void onPause() {
        if (mTrackingLocation) {
            stopTrackingLocation();
            mTrackingLocation = true;
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        if (mTrackingLocation) {
            startTrackingLocation();
        }
        super.onResume();
    }

    @Override
    public void onDestroy() {
        if (mTrackingLocation){
            stopTrackingLocation();
        }
        super.onDestroy();
    }
}