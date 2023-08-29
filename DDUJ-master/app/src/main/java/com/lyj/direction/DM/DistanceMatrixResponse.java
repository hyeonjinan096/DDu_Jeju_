package com.lyj.direction.DM;

import com.google.gson.annotations.SerializedName;
import com.google.maps.model.DistanceMatrixRow;

import java.util.List;

public class DistanceMatrixResponse {
    @SerializedName("status")
    private String status;

    @SerializedName("rows")
    private List<Row> rows;

    // 필요한 getter 및 setter 메서드

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Row> getRows() {
        return rows;
    }

    public void setRows(List<Row> rows) {
        this.rows = rows;
    }
    public class Row {
        @SerializedName("elements")
        private List<Element> elements;

        public List<Element> getElements() {
            return elements;
        }
    }

    public class Element {
        @SerializedName("distance")
        private Distance distance;
        @SerializedName("duration")
        private Duration duration;

        public Distance getDistance() {
            return distance;
        }

        public Duration getDuration() {
            return duration;
        }
    }
    public class Distance {
        @SerializedName("text")
        private String text;
        @SerializedName("value")
        private int value;

        public String getText() {
            return text;
        }

        public int getValue() {
            return value;
        }
    }

    public class Duration {
        @SerializedName("text")
        private String text;
        @SerializedName("value")
        private int value;

        public String getText() {
            return text;
        }

        public int getValue() {
            return value;
        }
    }
}