package io.fabric8.controller.controller_runtime.pkg;

public interface Reconciler {

    //Result reconcile(Request request);
    Result reconcile(Request request);
}
