package com.cmpna.npl;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DBHelper extends SQLiteAssetHelper {

	private static final String DATABASE_NAME = "centraldb.db";
	private static final int DATABASE_VERSION = 1;

	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);

		// you can use an alternate constructor to specify a database location
		// (such as a folder on the sd card)
		// you must ensure that this folder is available and you have permission
		// to write to it
		// super(context, DATABASE_NAME,
		// context.getExternalFilesDir(null).getAbsolutePath(), null,
		// DATABASE_VERSION);

	}

	public void addStudent(int roll, String badd) {
		SQLiteDatabase sdb = getReadableDatabase();
		sdb.execSQL("INSERT INTO Main VALUES('" + roll + "','"
				+ badd + "');");
	}

	public Cursor getStudents() {

		SQLiteDatabase sdb = getReadableDatabase();
		Cursor c = sdb.rawQuery("SELECT * FROM Main", null);
		c.moveToFirst();
		return c;

	}

	public Cursor StudentCheck(String sqlcommand) {
		SQLiteDatabase sdb = getReadableDatabase();
		Cursor d = sdb.rawQuery(sqlcommand, null);
		d.moveToFirst();
		return d;
	}

	public void deleteStudent(int w) {
		// TODO Auto-generated method stub
		SQLiteDatabase sdb = getReadableDatabase();
		sdb.execSQL("DELETE from Main WHERE rollno = '"
				+ w + "'");
		
	}

}