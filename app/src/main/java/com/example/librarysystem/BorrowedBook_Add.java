package com.example.librarysystem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BorrowedBook_Add extends AppCompatActivity {
    ArrayList<Spinner_book_Adaptor> spinner_book_adaptors_list;
    ArrayList<Spinner_Borrower_Adaptor> spinner_borrower_adaptors_list;
    ArrayAdapter<Spinner_book_Adaptor> adapterBook;
    ArrayAdapter<Spinner_Borrower_Adaptor> adapterBorrower;
    TextView txtbookid,txtborrowerid,txtquanity;
    Connection connection;
    SharedPreferences_Class sessionManager;
    EditText numcopies,bstatus;
    private TextView duedate,dateborrowed;
    private DatePicker datePicker;
    private Calendar calendar;
    private int year, month, day;
    private static final String TAG = EditProfile.class.getSimpleName();
    Button add,cancle;
    Spinner spinner_book,spinner_borrower;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrowed_book_add);
        spinner_book_adaptors_list = new ArrayList<>();
        spinner_borrower_adaptors_list = new ArrayList<>();
        duedate = (TextView) findViewById(R.id.Add_duedate);
        dateborrowed = (TextView) findViewById(R.id.Add_date_barrowed);
        spinner_book = findViewById(R.id.spinner_book);
        spinner_borrower = findViewById(R.id.spinner_borrower);
        numcopies = findViewById(R.id.add_borrowedbook_numcopies);
        bstatus = findViewById(R.id.add_borrowedbook_bstatus);
        add = findViewById(R.id.btn_ADD_borrowedbook);
        cancle = findViewById(R.id.btn_Cancle_ADD_borrowedbook);
        txtbookid = findViewById(R.id.txt_bookid);
        txtborrowerid = findViewById(R.id.txt_borrowerid);
        txtquanity = findViewById(R.id.txtAvilable);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate1(year, month+1, day);
        showDate2(year, month+1, day);
        sessionManager = new SharedPreferences_Class(this);
        HashMap<String, String> user = sessionManager.getUserDetail();
        final String id = user.get(sessionManager.ID);

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();
        StringRequest stringRequest1 = new StringRequest(Request.Method.POST, connection.URL_SPINNER_BOOK_TB_BORROWEDBOOK,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.i(TAG, response.toString());
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);
                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject product = array.getJSONObject(i);
                                String id = product.getString("id");
                                String book = product.getString("title");
                                Spinner_book_Adaptor class_addaptor = new Spinner_book_Adaptor(id,book);
                                spinner_book_adaptors_list.add(class_addaptor);
                                adapterBook= new ArrayAdapter<Spinner_book_Adaptor>(BorrowedBook_Add.this, android.R.layout.simple_list_item_1, spinner_book_adaptors_list);
                                adapterBook.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinner_book.setAdapter(adapterBook);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),
                                    "View Error! " + e.toString(),
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
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("account_id", id);
                return params;
            }
        };
        Volley.newRequestQueue(this).add(stringRequest1);

        spinner_book.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Spinner_book_Adaptor getselected = (Spinner_book_Adaptor) adapterView.getSelectedItem();
                txtbookid.setText(getselected.getCourseID());
                getQuantity(getselected.getCourseID());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        StringRequest stringRequest2 = new StringRequest(Request.Method.POST, connection.URL_SPINNER_BORROWER_TB_BORROWEDBOOK,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            progressDialog.dismiss();
                            Log.i(TAG, response.toString());
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);
                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject product = array.getJSONObject(i);
                                String id = product.getString("id");
                                String fname = product.getString("fname");
                                String mname = product.getString("mname");
                                String lname = product.getString("lname");
                                Spinner_Borrower_Adaptor borrower_class = new Spinner_Borrower_Adaptor(id,fname,mname,lname);
                                spinner_borrower_adaptors_list.add(borrower_class);
                                adapterBorrower = new ArrayAdapter<Spinner_Borrower_Adaptor>(BorrowedBook_Add.this, android.R.layout.simple_spinner_item, spinner_borrower_adaptors_list);
                                adapterBorrower.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinner_borrower.setAdapter(adapterBorrower);


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),
                                    "View Error! " + e.toString(),
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
                        progressDialog.dismiss();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("account_id", id);
                return params;
            }
        };
        Volley.newRequestQueue(this).add(stringRequest2);
        spinner_borrower.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Spinner_Borrower_Adaptor getselected = (Spinner_Borrower_Adaptor) adapterView.getSelectedItem();
                txtborrowerid.setText(getselected.getCourseID());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


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
    public void Cancle_Add_borrowedbook(View view){
        finish();
    }
    public void Borrowedbook_added(View view){
        int avilable = Integer.parseInt(txtquanity.getText().toString());
        int storequantity = Integer.parseInt(numcopies.getText().toString());
        int value = avilable-storequantity;
        if(avilable>storequantity){
            Borrowedbook_adding();
            edit_book(value+"");

        }else{
            Toast.makeText(BorrowedBook_Add.this,
                    "Book is not enough ",
                    Toast.LENGTH_LONG).show();
            numcopies.setError("Book is not enough");
        }
    }
    private void Borrowedbook_adding(){
        sessionManager = new SharedPreferences_Class(this);
        HashMap<String, String> user = sessionManager.getUserDetail();
        final String id = user.get(sessionManager.ID);
        final String bookid = txtbookid.getText().toString();
        final String borrowerid = txtborrowerid.getText().toString();
        final String duedate = this.duedate.getText().toString();
        final String numcopies = this.numcopies.getText().toString();
        final String bstatus = this.bstatus.getText().toString();
        final String dateborrowed = this.dateborrowed.getText().toString();

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, connection.URL_ADD_TB_BORROWEDBOOK,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String succes = jsonObject.getString("success");
                            if(succes.equals("1")){
                                Toast.makeText(getApplicationContext(),
                                        "Success Added",
                                        Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),
                                    "Error Added! "+e.toString(),
                                    Toast.LENGTH_LONG).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),
                                "Error Added!! "+error.toString(),
                                Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("bookid",bookid);
                params.put("borrowerid",borrowerid);
                params.put("duedate",duedate);
                params.put("numcopies",numcopies);
                params.put("bstatus",bstatus);
                params.put("dateborrowed",dateborrowed);
                params.put("account_id",id);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    private void edit_book(final String edit){
        final String bookid = txtbookid.getText().toString();
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
    public void getQuantity(final String id){
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
                                txtquanity.setText(strquantity);
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
}