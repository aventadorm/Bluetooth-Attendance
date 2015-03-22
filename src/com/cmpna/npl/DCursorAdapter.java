package com.cmpna.npl;


import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class DCursorAdapter extends CursorAdapter {

	public DCursorAdapter(Context context, Cursor c) {
		super(context, c, 0);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		return LayoutInflater.from(context).inflate(
				R.layout.simple_list_item_1, parent, false);
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		// TODO Auto-generated method stub
		TextView wtext = (TextView) view.findViewById(R.id.text1);
		String rollno = cursor.getString(cursor.getColumnIndexOrThrow("rollno"));
		wtext.setText(rollno);
	}
}
