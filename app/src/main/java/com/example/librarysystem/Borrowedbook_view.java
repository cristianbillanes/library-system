package com.example.librarysystem;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

import java.util.HashMap;
import java.util.Map;

public class Borrowedbook_view extends AppCompatActivity {
    Connection connection;
    SharedPreferences_Class sessionManager;
    private TextView txt_id,name,title,duedate,numcopies,bstatus,dateborrowed;
    private static final String TAG = EditProfile.class.getSimpleName();
    private Intent intent;
    private Bundle extra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrowedbook_view);
        txt_id = findViewById(R.id.borrowedbook_view_id);
        name = findViewById(R.id.borrowedbook_view_name);
        title = findViewById(R.id.borrowedbook_view_title);
        duedate = findViewById(R.id.borrowedbook_view_duedate);
        numcopies = findViewById(R.id.borrowedbook_view_numcopies);
        bstatus = findViewById(R.id.borrowedbook_view_bstatus);
        dateborrowed = findViewById(R.id.borrowedbook_view_dateborrowed);
        intent= getIntent();
        extra = intent.getExtras();
        txt_id.setText(extra.getString("id"));
    }
    @Override
    protected void onResume() {
        super.onResume();
        getUserDetail();
    }
    public void getUserDetail(){
        final String extra_id = extra.getString("id");
        txt_id.setText(extra_id);
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, connection.URL_DETAILS3_TB_BORROWEDBOOK,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.i(TAG, response.toString());
                            progressDialog.dismiss();
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);
                                String strduedate = object.getString("duedate").trim();
                                String strnumcopies = object.getString("numcopies").trim();
                                String strbstatus = object.getString("bstatus").trim();
                                String strdateborrowed = object.getString("dateborrowed").trim();
                                String strtitle = object.getString("title").trim();
                                String strauhtor = object.getString("author").trim();
                                String strlname = object.getString("lname").trim();
                                String strfname = object.getString("fname").trim();
                                String strmname = object.getString("mname").trim();
                                name.setText(strfname+" "+strlname);
                                title.setText(strtitle);
                                duedate.setText(strduedate);
                                numcopies.setText(strnumcopies);
                                bstatus.setText(strbstatus);
                                dateborrowed.setText(strdateborrowed);
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
    public void borrowedbook_view_back(android.view.View view){
        finish();
    }
    public void borrrowedbook_view_return(View view){
        final String bookid = extra.getString("bookid");
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Finding Book....");
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
                                int value2 = Integer.parseInt(numcopies.getText().toString());
                                int total = value1+value2;
                                edit_book( total+"");

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
                params .put("id",bookid);
                return params ;
            }
        };
        Volley.newRequestQueue(this).add(stringRequest);
    }
    private void edit_book(final String edit){
        final String bookid = extra.getString("bookid");
        sessionManager = new SharedPreferences_Class(this);
        HashMap<String, String> user = sessionManager.getUserDetail();
        final String id = user.get(sessionManager.ID);
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Editing Book....");
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
                                delete();
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
    public void borrowedbook_view_delete(View view){
        delete();
    }
    private void delete(){
        final String extra_id = extra.getString("id");
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Deleting....");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST, Connection.URL_DELETE_TB_BORROWEDBOOK, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Deleting faild",Toast.LENGTH_LONG).show();
            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>parms=new HashMap<String, String>();
                parms.put("id",extra_id);
                return parms;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
    public void borrowedbook_view_to_edit(View view){
        final String extra_id = extra.getString("id");
        Intent intent = new Intent(this, BorrowedBook_Edit.class);
        intent.putExtra("id",extra_id);
        startActivity(intent);
    }
}