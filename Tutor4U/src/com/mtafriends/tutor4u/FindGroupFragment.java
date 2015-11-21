package com.mtafriends.tutor4u;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.http.util.LangUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mtafriends.tutor4u.model.GroupStudy;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class FindGroupFragment extends Fragment {
	GoogleMap map;// Maps
	private LocationManager locationManager;

	Location Current_Location;

	// flag for GPS status
	boolean isGPSEnabled = false;

	// flag for network status
	boolean isNetworkEnabled = false;
	// flag for GPS status
	boolean canGetLocation = false;

	// Start button is clicked
	boolean isStart = false;
	Marker Marcur;// Đánh dấu vị trí hiện tại
	Marker Marcur_group;// Đánh dấu vị trí group
	Location location; // location
	double lat_new, lat_old; // latitude
	double lon_new, lon_old; // longitude

	public FindGroupFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_find_group, container, false);
		checkGPS();

		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		drawMaker();

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);

		String url = "http://tutor4u-anhdat.rhcloud.com/TutorWS/data/json/nearby/21.038665/105.782558";
		String jsonString = callURL(url);
		JSONObject json = null;
		try {
			JSONArray jArray = new JSONArray(jsonString);

			for (int i = 0; i < jArray.length(); i++) {
				json = jArray.getJSONObject(i);

				String username = json.getString("username");
				String name_subject = json.getString("name_subject");
				Double latitude = json.getDouble("latitude");
				Double longitude = json.getDouble("longitude");

				GroupStudy g = new GroupStudy();
				g.setUsername(username);
				g.setName_subject(name_subject);
				g.setLatitude(latitude);
				g.setLatitude(longitude);

				MarkerOptions option = new MarkerOptions();
				option.title(username);
				option.snippet(name_subject);
				option.position(new LatLng(latitude, longitude));
				Marcur = map.addMarker(option);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return rootView;
	}

	public static String callURL(String myURL) {
		System.out.println("Requested URL:" + myURL);
		StringBuilder sb = new StringBuilder();
		URLConnection urlConn = null;
		InputStreamReader in = null;
		try {
			URL url = new URL(myURL);
			urlConn = url.openConnection();
			if (urlConn != null)
				urlConn.setReadTimeout(60 * 1000);
			if (urlConn != null && urlConn.getInputStream() != null) {
				in = new InputStreamReader(urlConn.getInputStream(), Charset.defaultCharset());
				BufferedReader bufferedReader = new BufferedReader(in);
				if (bufferedReader != null) {
					int cp;
					while ((cp = bufferedReader.read()) != -1) {
						sb.append((char) cp);
					}
					bufferedReader.close();
				}
			}
			in.close();
		} catch (Exception e) {
			throw new RuntimeException("Exception while calling URL:" + myURL, e);
		}

		return sb.toString();
	}

	void drawMaker() {
		Current_Location = getLocation();
		LatLng Current_LatLng = new LatLng(Current_Location.getLatitude(), Current_Location.getLongitude());
		if (map != null) {
			// Marcur = map.addMarker(new
			// MarkerOptions().position(Current_LatLng).title("Vị trí hiện tại")
			// .snippet("Vị trí hiện tại của bạn")
			// .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_group_location)));

			// Điều hướng camera nhìn bao quát vị trí mong muốn
			LatLngBounds.Builder builder = new LatLngBounds.Builder();
			// builder.include(HO_HOAN_KIEM);
			builder.include(Current_LatLng);
			map.moveCamera(CameraUpdateFactory.newLatLngZoom(Current_LatLng, 13));

			Current_LatLng = new LatLng(Current_Location.getLatitude(), Current_Location.getLongitude());
			drawMakerAt(Current_LatLng);

		}

	}

	void drawMakerAt(LatLng location) {
		if (map != null) {
			Marcur = map.addMarker(
					new MarkerOptions().position(location).title("Vị trí Group").snippet("Vị trí nhóm học toán")
							.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_group_location)));
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
}