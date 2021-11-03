package com.mzy.hdum.util;

import java.util.List;

public class ClsVo {

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<ClsVo> getChildren() {
        return children;
    }

    public void setChildren(List<ClsVo> children) {
        this.children = children;
    }

    private String label;

    private List<ClsVo> children;

}
