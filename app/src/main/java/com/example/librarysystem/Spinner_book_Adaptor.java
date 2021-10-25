package com.example.librarysystem;

import android.content.Context;

public class Spinner_book_Adaptor {
    private String id;
    private String title;

    public Spinner_book_Adaptor(String id, String title) {
        this.id = id;
        this.title = title;
    }

    public String getCourseID() {
        return id;
    }


    public String getCourseTITLE() {
        return title;
    }

    public void setCourse_id(String course_id) {
        this.id = course_id;
    }

    public void setTitle(String Title) {
        this.title = Title;
    }

    @Override
    public String toString() {
        return title;
    }
}
