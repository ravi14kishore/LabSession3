package com.example.kisho.mapandphotoapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class HomeMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Geocoder geocode;
    LocationManager currentLocation;
    LocationListener currentLocationListener;
    LatLng currentLocationLatLng;
    StringBuilder UserPresentAddress;
    Bitmap bitMapMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Intent getPreviousIntent = getIntent();
        LoginDetails NewLogin = (LoginDetails)getPreviousIntent.getSerializableExtra("New Login");
       bitMapMarker = NewLogin.DisplayPicture;

    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        geocode = new Geocoder(this);
        currentLocation = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        currentLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

                Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(intent);

            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }

        currentLocation.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, currentLocationListener);
        currentLocationLatLng = new LatLng(currentLocation.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLatitude(), currentLocation.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLongitude());

        try {

            List<Address> PresentLocationAddresses = geocode.getFromLocation(currentLocation.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLatitude(), currentLocation.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLongitude(), 1);
            Address PresentLocationAddress = PresentLocationAddresses.get(0);
            UserPresentAddress =  new StringBuilder();

            for (int i = 0; i < PresentLocationAddress.getMaxAddressLineIndex(); i++) {

                UserPresentAddress.append(PresentLocationAddress.getAddressLine(i)).append("\t");

            }
            UserPresentAddress.append(PresentLocationAddress.getCountryName()).append("\t");

        } catch (IOException e) {
            e.printStackTrace();
        }
        mMap.addMarker(new MarkerOptions().position(currentLocationLatLng).title("you are here").snippet(UserPresentAddress.toString())).setIcon(BitmapDescriptorFactory.fromBitmap(bitMapMarker));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocationLatLng, 7));

    }
}
