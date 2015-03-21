package com.cmpna.npl;

import com.cmpna.npl.R;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import jxl.*;
import jxl.write.*;
import jxl.write.Number;
import jxl.write.biff.RowsExceededException;

public class AttendanceDatabase extends Activity {
	SQLiteDatabase db;
	String[] address = new String[100];
	String[] dbaddress = new String[100];;
	Integer[] roll = new Integer[100];
	private Cursor students;
	DBHelper sdb;
	WritableWorkbook workbook;

	ArrayList<BluetoothDevice> mDeviceList;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_paired_devices);

		mDeviceList = getIntent().getExtras().getParcelableArrayList(
				"device.list");// retrieve arraylist of bluetoothdevice objects
								// from the intent

		Calendar c = Calendar.getInstance();
		// Get the current time stamp through the calendar object
		int minute = c.get(Calendar.MINUTE);
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int date = c.get(Calendar.DATE);
		int month = c.get(Calendar.MONTH);

		// Convert the time integers to strings
		String m = String.valueOf(minute);
		String h = String.valueOf(hour);
		String d = String.valueOf(date);
		String mo = String.valueOf(month);
		// create database with time stamp as the name
		String dbname = "Date:" + d + "//" + mo + "Time:" + h + ":" + m;
		db = openOrCreateDatabase(dbname, Context.MODE_PRIVATE, null);
		db.execSQL("CREATE TABLE IF NOT EXISTS attendancetable(rollno INTEGER, attendance INTEGER);");
		// db.execSQL("insert into "+"attendancetable"+"(rollno,attendance)values(33,0)");

		// Get address array from arraylist of bluetoothdevice objects
		int j = 0;
		Iterator<BluetoothDevice> iterator = mDeviceList.iterator();// Iterator
																	// iterates
																	// through
																	// the
																	// arraylist
																	// objects
		while (iterator.hasNext()) {
			address[j] = iterator.next().getAddress();
			// showToast(address[j]);
			j++;
		}
		int sizeofdcb = j;

		// Instance of the DBHelper class to use the asset database
		sdb = new DBHelper(this);
		students = sdb.getStudents();
		int i = 0;
		// retrieves the roll nos and bluetooth addresses from cursor student
		// into respective arrays
		if (!students.moveToFirst()) {
			showToast("No records in database.");
		} else {
			students.moveToFirst();
			while (!students.isAfterLast()) {
				dbaddress[i] = students.getString(1);// address retrieval
				roll[i] = students.getInt(0);// roll no retrieval
				students.moveToNext();
				i++;
			}
		}
		int sizeofdb = i;
		showToast("db length" + sizeofdb);
		showToast("dcb length" + sizeofdcb);
		int[] flag = new int[100];

		for (i = 0; i < sizeofdcb; i++) {
			for (j = 0; j < sizeofdb; j++) {
				if (address[i].equals(dbaddress[j])) {
					flag[j] = 1;

				} else {

				}
			}
		}
		for (i = 0; i < sizeofdb; i++)
			showToast(roll[i] + Integer.toString(flag[i]));

		//for (j = 0; j < sizeofdb; j++)
			//db.execSQL("INSERT INTO attendancetable VALUES('" + roll[j] + "','"
				//	+ flag[j] + "');");
		File path = Environment.getExternalStorageDirectory();
		File file = new File(path, "/"+dbname+".xls");
		try {
			workbook = Workbook.createWorkbook(file);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		WritableSheet sheet = workbook.createSheet("First Sheet", 0);
		Label label = new Label(0, 2, "A label record");
		try {
			sheet.addCell(label);
		} catch (RowsExceededException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (WriteException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		Number number = new Number(3, 4, 3.1459);
		try {
			sheet.addCell(number);
		} catch (RowsExceededException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (WriteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// All sheets and cells added. Now write out the workbook
		try {
			workbook.write();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			workbook.close();
		} catch (WriteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Cursor e = db.rawQuery("SELECT * FROM attendancetable", null);
		e.moveToFirst();
		while (!e.isAfterLast()) {
			showToast(e.getString(0) + ":" + e.getString(1));
			e.moveToNext();
		}

		// String[] dbaddress = { "40B0FA3B51A8", "7AEEBC662872",
		// "BCF5AC9A9B5F",
		// "80E650036B1D", "B8EE65AB96BC", "48D705D72C8E" };
		// Integer[] roll = { 29, 17, 48, 49, 50, 30 };
		/*
		 * for(i=0;i<address.length;i++) { for(j=0;j<5;j++) {
		 * if(address[i]==dbaaddress[j]) { System.out.println(roll[j]); } else {
		 * System.out.println("student does not belong
		 */
	}

	// displays toasts of absent roll numbers
	public void Absent(View view) {
		Cursor cu = db.rawQuery(
				"SELECT rollno FROM attendancetable WHERE attendance = '0'",
				null);// retrieve student roll nos with attendance marked as
						// zero
		// cursor traversal
		if (!cu.moveToFirst()) {
			showToast("Everyone is present.");
		} else {
			cu.moveToFirst();
			while (!cu.isAfterLast()) {
				showToast(cu.getString(0) + " is absent.");
				cu.moveToNext();
			}
		}

	}

	// function for displaying toast messages
	private void showToast(String message) {
		Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT)
				.show();
	}

}