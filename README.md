# Bluetooth-Attendance
Scans the vicinity for students' bluetooth devices and matches them against a database of students to check attendance and save to excel sheets.
  The application saves the bluetoothdevice objects of the discovered devices into an arraylist. Bluetooth addresses from this arraylist are stored in a string array and then matched with a database of students' roll numbers and corresponding bluetooth addresses. The addresses with a match in the database are marked present, others absent.
  The final list of roll numbers and attendance for the corresponding roll numbers (1 for present, 0 for absent) is then saved into an excel spreadsheet and attached directly to a mail with the date and time as the file name and email subject for the teacher to keep track.
Uses the [JExcelApi](http://jexcelapi.sourceforge.net) Java API to create and modify excel spreadsheets.
Uses the [Android SQLiteAssetHelper](https://github.com/jgilfelt/android-sqlite-asset-helper) library to access the SQLiteDatabase.
