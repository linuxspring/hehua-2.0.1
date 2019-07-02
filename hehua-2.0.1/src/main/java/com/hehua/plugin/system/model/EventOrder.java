package com.hehua.plugin.system.model;

/**
 * Created by Administrator on 5/11/2017.
 * IntelliJ IDEA 2017 of gzcss
 */
public class EventOrder {
    public String isAccept;
    public String isAssignment;
    public String isDeal;

    public String getCss_status() {
        return css_status;
    }

    public void setCss_status(String css_status) {
        this.css_status = css_status;
    }

    public String css_status;

    public String getIsAccept() {
        return isAccept;
    }

    public void setIsAccept(String isAccept) {
        this.isAccept = isAccept;
    }

    public String getIsAssignment() {
        return isAssignment;
    }

    public void setIsAssignment(String isAssignment) {
        this.isAssignment = isAssignment;
    }

    public String getIsDeal() {
        return isDeal;
    }

    public void setIsDeal(String isDeal) {
        this.isDeal = isDeal;
    }
}
