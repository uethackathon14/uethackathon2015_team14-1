package com.mtafriends.tutor4u;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.mtafriends.tutor4u.model.Tutor;

public class Detail_Tutor_Activity extends Activity {

	private TextView txtName, txtSubject, txtLevel, txt_decribsion, txtEmail,
			txtMobie, btnCall;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_tutor);
		txtName = (TextView) findViewById(R.id.txtName);
		txtSubject = (TextView) findViewById(R.id.txtSubject);
		txtLevel = (TextView) findViewById(R.id.txtLevel);
		txt_decribsion = (TextView) findViewById(R.id.txt_decribsion);
		txtEmail = (TextView) findViewById(R.id.txtEmail);
		txtMobie = (TextView) findViewById(R.id.txtMobie);
		btnCall = (Button) findViewById(R.id.btnCall);

		Intent intent = getIntent();
		Tutor t = (Tutor) intent.getSerializableExtra("tutor");
		txtName.setText(t.getName());
		txtSubject.setText(t.getSubject());
		txtLevel.setText(t.getLevel());
		
		new TaskGetDetails().execute("http://tutor4u-anhdat.rhcloud.com/TutorWS/data/json/getdetailstutor/"+t.getUsername());

		btnCall.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				call(txtMobie.getText().toString());
			}
		});
	}
	
	public void call(String _phone)
	{
		Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:"+_phone));
        startActivity(callIntent);
	}

	private class TaskGetDetails extends AsyncTask<String, Void, Tutor> {

		@Override
		protected Tutor doInBackground(String... params) {

			String jsonString = "";
			Tutor t = new Tutor();
			try {
				URL url = new URL(params[0]);
				URLConnection connection = url.openConnection();
				connection.connect();
				BufferedReader bufferedReader = new BufferedReader(
						new InputStreamReader(connection.getInputStream()));
				jsonString = bufferedReader.readLine();
			} catch (Exception e) {
			}

			try {
				JSONObject jsonObject = new JSONObject(jsonString);

				String des = jsonObject.optString("description").toString();
				String email = jsonObject.optString("email").toString();
				String phone = jsonObject.optString("phone").toString();

				t.setDescription(des);
				t.setEmail(email);
				t.setPhone(phone);

			} catch (Exception e) {

			}
			return t;

		}

		@Override
		protected void onPostExecute(Tutor result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			txt_decribsion.setText(result.getDescription());
			txtEmail.setText(result.getEmail());
			txtMobie.setText(result.getPhone());
		}

	}

}
