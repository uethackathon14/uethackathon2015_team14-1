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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.mtafriends.tutor4u.adapter.ListviewTutorAdapter;
import com.mtafriends.tutor4u.model.Tutor;

public class FindTutorFragment extends Fragment {

	private ArrayList<Tutor> lstTutor = new ArrayList<Tutor>();
	private ListView lvTutor;
	private ListviewTutorAdapter adapter;

	private ArrayList<String> lstCity = new ArrayList<String>();
	private ArrayList<String> lstSubject = new ArrayList<String>();
	private ArrayList<String> lstLevel = new ArrayList<String>();

	private Button btnSearch;

	private Spinner spinCity, spinSubject, spinLevel;

	private int poscity, possubject, poslevel;

	public FindTutorFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_findtutor,
				container, false);
		lvTutor = (ListView) rootView.findViewById(R.id.lvTutor);
		spinCity = (Spinner) rootView.findViewById(R.id.spinCity);
		spinSubject = (Spinner) rootView.findViewById(R.id.spinSubject);
		spinLevel = (Spinner) rootView.findViewById(R.id.spinLevel);
		btnSearch = (Button) rootView.findViewById(R.id.btnSearch);

		setHasOptionsMenu(true);
		new TaskGetTutor()
				.execute("http://tutor4u-anhdat.rhcloud.com/TutorWS/data/json/listtutor");

		new TaskGetData(0)
				.execute("http://tutor4u-anhdat.rhcloud.com/TutorWS/data/json/city");
		new TaskGetData(1)
				.execute("http://tutor4u-anhdat.rhcloud.com/TutorWS/data/json/subject");
		new TaskGetData(2)
				.execute("http://tutor4u-anhdat.rhcloud.com/TutorWS/data/json/level");

		spinCity.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				poscity = arg2;
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}

		});

		spinSubject.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				possubject = arg2;
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}

		});

		spinLevel.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				poslevel = arg2;
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}

		});

		btnSearch.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				lstTutor.clear();
				adapter.notifyDataSetChanged();
				new TaskGetTutor()
						.execute("http://tutor4u-anhdat.rhcloud.com/TutorWS/data/json/searchtutor/"
								+ convertToUTF8(lstCity.get(poscity))
								+ "/"
								+ convertToUTF8(lstSubject.get(possubject))
								+ "/"
								+ convertToUTF8(lstLevel.get(poslevel)));

			}
		});

		return rootView;
	}

	public static String convertToUTF8(String s) {
		String out = null;
		try {
			out = new String(s.replace(" ", "%20").getBytes("UTF-8"),
					"ISO-8859-1");
		} catch (java.io.UnsupportedEncodingException e) {
			return null;
		}
		return out;
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
					String level = jsonObject.optString("level").toString();
					String name = jsonObject.optString("name").toString();
					String username = jsonObject.optString("username")
							.toString();
					String subject = jsonObject.optString("subject").toString();

					Tutor t = new Tutor();
					t.setSubject(subject);
					t.setLevel(level);
					t.setName(name);
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

	private class TaskGetData extends AsyncTask<String, Void, Void> {

		int k;

		public TaskGetData(int _k) {
			this.k = _k;
		}

		@Override
		protected Void doInBackground(String... params) {

			String jsonString = "";
			try {
				URL url = new URL(params[0]);
				URLConnection connection = url.openConnection();
				connection.connect();
				BufferedReader bufferedReader = new BufferedReader(
						new InputStreamReader(connection.getInputStream()));
				jsonString = "{ Data:" + bufferedReader.readLine() + "}";
			} catch (Exception e) {
			}

			try {
				JSONObject jsonRootObject = new JSONObject(jsonString);
				JSONArray jsonArray = jsonRootObject.optJSONArray("Data");

				String name = "";

				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					if (k == 0) {
						name = jsonObject.optString("name_city");
						lstCity.add(name);
					} else if (k == 1) {
						name = jsonObject.optString("name_subject");
						lstSubject.add(name);
					} else {
						name = jsonObject.optString("name_level");
						lstLevel.add(name);
					}

				}

			} catch (Exception e) {
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (k == 0) {
				spinCity.setAdapter(new ArrayAdapter<String>(getActivity(),
						android.R.layout.simple_spinner_dropdown_item, lstCity));
			} else if (k == 1) {
				spinSubject.setAdapter(new ArrayAdapter<String>(getActivity(),
						android.R.layout.simple_spinner_dropdown_item,
						lstSubject));
			} else {
				spinLevel
						.setAdapter(new ArrayAdapter<String>(getActivity(),
								android.R.layout.simple_spinner_dropdown_item,
								lstLevel));
			}

		}

	}

}
