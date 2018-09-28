package com.example.a7invensun.rewardapp.model;

import java.util.List;

/**
 * Created by 7invensun on 2018/9/28.
 */

public class DeliveryMessages {
    //派送单号
    private String nu;
    //快递公司名称
    private String com;
    //快递单当前签收状态，包括0在途中、1已揽收、2疑难、3已签收、4退签、5同城派送中、6退回、7转单等7个状态，其中4-7需要另外开通才有效
    private String state;
    //快递信息
    private List<Message> data;
    //消息类
    public static class Message {
        //时间，格式为年-月-日 时:分:秒
        private String time;
        //详细信息内容
        private String context;
    }
}
