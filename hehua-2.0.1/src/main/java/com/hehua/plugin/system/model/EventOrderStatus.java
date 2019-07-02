package com.hehua.plugin.system.model;

/**
 * Created by Administrator on 2017/11/17.
 * IntelliJ IDEA 2017 of gzcss
 */
public class EventOrderStatus {
    private String numbers;
    private String isJieShou;
    private String isFenPa;

    public String getCss_status() {
        return css_status;
    }

    public void setCss_status(String css_status) {
        this.css_status = css_status;
    }

    public String getNumbers() {
        return numbers;
    }

    public void setNumbers(String numbers) {
        this.numbers = numbers;
    }

    public String getIsJieShou() {
        return isJieShou;
    }

    public void setIsJieShou(String isJieShou) {
        this.isJieShou = isJieShou;
    }

    public String getIsFenPa() {
        return isFenPa;
    }

    public void setIsFenPa(String isFenPa) {
        this.isFenPa = isFenPa;
    }

    private String css_status;
}
