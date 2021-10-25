package com.example.librarysystem;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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
import java.util.HashMap;
import java.util.Map;

public class ViewBook extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    Connection connection;
    SharedPreferences_Class sessionManager;
    GridView book_gradeview;
    ArrayList<GradeView_Support> courseModelArrayList;
    EditText Search;
    Spinner spinner_view_book;
    TextView txt_spinner_view;
    private boolean onResum_check;
    String[] users = { "author", "isbn", "purchdate", "publishyear" };
    private static final String TAG = EditProfile.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_book);
        courseModelArrayList = new ArrayList<GradeView_Support>();
        book_gradeview = findViewById(R.id.Book_gridView);
        Search = findViewById(R.id.view_Search);
        txt_spinner_view = findViewById(R.id.txt_spinner_value_book);
        spinner_view_book = findViewById(R.id.spinner_view_book);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, users);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_view_book.setAdapter(adapter);
        spinner_view_book.setOnItemSelectedListener(this);
        onResum_check = false;
    }
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position,long id) {
        String get_Search_value = Search.getText().toString();
        courseModelArrayList.clear();
        txt_spinner_view.setText(users[position]);
        Grade_View_Reaserch(users[position],get_Search_value);
    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        String get_Search_value = Search.getText().toString();
        courseModelArrayList.clear();
        Grade_View_Reaserch("author",get_Search_value);
    }
    public void btn_Research(View view){
        action_show();
    }
    private void action_show(){
        String get_Search_value = Search.getText().toString();
        courseModelArrayList.clear();
        Grade_View_Reaserch(txt_spinner_view.getText().toString(),get_Search_value);
    }
    private void Grade_View_Reaserch(final String choose,final String research){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();
        onResum_check = true;

        sessionManager = new SharedPreferences_Class(this);
        HashMap<String, String> user = sessionManager.getUserDetail();
        final String f_id = user.get(sessionManager.ID);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, connection.URL_BOOK_DETAILS,
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
                                JSONObject product = array.getJSONObject(i);
                                String title = product.getString("title");
                                String id = product.getString("id");
                                String getselected =  product.getString("choose");
                                //adding the product to product list
                                courseModelArrayList.add(new GradeView_Support(
                                        ViewBook.this,getApplicationContext(),
                                        title," ",getselected,id

                                ));
                            }

                            //creating adapter object and setting it to recyclerview
                            Adaptor_GradeView adapter = new Adaptor_GradeView(ViewBook.this, courseModelArrayList);
                            book_gradeview.setAdapter(adapter);
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
                params .put("choose",choose);
                params .put("search",research);
                return params ;
            }
        };


        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(onResum_check)
        action_show();
    }

    public void View_Back_TO_HOME(View view){
        finish();
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }
    public void View_ADD_BOOK(View view){
        finish();
        Intent intent = new Intent(this, AddBook.class);
        startActivity(intent);
    }
}