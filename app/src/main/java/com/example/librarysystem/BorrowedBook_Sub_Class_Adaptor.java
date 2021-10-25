package com.example.librarysystem;

import android.content.Context;

public class BorrowedBook_Sub_Class_Adaptor {
    private String id;
    private String book_id;
    private String borrowed_id;
    private String fname;
    private String mname;
    private String lname;
    private String title;
    private String author;
    private String numcopies;
    private String bstatus;
    private String duedate;
    private String dateborrowed;
    private Context context1;
    private Context context2;

    public BorrowedBook_Sub_Class_Adaptor(String id, String book_id,
                                         String borrowed_id, String numcopies,
                                         String bstatus, String duedate,
                                         String dateborrowed,String fname,
                                         String mname, String lname,
                                         String title, String author,
                                         Context context1, Context context2) {
        this.context1 = context1;
        this.context2 = context2;
        this.id = id;
        this.borrowed_id = borrowed_id;
        this.book_id = book_id;
        this.numcopies = numcopies;
        this.bstatus = bstatus;
        this.duedate = duedate;
        this.dateborrowed = dateborrowed;
        this.fname = fname;
        this.mname = mname;
        this.lname = lname;
        this.title = title;
        this.author = author;
    }

    public String getCourseID() {
        return id;
    }
    public String getCourseTitle() {
        return title;
    }
    public String getCourseAuthor() {
        return author;
    }
    public String getCourseFname() {
        return fname;
    }
    public String getCourseMname() {
        return mname;
    }
    public String getCourseLname() {
        return lname;
    }

    public Context getContext1() {
        return context1;
    }

    public Context getContext2() {
        return context2;
    }

    public String getCourseBorrowed_id() {
        return borrowed_id;
    }

    public String getCourseBook_id() {
        return book_id;
    }

    public String getCourseNumcopies() {
        return numcopies;
    }

    public String getCourseBstatus() {
        return bstatus;
    }
    public String getCourseDuedate() {
        return duedate;
    }
    public String getCourseDateBarrowed() {
        return dateborrowed;
    }

    public void setCourse_id(String course_id) {
        this.id = course_id;
    }

    public void setCourse_title(String course_title) {
        this.title = course_title;
    }

    public void setCourse_author(String course_author) {
        this.author = course_author;
    }

    public void setCourse_lname(String course_lname) {
        this.lname = course_lname;
    }
    public void setCourse_mname(String course_mname) {
        this.mname = course_mname;
    }

    public void setCourse_fname(String course_fname) {
        this.fname = course_fname;
    }


    public void setContext1(Context context1) {
        this.context1 = context1;
    }

    public void setContext2(Context context2) {
        this.context2 = context2;
    }

    public void setCourse_Book_ID(String course_bookid) {
        this.book_id = course_bookid;
    }

    public void setCourse_Borrowerd_ID(String course_borrowerdid) {
        this.borrowed_id = course_borrowerdid;
    }

    public void setCourse_Numcopies(String course_numcopies) {
        this.numcopies = course_numcopies;
    }

    public void setCourse_Bstatus(String course_bstatus) {
        this.bstatus = course_bstatus;
    }

    public void setCourse_Duedate(String course_duedate) {
        this.duedate = course_duedate;
    }

    public void setCourse_Dateborrowed(String course_dateborrowed) {
        this.dateborrowed = course_dateborrowed;
    }
}
