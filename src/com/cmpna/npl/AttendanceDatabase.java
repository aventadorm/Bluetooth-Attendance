package com.cmpna.npl;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;
import java.io.File;
import java.io.IOException;
import jxl.*;
import jxl.write.*;
import jxl.write.Number;
import jxl.write.biff.RowsExceededException;

public class AttendanceDatabase extends Activity {
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

		//setContentView(R.layout.activity_paired_devices);

		mDeviceList = getIntent().getExtras().getParcelableArrayList(
				"device.list");// retrieve arraylist of bluetoothdevice objects
								// from the intent

		Calendar c = Calendar.getInstance();
		// Get the current time stamp through the calendar object
		int minute = c.get(Calendar.MINUTE);
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int date = c.get(Calendar.DATE);
		int month = c.get(Calendar.MONTH) + 1;

		// Convert the time integers to strings
		String h = String.valueOf(hour);
		String m = String.valueOf(minute);

		String d = String.valueOf(date);
		String mo = String.valueOf(month);

		// create database with time stamp as the name
		String filename = "Date:" + d + "|" + mo + "Time:" + h + ":" + m;

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
			showToast(roll[i] + ":" + Integer.toString(flag[i]));

		File path = Environment.getExternalStorageDirectory();
		File file = new File(path, "/" + filename + ".xls");
		try {
			workbook = Workbook.createWorkbook(file);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		WritableSheet sheet = workbook.createSheet("Attendance Sheet", 0);

		for (j = 0; j < sizeofdb; j++) {
			Number number = new Number(0, j, roll[j]);
			try {
				sheet.addCell(number);
			} catch (RowsExceededException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (WriteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		for (j = 0; j < sizeofdb; j++) {
			Number number = new Number(1, j, flag[j]);
			try {
				sheet.addCell(number);
			} catch (RowsExceededException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (WriteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
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
		// Uri uri = Uri.fromFile(new
		// File(Environment.getExternalStorageDirectory(),
		// filename+".xls"));
		Uri uri = Uri.fromFile(file);
		//Intent emailintent = new Intent(Intent.ACTION_SEND);
		Intent emailintent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
	            "mailto","", null));
		emailintent.putExtra(Intent.EXTRA_SUBJECT, "Attendance for " + filename
				+ ".");
		emailintent.putExtra(Intent.EXTRA_TEXT, "Attached is attendance for "
				+ d + "|" + mo + " at time :" + h + ":" + m + ".");
		emailintent.putExtra(Intent.EXTRA_STREAM, uri);
		startActivity(Intent.createChooser(emailintent,
				"Send attendance as mail"));
	}

	// function for displaying toast messages
	private void showToast(String message) {
		Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT)
				.show();
	}

}