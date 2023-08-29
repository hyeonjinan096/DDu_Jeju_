package com.lyj.direction.search_view;

public class tour_ItemModel {

    private int imageResource;
    private String text1;
    private String text2;
    private String number;
    private String y;
    private String x;

    public tour_ItemModel(int imageResource, String text1, String text2, String number,String x,String y) {
        this.imageResource = imageResource; //이미지
        this.text1 = text1;  //이름
        this.text2 = text2;  //주소
        this.number = number;  //장소 번호
        this.x =x;
        this.y =y;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    public String getText1() {
        return text1;
    }

    public void setText1(String text1) {
        this.text1 = text1;
    }

    public String getText2() {
        return text2;
    }

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