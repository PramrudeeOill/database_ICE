package ice_pbru.pramrudeevajasen.ice_database;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class SingUp extends AppCompatActivity {
    private EditText nameEditText, surnameEditText, userEditText, passwordEditText, emailEditText;
    private String nameString, surnameString, userString, paswordString, emailString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);

        bindwiget();


    }//1

    public void clicksignup(View view) {
        nameString = nameEditText.getText().toString().trim(); //check ช่องว่าง
        surnameString = surnameEditText.getText().toString().trim();
        userString = userEditText.getText().toString().trim();
        paswordString = passwordEditText.getText().toString().trim();
        emailString = emailEditText.getText().toString().trim();



        if (checkSpace()) {
            //have space
            MyAlert myAlert = new MyAlert();
            myAlert.myDialog(this, "มีช่องว่าง", "กรุณาตรวจสอบข้อมูลให้ครบถ้วน");
        } else {
            updateValueToSever();

        }
    }

    private void updateValueToSever() {
        String strURL = "http://ice.pbru.ac.th/ICE56/pramrudee/addData.php";
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = new FormEncodingBuilder()
                .add("isAdd", "true")
                .add("Name", nameString)
                .add("Surname", surnameString)
                .add("User", userString)
                .add("Password", paswordString)
                .add("Email", emailString)
                .build();
        Request.Builder builder = new Request.Builder();
        Request request = builder.url(strURL).post(requestBody).build();

        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {

                try {
                    finish();
                } catch (Exception e) {
                    Log.d("Pramrudee--->", "error" + e.toString());
                }

            }
        });
    }

    private boolean checkSpace() {
        return nameString.equals("") || surnameString.equals("") || userString.equals("") || paswordString.equals("") || emailString.equals("");
    }

    private void bindwiget() {
        nameEditText = (EditText) findViewById(R.id.editText);
        surnameEditText = (EditText) findViewById(R.id.editText2);
        userEditText = (EditText) findViewById(R.id.editText3);
        passwordEditText = (EditText) findViewById(R.id.editText4);
        emailEditText = (EditText) findViewById(R.id.editText5);
    }

}//2


