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
import android.widget.Spinner;
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

public class BorrowedBook_Edit extends AppCompatActivity {
    Connection connection;
    SharedPreferences_Class sessionManager;
    private TextView bookid,borrowerid;
    private EditText numcopies,bstatus;
    private static final String TAG = EditProfile.class.getSimpleName();
    private TextView duedate,dateborrowed, txtquantity;
    private DatePicker datePicker;
    private Calendar calendar;
    private int year, month, day;
    private Intent intent;
    private Bundle extra;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrowed_book_edit);
        bookid = findViewById(R.id.txt_bookid);
        borrowerid = findViewById(R.id.txt_borrowerid);
        duedate = (TextView) findViewById(R.id.edit_duedate);
        dateborrowed = (TextView) findViewById(R.id.edit_date_barrowed);
        txtquantity = findViewById(R.id.txtAvilable);
        numcopies = findViewById(R.id.edit_borrowedbook_numcopies);
        bstatus = findViewById(R.id.edit_borrowedbook_bstatus);
        intent= getIntent();
        extra = intent.getExtras();
        getUserDetail();
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate1(year, month+1, day);
        showDate2(year, month+1, day);
    }
    @SuppressWarnings("deprecation")
    public void btn_Duedate(android.view.View view) {
        showDialog(101);
        Toast.makeText(getApplicationContext(), "ca",
                Toast.LENGTH_SHORT)
                .show();
    }
    @SuppressWarnings("deprecation")
    public void btn_DateBorrowed(View view) {
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
            }}
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
        duedate.setText(new StringBuilder().append(year).append("-")
                .append(month).append("-").append(day));
    }
    private void showDate2(int year, int month, int day) {
        dateborrowed.setText(new StringBuilder().append(year).append("-")
                .append(month).append("-").append(day));
    }
    public void getUserDetail(){
        final String extra_id = extra.getString("id");
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, connection.URL_DETAILS2_TB_BORROWEDBOOK,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.i(TAG, response.toString());
                            progressDialog.dismiss();
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);
                                String strbookid = object.getString("bookid").trim();
                                String strborrowerid = object.getString("borrowerid").trim();
                                String strduedate = object.getString("duedate").trim();
                                String strnumcopies = object.getString("numcopies").trim();
                                String strbstatus = object.getString("bstatus").trim();
                                String strdateborrowed = object.getString("dateborrowed").trim();
                                bookid.setText(strbookid);
                                borrowerid.setText(strborrowerid);
                                numcopies.setText(strnumcopies);
                                duedate.setText(strduedate);
                                bstatus.setText(strbstatus);
                                dateborrowed.setText(strdateborrowed);
                                int valuer = Integer.parseInt(strnumcopies);
                                getQuantity(strbookid,valuer);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(),
                                    "View Error!!! "+e.toString(),
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
    public void Cancle_edit_borrowedbook(View view){
        finish();
    }
    public void Edit_borrowedbook(View view){
        int avilable = Integer.parseInt(txtquantity.getText().toString());
        int storequantity = Integer.parseInt(numcopies.getText().toString());
        int value = avilable-storequantity;
        if(avilable>storequantity){
            Edit();
            edit_book(value+"");

        }else{
            Toast.makeText(getApplicationContext(),
                    "Book is not enough ",
                    Toast.LENGTH_LONG).show();
        }
    }
    private void Edit() {
        sessionManager = new SharedPreferences_Class(this);
        final String extra_id = extra.getString("id");
        HashMap<String, String> user = sessionManager.getUserDetail();
        final String id = user.get(sessionManager.ID);
        final String bookid = this.bookid.getText().toString();
        final String borrowerid = this.borrowerid.getText().toString();
        final String duedate = this.duedate.getText().toString();
        final String numcopies = this.numcopies.getText().toString();
        final String bstatus = this.bstatus.getText().toString();
        final String dateborrowed = this.dateborrowed.getText().toString();

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, connection.URL_EDIT_TB_BORROWEDBOOK,
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
                params.put("bookid",bookid);
                params.put("borrowerid",borrowerid);
                params.put("duedate",duedate);
                params.put("numcopies",numcopies);
                params.put("bstatus",bstatus);
                params.put("dateborrowed",dateborrowed);
                params.put("id",extra_id);
                params.put("account_id",id);
                return params ;
            }
        };
        requestQueue.add(stringRequest);
    }
    public void getQuantity(final String id, int value2){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, connection.URL_BOOK_QUANTITY,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.i(TAG, response.toString());
                            progressDialog.dismiss();
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);
                                String strquantity = object.getString("quantity").trim();
                                int value1 = Integer.parseInt(strquantity);
                                int total = value1+value2;
                                txtquantity.setText(total+"");

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
                params .put("id",id);
                return params ;
            }
        };
        Volley.newRequestQueue(this).add(stringRequest);
    }
    private void edit_book(final String edit){
        final String bookid = this.bookid.getText().toString();
        sessionManager = new SharedPreferences_Class(this);
        HashMap<String, String> user = sessionManager.getUserDetail();
        final String id = user.get(sessionManager.ID);
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, connection.URL_EDIT_QUANITTY,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i(TAG, response.toString());
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if(success.equals("1")){
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
                params .put("quantity",edit);
                params .put("id",bookid);
                params .put("accountid",id);
                return params ;
            }
        };
        requestQueue.add(stringRequest);
    }
}