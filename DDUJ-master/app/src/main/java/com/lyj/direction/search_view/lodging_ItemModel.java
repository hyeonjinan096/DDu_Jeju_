package com.lyj.direction.search_view;

public class lodging_ItemModel {

    private String text1;
    private String text2;
    private String number;
    private String y;
    private String x;

    public lodging_ItemModel(String text1, String text2,String number,String x,String y) {
        this.text1 = text1;
        this.text2 = text2;
        this.number = number;
        this.x =x;
        this.y =y;
    }


    public String getText1() {
        return text1;
    }

    public void setText1(String text1) {
        this.text1 = text1;
    }

    public String getText2() { return text2; }

    public void setText2(String text2) {
        this.text2 = text2;
    }

    public String getNumber() {
        return number;
    }


    public void setX(String x) {
        this.text2 = x;
    }

    public String getX() {
        return x;
    }

    public void setY(String y) {
        this.text2 = y;
    }

    public String getY() {
        return y;
    }


}