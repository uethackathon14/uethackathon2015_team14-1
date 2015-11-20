package com.mtafriends.tutor4u.adapter;

import info.androidhive.slidingmenu.R;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mtafriends.tutor4u.model.ListviewTutor;

public class LisviewTutorAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<ListviewTutor> listViewTutor;

	public LisviewTutorAdapter(Context context,
			ArrayList<ListviewTutor> listViewTutor) {
		super();
		this.context = context;
		this.listViewTutor = listViewTutor;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listViewTutor.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return listViewTutor.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			LayoutInflater mInflater = (LayoutInflater) context
					.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			convertView = mInflater.inflate(R.layout.drawer_list_tutor, null);
		}
		ImageView imgIcon = (ImageView) convertView.findViewById(R.id.avaTutor);
		TextView txtNameTutor = (TextView) convertView
				.findViewById(R.id.txtNameTutor);
		TextView txtSubject = (TextView) convertView
				.findViewById(R.id.txtSubject);
		TextView txtLevel = (TextView) convertView.findViewById(R.id.txtLevel);

		imgIcon.setBackgroundColor(R.drawable.ic_home);
		// TODO set Ava
		txtNameTutor.setText(listViewTutor.get(position).getTxtNameTutor());
		txtSubject.setText(listViewTutor.get(position).getTxtSubjectTutor());
		txtLevel.setText(listViewTutor.get(position).getTxtLevelTutor());
		return null;
	}

}
