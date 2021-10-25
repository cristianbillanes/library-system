package com.example.librarysystem;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Borrower_Adaptor extends ArrayAdapter<Borrower_Sub_Class_Adaptor> {
    public Borrower_Adaptor(@NonNull Context context, ArrayList<Borrower_Sub_Class_Adaptor> courseModelArrayList) {
        super(context, 0, courseModelArrayList);
    }
    Connection connection;
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listitemView = convertView;
        if (listitemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.grade_view_list_borrower, parent, false);
        }
        Borrower_Sub_Class_Adaptor courseModel = getItem(position);
        TextView id = listitemView.findViewById(R.id.borrower_gradeview_id);
        TextView name = listitemView.findViewById(R.id.borrower_gradeview_name);
        TextView conatct = listitemView.findViewById(R.id.borrower_gradeview_contact);
        TextView email = listitemView.findViewById(R.id.borrower_gradeview_email);
        androidx.cardview.widget.CardView cardview = listitemView.findViewById(R.id.borrower_grade_view_list);;
        String strid = courseModel.getCourseID();
        String strfname = courseModel.getCourseFname();
        String strmname = courseModel.getCourseMname();
        String strlname = courseModel.getCourselname();
        String strcontact = courseModel.getCoursecontact();
        String stremail = courseModel.getCourseemail();
        Context context1 = courseModel.getContext1();
        Context context2 = courseModel.getContext2();
        name.setText(strfname+" "+strlname);
        conatct.setText(strcontact);
        email.setText(stremail);

        cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context1, Borrower_View.class);
                intent.putExtra("id",strid);
                context1.startActivity(intent);
            }
        });
        return listitemView;
    }

}
