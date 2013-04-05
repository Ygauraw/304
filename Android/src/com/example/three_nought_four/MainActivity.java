package com.example.three_nought_four;

import java.io.IOException;
import java.net.UnknownHostException;

import org.json.simple.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Connection exampleConn = new Connection("149.241.1.160", 59422);
		JSONObject object = new JSONObject();
		object.put("req", "new_game");
		exampleConn.sendMessage(object.toJSONString());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
