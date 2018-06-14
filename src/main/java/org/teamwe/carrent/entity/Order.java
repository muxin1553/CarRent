package org.teamwe.carrent.entity;

public class Order {

    private int  orderid;
    private String email;
    private int   card;
    private long  timebegin;
    private long  timeende;
    private long  timeendr;
    private String comment;
    private int    status;

    public int getOrderid() {
        return orderid;
    }

    public String getEmail() {
        return email;
    }

    public int getCard() {
        return card;
    }

    public long getTimebegin() {
        return timebegin;
    }

    public long getTimeende() {
        return timeende;
    }

    public long getTimeendr() {
        return timeendr;
    }

    public String getComment() {
        return comment;
    }

    public int getStatus() {
        return status;
    }

    public void setOrderid(int orderid) {
        this.orderid = orderid;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCard(int card) {
        this.card = card;
    }

    public void setTimebegin(long timebegin) {
        this.timebegin = timebegin;
    }

    public void setTimeende(long timeende) {
        this.timeende = timeende;
    }

    public void setTimeendr(long timeendr) {
        this.timeendr = timeendr;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderid=" + orderid +
                ", email='" + email + '\'' +
                ", card=" + card +
                ", timebegin=" + timebegin +
                ", timeende=" + timeende +
                ", timeendr=" + timeendr +
                ", comment='" + comment + '\'' +
                ", status=" + status +
                '}';
    }


    public Order() {
    }

    public Order(int orderid, String email, int card, int status) {
        this.orderid = orderid;
        this.email = email;
        this.card = card;
        this.status = status;
    }
}