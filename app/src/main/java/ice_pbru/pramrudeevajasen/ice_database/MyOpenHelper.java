package ice_pbru.pramrudeevajasen.ice_database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ICE on 4/29/2016.
 */
public class MyOpenHelper extends SQLiteOpenHelper{

    //explicit

    public static final String database_name = "MyDatabase.db";
    private static final int database_version = 1;

    private static final String create_user_table = "create table userTABLE (" +
            "_id integer primary key, " +
            "Name text, " +
            "Surname text, " +
            "User text, " +
            "Password text, " +
            "Email text); ";

    public  MyOpenHelper (Context context) {
        super(context, database_name, null, database_version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(create_user_table);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}// Main
