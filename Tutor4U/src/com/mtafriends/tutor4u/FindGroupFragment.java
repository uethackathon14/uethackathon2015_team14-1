package com.mtafriends.tutor4u;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;

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

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class FindGroupFragment extends Fragment implements OnClickListener {
	GoogleMap map;// Maps
	private LocationManager locationManager;
	private ArrayList<String> lstSubject = new ArrayList<String>();
	Location Current_Location;
	Spinner spinSubject;// Combobox Tên môn học
	Spinner spinDistance;// Combobox khoảng cách
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

	View rootView;
	private Button btnSearch;
	private Button btnCreate;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		try {
			rootView = inflater.inflate(R.layout.fragment_find_group, container, false);
			checkGPS();
			btnSearch = (Button) rootView.findViewById(R.id.btnSearch);
			btnCreate = (Button) rootView.findViewById(R.id.btnAdd);
			// btnJoin = (Button) rootView.findViewById(R.id.btnJoin);
			btnSearch.setOnClickListener(this);
			btnCreate.setOnClickListener(this);

			map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

			drawMaker();

			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);

		} catch (Exception e) {
			Toast.makeText(null, e.toString(), Toast.LENGTH_SHORT).show();
		}
		return rootView;
	}

	private class TaskGetData extends AsyncTask<String, Void, Void> {
		public TaskGetData() {
			// TODO Auto-generated constructor stub
		}

		@Override
		protected Void doInBackground(String... params) {

			String jsonString = "";
			try {
				URL url = new URL(params[0]);
				URLConnection connection = url.openConnection();
				connection.connect();
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				jsonString = "{ Data:" + bufferedReader.readLine() + "}";
			} catch (Exception e) {
			}

			try {
				JSONObject jsonRootObject = new JSONObject(jsonString);
				JSONArray jsonArray = jsonRootObject.optJSONArray("Data");

				String name = "";

				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject = jsonArray.getJSONObject(i);

					name = jsonObject.optString("name_subject");
					lstSubject.add(name);

				}

			} catch (Exception e) {
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			spinSubject.setAdapter(
					new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, lstSubject));

		}
	}

	int km = 0;
	String nameSubject = "";

	public void createDialogSearch() {
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View dialoglayout = inflater.inflate(R.layout.search_groups, null);

		spinSubject = (Spinner) dialoglayout.findViewById(R.id.spinNameSubject);
		spinDistance = (Spinner) dialoglayout.findViewById(R.id.spinDistance);
		setHasOptionsMenu(true);

		ArrayList<String> arrDistance = new ArrayList<String>();

		arrDistance.add(0, "<=5km");
		arrDistance.add(1, "<=10km");
		arrDistance.add(2, "<=20km");

		spinDistance.setAdapter(
				new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, arrDistance));

		new TaskGetData().execute("http://tutor4u-anhdat.rhcloud.com/TutorWS/data/json/subject");

		spinDistance.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				if (position == 0) {
					km = 5;
				} else if (position == 1) {
					km = 10;
				} else {
					km = 20;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});

		spinSubject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				nameSubject = lstSubject.get(position);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});
		
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setView(dialoglayout);
		builder.setTitle("Tạo nhóm học");
		builder.setNegativeButton("Áp dụng", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				 Search(km,nameSubject);
			}
		});
		builder.setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.show();

	}

	public void createDialogCreate() {
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View dialoglayout = inflater.inflate(R.layout.create_groups, null);

		spinSubject = (Spinner) dialoglayout.findViewById(R.id.spinSubject);

		setHasOptionsMenu(true);

		new TaskGetData().execute("http://tutor4u-anhdat.rhcloud.com/TutorWS/data/json/subject");

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setView(dialoglayout);
		builder.setTitle("Tạo nhóm học");
		builder.setNegativeButton("Áp dụng", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// Search();
			}
		});
		builder.setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.show();
	}

	public static String convertToUTF8(String s) {
		String out = null;
		try {
			out = new String(s.replace(" ", "%20"));
		} catch (Exception e) {
			return null;
		}
		return out;
	}

	private void Search(int km, String nameSubject) {
		Marcur.remove();
		drawMaker();
		String url = "http://tutor4u-anhdat.rhcloud.com/TutorWS/data/json/nearby/" + getLocation().getLatitude() + "/"
				+ getLocation().getLongitude() + "/" + km + "/" + convertToUTF8(nameSubject);		
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
				int limit_mem = json.getInt("limit_mem");
				int countNow = json.getInt("count_mem_joined");

				GroupStudy g = new GroupStudy();
				g.setUsername(username);
				g.setName_subject(name_subject);
				g.setLatitude(latitude);
				g.setLatitude(longitude);
				g.setLimitMem(limit_mem);
				g.setCountNow(countNow);

				MarkerOptions option = new MarkerOptions();
				option.title(username);
				option.snippet(name_subject+"- Tổng số: "+countNow+"/"+limit_mem);
				option.position(new LatLng(latitude, longitude));
				option.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_group_location));
				Marcur = map.addMarker(option);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
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
			Marcur = map.addMarker(new MarkerOptions().position(Current_LatLng).title("Vị trí hiện tại")
					.snippet("Vị trí hiện tại của bạn")
					.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_flag_location)));

			// Điều hướng camera nhìn bao quát vị trí mong muốn
			LatLngBounds.Builder builder = new LatLngBounds.Builder();
			builder.include(Current_LatLng);
			map.moveCamera(CameraUpdateFactory.newLatLngZoom(Current_LatLng, 13));

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

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btnAdd:
			createDialogCreate();
			break;
		case R.id.btnSearch:
			createDialogSearch();
			break;
		default:
			Toast.makeText(getActivity(), "Failed", Toast.LENGTH_LONG).show();
			break;
		}
	}
}