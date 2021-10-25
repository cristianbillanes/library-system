package com.example.librarysystem;

import android.app.ProgressDialog;
import android.content.Intent;
import android.widget.ArrayAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Adaptor_GradeView extends ArrayAdapter<GradeView_Support> {
    public Adaptor_GradeView(@NonNull Context context, ArrayList<GradeView_Support> courseModelArrayList) {
        super(context, 0, courseModelArrayList);
    }
    Connection connection;


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listitemView = convertView;
        if (listitemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.grade_view_list, parent, false);
        }
        GradeView_Support courseModel = getItem(position);
        TextView course1 = listitemView.findViewById(R.id.text_gradeview1);
        TextView course2 = listitemView.findViewById(R.id.text_gradeview2);
        TextView course3 = listitemView.findViewById(R.id.text_gradeview3);
        TextView course4 = listitemView.findViewById(R.id.text_gradeview4);
        ImageView button1 = listitemView.findViewById(R.id.show_bar);
        ImageView button2 = listitemView.findViewById(R.id.delete_bar);
        String string1 = courseModel.getTextview1();
        String string2 = courseModel.getTextview2();
        String string3 = courseModel.getTextview3();
        String id = courseModel.getCourse_id();
        Context context1 = courseModel.getContext1();
        Context context2 = courseModel.getContext2();
        course1.setText(string1);
        course2.setText(string2);
        course3.setText(string3);
        course4.setText(id);
        if(string1.isEmpty()||string1.equals("")||string1.equals(" ")||string1.equals("Title")){
            button1.setVisibility(View.GONE);
        }else{
            course1.setVisibility(View.VISIBLE);
            course2.setVisibility(View.VISIBLE);
            course3.setVisibility(View.VISIBLE);
            button2.setVisibility(View.VISIBLE);
            button1.setVisibility(View.VISIBLE);
        }
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context1, EditBook.class);
                intent.putExtra("id",id);
                context1.startActivity(intent);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog = new ProgressDialog(context1);
                progressDialog.setMessage("Loading....");
                progressDialog.show();
                StringRequest stringRequest = new StringRequest(
                        Request.Method.POST, Connection.URL_DELETE_BOOK, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        course1.setVisibility(View.GONE);
                        course2.setVisibility(View.GONE);
                        course3.setVisibility(View.GONE);
                        course4.setVisibility(View.GONE);
                        button1.setVisibility(View.GONE);
                        button2.setVisibility(View.GONE);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(context2,"Deleting faild",Toast.LENGTH_LONG).show();
                        course1.setVisibility(View.VISIBLE);
                        course2.setVisibility(View.VISIBLE);
                        course3.setVisibility(View.VISIBLE);
                        button2.setVisibility(View.VISIBLE);
                        button1.setVisibility(View.VISIBLE);
                    }
                }
                ){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String>parms=new HashMap<String, String>();
                        parms.put("id",id);
                        return parms;
                    }
                };
                RequestQueue requestQueue= Volley.newRequestQueue(context1);
                requestQueue.add(stringRequest);

            }
        });
        return listitemView;
    }
}
