package com.qql.dagger.recommend.utils;


import org.geometerplus.fbreader.book.Book;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by T-46 on 2017/8/2.
 */

public class CommonUtils {

    public static void move(List<Book> myBooks, int fromPosition, int toPosition){
        if (fromPosition == toPosition || myBooks == null || myBooks.size() < 1) {
            return;
        }

        final Book item = myBooks.remove(fromPosition);

        myBooks.add(toPosition, item);
    }

    /**
     * byte(字节)根据长度转成kb(千字节)和mb(兆字节)
     *
     * @param bytes
     * @return
     */
    public static String bytes2kb(long bytes) {
        BigDecimal filesize = new BigDecimal(bytes);
        BigDecimal megabyte = new BigDecimal(1024 * 1024);
        float returnValue = filesize.divide(megabyte, 2, BigDecimal.ROUND_UP)
                .floatValue();
        if (returnValue > 1)
            return (returnValue + "MB");
        BigDecimal kilobyte = new BigDecimal(1024);
        returnValue = filesize.divide(kilobyte, 2, BigDecimal.ROUND_UP)
                .floatValue();
        return (returnValue + "KB");
    }

    public static boolean emptyList(List list){
        return list == null || list.size() == 0;
    }

}
