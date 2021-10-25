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

public class BorrowedBook_Adaptor extends ArrayAdapter<BorrowedBook_Sub_Class_Adaptor> {
    public BorrowedBook_Adaptor(@NonNull Context context, ArrayList<BorrowedBook_Sub_Class_Adaptor> courseModelArrayList) {
        super(context, 0, courseModelArrayList);
    }
    Connection connection;
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listitemView = convertView;
        if (listitemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.grade_view_list_borrowedbook, parent, false);
        }
        BorrowedBook_Sub_Class_Adaptor courseModel = getItem(position);
        TextView id = listitemView.findViewById(R.id.borrowedbook_gradeview_id);
        TextView name = listitemView.findViewById(R.id.borrowedbook_gradeview_name);
        TextView duedate = listitemView.findViewById(R.id.borrowedbook_gradeview_duedate);
        TextView numcopies = listitemView.findViewById(R.id.borrowedbook_gradeview_quantity);
        TextView title = listitemView.findViewById(R.id.borrowedbook_gradeview_title);
        androidx.cardview.widget.CardView cardview = listitemView.findViewById(R.id.borrowedbook_grade_view_list);;
        String strid = courseModel.getCourseID();
        String strtitle = courseModel.getCourseTitle();
        String strauthor = courseModel.getCourseAuthor();
        String strbookid = courseModel.getCourseBook_id();
        String strduedate = courseModel.getCourseDuedate();
        String strquntity = courseModel.getCourseNumcopies();
        String strlname = courseModel.getCourseLname();
        String strmname = courseModel.getCourseMname();
        String strfname = courseModel.getCourseFname();
        Context context1 = courseModel.getContext1();
        Context context2 = courseModel.getContext2();
        title.setText(strtitle);
        name.setText(strfname+" "+strlname);
        duedate.setText(strduedate);
        numcopies.setText(strquntity);

        cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context1, Borrowedbook_view.class);
                intent.putExtra("id",strid);
                intent.putExtra("bookid",strbookid);
                context1.startActivity(intent);
            }
        });
        return listitemView;
    }
}
