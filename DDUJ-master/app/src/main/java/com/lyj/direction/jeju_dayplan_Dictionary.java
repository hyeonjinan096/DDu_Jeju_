package com.lyj.direction;

public class jeju_dayplan_Dictionary {
    private String id;
    private String English;//숙소
    private String Address;
    private String Date; //여행일자
    private String X,Y;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEnglish() {
        return English;
    }

    public void setEnglish(String english) {
        English = english;
    }

    public String getAddress() {return Address;};
    ///추가코드 숙소, 주소 넘겨주기 위해

    //위도 경도
    public String getX() {return X;};
    public String getY() {return Y;};

    public String getDate() {return Date;};
    //여행일자 넘겨주기

    public jeju_dayplan_Dictionary(String id, String english, String address, String date,String x,String y) {
        this.id = id;
        English = english;
        Address = address;
        Date = date;
        X = x;
        Y = y;
    }
}
