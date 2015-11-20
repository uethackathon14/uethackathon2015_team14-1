package com.mtafriends.tutor4u;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class FindGroupFragment extends Fragment implements LocationListener{
	GoogleMap map;
	private LocationManager locationManager;
	// The minimum distance to change Updates in meters
	private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 1; // 1 meter
	Location Current_Location;
	// The minimum time between updates in milliseconds
	private static final long MIN_TIME_BW_UPDATES = 2000; // 2s
	// flag for GPS status
	boolean isGPSEnabled = false;

	// flag for network status
	boolean isNetworkEnabled = false;
	// flag for GPS status
	boolean canGetLocation = false;

	// Start button is clicked
	boolean isStart = false;
	Marker Marcur;// Đánh dấu vị trí hiện tại

	Location location; // location
	double lat_new, lat_old; // latitude
	double lon_new, lon_old; // longitude
	public FindGroupFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_find_group, container, false);
        checkGPS();

       map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
       drawMaker();
        return rootView;
    }

	void drawMaker() {
		Current_Location = getLocation();
		LatLng Current_LatLng = new LatLng(Current_Location.getLatitude(), Current_Location.getLongitude());
		if (map != null) {
			if (Marcur != null)
				Marcur.remove();
			Marcur = map.addMarker(new MarkerOptions().position(Current_LatLng).title("Vị trí hiện tại")
					.snippet("Vị trí hiện tại của bạn")
					.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_flag_location)));

			// Điều hướng camera nhìn bao quát vị trí mong muốn
			LatLngBounds.Builder builder = new LatLngBounds.Builder();
			// builder.include(HO_HOAN_KIEM);
			builder.include(Current_LatLng);
			map.moveCamera(CameraUpdateFactory.newLatLngZoom(Current_LatLng, 13));
		}
	}
	private void checkGPS() {
		LocationManager service = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
		boolean enabled = service.isProviderEnabled(LocationManager.GPS_PROVIDER);
		// check if enabled and if not send user to the GSP settings
		// Better solution would be to display a dialog and suggesting to
		// go to the settings
		if (!enabled) {
			
			Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
			startActivity(intent);
		} else {


			// Khi GPS được bật thì bắt đầu dò tìm vị trí
			location = getLocation(); // Check thiết bị và lấy đối tượng
										// Location

			// Initialize the location fields
			if (location != null) {
				onLocationChanged(location);
			}
		}
	}
	// Kiểm tra xem thiết bị có sẵn cảm biến GPS loại nào và sử dụng
	public Location getLocation() {
		try {
			locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
	
			// getting GPS status
			isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

			// getting network status
			isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

			if (!isGPSEnabled && !isNetworkEnabled) {
				
			} else {
				this.canGetLocation = true;
				if (isNetworkEnabled) {
					locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES,
							MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
					Log.d("Network", "Network");
					if (locationManager != null) {
						location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
						if (location != null) {
							lat_new = location.getLatitude();
							lon_new = location.getLongitude();
						}
					}
				}
				// if GPS Enabled get lat/long using GPS Services
				if (isGPSEnabled) {
					if (location == null) {
						locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES,
								MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
						Log.d("GPS Enabled", "GPS Enabled");
						if (locationManager != null) {
							location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
							if (location != null) {
								lat_new = location.getLatitude();
								lon_new = location.getLongitude();
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return location;
	}
	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub


			lat_old = lat_new;
			lon_old = lon_new;

			lat_new = location.getLatitude();
			lon_new = location.getLongitude();

			drawMaker();
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}
}
