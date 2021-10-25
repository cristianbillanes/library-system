package com.example.librarysystem;

import android.content.Context;

public class Book_Sub_Class_Adaptor {
    private String id;
    private String title;
    private String author;
    private String isbn;
    private String purchdate;
    private String publishyear;
    private Context context1;
    private Context context2;

    public Book_Sub_Class_Adaptor(String id, String title,
                                   String author, String purchdate,
                                   String publishyear, String isbn,
                                   Context context1, Context context2) {
        this.context1 = context1;
        this.context2 = context2;
        this.isbn = isbn;
        this.id = id;
        this.title = title;
        this.author = author;
        this.purchdate = purchdate;
        this.publishyear = publishyear;
    }

    public String getCourseID() {
        return id;
    }

    public Context getContext1() {
        return context1;
    }

    public Context getContext2() {
        return context2;
    }

    public String getCourseTitle() {
        return title;
    }

    public String getCourseISBN() {
        return isbn;
    }

    public String getCourseAuthor() {
        return author;
    }

    public String getCoursePurchdate() {
        return purchdate;
    }

    public String getCoursePublishyear() {
        return publishyear;
    }

    public void setCourse_id(String course_id) {
        this.id = course_id;
    }

    public void setContext1(Context context1) {
        this.context1 = context1;
    }

    public void setContext2(Context context2) {
        this.context2 = context2;
    }

    public void setCourse_Title(String course_title) {
        this.title = course_title;
    }

    public void setCourse_Auhtor(String course_author) {
        this.author = course_author;
    }

    public void setCourse_Publishyear(String course_publishyear) {
        this.purchdate = course_publishyear;
    }

    public void setCourse_Purchdate(String course_purchdate) {
        this.purchdate = course_purchdate;
    }
}
