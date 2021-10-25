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

public class Book_Edit extends AppCompatActivity {
    Connection connection;
    private static final String TAG = EditProfile.class.getSimpleName();
    SharedPreferences_Class sessionManager;
    private DatePicker datePicker;
    private Calendar calendar;
    private TextView purchDate,publishYear,copyrightyear,dateencoded;
    private int year, month, day;
    EditText title,author,isbn,numpages,numquantity,discription,encodedby;
    Button editbook,cancle;
    Intent intent;
    Bundle extra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_edit);
        purchDate = (TextView) findViewById(R.id.edit_Purch_Date);
        publishYear = (TextView) findViewById(R.id.edit_Publish_Year);
        copyrightyear = (TextView) findViewById(R.id.edit_copy_righ_year_book);
        dateencoded = (TextView) findViewById(R.id.edit_date_encoded_book);
        title = findViewById(R.id.edit_book_title);
        author = findViewById(R.id.edit_book_Author);
        isbn = findViewById(R.id.edit_book_ISBN);
        numpages = findViewById(R.id.edit_book_numpages);
        numquantity = findViewById(R.id.edit_book_number_of_quantity);
        discription = findViewById(R.id.edit_book_discription);
        encodedby = findViewById(R.id.edit_book_encoded_by);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate1(year, month+1, day);
        showDate2(year, month+1, day);
        showDate3(year, month+1, day);
        showDate4(year, month+1, day);
        intent= getIntent();
        extra = intent.getExtras();
        getUserDetail();
    }

    @SuppressWarnings("deprecation")
    public void btn_purchDate(android.view.View view) {
        showDialog(101);
        Toast.makeText(getApplicationContext(), "ca",
                Toast.LENGTH_SHORT)
                .show();
    }
    @SuppressWarnings("deprecation")
    public void btn_publishYear(android.view.View view) {
        showDialog(102);
        Toast.makeText(getApplicationContext(), "ca",
                Toast.LENGTH_SHORT)
                .show();
    }

    @SuppressWarnings("deprecation")
    public void btn_copyrightyear(android.view.View view) {
        showDialog(103);
        Toast.makeText(getApplicationContext(), "ca",
                Toast.LENGTH_SHORT)
                .show();
    }
    @SuppressWarnings("deprecation")
    public void btn_dateencoded(View view) {
        showDialog(104);
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
            }else{
                if (id == 103){
                    return new DatePickerDialog(this,
                            myDateListener3, year, month, day);
                }else {
                    if (id == 104)
                        return new DatePickerDialog(this,
                                myDateListener3, year, month, day);
                }}}
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
    private DatePickerDialog.OnDateSetListener myDateListener3 = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    showDate3(arg1, arg2+1, arg3);
                }
            };
    private DatePickerDialog.OnDateSetListener myDateListener4 = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    showDate4(arg1, arg2+1, arg3);
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
    private void showDate3(int year, int month, int day) {
        copyrightyear.setText(new StringBuilder().append(year).append("-")
                .append(month).append("-").append(day));
    }
    private void showDate4(int year, int month, int day) {
        dateencoded.setText(new StringBuilder().append(year).append("-")
                .append(month).append("-").append(day));
    }
    public void Cancle_edit_book(View view){
        finish();
    }

    public void Edit_book(View view) {
        sessionManager = new SharedPreferences_Class(this);
        final String extra_id = extra.getString("id");
        HashMap<String, String> user = sessionManager.getUserDetail();
        final String id = user.get(sessionManager.ID);
        final String title = this.title.getText().toString();
        final String author = this.author.getText().toString();
        final String isbn = this.isbn.getText().toString();
        final String purchDate = this.purchDate.getText().toString();
        final String publishYear = this.publishYear.getText().toString();
        final String copyrightyear = this.copyrightyear.getText().toString();
        final String dateencoded = this.dateencoded.getText().toString();
        final String numpages = this.numpages.getText().toString();
        final String quantity = this.numquantity.getText().toString();
        final String encodedby = this.encodedby.getText().toString();
        final String discription = this.discription.getText().toString();

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, connection.URL_EDIT_TB_BOOK,
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
                                    "Editing Error!!!! "+e.toString(),
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
                params.put("id",extra_id);
                params.put("title",title);
                params.put("author",author);
                params.put("isbn",isbn);
                params.put("purchdate",purchDate);
                params.put("publishyear",publishYear);
                params.put("copyrightyear",copyrightyear);
                params.put("dateencoded",dateencoded);
                params.put("numpages",numpages);
                params.put("quantity",quantity);
                params.put("encodedby",encodedby);
                params.put("discription",discription);
                params.put("account_id",id);
                return params ;
            }
        };
        requestQueue.add(stringRequest);
    }
    public void getUserDetail(){
        final String extra_id = extra.getString("id");
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, connection.URL_DETAILS2_TB_BOOK,
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
                                String strnumpages = object.getString("numpages").trim();
                                String strquantity = object.getString("quantity").trim();
                                String strdateencoded = object.getString("dateencoded").trim();
                                String strencodedby = object.getString("encodedby").trim();
                                String strdiscription = object.getString("discription").trim();
                                String strcopyright = object.getString("copyrightyear").trim();
                                title.setText(strTitle);
                                author.setText(strAuthor);
                                isbn.setText(strisbn);
                                purchDate.setText(strpurchdate);
                                publishYear.setText(strpublishyear);
                                numpages.setText(strnumpages);
                                numquantity.setText(strquantity);
                                dateencoded.setText(strdateencoded);
                                encodedby.setText(strencodedby);
                                discription.setText(strdiscription);
                                copyrightyear.setText(strcopyright);
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
}