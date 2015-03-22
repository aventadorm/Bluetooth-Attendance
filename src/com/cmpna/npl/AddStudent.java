package com.cmpna.npl;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddStudent extends Activity {
	EditText roll, badd;
	Button add;
	DBHelper sdb;
	Cursor d, e;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_student);
		// Initializing
		roll = (EditText) findViewById(R.id.rollno);
		badd = (EditText) findViewById(R.id.bluetoothaddress);
		add = (Button) findViewById(R.id.adds);
		sdb = new DBHelper(this);

	}

	public void AddInfo(View view) {
		
		d = sdb.StudentCheck("SELECT * from Main WHERE rollno = '" + roll.getText()
				+ "'");
		e = sdb.StudentCheck("SELECT * from Main WHERE btadd = '" + badd.getText()
				+ "'");
		if (roll.getText().toString().isEmpty()
				|| roll.getText().toString().trim().length() == 0) {
			Toast.makeText(getBaseContext(), "Please insert a Roll.No.",
					Toast.LENGTH_SHORT).show();
		} else if (badd.getText().toString().isEmpty()
				|| badd.getText().toString().trim().length() == 0) {
			Toast.makeText(getBaseContext(), "Please insert the bluetooth address.",
					Toast.LENGTH_SHORT).show();
		} 
		 else if (d.getCount() != 0)
			Toast.makeText(getBaseContext(), "Roll no already exists.",
					Toast.LENGTH_SHORT).show();
		 else if (e.getCount() != 0)
				Toast.makeText(getBaseContext(), "Bluetooth address already exists.",
						Toast.LENGTH_SHORT).show();
		else {
			sdb.addStudent(Integer.parseInt(roll.getText().toString()), badd.getText().toString());
			Toast.makeText(getBaseContext(), "Student has been added.",
					Toast.LENGTH_SHORT).show();
		}

	}
}