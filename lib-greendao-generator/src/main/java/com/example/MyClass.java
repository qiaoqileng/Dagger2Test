package com.example;

import org.greenrobot.greendao.generator.DaoGenerator;
import org.greenrobot.greendao.generator.Entity;
import org.greenrobot.greendao.generator.Schema;

public class MyClass {
    public static void main(String[] args) throws Exception {
        //参数1是版本号
        //参数2是生成文件的包
        Schema schema = new Schema(1, "com.green.dao.output");
        addNewsDetail(schema);
        try {
            //后面的目录是在gradle中配置的路径
            new DaoGenerator().generateAll(schema, "app/src/main/java");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建表
     * 生成dao和bean
     *
     * @param schema
     */
    private static void addNewsDetail(Schema schema) {
        Entity newsDetail = schema.addEntity("Book");
        newsDetail.setHasKeepSections(true);
        newsDetail.addIdProperty().primaryKey().index();
        newsDetail.addStringProperty("user_id");//匹配用户
        newsDetail.addStringProperty("title");
        newsDetail.addStringProperty("cover_url");//封面
        newsDetail.addStringProperty("content_url");//正文url
        newsDetail.addStringProperty("author");
        newsDetail.addDoubleProperty("read_rate");
        newsDetail.addStringProperty("formats");//格式
        newsDetail.addBooleanProperty("probation");//是否为试读
        newsDetail.addStringProperty("bookmarks");
        newsDetail.addStringProperty("notes");
        newsDetail.addStringProperty("remarks");//备注
        newsDetail.addStringProperty("folder");//文件夹
        newsDetail.addLongProperty("last_read_time");
    }
}
