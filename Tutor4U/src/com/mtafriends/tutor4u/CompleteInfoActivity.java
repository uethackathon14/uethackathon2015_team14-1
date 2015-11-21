package com.mtafriends.tutor4u;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class CompleteInfoActivity extends Activity implements OnClickListener {
	private Button completeInfoTutor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_complete_info);
		completeInfoTutor = (Button) findViewById(R.id.btnCompleteInfo);
		completeInfoTutor.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btnCompleteInfo:
			Intent fin = new Intent(this, MainActivity.class);
			startActivity(fin);
			break;

		default:
			break;
		}
	}

}
