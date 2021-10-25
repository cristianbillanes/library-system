package com.example.librarysystem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class EditBook extends AppCompatActivity {
    Connection connection;
    private static final String TAG = EditProfile.class.getSimpleName();
    SharedPreferences_Class sessionManager;
    private Calendar calendar;
    private TextView purchDate,publishYear;
    private int year, month, day;
    EditText title,author,isbn;
    Intent intent;
    Bundle extra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_book);
        purchDate = (TextView) findViewById(R.id.edit_Purch_Date);
        publishYear = (TextView) findViewById(R.id.edit_Publish_Year);
        title = findViewById(R.id.edit_book_title);
        author = findViewById(R.id.edit_book_Author);
        isbn = findViewById(R.id.edit_book_ISBN);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate1(year, month+1, day);
        showDate2(year, month+1, day);
        intent= getIntent();
        extra = intent.getExtras();
        getUserDetail();
    }

    @SuppressWarnings("deprecation")
    public void btn_edit_purchDate(View view) {
        showDialog(101);
        Toast.makeText(getApplicationContext(), "ca",
                Toast.LENGTH_SHORT)
                .show();
    }
    @SuppressWarnings("deprecation")
    public void btn_edit_publishYear(View view) {
        showDialog(102);
        Toast.makeText(getApplicationContext(), "ca",
                Toast.LENGTH_SHORT)
                .show();
    }

    @Nullable
    @Override
    protected Dialog onCreateDialog(int id, Bundle args) {
        // TODO Auto-generated method stub
        if (id == 101) {
            return new DatePickerDialog(this,
                    myDateListener1, year, month, day);
        }else{
            if (id == 102){
                return new DatePickerDialog(this,
                        myDateListener2, year, month, day);
            }
        }
        return null;
    }


    private DatePickerDialog.OnDateSetListener myDateListener1 = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    showDate1(arg1, arg2+1, arg3);
                }
            };
    private DatePickerDialog.OnDateSetListener myDateListener2 = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    showDate2(arg1, arg2+1, arg3);
                }
            };

    private void showDate1(int year, int month, int day) {
        purchDate.setText(new StringBuilder().append(year).append("-")
                .append(month).append("-").append(day));
    }
    private void showDate2(int year, int month, int day) {
        publishYear.setText(new StringBuilder().append(year).append("-")
                .append(month).append("-").append(day));
    }
    public void EditBook(View view) {
        sessionManager = new SharedPreferences_Class(this);
        HashMap<String, String> user = sessionManager.getUserDetail();
        final String extra_id = extra.getString("id");
        final String session_id = user.get(sessionManager.ID);
        final String f_title = title.getText().toString();
        final String f_author = author.getText().toString();
        final String f_isbn = isbn.getText().toString();
        final String f_purchDate = this.purchDate.getText().toString();
        final String f_publishYear = this.publishYear.getText().toString();

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, connection.URL_EDIT_BOOK,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i(TAG, response.toString());
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if(success.equals("1")){
                                Toast.makeText(getApplicationContext(),
                                        "Success Edit",
                                        Toast.LENGTH_LONG).show();
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),
                                    "Editing Error!! "+e.toString(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),
                                "Editing Error! "+error.toString(),
                                Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String>params = new HashMap<>();
                params .put("account_id",session_id);
                params .put("id",extra_id);
                params.put("title",f_title);
                params.put("author",f_author);
                params.put("isbn",f_isbn);
                params.put("purchdate",f_purchDate);
                params.put("publishyear",f_publishYear);
                return params ;
            }
        };
        requestQueue.add(stringRequest);
    }
    public void getUserDetail(){
        final String extra_id = extra.getString("id");
        title.setText(extra_id);
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, connection.URL_BOOK_DETAILS2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.i(TAG, response.toString());
                            progressDialog.dismiss();
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);
                                String strTitle = object.getString("title").trim();
                                String strAuthor = object.getString("author").trim();
                                String strisbn = object.getString("isbn").trim();
                                String strpurchdate = object.getString("purchdate").trim();
                                String strpublishyear = object.getString("publishyear").trim();
                                title.setText(strTitle);
                                author.setText(strAuthor);
                                isbn.setText(strisbn);
                                purchDate.setText(strpurchdate);
                                publishYear.setText(strpublishyear);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(),
                                    "View Error! "+e.toString(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),
                                "View Error! " + error.toString(),
                                Toast.LENGTH_LONG).show();

                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String>params = new HashMap<>();
                params .put("id",extra_id);
                return params ;
            }
        };
        Volley.newRequestQueue(this).add(stringRequest);
    }
    public void cancle(View view){
        finish();
    }
}