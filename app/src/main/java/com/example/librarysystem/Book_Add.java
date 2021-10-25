package com.example.librarysystem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Book_Add extends AppCompatActivity {
    Connection connection;
    SharedPreferences_Class sessionManager;
    private DatePicker datePicker;
    private Calendar calendar;
    private TextView purchDate,publishYear,copyrightyear,dateencoded;
    private int year, month, day;
    EditText title,author,isbn,numpages,numquantity,discription,encodedby;
    Button addbook,cancle;
    ProgressBar loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_add);
        purchDate = (TextView) findViewById(R.id.Add_Purch_Date);
        publishYear = (TextView) findViewById(R.id.Add_Publish_Year);
        copyrightyear = (TextView) findViewById(R.id.Add_copy_righ_year_book);
        dateencoded = (TextView) findViewById(R.id.Add_date_encoded_book);
        title = findViewById(R.id.add_book_title);
        author = findViewById(R.id.add_book_Author);
        isbn = findViewById(R.id.add_book_ISBN);
        numpages = findViewById(R.id.add_book_numpages);
        numquantity = findViewById(R.id.add_book_number_of_quantity);
        discription = findViewById(R.id.add_book_discription);
        encodedby = findViewById(R.id.add_book_encoded_by);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate1(year, month+1, day);
        showDate2(year, month+1, day);
        showDate3(year, month+1, day);
        showDate4(year, month+1, day);

        loading = findViewById(R.id.addbook_progressbar);
        addbook = findViewById(R.id.ADD_Book);
        cancle = findViewById(R.id.add_Home);
    }

    @SuppressWarnings("deprecation")
    public void btn_purchDate(android.view.View view) {
        showDialog(101);
        Toast.makeText(getApplicationContext(), "ca",
                Toast.LENGTH_SHORT)
                .show();
    }
    @SuppressWarnings("deprecation")
    public void btn_publishYear(View view) {
        showDialog(102);
        Toast.makeText(getApplicationContext(), "ca",
                Toast.LENGTH_SHORT)
                .show();
    }

    @SuppressWarnings("deprecation")
    public void btn_copyrightyear(View view) {
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
    public void Cancle_Add_book(View view){
        finish();
    }
    private void show_back_button(){
        loading.setVisibility(View.GONE);
        addbook.setVisibility(View.VISIBLE);
    }
    public void Book_added(View view) {
        sessionManager = new SharedPreferences_Class(this);
        HashMap<String, String> user = sessionManager.getUserDetail();
        final String id = user.get(sessionManager.ID);
        loading.setVisibility(View.VISIBLE);
        addbook.setVisibility(View.GONE);
        final String title = this.title.getText().toString();
        final String author = this.author.getText().toString();
        final String isbn = this.isbn.getText().toString();
        final String purchdate = this.purchDate.getText().toString();
        final String publishyear = this.publishYear.getText().toString();
        final String copyrightyear = this.copyrightyear.getText().toString();
        final String dateencoded = this.dateencoded.getText().toString();
        final String numpages = this.numpages.getText().toString();
        final String quantity = this.numquantity.getText().toString();
        final String encodedby = this.encodedby.getText().toString();
        final String discription = this.discription.getText().toString();

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, connection.URL_ADD_TB_BOOK,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String succes = jsonObject.getString("success");
                            if (succes.equals("1")) {
                                Toast.makeText(getApplicationContext(),
                                        "Success",
                                        Toast.LENGTH_LONG).show();
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),
                                    "Error! " + e.toString(),
                                    Toast.LENGTH_LONG).show();
                            show_back_button();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),
                                "Error!! " + error.toString(),
                                Toast.LENGTH_LONG).show();
                        loading.setVisibility(View.GONE);
                        show_back_button();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("title", title);
                params.put("author", author);
                params.put("isbn", isbn);
                params.put("purchdate", purchdate);
                params.put("publishyear", publishyear);
                params.put("dateencoded", dateencoded);
                params.put("copyrightyear", copyrightyear);
                params.put("numpages", numpages);
                params.put("quantity", quantity);
                params.put("encodedby", encodedby);
                params.put("discription", discription);
                params.put("accountid", id);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

}