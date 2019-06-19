package com.za.common.generate;

import java.util.concurrent.ConcurrentHashMap;

public class SnowflakeIdWorkerFactory {

    private static final ConcurrentHashMap<String, SnowflakeIdWorker> concurrentHashMap = new ConcurrentHashMap<>();

    public SnowflakeIdWorker getSnowflakeIdWorker(long workerId, long dataCenterId) {
        String key = getKey(workerId, dataCenterId);
        SnowflakeIdWorker tempResult = concurrentHashMap.get(key);
        if (tempResult == null) {
            concurrentHashMap.putIfAbsent(key, new SnowflakeIdWorker(workerId, dataCenterId));
            return concurrentHashMap.get(key);
        }
        return tempResult;
    }

    private static String getKey(long workerId, long dataCenterId) {
        return workerId + "_" + dataCenterId;
    }

}
