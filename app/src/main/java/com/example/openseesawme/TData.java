package com.example.openseesawme;


public class TData {
    private String cdoorlockIndex;
    private String cdoorlockName;
    private int ccardIndex;
    private String cbgcolor;

    public TData(String cdoorlockIndex, String cdoorlockName, int ccardIndex, String cbgcolor) { //카드에 들어가야하는 데이터 //인덱스는 없어도 되나..?
        this.cdoorlockIndex = cdoorlockIndex;
        this.cdoorlockName = cdoorlockName;
        this.ccardIndex = ccardIndex;
        this.cbgcolor = cbgcolor;
    }

    public String getcDoorlockName() { return cdoorlockName; }

    public void setcDoorlockName(String cdoorlockName) { this.cdoorlockName = cdoorlockName; }

    public String getcDoorlockIndex() { return cdoorlockIndex; }

    public void setcDoorlockIndex(String cdoorlockIndex) { this.cdoorlockIndex = cdoorlockIndex; }

    public int getcCardIndex() { return ccardIndex; }

    public void setcCardIndex(int ccardIndex) { this.ccardIndex = ccardIndex; }

    public String getcBgcolor() { return cbgcolor; }

    public void setcBgcolor(String cbgcolor) { this.cbgcolor = cbgcolor; }



}
