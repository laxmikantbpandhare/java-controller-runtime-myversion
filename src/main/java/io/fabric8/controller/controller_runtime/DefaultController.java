package io.fabric8.controller.controller_runtime;

import io.fabric8.controller.controller_runtime.pkg.Request;
import io.fabric8.controller.controller_runtime.pkg.Reconciler;
import io.fabric8.controller.controller_runtime.pkg.Result;

public class DefaultController {

    private Reconciler reconciler;
    private Request request;

    public DefaultController(Reconciler reconciler,Request request) {
        this.reconciler = reconciler;
        this.request = request;
    }

    public void run(){
        System.out.println("calling RUN() from default controller controller_runtime");
        Result result= null;
        result = reconciler.reconcile(request);
    }

}
