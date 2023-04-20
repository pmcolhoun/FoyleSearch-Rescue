package com.example.fsdemo.Activities;

/**
 *MapsActivity tracks the user's location and maps their route using a combination of polylines and
 * markers placed throughout the map. A toast message is displayed to the user showing their address
 * as their location updates using a geocoder.
 */

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.fsdemo.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private boolean flag = true;

    LocationManager locationManager;
    LocationListener locationListener;

    Marker marker;
    Marker marker2;

    Polyline polyline;

    //ArrayList to store geo-points for current movement
    private ArrayList<LatLng> geoPoints;

    //Asks user permission to access their location
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 5, locationListener);
                    }//if
                }//if
            }//if
        }//if
    }//onRequestPermissionsResult()


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }//onCreate()


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //Sets camera zoom when user starts activity
        mMap.moveCamera(CameraUpdateFactory.zoomTo(15));

        //ArrayList to store the users movements
        final ArrayList<LatLng> locations = new ArrayList<>();

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());

                //Camera moves as user location updates
                mMap.moveCamera(CameraUpdateFactory.newLatLng(userLocation));

                //Sets the users starting location
                if(flag){
                    mMap.clear();
                    marker2 = mMap.addMarker(new MarkerOptions().position(userLocation).title("Starting Location"));
                }//if

                //Updates users current location and sets a marker - removes previous markers
                if(marker != null){
                    marker.remove();
                    marker = mMap.addMarker(new MarkerOptions().position(userLocation).title("User Location"));
                } else {
                    marker = mMap.addMarker(new MarkerOptions().position(userLocation).title("User Location"));
                }

                //Geocoder to pass co-ordinates (latitude and longitude and store within List to display users current address
                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

                try{
                    List<Address> listAddresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 2);

                    if (listAddresses != null && listAddresses.size() > 0) {

                        Log.i("PlaceInfo", listAddresses.get(0).toString());

                        String address = "";

                        if (listAddresses.get(0).getSubThoroughfare() != null) {

                            address += listAddresses.get(0).getSubThoroughfare() + " ";

                        }

                        if (listAddresses.get(0).getThoroughfare() != null) {

                            address += listAddresses.get(0).getThoroughfare() + ", ";

                        }

                        if (listAddresses.get(0).getLocality() != null) {

                            address += listAddresses.get(0).getLocality() + ", ";

                        }

                        if (listAddresses.get(0).getPostalCode() != null) {

                            address += listAddresses.get(0).getPostalCode() + ", ";

                        }

                        if (listAddresses.get(0).getCountryName() != null) {

                            address += listAddresses.get(0).getCountryName();

                        }

                        //store user location in ArrayList
                        locations.add(userLocation);

                        //marking polyline on Google Maps API to track users movements
                        if(polyline != null){
                            polyline.setPoints(locations);
                        }else {
                            polyline = mMap.addPolyline(new PolylineOptions().addAll(locations));
                        }

                        //Toast message to display users current address
                        Toast.makeText(MapsActivity.this, address, Toast.LENGTH_SHORT).show();
                    }

                    //removes previous markers from map - except start location and current location
                    flag = false;

                } catch (IOException e) {
                    e.printStackTrace();
                }//catch

            }//onLocationChanged()


            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };

        //For app compatibility if the user is using an Android device lower than SDK v.23
        if (Build.VERSION.SDK_INT < 23) {

            //Permission check
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }//if

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

        } else {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

            } else {

                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

                Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                LatLng userLocation = new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
                mMap.clear();

                mMap.addMarker(new MarkerOptions().position(userLocation).title("Starting Location"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(userLocation));

            }//else

        }//else

    }//onMapReady()


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        locationManager.removeUpdates(locationListener);
        mMap.clear();
        this.finish();
    }//onBackPressed()


}//class