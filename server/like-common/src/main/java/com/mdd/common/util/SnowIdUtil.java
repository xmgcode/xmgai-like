package com.mdd.common.util;
import cn.hutool.core.util.IdUtil;

import java.util.Random;
import java.util.UUID;

/**
 * @author zhangyujun
 * @date 2024/5/13 14:36
 */
public class SnowIdUtil {

    // 毫秒级时间戳(42位)
    private final long twepoch = 1288834974657L;
    private final long workerIdBits = 5L;
    private final long datacenterIdBits = 5L;
    private final long maxWorkerId = -1L ^ (-1L << workerIdBits);
    private final long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);
    private final long sequenceBits = 12L;
    private final long workerIdShift = sequenceBits;
    private final long datacenterIdShift = sequenceBits + workerIdBits;
    private final long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;
    private final long sequenceMask = -1L ^ (-1L << sequenceBits);

    private long workerId;
    private long datacenterId;
    private long sequence = 0L;
    private long lastTimestamp = -1L;

    private final Random random = new Random();

    public SnowIdUtil(long workerId, long datacenterId) {
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException("Worker ID must be between 0 and " + maxWorkerId + ", but was: " + workerId);
        }
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException("Datacenter ID must be between 0 and " + maxDatacenterId + ", but was: " + datacenterId);
        }
        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }

    public synchronized long nextId() {
        long timestamp = timeGen();
        if (timestamp < lastTimestamp) {
            // 如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过，这时可以拒绝生成ID，并抛出异常
            throw new RuntimeException("Clock moved backwards. Refusing to generate id for the time being.");
        }
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                // 同一毫秒的序列数已经达到最大，等待下一个毫秒
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0;
        }
        lastTimestamp = timestamp;

        // 时间戳部分 | 数据中心部分 | 机器ID部分 | 序列号部分的组合
        long id = ((timestamp - twepoch) << timestampLeftShift)
                | (datacenterId << datacenterIdShift)
                | (workerId << workerIdShift)
                | sequence;
        return id;
    }

    private long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    private long timeGen() {
        return System.currentTimeMillis();
    }

    // 测试
    public static void main(String[] args) {
        SnowIdUtil idWorker = new SnowIdUtil(1, 1);
        for (int i = 0; i < 10; i++) {
            System.out.println(idWorker.nextId());
        }
    }

    public static long getLongUUid() {
        SnowIdUtil idWorker = new SnowIdUtil(1, 1);
        long id = idWorker.nextId();
        return id;
    }




    public static String getStrUUid() {
        String id = IdUtil.randomUUID().replaceAll("-","");
        return id;

//        SnowIdUtil idWorker = new SnowIdUtil(1, 1);
//        long id = idWorker.nextId();
//        String idStr = Long.toString(id);
//        return idStr;
    }


}
