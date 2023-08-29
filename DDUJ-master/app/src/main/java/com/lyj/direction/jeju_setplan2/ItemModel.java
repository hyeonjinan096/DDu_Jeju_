package com.lyj.direction.jeju_setplan2;

public class ItemModel {
    private int Type;  //0:숙소 1:관광지 2:맛집
    private String Name; //장소 이름
    private String Adress; //주소
    private int Hour; //체류 시간
    private String Number; //장소 번호
    private String X; //경도
    private String Y;//위도

    public ItemModel(int type, String name,String adress, int hour,  String number ,  String x,  String y) {
        Type = type;
        Name = name;
        Adress = adress;
        Hour = hour;
        Number = number;
        X =x;
        Y =y;
    }


    public int getType(){
        return Type;
    }

    public String getName() { return Name; }

    public String getAdress() {
        return Adress;
    }

    public int getHour() { return Hour; }

    public String getNumber() { return Number; }
    public String getY() { return Y; }
    public String getX() { return X; }

}