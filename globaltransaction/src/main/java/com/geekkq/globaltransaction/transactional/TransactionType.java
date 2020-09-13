package com.geekkq.globaltransaction.transactional;

public enum TransactionType {
     COMMIT("commit"),
     ROLLBACK("rollback");

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    String type;
    private TransactionType(String type){
        this.type=type;
    }
}
