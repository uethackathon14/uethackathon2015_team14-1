package com.mtafriends.tutor4u;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class LoginActivity extends Activity implements OnClickListener {
	private Button btnLogin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		btnLogin = (Button) findViewById(R.id.btnLoginTutor);
		btnLogin.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btnLoginTutor:
			Toast.makeText(getApplicationContext(), "Login Success",
					Toast.LENGTH_SHORT).show();
			Intent completeInfo = new Intent(this, CompleteInfoActivity.class);
			startActivity(completeInfo);
			break;

		default:
			break;
		}
	}

}
