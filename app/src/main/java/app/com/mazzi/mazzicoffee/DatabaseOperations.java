package app.com.mazzi.mazzicoffee;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.database.Cursor;
import app.com.mazzi.mazzicoffee.DatabaseContract.Info;
public class DatabaseOperations extends SQLiteOpenHelper {
    public static final int database_version=1;
    private static final String DATABASE_NAME = "employee.db";
    public String CREATE_QUERY="CREATE TABLE "+ Info.TABLE_NAME+"("+Info.USER_NAME+" TEXT PRIMARY KEY, "+Info.USER_PASS+" TEXT);";
    public DatabaseOperations(Context context){
        super(context,Info.TABLE_NAME,null,database_version);
        Log.d("Database Operations","Database Created");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_QUERY);
        Log.d("Database Operations","Table Created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public Cursor putInformation(DatabaseOperations dop, String uname, String upass){
        SQLiteDatabase SQL=dop.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(Info.USER_NAME,uname);
        values.put(Info.USER_PASS,upass);
        long k=SQL.insert(Info.TABLE_NAME,null,values);
        Log.d("Database Operations","One Row Inserted");
        return null;
    }
}
