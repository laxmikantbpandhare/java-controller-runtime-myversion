package io.fabric8.memcached.operator.controller_runtime.pkg;

import java.time.Duration;

public class Result {

    public boolean isRequeue() {
        return requeue;
    }

    public void setRequeue(boolean requeue) {
        this.requeue = requeue;
    }

    public Duration getRequeueAfter() {
        return requeueAfter;
    }

    public void setRequeueAfter(Duration requeueAfter) {
        this.requeueAfter = requeueAfter;
    }

    private boolean requeue;
    private Duration requeueAfter;
}
