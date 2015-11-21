package com.mtafriends.tutor4u;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class RegisterFragment extends Fragment implements OnClickListener {

	public RegisterFragment() {
	}

	private Button btnLogin;
	private Button btnRegister;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_register, container,
				false);
		btnLogin = (Button) rootView.findViewById(R.id.btnLogin);
		btnRegister = (Button) rootView.findViewById(R.id.btnRegister);
		btnLogin.setOnClickListener(this);
		btnRegister.setOnClickListener(this);
		return rootView;
	}

	public void createDialogLogin() {
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View dialoglayout = inflater
				.inflate(R.layout.dialog_search_tutor, null);

		final Spinner spinCity = (Spinner) dialoglayout
				.findViewById(R.id.spinCity);
		final Spinner spinSubject = (Spinner) dialoglayout
				.findViewById(R.id.spinSubject);
		final Spinner spinLevel = (Spinner) dialoglayout
				.findViewById(R.id.spinLevel);

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setView(dialoglayout);
		builder.setTitle("Tìm kiếm gia sư");
		builder.setNegativeButton("Áp dụng",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {

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

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btnLogin:
			// Toast.makeText(getActivity(), "Login",
			// Toast.LENGTH_SHORT).show();
			Intent login = new Intent(getActivity(), LoginActivity.class);
			startActivity(login);
			break;
		case R.id.btnRegister:
			Toast.makeText(getActivity(), "Register", Toast.LENGTH_SHORT).show();
			break;

		default:
			break;
		}
	}
}
