package com.mtafriends.tutor4u;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.mtafriends.tutor4u.adapter.ListviewTutorAdapter;
import com.mtafriends.tutor4u.model.Tutor;

public class FindTutorFragment extends Fragment {

	private ArrayList<Tutor> lstTutor = new ArrayList<Tutor>();
	private ListView lvTutor;
	private ListviewTutorAdapter adapter;

	public FindTutorFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_findtutor,
				container, false);
		lvTutor = (ListView) rootView.findViewById(R.id.lvTutor);
		new TaskGetTutor()
				.execute("http://tutor4u-anhdat.rhcloud.com/TutorWS/data/json/listtutor");
		
		lvTutor.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				
				Toast.makeText(getActivity(), pos+"", Toast.LENGTH_LONG).show();
				
			}
			
		});

		return rootView;
	}

	private class TaskGetTutor extends
			AsyncTask<String, Void, ArrayList<Tutor>> {

		@Override
		protected ArrayList<Tutor> doInBackground(String... params) {

			String jsonString = "";
			try {
				URL url = new URL(params[0]);
				URLConnection connection = url.openConnection();
				connection.connect();
				BufferedReader bufferedReader = new BufferedReader(
						new InputStreamReader(connection.getInputStream()));
				jsonString = "{ Tutor:" + bufferedReader.readLine() + "}";
			} catch (Exception e) {
			}

			try {
				JSONObject jsonRootObject = new JSONObject(jsonString);
				JSONArray jsonArray = jsonRootObject.optJSONArray("Tutor");

				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					String address = jsonObject.optString("address").toString();
					String avatar = jsonObject.optString("avatar").toString();
					String birthday = jsonObject.optString("birthday")
							.toString();
					String email = jsonObject.optString("email").toString();
					String level = jsonObject.optString("level").toString();
					String name = jsonObject.optString("name").toString();
					String phone = jsonObject.optString("phone").toString();
					int status = jsonObject.optInt("status");
					int type = jsonObject.optInt("type");
					String username = jsonObject.optString("username")
							.toString();

					Tutor t = new Tutor();
					t.setAddress(address);
					t.setAvatar(avatar);
					t.setBirthday(birthday);
					t.setEmail(email);
					t.setLevel(level);
					t.setName(name);
					t.setPhone(phone);
					t.setStatus(status);
					t.setType(type);
					t.setUsername(username);

					lstTutor.add(t);

				}

			} catch (Exception e) {
			}

			return lstTutor;
		}

		@Override
		protected void onPostExecute(ArrayList<Tutor> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			adapter = new ListviewTutorAdapter(getActivity(), result);
			lvTutor.setAdapter(adapter);
			adapter.notifyDataSetChanged();

		}
	}

}
