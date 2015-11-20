package com.mtafriends.tutor4u.adapter;

import com.mtafriends.tutor4u.R;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mtafriends.tutor4u.model.Tutor;

public class ListviewTutorAdapter extends BaseAdapter {

	private ArrayList<Tutor> lstTutor;
	private LayoutInflater inflate;
	private Context context;

	public ListviewTutorAdapter(Context _context, ArrayList<Tutor> _lst) {
		inflate = LayoutInflater.from(_context);
		lstTutor = _lst;
		context = _context;
	}

	private class ViewHolder {
		public ImageView ivAvatar;
		public TextView txtName;
		public TextView txtSubject;
		public TextView txtLevel;

	}

	@Override
	public int getCount() {
		return lstTutor.size();
	}

	@Override
	public Object getItem(int position) {
		return lstTutor.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view;
		final ViewHolder holder;
		if (convertView == null) {
			view = inflate.inflate(R.layout.drawer_list_tutor, parent, false);
			holder = new ViewHolder();
			holder.ivAvatar = (ImageView) view.findViewById(R.id.avaTutor);
			holder.txtName = (TextView) view.findViewById(R.id.txtNameTutor);
			holder.txtSubject = (TextView) view.findViewById(R.id.txtSubject);
			holder.txtLevel = (TextView) view.findViewById(R.id.txtLevel);

			view.setTag(holder);

		} else {
			view = convertView;
			holder = (ViewHolder) view.getTag();
		}

		Tutor item = lstTutor.get(position);
		holder.ivAvatar.setImageResource(R.drawable.ic_home);
		holder.txtName.setText(item.getName());
		holder.txtSubject.setText(item.getEmail());
		holder.txtLevel.setText(item.getLevel());

		return view;
	}

}
