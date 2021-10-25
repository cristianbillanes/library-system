package com.example.librarysystem;

import android.content.Context;

public class Borrower_Sub_Class_Adaptor {
    private String id;
    private String fname;
    private String mname;
    private String lname;
    private String contact;
    private String email;
    private Context context1;
    private Context context2;

    public Borrower_Sub_Class_Adaptor(String id, String fname,
                                      String mname, String lname,
                                      String contact, String email,
                                      Context context1, Context context2) {
        this.context1 = context1;
        this.context2 = context2;
        this.id = id;
        this.fname = fname;
        this.mname = mname;
        this.lname = lname;
        this.contact = contact;
        this.email = email;
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

    public String getCourseFname() {
        return fname;
    }

    public String getCourseMname() {
        return mname;
    }

    public String getCourselname() {
        return lname;
    }

    public String getCoursecontact() {
        return contact;
    }

    public String getCourseemail() {
        return email;
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

    public void setCourse_Fname(String course_fname) {
        this.fname = course_fname;
    }

    public void setCourse_Mname(String course_mname) {
        this.mname = course_mname;
    }

    public void setCourse_Lname(String course_lname) {
        this.lname = course_lname;
    }

    public void setCourse_Conatct(String course_contact) {
        this.contact = course_contact;
    }
    public void setCourse_Email(String course_email) {
        this.contact = course_email;
    }

}
