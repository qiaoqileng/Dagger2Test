package com.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by qql on 2018/1/23.
 */

public class Test {
    public static String[]  COMMON = new String[]{"网格分级","网格类型","日志种类","所属行业","关注程度",
            "学校性质","其他场所类型","消防安全重点类别","治安重点类别","社会组织类型","社会组织类型分类"};

    public static String[] POPULATION=new String[]{"与户主关系","民族", "职业","现居住址类型","外出原因", "产权类型",
            "证件类型","政治面貌","出租房类别", "出租房性质","出租用途", "隐患程度","救助原因","组织大类","走访情况","房屋类型"
            ,"性别","宗教信仰","血型","婚姻状况","文化程度"};

    public static String[] TASK_DIC = new String[]{"性别(男、女)", "性别"};

    public static String[] HOUSE_HOLD_DIC = new String[]{"房屋用途","建筑物用途", "人户状态","流动人口_暂住处所",
            "流动人口_经济来源","流动人口_已居住时限","流入原因","登记证情况","境外人员职业"};
    public static String[] IMPORTANT_DIC = new String[]{"刑法类别","闲散青少年人员类型", "吸毒原因","毒品来源","戒毒情况",
            "吸毒状态", "刑释解教类型","刑满释放类型","管控等级","危险程度","结构层级","违法情况","犯罪类型","管理手段", "危险从业类别"
            ,"上访类型","正常上访","异常上访","上访状态","经济来源","精神病类型","重点青少年年龄区间"};
    public static String[] NEEDHELP_DIC = new String[]{"困难类型","救助原因"};
    public static String[] PLACE_DIC = new String[]{"刑法类别","危险程度","新经济组织类别","重点青少年年龄区间","安全生产企业类型",
            "走访情况","企业类型","房屋类型","学校类型"};
    public  static String[] HOUSE_INFO_DIC = new String[]{"建筑物用途","房屋来源","出租房结构","房屋用途","房屋凭证","土地凭证","产权类型",
            "自有产权","租赁公房", "出租房类别","出租人类型","管理类别","出租房性质","出租用途","隐患程度","文化程度"};
    public static String[] WORKDAILY_DIC = new String[]{"台账类型","台帐关键字","部门类型","工作日志类型"};
    public static String[] INSPECT_DIC = new String[]{"安全生产企业类型","隐患等级","出租房结构-大唐巡检","出租房类型","企业类型","学校类型"};
    public static String[] UNAUNOUNCE_DIC = new String[]{"企业类型","学校类型"};

    public static void main(String[] args) throws Exception {
//        preData(INSPECT_DIC);
//        System.out.println();
        preData(HOUSE_INFO_DIC);
//        System.out.println();
//        preData(WORKDAILY_DIC);
//        System.out.println();
//        preData(UNAUNOUNCE_DIC);
//        System.out.println();
    }
    public static boolean prepareModulePropertyMap(String[] params) {
        int subsectionCount = 6;
        int subsectionIndex = params.length / subsectionCount;
        if(subsectionIndex == 0) {
            subsectionIndex = 1;
        }

        for(int subsection = 0; subsection < subsectionCount; ++subsection) {
            String[] propertyNames;
            if(subsection == subsectionCount - 1) {
                propertyNames = new String[subsectionIndex + params.length % subsectionCount];
            } else {
                propertyNames = new String[subsectionIndex];
            }

            for(int i = 0; i < propertyNames.length; ++i) {
                try {
                    propertyNames[i] = params[i];
                } catch (Exception var9) {
                    return false;
                }
            }
            System.out.println(Arrays.toString(propertyNames));
        }

        return true;
    }

    private static void preData(String[] datas){
        if (datas == null || datas.length == 0){
            return;
        }
        int singleMaxLength = 6;
        int length = datas.length;
        List<String> singleOut = new ArrayList<>();
        if (length <= singleMaxLength) {
            for (int i=0;i<length;i++){
                singleOut.add(datas[i]);
            }
            System.out.println(singleOut.toArray(new String[0]));
        } else {
            int count = length/singleMaxLength;
            for (int i=0;i<count;i++){
                singleOut = new ArrayList<>();
                int currMax = (i+1)*singleMaxLength;
                for (int j=i*singleMaxLength;j<(currMax>length?length:currMax);j++){
                    singleOut.add(datas[j]);
                }
                System.out.println(singleOut.toArray(new String[0]));
            }
        }
    }
}
