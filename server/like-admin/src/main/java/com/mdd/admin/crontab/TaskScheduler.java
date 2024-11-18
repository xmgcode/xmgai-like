package com.mdd.admin.crontab;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
public class TaskScheduler {

    private final ThreadPoolExecutor executorService;
    private final Queue<TaskWrapper> taskQueue;
    private final int maxRunningTasks;
    private boolean isShutdown = false;

    public TaskScheduler(@Value("${task.scheduler.maxRunningTasks}") int maxRunningTasks) {
        this.maxRunningTasks = maxRunningTasks;
        this.taskQueue = new LinkedList<>();
        this.executorService = new ThreadPoolExecutor(maxRunningTasks, maxRunningTasks,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>());
    }

    public void scheduleTask(String taskName, Runnable task) {
        if (!isShutdown) {
            taskQueue.offer(new TaskWrapper(taskName, task));
            tryStartTask();
        } else {
            throw new IllegalStateException("Task scheduler has been shut down");
        }
    }

    private synchronized void tryStartTask() {
        if (executorService.getActiveCount() < maxRunningTasks && !taskQueue.isEmpty()) {
            TaskWrapper taskWrapper = taskQueue.poll();
            if (taskWrapper != null) {
                executorService.submit(() -> {
                    try {
                        taskWrapper.getTask().run();
                    } finally {
                        taskCompleted();
                    }
                });
            }
        }
    }

    private synchronized void taskCompleted() {
        tryStartTask();
    }

    // 停止任务调度器
    public synchronized void shutdown() {
        executorService.shutdown();
        isShutdown = true;
    }

    // 定时检查任务状态的方法，每10秒执行一次
    @Scheduled(fixedRate = 10000)
    public synchronized void checkStatus() {
        // 检查任务队列和运行中任务的状态，根据需要进行调整
    }

    // 内部类，用于包装任务和保持加入顺序
    private static class TaskWrapper {
        private final String taskName;
        private final Runnable task;

        public TaskWrapper(String taskName, Runnable task) {
            this.taskName = taskName;
            this.task = task;
        }

        public Runnable getTask() {
            return task;
        }

        public String getTaskName() {
            return taskName;
        }
    }

    // 主方法用于演示
    public static void main(String[] args) {
        // 注意：这里不能直接在 main 方法中使用 @Value 注解，需要在实际的 Spring 环境中测试
        // 此处示例中将 maxRunningTasks 作为构造函数参数传入
        TaskScheduler scheduler = new TaskScheduler(50); // 最多同时执行50个任务

        // 模拟添加多个任务
        for (int i = 1; i <= 100; i++) {
            final int taskId = i;
            Runnable task = () -> {
                System.out.println("Task " + taskId + " is running...");
                // 实际业务逻辑
                try {
                    Thread.sleep(2000); // 模拟任务执行时间
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Task " + taskId + " completed.");
            };
            scheduler.scheduleTask("Task " + taskId, task);
        }

        // 模拟在某个时刻停止任务调度器
        // 在合适的地方调用 shutdown 方法关闭任务调度器
//        scheduler.shutdown();
    }
}
