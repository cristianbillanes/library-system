package com.example.librarysystem;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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

public class EditProfile extends AppCompatActivity {
    Connection connection;
    SharedPreferences_Class sessionManager;
    private static final String TAG = EditProfile.class.getSimpleName();
    EditText e_name,e_email, e_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        e_name = findViewById(R.id.edit_name);
        e_email = findViewById(R.id.edit_email);
        e_password = findViewById(R.id.edit_password);
        getUserDetail();
    }
    public void Save_Edit_profile(View view){
        String s_name = e_name.getText().toString();
        String s_email = e_email.getText().toString();
        String s_password = e_password.getText().toString();
        if(s_name.isEmpty()||s_email.isEmpty()||s_password.isEmpty()){
            e_name.setError("Please insert name");
            e_email.setError("Please insert email");
            e_password.setError("PLease insert password");
        }else {
            SaveDetail();
        }
    }
    public void getUserDetail(){
        sessionManager = new SharedPreferences_Class(this);
        HashMap<String, String> user = sessionManager.getUserDetail();
        final String name = e_name.getText().toString();
        final String email = e_email.getText().toString();
        final String password = e_password.getText().toString();
        final String id = user.get(sessionManager.ID);
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, connection.URL_DETAILS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Log.i(TAG, response.toString());
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("read");
                            if(success.equals("1")) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String strName = object.getString("name").trim();
                                    String strEmail = object.getString("email").trim();
                                    e_email.setText(strEmail);
                                    e_name.setText(strName);
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),
                                    "Error Reading Detail! " + e.toString(),
                                    Toast.LENGTH_LONG).show();
                        }

                    }
                    },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),
                                "Details Error! "+error.toString(),
                                Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String>params = new HashMap<>();
                params .put("id",id);
                return params ;
            }
        };
        requestQueue.add(stringRequest);
    }
    public void Cancel_Edit(View view){
        finish();
    }

    private void SaveDetail() {
        sessionManager = new SharedPreferences_Class(this);
        HashMap<String, String> user = sessionManager.getUserDetail();
        final String name = e_name.getText().toString();
        final String email = e_email.getText().toString();
        final String password = e_password.getText().toString();
        final String id = user.get(sessionManager.ID);

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, connection.URL_EDIT,
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
                                        "Success Saving",
                                        Toast.LENGTH_LONG).show();
                                sessionManager.createsession(name,email,id);
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),
                                    "Error! please check your password!! "+e.toString(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),
                                "Saving Error! "+error.toString(),
                                Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String>params = new HashMap<>();
                params .put("name",name);
                params .put("email",email);
                params .put("id",id);
                params .put("password",password);
                return params ;
            }
        };
        requestQueue.add(stringRequest);
    }
}