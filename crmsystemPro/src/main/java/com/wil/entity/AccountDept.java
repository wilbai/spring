package com.wil.entity;

public class AccountDept {
    private Integer accountId;

    private Integer deptId;

    public AccountDept() {}

    public AccountDept(Integer accountId, Integer deptId) {
        this.accountId = accountId;
        this.deptId = deptId;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }
}