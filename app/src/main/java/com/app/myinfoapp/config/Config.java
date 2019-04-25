package com.app.myinfoapp.config;

public class Config {

    public static String    BASE_URL              = "http://192.168.43.196/my_info/rest",
                            LOGIN                 =  BASE_URL + "/login.php",
                            CHECK_USER_EXISTING   =  BASE_URL+  "/checkUser.php",
                            GET_JADWAL_BY_KELAS   =  BASE_URL+  "/getJadwalByKelas.php?idKelas=_PARAM_",
                            GET_JADWAL_BY_HARI    =  BASE_URL+  "/getJadwalByHari.php?hari=_PARAM1_&idKelas=_PARAM2_",
                            REGISTER              =  BASE_URL+  "/register.php";

}
