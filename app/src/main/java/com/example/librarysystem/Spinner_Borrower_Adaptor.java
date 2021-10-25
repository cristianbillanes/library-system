package com.example.librarysystem;

public class Spinner_Borrower_Adaptor {
    private String id;
    private String fname;
    private String mname;
    private String lname;

    public Spinner_Borrower_Adaptor(String id, String fname, String mname, String lname) {
        this.id = id;
        this.fname = fname;
        this.mname = mname;
        this.lname = lname;
    }


    public String getCourseID() {
        return id;
    }
    public String getCourseFNAME() {
        return fname;
    }
    public String getCourseMNAME() {
        return mname;
    }
    public String getCourseLNAME() {
        return lname;
    }

    public void setCourse_id(String course_id) {
        this.id = course_id;
    }
    public void setCourse_Fname(String fname) {
        this.fname = fname;
    }
    public void setCourse_Mname(String mname) {
        this.mname = mname;
    }
    public void setCourse_Lname(String lname) {
        this.lname = lname;
    }

    @Override
    public String toString() {
        return fname+" "+lname;
    }
}
