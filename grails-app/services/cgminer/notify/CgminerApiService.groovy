package cgminer.notify

import com.gridbuglabs.cgminer.api.CGMinerApi


class CgminerApiService {

    ApiTransformerService apiTransformerService
    private CGMinerApi api = new CGMinerApi()
    private static final long ONE_DAY = 24L*60*60;


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

    def updateGpus() {
        // we want to get gpu records for all gpus
        Gpu.withTransaction() {
            def count = api.gpucount()
            0.upto(count.getCount()-1) { i ->
                Gpu gpu = apiTransformerService.gpu(api.gpu(i))
                gpu.save(flush: true)
            }
        }

        Gpu.withTransaction() {
            // we then clean out old records (more than 1 day) because we aren't keeping these things longer
            // than necessary to do our rolling 24hr analytics
            long now = new Date().getTime()/1000; // the "when" is a unix style timestamp, i.e. # SECONDS since 1/1/70
            def old = Gpu.findAllByWhenLessThan(now - ONE_DAY)
            old.each() { event ->
                println("now: " + now + ", now - day: " + (now - ONE_DAY) + ", evt time: " + event.when)
                event.delete(flush: true)
            }
        }
    }

    def updatePools() {
        Pools.withTransaction() {
            Pools pools = apiTransformerService.pools(api.pools());
            pools.save(flush: true)
            def x = "aoeu"
        }

        Pools.withTransaction() {
            long now = new Date().getTime()/1000; // the "when" is a unix style timestamp, i.e. # SECONDS since 1/1/70
            def old = Pools.findAllByWhenLessThan(now - ONE_DAY)
            old.each() { poolitem ->
                println("now: " + now + ", now - day: " + (now - ONE_DAY) + ", evt time: " + poolitem.when)
                poolitem.delete(flush: true)
            }
        }
    }

    def gpu(num) {
        // we get the results in descending order so we get the latest n results
        def results = Gpu.findAllByCall(num, [max: 15, order: "desc", sort: "when"])
        // what we actually want to return is the list in ascending order however
        return results.reverse()
    }

    def pools() {
        // we get the results in descending order so we get the latest n results
        def results = Pools.findAll([max: 15, order: "desc", sort: "when"])
        // what we actually want to return is the list in ascending order however
        return results.reverse()
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
