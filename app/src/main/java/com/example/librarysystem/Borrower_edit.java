package com.example.librarysystem;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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

public class Borrower_edit extends AppCompatActivity {
    Connection connection;
    SharedPreferences_Class sessionManager;
    EditText fname,mname,lname,contact,email;
    Button add,cancle;
    private static final String TAG = EditProfile.class.getSimpleName();
    private Intent intent;
    private Bundle extra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrower_edit);
        fname = findViewById(R.id.edit_borrower_fname);
        mname = findViewById(R.id.edit_borrower_mname);
        lname = findViewById(R.id.edit_borrower_lname);
        contact = findViewById(R.id.edit_borrower_contact);
        email = findViewById(R.id.edit_borrower_email);
        add = findViewById(R.id.btn_edit_borrower);
        cancle = findViewById(R.id.btn_Cancle_edit_borrower);
        intent= getIntent();
        extra = intent.getExtras();
        getUserDetail();
    }
    public void getUserDetail(){
        final String extra_id = extra.getString("id");
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
    public void Cancle_edit_borrower(View view){
        finish();
    }
    public void edit_borrower(View view) {
        sessionManager = new SharedPreferences_Class(this);
        final String extra_id = extra.getString("id");
        HashMap<String, String> user = sessionManager.getUserDetail();
        final String id = user.get(sessionManager.ID);
        final String fname = this.fname.getText().toString();
        final String mname = this.mname.getText().toString();
        final String lname = this.lname.getText().toString();
        final String contact = this.contact.getText().toString();
        final String email = this.email.getText().toString();

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, connection.URL_EDIT_TB_BORROWER,
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
                params.put("fname",fname);
                params.put("mname",mname);
                params.put("lname",lname);
                params.put("contact",contact);
                params.put("email",email);
                params.put("account_id",id);
                params.put("id",extra_id);
                return params ;
            }
        };
        requestQueue.add(stringRequest);
    }
}