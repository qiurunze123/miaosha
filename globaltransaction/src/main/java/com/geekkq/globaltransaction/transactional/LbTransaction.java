package com.geekkq.globaltransaction.transactional;

public class LbTransaction {

    private String groupId;
    private String transactionId;

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    private String transactionType;
    public LbTransaction(String groupId, String transactionId) {
        this.groupId=groupId;
        this.transactionId=transactionId;
    }

    public Object getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public Object getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
}
