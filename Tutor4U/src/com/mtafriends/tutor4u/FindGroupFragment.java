package com.mtafriends.tutor4u;

import android.app.Fragment;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

public class FindGroupFragment extends Fragment {

	private static final String LOCATION_SERVICE = null;
	private GoogleMap map;
	private SupportMapFragment fragment;

	public FindGroupFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_find_group,
				container, false);
		MapFragment mapFragment = MapFragment.newInstance();
		map = mapFragment.getMap();
		getFragmentManager().beginTransaction()
				.add(R.id.frame_container, mapFragment).commit();

		return rootView;
	}

	private void getMyLocation() {

	}

}
