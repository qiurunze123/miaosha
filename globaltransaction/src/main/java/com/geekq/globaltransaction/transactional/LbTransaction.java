package com.geekq.globaltransaction.transactional;

public class LbTransaction {

    private String groupId;
    private String transactionId;

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    private Task task;
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
        this.task=new Task();
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
