package model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import java.util.ArrayList;

import bean.UserData;

import com.google.gson.Gson;


public class ReadJSON {
	public ArrayList<UserData> getData() {
		String jSonString = "";
		ArrayList<UserData> list = new ArrayList<UserData>();

		try {
			URL url = new URL(
					"http://tutor4u-anhdat.rhcloud.com/TutorWS/data/json/toaccept");

			URLConnection connection = url.openConnection();
			connection.connect();

			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(connection.getInputStream()));
			jSonString = bufferedReader.readLine();
			// gson = bufferedReader.getClass().toString();
//			System.out.println("" + jSonString);
			UserData[] items = (UserData[]) new Gson().fromJson(jSonString,
					UserData[].class);
			for (int i = 0; i < items.length; i++) {
				list.add(items[i]);
			}
			
			return list;

		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

}
