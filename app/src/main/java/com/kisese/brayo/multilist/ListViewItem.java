package com.kisese.brayo.multilist;

/**
 * Created by Brayo on 4/26/2015.
 */
public class ListViewItem {
    private String text;
    private int type;
    private Boolean answered;

    public ListViewItem(String text, int type, Boolean answered) {
        this.text = text;
        this.type = type;
        this.answered = answered;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setAnswered(Boolean answered){
        this.answered = answered;
    }

    public Boolean getAnswered(){
        return answered;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
