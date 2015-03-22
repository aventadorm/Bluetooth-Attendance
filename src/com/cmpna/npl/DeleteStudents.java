package com.cmpna.npl;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class DeleteStudents extends Activity {
	ListView l;
	DCursorAdapter dcursoradapter;
	DBHelper sdb;
	String w;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Cursor query to display all
		sdb = new DBHelper(this);
		Cursor c = sdb.StudentCheck("SELECT rowid _id,* FROM Main");
		setContentView(R.layout.display);
		l = (ListView) findViewById(R.id.listview1);
		dcursoradapter = new DCursorAdapter(this, c);
		l.setAdapter(dcursoradapter);

		l.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				w = ((TextView) view).getText().toString();

				new AlertDialog.Builder(DeleteStudents.this)
						.setTitle("Delete entry")
						.setMessage(
								"Are you sure you want to delete this student ?")
						.setPositiveButton(android.R.string.yes,
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
										// continue with delete
										sdb.deleteStudent(Integer.parseInt(w));
										//refresh the adapter
										Cursor d = sdb
												.StudentCheck(
														"SELECT rowid _id,* FROM Main");
										dcursoradapter.changeCursor(d);
										l.setAdapter(dcursoradapter);
									}
								})
						.setNegativeButton(android.R.string.no,
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
										// do nothing
									}
								}).setIcon(android.R.drawable.ic_dialog_alert)
						.show();

			}
		});

	}

}
