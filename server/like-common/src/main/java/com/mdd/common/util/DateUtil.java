package com.mdd.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author zhangyujun
 * @date 2023/12/7 8:41
 */
public class DateUtil {

    public static Date timeStampToDate(String timeStamp){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Long dateLong = Long.parseLong(timeStamp);
        String formattedDate = sdf.format(dateLong);
        Date newDate;
        try {
            newDate = sdf.parse(formattedDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return newDate;

    }
}
