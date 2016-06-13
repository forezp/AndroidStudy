package com.example.administrator.androidstudy.bean;

import org.json.JSONObject;

/**
 * Created by Administrator on 2016/6/13 0013.
 */
public class VersionBean  extends  BaseBean {

    private static final long serialVersionUID = -9162772930500491389L;
    public String version;
    public String url;
    public String platform;
    public String time;
    public String description;
    /**
     * 1强制更新，0不是
     */
    public int type;
    /**
     * 是否是最新
     */
    public boolean isNew;

    public static VersionBean parse(JSONObject jo){
        if(jo == null)
            return null;
        VersionBean bean = new VersionBean();
        bean.id = jo.optInt("id");
        bean.version = jo.optString("version");
        bean.description = jo.optString("description");
        bean.url = jo.optString("url");
        bean.time = jo.optString("time");
        bean.platform = jo.optString("platform");
        bean.type = jo.optInt("type");
        bean.isNew = jo.optInt("is_new") > 0 ? true : false;
        return bean;
    }
}
