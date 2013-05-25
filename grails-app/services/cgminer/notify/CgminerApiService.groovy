package cgminer.notify

import com.gridbuglabs.cgminer.api.CGMinerApi


class CgminerApiService {

    ApiTransformerService apiTransformerService
    private CGMinerApi api = new CGMinerApi()
    private static final long ONE_MINUTE = 60;


    private Runnable updater;

    def isUpdating() {
        return (updater != null);
    }

    def startUpdating() {
        updater = new UpdateRunnable(apiTransformerService, this);
        updater.start();
    }

    def stopUpdating() {
        updater.stop();
        updater = null;
    }

    private def expire(type) {
        type.withTransaction() {
            long now = new Date().getTime()/1000; // the "when" is a unix style timestamp, i.e. # SECONDS since 1/1/70
            def old = type.findAllByWhenLessThan(now - ONE_MINUTE)
            old.each() { poolitem ->
                println("now: " + now + ", now - day: " + (now - ONE_MINUTE) + ", evt time: " + poolitem.when)
                poolitem.delete(flush: true)
            }
        }
    }

    def updateGpus() {
        // we want to get gpu records for all gpus
        Gpu.withTransaction() {
            def count = api.gpucount()
            0.upto(count.getCount()-1) { i ->
                Gpu gpu = apiTransformerService.gpu(api.gpu(i))
                gpu.save(flush: true)
            }
        }

        expire(Gpu)
    }

    def updatePools() {
        Pools.withTransaction() {
            Pools pools = apiTransformerService.pools(api.pools());
            pools.save(flush: true)
        }

        expire(Pools)
    }

    def gpu(num) {
        // we get the results in descending order so we get the latest n results
        def results = Gpu.findAllByCall(num, [max: 15, order: "desc", sort: "when"])
        // what we actually want to return is the list in ascending order however
        return results.reverse()
    }

    def latestgpu(num) {
        return gpu(num).last()
    }

    def pools() {
        // we get the results in descending order so we get the latest n results
        def results = Pools.findAll([max: 15, order: "desc", sort: "when"])
        // what we actually want to return is the list in ascending order however
        return results.reverse()
    }

    def latestpool(num) {
        def poolslist = pools()
        def latestpool = poolslist.last()
        for (Pool pool : latestpool.pools) {
            if (pool.call.equalsIgnoreCase(String.valueOf(num))) return pool
        }
        return null
    }


    class UpdateRunnable implements Runnable {

        private volatile Thread updatethread;
        private ApiTransformerService transformer
        private CgminerApiService service

        public UpdateRunnable(ApiTransformerService transformer, CgminerApiService service) {
            this.transformer = transformer
            this.service = service
        }


        def Thread start() {
            updatethread = new Thread(this);
            updatethread.start();
            return updatethread;
        }

        def void stop() {
            updatethread = null;
        }

        def void run() {
            Thread thisthread = Thread.currentThread();
            while (thisthread == updatethread) {
                try {
                    updateGpus();
                    updatePools();
                    Thread.sleep(1000);
                }
                catch (InterruptedException ie) {
                    // we don't want to do anything here, if the thread is interrupted in order to stop it
                    // that will happen next time through the while loop
                }
            }
        }

        def updatePools() {
            service.updatePools()
        }

        def updateGpus() {
            service.updateGpus()
        }
    }
}
