package com.mtafriends.tutor4u;

import info.androidhive.slidingmenu.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FindTutorFragment extends Fragment {
	
	public FindTutorFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_findtutor, container, false);
         
        return rootView;
    }
}
