package com.lyj.direction.jeju_setplan;

public class jeju_setplan_Dictionary {
    private String id;
    private String English;

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


    public jeju_setplan_Dictionary(String id, String english) {
        this.id = id;
        English = english;
    }
}


