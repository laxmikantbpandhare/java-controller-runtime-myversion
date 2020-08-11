package io.fabric8.controller.controller_runtime;

import io.fabric8.controller.controller_runtime.pkg.Request;
import io.fabric8.controller.controller_runtime.pkg.Reconciler;
import io.fabric8.controller.controller_runtime.pkg.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

public class DefaultController implements Controller {
    private static final Logger log = LoggerFactory.getLogger(DefaultController.class);

    public Reconciler getReconciler() {
        return reconciler;
    }

    public void setReconciler(Reconciler reconciler) {
        this.reconciler = reconciler;
    }

    private Reconciler reconciler;
    private Request request;

    public BlockingQueue<Request> getWorkQueue() {
        return workQueue;
    }

    public void setWorkQueue(BlockingQueue<Request> workQueue) {
        this.workQueue = workQueue;
    }

    private BlockingQueue<Request> workQueue =  new ArrayBlockingQueue<>(1024);

    private String name;
    private int workerCount;
    private ScheduledExecutorService workerThreadPool;

    public DefaultController(Reconciler reconciler,BlockingQueue<Request> workQueue){//},Request request)
        this.reconciler = reconciler;
        this.workQueue = workQueue;
//        this.request = request;
    }

    public DefaultController(){

    }

    @Override
    public void run() {
        System.out.println("calling RUN() from default controller controller_runtime");

        // spawns worker threads for the controller.
        CountDownLatch latch = new CountDownLatch(workerCount);
        for (int i = 0; i < this.workerCount; i++) {
            final int workerIndex = i;
            System.out.println("In for loop controller runtime");
            workerThreadPool.scheduleWithFixedDelay(
                    () -> {
                        System.out.println("In workerThreadPool"+this.name+ workerIndex);
                        log.debug("Starting controller {} worker {}..", this.name, workerIndex);
                        try {
                            Result result= null;
                            Request request = null;
                            System.out.println("before request.take");
                            request = workQueue.take();
                            System.out.println("After request.take");
                            System.out.println("Request in controller_runtime" + request.getNamespace());
                            System.out.println("Request in controller_runtime" + request.getName());
                            result = reconciler.reconcile(request);
//                            workQueue.poll();
                        } catch (Throwable t) {
                            log.error("Unexpected controller loop abortion", t);
                        }
                        latch.countDown();
                        log.debug("Exiting controller {} worker {}..", this.name, workerIndex);
                    },
                    0,
                    1   ,
                    TimeUnit.SECONDS);
        }
    }

    // preFlightCheck checks if the controller is ready for working.
    private boolean preFlightCheck() {
        if (workerCount <= 0) {
            log.error("Fail to start controller {}: worker count must be positive.", this.name);
            return false;
        }
        if (workerThreadPool == null) {
            log.error("Fail to start controller {}: missing worker thread-pool.", this.name);
            return false;
        }
//        if (!isReady()) {
//            log.error(
//                    "Fail to start controller {}: Timed out waiting for cache to be synced.", this.name);
//            return false;
//        }
        return true;
    }

    @Override
    public void shutdown() {
        workerThreadPool.shutdown();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWorkerCount() {
        return workerCount;
    }

    public void setWorkerCount(int workerCount) {
        this.workerCount = workerCount;
    }

    public ScheduledExecutorService getWorkerThreadPool() {
        return workerThreadPool;
    }

    public void setWorkerThreadPool(ScheduledExecutorService workerThreadPool) {
        this.workerThreadPool = workerThreadPool;
    }
}
