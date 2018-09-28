package com.example.a7invensun.rewardapp.util;

import com.google.gson.Gson;

import java.lang.reflect.Type;


public class GsonUtil {

    public static Object jsontoBean(String result, Type type){
        return new Gson().fromJson(result,type);
    }
}
