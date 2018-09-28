package com.example.a7invensun.rewardapp.util;

import com.example.a7invensun.rewardapp.MyApplication;
import com.example.a7invensun.rewardapp.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 7invensun on 2018/9/28.
 */

public class CompanyCodeUtil {


    public static String getDeliveryCompanyNo(String deliveryCompanyName) throws RuntimeException {
        Map<String, String> mDeliveryCompanyTable = new HashMap<>();
        if (mDeliveryCompanyTable.isEmpty()) {
            String[] names = MyApplication.getInstance().getResources().getStringArray(R.array.delivery_company);
            String[] ids  = MyApplication.getInstance().getResources().getStringArray(R.array.delivery_company_id);
            if (names.length != ids.length) {
                throw new RuntimeException();
            }

            for (int i = 0; i < names.length; i++) {
                mDeliveryCompanyTable.put(names[i], ids[i]);
            }
        }

        return mDeliveryCompanyTable.get(deliveryCompanyName);
    }
}
