//package com.geekq.miaosha.redis;
//
//import org.springframework.boot.context.properties.ConfigurationProperties;
//
//@ConfigurationProperties(prefix = "redisson")
//public class RedissonProperties {
//
//    private int timeout = 3000;
//
//    private String address;
//
//    private String password;
//
//    private int connectionPoolSize = 64;
//
//    private int connectionMinimumIdleSize=10;
//
//    private int slaveConnectionPoolSize = 250;
//
//    private int masterConnectionPoolSize = 250;
//
//    private String[] sentinelAddresses;
//
//    private String masterName;
//
//    public int getTimeout() {
//        return timeout;
//    }
//
//    public void setTimeout(int timeout) {
//        this.timeout = timeout;
//    }
//
//    public int getSlaveConnectionPoolSize() {
//        return slaveConnectionPoolSize;
//    }
//
//    public void setSlaveConnectionPoolSize(int slaveConnectionPoolSize) {
//        this.slaveConnectionPoolSize = slaveConnectionPoolSize;
//    }
//
//    public int getMasterConnectionPoolSize() {
//        return masterConnectionPoolSize;
//    }
//
//    public void setMasterConnectionPoolSize(int masterConnectionPoolSize) {
//        this.masterConnectionPoolSize = masterConnectionPoolSize;
//    }
//
//    public String[] getSentinelAddresses() {
//        return sentinelAddresses;
//    }
//
//    public void setSentinelAddresses(String sentinelAddresses) {
//        this.sentinelAddresses = sentinelAddresses.split(",");
//    }
//
//    public String getMasterName() {
//        return masterName;
//    }
//
//    public void setMasterName(String masterName) {
//        this.masterName = masterName;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public String getAddress() {
//        return address;
//    }
//
//    public void setAddress(String address) {
//        this.address = address;
//    }
//
//    public int getConnectionPoolSize() {
//        return connectionPoolSize;
//    }
//
//    public void setConnectionPoolSize(int connectionPoolSize) {
//        this.connectionPoolSize = connectionPoolSize;
//    }
//
//    public int getConnectionMinimumIdleSize() {
//        return connectionMinimumIdleSize;
//    }
//
//    public void setConnectionMinimumIdleSize(int connectionMinimumIdleSize) {
//        this.connectionMinimumIdleSize = connectionMinimumIdleSize;
//    }
//}
//
