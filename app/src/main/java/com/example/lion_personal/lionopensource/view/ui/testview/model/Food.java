package com.example.lion_personal.lionopensource.view.ui.testview.model;

/**
 * 食物
 */
public class Food {

    // 类型实例
    public static final int TYPE_CHINESE_FOOD = 0;
    public static final int TYPE_FAST_FOOD = 1;
    public static final int TYPE_DESSERT_FOOD = 2;

    // 类型
    private int type;

    // 名称
    private String name;

    // 图片id
    private int icResId;

    // 价格
    private float price;

    // 简介
    private String description;

    // 评分
    private float rating;

    // 是否免辣
    private boolean isSpicy;

    public Food(int type, String name, int icResId, float price, String description, float rating, boolean isSpicy) {
        this.type = type;
        this.name = name;
        this.icResId = icResId;
        this.price = price;
        this.description = description;
        this.rating = rating;
        this.isSpicy = isSpicy;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIcResId() {
        return icResId;
    }

    public void setIcResId(int icResId) {
        this.icResId = icResId;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public boolean isSpicy() {
        return isSpicy;
    }

    public void setSpicy(boolean spicy) {
        isSpicy = spicy;
    }
}
