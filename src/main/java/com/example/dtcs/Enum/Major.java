package com.example.dtcs.Enum;

/**
 * Created by q9163 on 08/02/2018.
 */

public enum  Major {
    D101("1011","计算机科学与技术"),D1011("101","计算机"),D102("1022","信息管理与信息系统"), D1022("102","信管"),D103("103","电子商务")
    ,D201("201","勘查技术与贸易"),D202("202","资源勘查工程"),D203("203","资源勘查工程")
    ,D301("301","国际经济与贸易"),D302("302","金融学")
    ,D401("401","会计学"),D402("402","环境工程"),D403("403","市场营销")
    ,D501("501","法学"),D502("502","英语"),D503("503","广告学"),
    D601("601","工商管理"),D602("602","人力资源管理"),D604("604","行政管理"),D605("605","劳动与社会保障"),D606("606","工程管理");


    //签到广播
    public static final String SIGNACTION="com.example.g150825_android28.RING";
    public static final int DAY_SECOND = 86400000;
    private String name;
    private String depart;
    //public static final String DTCSUrl = "http://c7sunh.natappfree.cc/dtcs/";
    //public static final String DTCSUrl = "http://192.168.1.2:81/DTCS/";
    public static final String DTCSUrl = "http://www.iec.d1.natapp.cc/DTCS/";
    Major(String name, String depart) {
        this.name = name;
        this.depart = depart;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepart() {
        return depart;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }


}
