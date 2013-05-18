import cgminer.notify.CgminerApiService

class BootStrap {

    CgminerApiService cgminerApiService

    def init = { servletContext ->
        cgminerApiService.startUpdating();
    }
    def destroy = {
    }
}
