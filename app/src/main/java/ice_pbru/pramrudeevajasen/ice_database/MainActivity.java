package ice_pbru.pramrudeevajasen.ice_database;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {


    //Explicit
    private EditText NameEditText , UsernameEditText;
    private  String NAString , USString;
    private MySQLite mySQLite;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //bind widget

        bindwidget();
        mySQLite = new MySQLite(this);

        //Synchronize mySQL to SQLite
        synAndDelete();



    }//Main Method

    public void singup (View view) {
        startActivity(new Intent(MainActivity.this, SingUp.class));


    }//clickSingUp

    public void testMyAlert(View view) {
        MyAlert myAlert = new MyAlert();
        myAlert.myDialog(this, "ใส่ไม่ครบ", "กรอกใหม่");
    }

    public void click_1(View view) {
        NAString = NameEditText.getText().toString().trim();
        USString = UsernameEditText.getText().toString().trim();
        ///

        if (checkspace_1()) {

            MyAlert myAlert = new MyAlert();
            myAlert.myDialog(this, "ใส่ไม่ครบ", "กรอกใหม่");

        } else {
            checkUserAndPassword();
        }
    }

    private void checkUserAndPassword() {

        try {
            SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name, MODE_PRIVATE, null); //บอกที่อยู่และชื่อ
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM userTABLE WHERE User = " +
                    "'" + NAString + "'", null);
            cursor.moveToFirst();
            String[] resultStrings = new String[cursor.getColumnCount()];
            for (int i = 0; i < cursor.getColumnCount(); i++) {

                resultStrings[i] = cursor.getString(i);

                //check user
            }
            cursor.close();

            //check pass
            if (USString.equals(resultStrings[4])) {
                Toast.makeText(this, "ยินดีตอนรับ" + resultStrings[1], Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this, LoginSuscess.class);
                intent.putExtra("Result", resultStrings);
                startActivity(intent);
                finish();

            } else {
                MyAlert myAlert = new MyAlert();
                myAlert.myDialog(this, "Wrong password", "กรุณากรอกให้ถูก");

            }


        } catch (Exception e) {

            MyAlert myAlert = new MyAlert();
            myAlert.myDialog(this, "User error", "ไม่มีชื่อผู้ใช้งานในระบบ");
        }
    }

    private boolean checkspace_1() {

        return NAString.equals("") || USString.equals("");
    }


    private void bindwidget() {
        NameEditText = (EditText) findViewById(R.id.editText6 );
        UsernameEditText = (EditText) findViewById(R.id.editText7);



    }//bind widget

    private void synAndDelete() {
        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                MODE_PRIVATE, null);

        sqLiteDatabase.delete(MySQLite.user_table, null, null);
        MySynJSON mySynJSON = new MySynJSON();
        mySynJSON.execute();

    }//SynandDelete




    public class  MySynJSON extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {

            try {
                String strURL = "http://ice.pbru.ac.th/ICE56/pramrudee/php_get_user.php";
                OkHttpClient okHttpClient = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                Request request = builder.url(strURL).build();
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();

            } catch (Exception e) {
                Log.d("Pramrudee -->", "doInback -->" + e.toString());
                return null;
            }

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("Pramrudee -->", "doInback-->" + s);
            try {
                JSONArray jsonArray = new JSONArray(s);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String strName = jsonObject.getString(MySQLite.colum_Name);
                    String strSurname = jsonObject.getString(MySQLite.colum_Surname);
                    String strUser = jsonObject.getString(MySQLite.colum_User);
                    String strPassword = jsonObject.getString(MySQLite.colum_Password);
                    String strEmail = jsonObject.getString(MySQLite.colum_Email);

                    mySQLite.addNewUser(strName, strSurname, strUser, strPassword, strEmail);
                    Log.d("Pramrudee -->", "doInback -->" + strName.toString());
                }

                    Toast.makeText(MainActivity.this, "Synchronize mySQL to SQLite Finish",
                            Toast.LENGTH_LONG).show();

                } catch (Exception e) {
                Log.d("Pramrudee  -->", "doInback --> " + e.toString());
            }
        }
    }
}//Main Class


