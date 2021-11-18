package com.example.appmouduleaccount.api.login.type;


public enum AccountType {
    // 客户
    CLIENT("CLIENT"),
    // 员工
    STAFF("STAFF");

    private final String value;

    AccountType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    //    /**
//     * 设置 账号类型
//     */
//    void setAccountType(@NonNull LoginRequest loginRequest);
}
