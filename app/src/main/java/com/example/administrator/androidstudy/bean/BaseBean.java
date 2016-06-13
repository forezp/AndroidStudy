package com.example.administrator.androidstudy.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/6/13 0013.
 */
public class BaseBean implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 2540159474464825615L;

    public static final String ID = "id";

    public int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "BaseBean [id=" + id + "]";
    }


}

