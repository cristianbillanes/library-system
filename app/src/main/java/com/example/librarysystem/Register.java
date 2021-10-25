package com.example.librarysystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    private EditText name, email, password, C_password;
    private Button btn_regist;
    private ProgressBar loading;

    Connection connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        loading = findViewById(R.id.register_progressbar);
        name = findViewById(R.id.register_name);
        email = findViewById(R.id.register_email);
        password = findViewById(R.id.register_password);
        C_password = findViewById(R.id.register_confrim_password);
        btn_regist = findViewById(R.id.register_done);
        btn_regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check_edittext()){
                    Register();
                }else{
                    Toast.makeText(getApplicationContext(),
                            "Insert your infromation are not valid",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

    }
    private boolean check_edittext(){
        String s_name = name.getText().toString();
        String s_email = email.getText().toString();
        String s_password = password.getText().toString();
        String s_c_password = C_password.getText().toString();
        if(s_password.isEmpty()){
            password.setError("PLease insert password");
            return false;
        }else{
            if(s_password.equals(s_c_password)){
                if(s_name.isEmpty()||s_email.isEmpty()){
                    name.setError("Please insert name");
                    email.setError("Please insert email");
                    return false;
                }else {
                    return true;
                }
            }else {
                C_password.setError("Not match password");
                return false;
            }
        }

    }
    private  void Register() {
        loading.setVisibility(View.VISIBLE);
        btn_regist.setVisibility(View.GONE);
        final String name = this.name.getText().toString();
        final String email = this.email.getText().toString();
        final String password = this.password.getText().toString();

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, connection.URL_REGISTER,
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String succes = jsonObject.getString("success");
                    if(succes.equals("1")){
                        Toast.makeText(getApplicationContext(),
                                "Register Success",
                                Toast.LENGTH_LONG).show();
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Register Error! "+e.toString(),
                            Toast.LENGTH_LONG).show();
                    loading.setVisibility(View.GONE);
                    btn_regist.setVisibility(View.VISIBLE);
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),
                                "Register Error! "+error.toString(),
                                Toast.LENGTH_LONG).show();
                        loading.setVisibility(View.GONE);
                        btn_regist.setVisibility(View.VISIBLE);
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> register = new HashMap<>();
                register.put("name",name);
                register.put("email",email);
                register.put("password",password);
                return register;
            }
        };
        requestQueue.add(stringRequest);
    }
    public void Register_Back(View view){
        finish();
    }
}