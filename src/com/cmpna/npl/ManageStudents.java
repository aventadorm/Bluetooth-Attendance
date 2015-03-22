package com.cmpna.npl;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ManageStudents extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.manage_students);
		
	}

	public void AddActivity(View view) {

		Intent intent = new Intent(this, AddStudent.class);
		startActivity(intent);
	}
	public void DeleteActivity(View view) {

		Intent intent = new Intent(this, DeleteStudents.class);
		startActivity(intent);
	}
	

}