package io.fabric8.memcached.operator.controller_runtime.pkg;

public interface Reconciler {

    //Result reconcile(Request request);
    Void reconcile(Request request);
}
