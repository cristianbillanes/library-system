package com.example.librarysystem;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public class Book_View extends AppCompatActivity {
    Connection connection;
    SharedPreferences_Class sessionManager;
    private TextView txt_id,purchDate,publishYear,copyrightyear,dateencoded;
    private TextView title,author,isbn,numpages,numquantity,discription,encodedby;
    private static final String TAG = EditProfile.class.getSimpleName();
    private Intent intent;
    private Bundle extra;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_view);
        txt_id = findViewById(R.id.id_book_view);
        purchDate = findViewById(R.id.purchdate_book_view);
        publishYear = findViewById(R.id.publishyear_book_view);
        copyrightyear = findViewById(R.id.copyrigthyear_book_view);
        dateencoded = findViewById(R.id.dateencoded_book_view);
        title = findViewById(R.id.title_book_view);
        author = findViewById(R.id.author_book_view);
        isbn = findViewById(R.id.isbn_book_view);
        numpages = findViewById(R.id.numpages_book_view);
        numquantity = findViewById(R.id.numquantity_book_view);
        discription = findViewById(R.id.dicription_book_view);
        encodedby = findViewById(R.id.encodedby_book_view);
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
        StringRequest stringRequest = new StringRequest(Request.Method.POST, connection.URL_DETAILS2_TB_BOOK,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.i(TAG, response.toString());
                            progressDialog.dismiss();
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);
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
                                title.setText(strTitle);
                                author.setText(strAuthor);
                                isbn.setText(strisbn);
                                purchDate.setText(strpurchdate);
                                publishYear.setText(strpublishyear);
                                numpages.setText(strnumpages);
                                numquantity.setText(strquantity);
                                dateencoded.setText(strdateencoded);
                                encodedby.setText(strencodedby);
                                discription.setText(strdiscription);
                                copyrightyear.setText(strcopyright);
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
                                "View Error!! " + error.toString(),
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
    public void book_view_back(View view){
        finish();
    }
    public void book_view_delete(View view){
        final String extra_id = extra.getString("id");
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST, Connection.URL_DELETE_TB_BOOK, new Response.Listener<String>() {
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
    public void book_view_to_edit(View view){
        final String extra_id = extra.getString("id");
        Intent intent = new Intent(this, Book_Edit.class);
        intent.putExtra("id",extra_id);
        startActivity(intent);
    }
}