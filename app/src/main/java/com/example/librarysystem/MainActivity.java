package com.example.librarysystem;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private EditText email, password;
    private Button btn_login;
    private ProgressBar loading;
    public static final String ID = "ID";
    public static final String NAME = "NAME";
    public static final String EMAIL = "EMAIL";
    SharedPreferences_Class sessionManager;
    Connection connection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sessionManager = new SharedPreferences_Class(this);

        loading = findViewById(R.id.login_progressbar);
        email = findViewById(R.id.login_email);
        password = findViewById(R.id.login_password);
        btn_login = findViewById(R.id.login_button);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s_email = email.getText().toString();
                String s_password = password.getText().toString();
                if(s_email.isEmpty()||s_password.isEmpty()){
                    email.setError("Please insert email");
                    password.setError("Please insert password");
                }else {
                    Loin_in(s_email,s_password);
                }
        }});
    }

    private void Loin_in(String s_email,String s_password){
        loading.setVisibility(View.VISIBLE);
        btn_login.setVisibility(View.GONE);
        final String email = s_email;
        final String password = s_password;

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, connection.URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String succes = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("login");
                            if(succes.equals("1")){
                                for(int i =0;i< jsonArray.length();i++){
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String v_name = object.getString("name").trim();
                                    String v_email = object.getString("email").trim();
                                    String v_id = object.getString("id").trim();
                                    Toast.makeText(getApplicationContext(),
                                            v_name+" is success Login",
                                            Toast.LENGTH_LONG).show();
                                    Home(v_name,v_email,v_id);
                                    loading.setVisibility(View.GONE);
                                    btn_login.setVisibility(View.VISIBLE);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),
                                    "Login Error!! "+e.toString(),
                                    Toast.LENGTH_LONG).show();
                            loading.setVisibility(View.GONE);
                            btn_login.setVisibility(View.VISIBLE);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),
                                "Login Error! "+error.toString(),
                                Toast.LENGTH_LONG).show();
                        loading.setVisibility(View.GONE);
                        btn_login.setVisibility(View.VISIBLE);
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> Login = new HashMap<>();
                Login.put("email",email);
                Login.put("password",password);
                return Login;
            }
        };
        requestQueue.add(stringRequest);
    }
    public void Register(View view){
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }
    private void Home(String v_name, String v_email, String id_value){
        sessionManager.createsession(v_name,v_email,id_value);
        Intent intent = new Intent(this, Home.class);
        intent.putExtra(ID, id_value);
        intent.putExtra(NAME, v_name);
        intent.putExtra(EMAIL, v_email);
        startActivity(intent);
    }

}