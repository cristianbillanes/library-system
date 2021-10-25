package com.example.librarysystem;

import androidx.appcompat.app.AppCompatActivity;

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

public class Borrower_Add extends AppCompatActivity {
    Connection connection;
    SharedPreferences_Class sessionManager;
    EditText fname,mname,lname,contact,email;
    Button add,cancle;
    ProgressBar loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrower_add);
        fname = findViewById(R.id.add_borrower_fname);
        mname = findViewById(R.id.add_borrower_mname);
        lname = findViewById(R.id.add_borrower_lname);
        contact = findViewById(R.id.add_borrower_contact);
        email = findViewById(R.id.add_borrower_email);
        add = findViewById(R.id.btn_ADD_borrower);
        cancle = findViewById(R.id.btn_Cancle_ADD_borrower);
    }
    private void show_back_button(){
        loading.setVisibility(View.GONE);
        add.setVisibility(View.VISIBLE);
        cancle.setVisibility(View.VISIBLE);
    }
    public void Cancle_Add_borrower(View view){
        finish();
    }
    public void Borrower_added(View view){
        sessionManager = new SharedPreferences_Class(this);
        HashMap<String, String> user = sessionManager.getUserDetail();
        final String id = user.get(sessionManager.ID);
        final String fname = this.fname.getText().toString();
        final String mname = this.mname.getText().toString();
        final String lname = this.lname.getText().toString();
        final String contact = this.contact.getText().toString();
        final String email = this.email.getText().toString();

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, connection.URL_ADD_TB_BORROWER,
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
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),
                                    "Error Added! "+e.toString(),
                                    Toast.LENGTH_LONG).show();
                            show_back_button();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),
                                "Error Added!! "+error.toString(),
                                Toast.LENGTH_LONG).show();
                        show_back_button();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("fname",fname);
                params.put("mname",mname);
                params.put("lname",lname);
                params.put("contact",contact);
                params.put("email",email);
                params.put("account_id",id);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}