package com.example.spark;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import androidx.core.app.ActivityCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;


public class LocationService extends Service implements
        LocationListener, GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {
    private final String TAG = getClass().getSimpleName();
    public static final String LOCATION_SERVICE = "LocationService";
    private FusedLocationProviderClient mLocationClient;
    private LocationRequest mLocationRequest;
    float minDist = 100f;
    int minTime = 10000;
    PendingIntent contentIntent;
    public final static String USER_LATITUDE = "user_latitude";
    public final static String USER_LONGITUDE = "user_longitude";
    private GoogleApiClient googleApiClient;
    private LocationCallback locationCallback;

    @Override
    public void onCreate() {
        Log.d("onCreate", "");
        super.onCreate();
        Intent intent = new Intent(this, MapActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        contentIntent = PendingIntent.getActivity(this, 0, intent, 0);
        mLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(minTime);
        mLocationRequest.setSmallestDisplacement(minDist);
        googleApiClient = new GoogleApiClient.Builder(this).addApi(Places.GEO_DATA_API)
                .addApi(LocationServices.API).build();
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                Location location = locationResult.getLastLocation();
                float latitude = (float) location.getLatitude();
                float longitude = (float) location.getLongitude();
                Log.d(TAG, latitude + ", " + longitude);
                Intent intent = new Intent(LOCATION_SERVICE);
                intent.putExtra(USER_LATITUDE, latitude);
                intent.putExtra(USER_LONGITUDE, longitude);
                LocalBroadcastManager.getInstance(LocationService.this).sendBroadcast(intent);
            }
        };
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        googleApiClient.connect();
        return Service.START_NOT_STICKY;
    }

    @Override
    public void onLocationChanged(Location location) {
        float latitude = (float) location.getLatitude();
        float longitude = (float) location.getLongitude();
        Log.d(TAG, latitude + ", " + longitude);
        Intent intent = new Intent(LOCATION_SERVICE);
        intent.putExtra(USER_LATITUDE, latitude);
        intent.putExtra(USER_LONGITUDE, longitude);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (googleApiClient.isConnected()) {
            stopPeriodicUpdates();
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.d(LOCATION_SERVICE, "onConnected");
        startPeriodicUpdates();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }


    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(LOCATION_SERVICE, "onConnectionFailed");
    }

    public void startPeriodicUpdates() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "ParkingSpot permissions not granted", Toast.LENGTH_SHORT).show();
            return;
        }
        mLocationClient.requestLocationUpdates(mLocationRequest, locationCallback, null);
        // mLocationClient.requestLocationUpdates(mLocationRequest, this);
        Log.d(LOCATION_SERVICE, "startPeriodicUpdates");
    }

    private void stopPeriodicUpdates() {
        //mLocationClient.removeLocationUpdates(this);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "ParkingSpot permissions not granted", Toast.LENGTH_SHORT).show();
            return;
        }
        mLocationClient.removeLocationUpdates(locationCallback);
        googleApiClient.disconnect();
        Log.d(LOCATION_SERVICE, "stopPeriodicUpdates");
    }
}