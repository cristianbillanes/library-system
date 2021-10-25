package com.example.librarysystem;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
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
import java.util.Map;

public class Search extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Connection connection;
    SharedPreferences_Class sessionManager;
    GridView gradeview;
    ArrayList<Book_Sub_Class_Adaptor> book_sub_class_adaptors;
    ArrayList<BorrowedBook_Sub_Class_Adaptor> borrowedBook_sub_class_adaptors;
    ArrayList<Borrower_Sub_Class_Adaptor> borrower_sub_class_adaptors;
    EditText Search;
    Spinner spinner_view_book;
    TextView txt_spinner_view;
    private boolean onResum_check;
    String[] users = { "Book", "Borrower", "Borrowed Book","Expired" };
    private static final String TAG = EditProfile.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        book_sub_class_adaptors = new ArrayList<Book_Sub_Class_Adaptor>();
        borrowedBook_sub_class_adaptors = new ArrayList<BorrowedBook_Sub_Class_Adaptor>();
        borrower_sub_class_adaptors = new ArrayList<Borrower_Sub_Class_Adaptor>();
        gradeview = findViewById(R.id.gridView);
        Search = findViewById(R.id.view_Search);
        txt_spinner_view = findViewById(R.id.txt_spinner_value);
        spinner_view_book = findViewById(R.id.spinner_view);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, users);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_view_book.setAdapter(adapter);
        spinner_view_book.setOnItemSelectedListener(this);
        onResum_check = false;
    }
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        String searching = Search.getText().toString();
        txt_spinner_view.setText(users[position]);
        if(position==0)Book_Search(searching);
        if(position==1)Borrower_Search(searching);
        if(position==2)Borrowedbook_Search(searching);
        if(position==3)expered();
    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(onResum_check)Searching();
    }

    public void btn_Search(View view){
        Searching();
    }
    private void Searching(){
        String searching = Search.getText().toString();
        String category = txt_spinner_view.getText().toString();
        if(category.equals("Book"))Book_Search(searching);
        if(category.equals("Borrower"))Borrower_Search(searching);
        if(category.equals("Borrowed Book"))Borrowedbook_Search(searching);
        if(category.equals("Expired"))expered();
    }
    private void Book_Search(final String research){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();
        onResum_check = true;
        book_sub_class_adaptors.clear();
        sessionManager = new SharedPreferences_Class(this);
        HashMap<String, String> user = sessionManager.getUserDetail();
        final String f_id = user.get(sessionManager.ID);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, connection.URL_DETAILS_TB_BOOK,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.i(TAG, response.toString());
                            progressDialog.dismiss();
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);
                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject object = array.getJSONObject(i);
                                String strid = object.getString("id");
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
                                book_sub_class_adaptors.add(new Book_Sub_Class_Adaptor(
                                        strid, strTitle, strAuthor, strpurchdate, strpublishyear,
                                        strisbn, Search.this,getApplicationContext()

                                ));
                            }

                            Book_Adaptor adapter = new Book_Adaptor(Search.this, book_sub_class_adaptors);
                            gradeview.setAdapter(adapter);
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
                params .put("account_id",f_id);
                params .put("search",research);
                return params ;
            }
        };


        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);

    }
    private void Borrower_Search(final String research){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();
        onResum_check = true;
        borrower_sub_class_adaptors.clear();
        sessionManager = new SharedPreferences_Class(this);
        HashMap<String, String> user = sessionManager.getUserDetail();
        final String f_id = user.get(sessionManager.ID);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, connection.URL_DETAILS_TB_BORROWER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.i(TAG, response.toString());
                            progressDialog.dismiss();
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);
                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);
                                String strid = object.getString("id");
                                String strfname = object.getString("fname").trim();
                                String strmname = object.getString("mname").trim();
                                String strlname = object.getString("lname").trim();
                                String strcontact = object.getString("contact").trim();
                                String stremail = object.getString("email").trim();

                                borrower_sub_class_adaptors.add(new Borrower_Sub_Class_Adaptor(
                                        strid, strfname, strmname, strlname,
                                        strcontact, stremail,
                                        Search.this,getApplicationContext()

                                ));
                            }

                            Borrower_Adaptor adapter = new Borrower_Adaptor(Search.this, borrower_sub_class_adaptors);
                            gradeview.setAdapter(adapter);
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
                params .put("account_id",f_id);
                params .put("search",research);
                return params ;
            }
        };


        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);

    }
    private void Borrowedbook_Search(final String research){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();
        onResum_check = true;
        borrowedBook_sub_class_adaptors.clear();
        sessionManager = new SharedPreferences_Class(this);
        HashMap<String, String> user = sessionManager.getUserDetail();
        final String f_id = user.get(sessionManager.ID);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, connection.URL_DETAILS_TB_BORROWEDBOOK,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.i(TAG, response.toString());
                            progressDialog.dismiss();
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);
                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject object = array.getJSONObject(i);
                                String strid = object.getString("id").trim();
                                String strbookid = object.getString("bookid").trim();
                                String strborrowerid = object.getString("borrowerid").trim();
                                String strduedate = object.getString("duedate").trim();
                                String strnumcopies = object.getString("numcopies").trim();
                                String strbstatus = object.getString("bstatus").trim();
                                String strdateborrowed = object.getString("dateborrowed").trim();
                                String strtitle = object.getString("title").trim();
                                String strauhtor = object.getString("author").trim();
                                String strlname = object.getString("lname").trim();
                                String strfname = object.getString("fname").trim();
                                String strmname = object.getString("mname").trim();

                                borrowedBook_sub_class_adaptors.add(new BorrowedBook_Sub_Class_Adaptor(
                                        strid, strbookid, strborrowerid, strnumcopies,
                                        strbstatus, strduedate, strdateborrowed, strfname,
                                        strmname, strlname, strtitle, strauhtor,
                                        Search.this,getApplicationContext()

                                ));
                            }

                            BorrowedBook_Adaptor adapter = new BorrowedBook_Adaptor(Search.this, borrowedBook_sub_class_adaptors);
                            gradeview.setAdapter(adapter);
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
                params .put("account_id",f_id);
                params .put("search",research);
                return params ;
            }
        };


        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);

    }
    public void Search_Home(View view){
        finish();
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }
    public void Search_ADD(View view){
        finish();
        Intent intent = new Intent(this, Add.class);
        startActivity(intent);
    }
    private void expered(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int value = year*365+month*31+day;
        final String searching = Search.getText().toString();
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();
        onResum_check = true;
        borrowedBook_sub_class_adaptors.clear();
        sessionManager = new SharedPreferences_Class(this);
        HashMap<String, String> user = sessionManager.getUserDetail();
        final String f_id = user.get(sessionManager.ID);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, connection.URL_DETAILS_TB_BORROWEDBOOK,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.i(TAG, response.toString());
                            progressDialog.dismiss();
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);
                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject object = array.getJSONObject(i);
                                String strid = object.getString("id").trim();
                                String strbookid = object.getString("bookid").trim();
                                String strborrowerid = object.getString("borrowerid").trim();
                                String strduedate = object.getString("duedate").trim();
                                String strnumcopies = object.getString("numcopies").trim();
                                String strbstatus = object.getString("bstatus").trim();
                                String strdateborrowed = object.getString("dateborrowed").trim();
                                String strtitle = object.getString("title").trim();
                                String strauhtor = object.getString("author").trim();
                                String strlname = object.getString("lname").trim();
                                String strfname = object.getString("fname").trim();
                                String strmname = object.getString("mname").trim();
                                int total = 0;
                                String getcharvalue="";
                                for(int x = 0; x < strduedate. length(); x++) {

                                    if(strduedate.charAt(x)!='-'){
                                        getcharvalue=getcharvalue+strduedate.charAt(x);
                                        if(getcharvalue.equals("0"))
                                            getcharvalue="";
                                    }else{
                                        if(total==0){
                                            total = total + Integer.parseInt(getcharvalue)*365;
                                            getcharvalue="";
                                        }else
                                        if(total%365==0){
                                            total = total + Integer.parseInt(getcharvalue)*31;
                                            getcharvalue="";
                                        }else
                                        {
                                            total = total + Integer.parseInt(getcharvalue);
                                            getcharvalue="";
                                        }
                                    }
                                }
                                if(total>value){

                                }else {
                                    borrowedBook_sub_class_adaptors.add(new BorrowedBook_Sub_Class_Adaptor(
                                            strid, strbookid, strborrowerid, strnumcopies,
                                            strbstatus, strduedate, strdateborrowed, strfname,
                                            strmname, strlname, strtitle, strauhtor,
                                            Search.this,getApplicationContext()

                                    ));
                                }

                            }

                            BorrowedBook_Adaptor adapter = new BorrowedBook_Adaptor(Search.this, borrowedBook_sub_class_adaptors);
                            gradeview.setAdapter(adapter);
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
                params .put("account_id",f_id);
                params .put("search",searching);
                return params ;
            }
        };


        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);
    }
}