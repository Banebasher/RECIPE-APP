package com.example.recipe;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationRequest;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.recipe.databinding.ActivityMapBinding;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Map extends DrawerBaseActivity implements OnMapReadyCallback {

    ModelClass modelClass;
    Boolean isPermissionGranted = true;
    GoogleMap googleMap;
    ActivityMapBinding activityMapBinding;
    EditText editTextsearchview;
    ImageView searchbutton;
    LocationRequest locationRequest;
    String address = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMapBinding = ActivityMapBinding.inflate(getLayoutInflater());
        allocateActivityTitle("Maps");

        setContentView(activityMapBinding.getRoot());

        editTextsearchview = findViewById(R.id.mapsearch);
        searchbutton = findViewById(R.id.searchbutton);

if(ProfilePage.getWait() == 1){
        cursor = sqLdbHelper.getusername(LoginPage.getValue());

while(cursor.moveToNext())
{
    editTextsearchview.setText(cursor.getString(4));
    ProfilePage.wait = 0;
}}
        checkPermission();

        if (isPermissionGranted) {
            if (checkgoogleplayservices()) {
                SupportMapFragment supportMapFragment = SupportMapFragment.newInstance();
                getSupportFragmentManager().beginTransaction().add(R.id.container, supportMapFragment).commit();
                supportMapFragment.getMapAsync(this);
            } else {
                Toast.makeText(this, "Google Play Services Not Available", Toast.LENGTH_SHORT).show();
            }
        }
        searchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String location = editTextsearchview.getText().toString();
                if (location == null) {
                    Toast.makeText(Map.this, "Type location", Toast.LENGTH_SHORT).show();
                } else {
                    Geocoder geocoder = new Geocoder(Map.this, Locale.getDefault());
                    try {
                        List<Address> listaddress = geocoder.getFromLocationName(location, 1);
                        if (listaddress.size() > 0) {
                            LatLng latLng = new LatLng(listaddress.get(0).getLatitude(), listaddress.get(0).getLongitude());
                            MarkerOptions markerOptions = new MarkerOptions();
                            markerOptions.title(location);
                            markerOptions.position(latLng);
                            googleMap.addMarker(markerOptions);
                            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 12);
                            googleMap.animateCamera(cameraUpdate);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }


    private Boolean checkgoogleplayservices() {

        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int result = googleApiAvailability.isGooglePlayServicesAvailable(this);
        if (result == ConnectionResult.SUCCESS) {
            return true;
        } else if (googleApiAvailability.isUserResolvableError(result)) {
            Dialog dialog = googleApiAvailability.getErrorDialog(this, result, 201, new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    Toast.makeText(Map.this, "User Cancelled Dialog", Toast.LENGTH_SHORT).show();
                }
            });

            dialog.show();
        }

        return false;
    }

    private void checkPermission() {
        Dexter.withContext(this).withPermission(Manifest.permission.ACCESS_FINE_LOCATION).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                isPermissionGranted = true;
                Toast.makeText(Map.this, "Permission Granted", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), "");
                intent.setData(uri);
                startActivity(intent);

            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();
    }

    @Override
    public void onMapReady(@NonNull GoogleMap map) {
        String searchlocation = editTextsearchview.getText().toString();
        googleMap = map;
        cursor = sqLdbHelper.getusername(LoginPage.getValue());

        while (cursor.moveToNext()) {
            address = cursor.getString(4);
        }
        Geocoder geocoder = new Geocoder(Map.this, Locale.getDefault());
        try {
            List<Address> listaddress = geocoder.getFromLocationName(address, 1);
            if (listaddress.size() > 0) {
                LatLng latLng = new LatLng(listaddress.get(0).getLatitude(), listaddress.get(0).getLongitude());
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.title(address);
                markerOptions.position(latLng);
                googleMap.addMarker(markerOptions);
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 12);
                googleMap.animateCamera(cameraUpdate);

                // googleMap.getUiSettings().setZoomControlsEnabled(true);
  /*      googleMap.getUiSettings().setCompassEnabled(true);
        googleMap.getUiSettings().setZoomGesturesEnabled(true);
        googleMap.getUiSettings().setScrollGesturesEnabled(true);
        googleMap.getUiSettings().setRotateGesturesEnabled(true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        googleMap.setMyLocationEnabled(true);*/
            }






 /*   @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.noneMap:
            {
                googleMap.setMapType(GoogleMap.MAP_TYPE_NONE);
                break;
            }
            case R.id.normalmap:
            {
                googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;
            }
            case R.id.satellitemap:
            {
                googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;
            }
            case R.id.maphybrid:
            {
                googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;
            }
            case R.id.mapterrain:
            {
                googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                break;
            }

        }
        return super.onOptionsItemSelected(item);
    }*/


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}