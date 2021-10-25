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

public class Borrower_View extends AppCompatActivity {
    Connection connection;
    SharedPreferences_Class sessionManager;
    private TextView txt_id,fname,mname,lname,contact,email;
    private static final String TAG = EditProfile.class.getSimpleName();
    private Intent intent;
    private Bundle extra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrower_view);
        txt_id = findViewById(R.id.borrower_view_id);
        fname = findViewById(R.id.borrower_view_fname);
        mname = findViewById(R.id.borrower_view_mname);
        lname = findViewById(R.id.borrower_view_lname);
        contact = findViewById(R.id.borrower_view_contact);
        email = findViewById(R.id.borrower_view_email);
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
        StringRequest stringRequest = new StringRequest(Request.Method.POST, connection.URL_DETAILS2_TB_BORROWER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.i(TAG, response.toString());
                            progressDialog.dismiss();
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);
                                String strfname = object.getString("fname").trim();
                                String strmname = object.getString("mname").trim();
                                String strlname = object.getString("lname").trim();
                                String strcontact = object.getString("contact").trim();
                                String stremail = object.getString("email").trim();
                                fname.setText(strfname);
                                mname.setText(strmname);
                                lname.setText(strlname);
                                contact.setText(strcontact);
                                email.setText(stremail);
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
                                "View Error!!! " + error.toString(),
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
    public void borrower_view_back(View view){
        finish();
    }
    public void borrower_view_delete(View view){
        final String extra_id = extra.getString("id");
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST, Connection.URL_DELETE_TB_BORROWER, new Response.Listener<String>() {
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
    public void borrower_view_to_edit(View view){
        final String extra_id = extra.getString("id");
        Intent intent = new Intent(this, Borrower_edit.class);
        intent.putExtra("id",extra_id);
        startActivity(intent);
    }
}