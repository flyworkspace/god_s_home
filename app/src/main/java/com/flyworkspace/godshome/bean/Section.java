package com.flyworkspace.godshome.bean;

/**
 * Created by jinpengfei on 2015/03/23.
 */
public class Section {
    private String path;
    private String SectionName;

    public Section() {
    }

    public Section(String path, String sectionName) {
        this.path = path;
        SectionName = sectionName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getSectionName() {
        return SectionName;
    }

    public void setSectionName(String sectionName) {
        SectionName = sectionName;
    }
}
