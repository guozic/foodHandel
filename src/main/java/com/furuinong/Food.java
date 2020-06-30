package com.furuinong;

import com.alibaba.excel.annotation.ExcelProperty;

public class Food {

    /**
     * 日期
     */
    @ExcelProperty(value = "日期", index = 0)
    private String date;
    /**
     * 班级
     */
    @ExcelProperty(value = "班级", index = 1)
    private String level;

    /**
     * 人数
     */
    @ExcelProperty(value = "人数", index = 2)
    private String numberOfPeople;

    /**
     * 菜名
     */
    @ExcelProperty(value = "菜名", index = 3)
    private String name;

    /**
     * 预购数
     */
    @ExcelProperty(value = "预购数", index = 4)
    private String num;

    /**
     * 单位
     */
    @ExcelProperty(value = "单位", index = 5)
    private String unit;

    /**
     * 实购数
     */
    @ExcelProperty(value = "实购数", index = 6)
    private String purchased;


    /**
     * 单价
     */
    @ExcelProperty(value = "单价", index = 7)
    private String price;

    /**
     * 小计
     */
    @ExcelProperty(value = "小计", index = 8)
    private String subtotal;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注", index = 9)
    private String remarks;

    public void setDate(String date) {
        this.date = date;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void setNumberOfPeople(String numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setPurchased(String purchased) {
        this.purchased = purchased;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getDate() {
        return date;
    }

    public String getLevel() {
        return level;
    }

    public String getNumberOfPeople() {
        return numberOfPeople;
    }

    public String getName() {
        return name;
    }

    public String getNum() {
        return num;
    }

    public String getUnit() {
        return unit;
    }

    public String getPurchased() {
        return purchased;
    }

    public String getPrice() {
        return price;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public String getRemarks() {
        return remarks;
    }
}
