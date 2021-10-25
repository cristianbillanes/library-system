package com.example.librarysystem;

import android.app.ProgressDialog;
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

public class Book_Adaptor extends ArrayAdapter<Book_Sub_Class_Adaptor> {
    public Book_Adaptor(@NonNull Context context, ArrayList<Book_Sub_Class_Adaptor> courseModelArrayList) {
        super(context, 0, courseModelArrayList);
    }
    Connection connection;
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listitemView = convertView;
        if (listitemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.grade_view_list_book, parent, false);
        }
        Book_Sub_Class_Adaptor courseModel = getItem(position);
        TextView id = listitemView.findViewById(R.id.book_gradeview_id);
        TextView title = listitemView.findViewById(R.id.book_gradeview_title);
        TextView author = listitemView.findViewById(R.id.book_gradeview_author);
        TextView isbn = listitemView.findViewById(R.id.book_gradeview_isbn);
        TextView purchdate = listitemView.findViewById(R.id.book_gradeview_purchdate);
        TextView publishyear = listitemView.findViewById(R.id.book_gradeview_publishyear);
        androidx.cardview.widget.CardView cardview = listitemView.findViewById(R.id.book_grade_view_list);;
        String strid = courseModel.getCourseID();
        String strtitle = courseModel.getCourseTitle();
        String strauthor = courseModel.getCourseAuthor();
        String strisbn = courseModel.getCourseISBN();
        String strpurchdate = courseModel.getCoursePurchdate();
        String strpublishyear = courseModel.getCoursePublishyear();
        Context context1 = courseModel.getContext1();
        Context context2 = courseModel.getContext2();
        title.setText(strtitle);
        author.setText(strauthor);
        isbn.setText(strisbn);
        purchdate.setText(strpurchdate);
        publishyear.setText(strpublishyear);

        cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context1, Book_View.class);
                intent.putExtra("id",strid);
                context1.startActivity(intent);
            }
        });
        return listitemView;
    }
}
