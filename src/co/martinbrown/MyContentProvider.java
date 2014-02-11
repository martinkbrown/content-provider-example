package co.martinbrown;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

public class MyContentProvider extends ContentProvider {

    public final static String DBNAME = "NameDatabase";
    public final static String TABLE_NAMESTABLE = "namestable";
    public final static String COLUMN_FIRSTNAME = "firstname";
    public final static String COLUMN_LASTNAME = "lastname";

    public static final String AUTHORITY = "co.martinbrown.provider";
    public static final Uri CONTENT_URI = Uri.parse("content://co.martinbrown.provider/" + TABLE_NAMESTABLE);

    private MainDatabaseHelper mOpenHelper;

    private static final String SQL_CREATE_MAIN = "CREATE TABLE " +
            TABLE_NAMESTABLE +                       // Table's name
            "(" +                           // The columns in the table
            " _ID INTEGER PRIMARY KEY, " +
            COLUMN_FIRSTNAME +
            " TEXT," +
            COLUMN_LASTNAME +
            " TEXT)";

    @Override
    public boolean onCreate() {

        mOpenHelper = new MainDatabaseHelper(getContext());


        return true;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        String fname = values.getAsString(COLUMN_FIRSTNAME).trim();
        String lname = values.getAsString(COLUMN_LASTNAME).trim();

        if(fname.equals(""))
            return null;

        if(lname.equals(""))
            return null;

        long id = mOpenHelper.getWritableDatabase().insert(TABLE_NAMESTABLE, null, values);

        return Uri.withAppendedPath(CONTENT_URI, "" + id);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        return mOpenHelper.getWritableDatabase().update(TABLE_NAMESTABLE, values, selection, selectionArgs);

    }

    @Override
    public int delete(Uri uri, String whereClause, String[] whereArgs) {

        return mOpenHelper.getWritableDatabase().delete(TABLE_NAMESTABLE, whereClause, whereArgs);

    }

    @Override
    public Cursor query(Uri table, String[] columns, String selection, String[] args, String orderBy) {

        return mOpenHelper.getReadableDatabase().query(TABLE_NAMESTABLE, columns, selection, args, null, null, orderBy);
    }

    @Override
    public String getType(Uri arg0) {
        return null;
    }

    protected static final class MainDatabaseHelper extends SQLiteOpenHelper {

        MainDatabaseHelper(Context context) {
            super(context, DBNAME, null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL(SQL_CREATE_MAIN);
        }

        @Override
        public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        }
    }
}
