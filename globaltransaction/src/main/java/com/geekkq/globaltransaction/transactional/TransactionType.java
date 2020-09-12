package com.geekkq.globaltransaction.transactional;

public enum TransactionType {
     COMMIT("commit"),
     ROLLBACK("rollback");
    String type;
    private TransactionType(String type){
        this.type=type;
    }
}
