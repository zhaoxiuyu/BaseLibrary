package com.base.app.module.test;

public class Order {

    private String start;
    private String end;
    private int day;
    private String roomId;

    public Order(String start, String end, int day, String roomId) {
        this.start = start;
        this.end = end;
        this.day = day;
        this.roomId = roomId;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    @Override
    public String toString() {
        return "Order{" +
                "start='" + start + '\'' +
                ", end='" + end + '\'' +
                ", day=" + day +
                ", roomId='" + roomId + '\'' +
                '}';
    }
}
