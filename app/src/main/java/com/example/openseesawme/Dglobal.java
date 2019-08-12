package com.example.openseesawme;

//로그인된 아이디와 도어락의 아이디를 저장할 글로벌 변수를 담을 클래스
public class Dglobal {
    static String loginID;  //현재 로그인 되어있는 아이디
    static String doorID;   //도어락의 아이디

    public static String getDoorID() {
        return doorID;
    }

    public static void setDoorID(String doorID) {
        Dglobal.doorID = doorID;
    }

    public static String getLoginID() {
        return loginID;
    }

    public static void setLoginID(String loginID) {
        Dglobal.loginID = loginID;
    }
}
