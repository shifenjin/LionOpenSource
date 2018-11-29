package com.example.applibcompiler;

import javax.lang.model.element.VariableElement;

// 变量元素信息
public class VariableInfo {

    VariableInfo() {

    }

    VariableInfo(int viewId, VariableElement variableElement) {
        this.viewId = viewId;
        this.variableElement = variableElement;
    }

    public int getViewId() {
        return viewId;
    }

    public void setViewId(int viewId) {
        this.viewId = viewId;
    }

    public VariableElement getVariableElement() {
        return variableElement;
    }

    public void setVariableElement(VariableElement variableElement) {
        this.variableElement = variableElement;
    }

    // 变量元素的注解的 的 id 值
    int viewId;
    // 变量元素
    VariableElement variableElement;
}
