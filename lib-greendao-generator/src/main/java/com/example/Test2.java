package com.example;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by qql on 2018/2/1.
 */

public class Test2 {

    public static void main(String[] args) throws Exception {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(Calendar.DAY_OF_WEEK, 1);
        System.out.println (new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()));
        cal.roll(Calendar.DAY_OF_WEEK, -1);
        System.out.println (new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()));
    }
}
