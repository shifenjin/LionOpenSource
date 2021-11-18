package com.example.lion_personal.lionopensource.view.ui.testview;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.lion_personal.lionopensource.R;
import com.example.lion_personal.lionopensource.view.ui.testview.model.Food;

import java.util.List;
import java.util.Set;

public class TestViewViewModel extends ViewModel {
    // TODO: Implement the ViewModel

    // 食物列表
    private LiveData<List<Food>> foodList;

    // 过滤条件 - 食物类型
    private LiveData<Set<Integer>> typeLimit;
    // 过滤条件 - 食物是否免辣
    private LiveData<Boolean> isSpicyLimit;
    // 过滤条件 - 食物价格最高值
    private LiveData<Float> priceMaxLimit;

    public TestViewViewModel() {
        initFood();

    }

    /**
     * 初始化食物
     */
    public void initFood() {
        this.foodList = new MutableLiveData<>();
        foodList.getValue().add(new Food(Food.TYPE_DESSERT_FOOD, "提拉米苏", R.drawable.tiramisu,
                20, "我是提拉米苏", 4.5f, false));
        foodList.getValue().add(new Food(Food.TYPE_CHINESE_FOOD, "宫保鸡丁", R.drawable.gongbaojiding,
                34, "我是宫保鸡丁", 4.0f, true));
    }

    /**
     * 通过过滤获取食物
     */
    public List<Food> getFilterFood() {
//        return Observable.fromIterable(foodList.getValue())
//                .filter(food -> typeLimit.getValue().contains(food.getType())
//                                && food.getPrice() < priceMaxLimit.getValue()
//                                && food.isSpicy() == isSpicyLimit.getValue())
//                .toList();
        initFood();
        return foodList.getValue();
    }
}
