package io.fabric8.memcached.operator.controller_runtime.pkg;

public interface Reconciler {

    void reconcile(Request request);
}
