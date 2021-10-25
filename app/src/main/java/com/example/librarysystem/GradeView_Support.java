package com.example.librarysystem;

import android.content.Context;

public class GradeView_Support {
    private String course_value1;
    private String course_value2;
    private String course_value3;
    private String course_id;
    private Context context1;
    private Context context2;

    public GradeView_Support(Context context1,Context context2,
                             String course_value1,String course_value2,
                             String course_value3,String course_id ) {
        this.context1 = context1;
        this.context2 = context2;
        this.course_id = course_id;
        this.course_value1 = course_value1;
        this.course_value2 = course_value2;
        this.course_value3 = course_value3;
    }
    public String getCourse_id(){return course_id;}
    public Context getContext1(){ return context1;}
    public Context getContext2(){ return context2;}
    public String getTextview1() {
        return course_value1;
    }
    public String getTextview2() {
        return course_value2;
    }
    public String getTextview3() {
        return course_value3;
    }

    public void setCourse_id(String course_id){this.course_id = course_id;}
    public void setContext1(Context context1){this.context1 = context1;}
    public void setContext2(Context context2){this.context2 = context2;}
    public void setTextview1(String course_value1) {
        this.course_value1 = course_value1;
    }
    public void setTextview2(String course_value1) {
        this.course_value2 = course_value2;
    }
    public void setTextview3(String course_value1) {
        this.course_value3 = course_value3;
    }

}
