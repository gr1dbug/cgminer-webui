import cgminer.notify.CgminerApiService
import cgminer.notify.NotificationsService

class BootStrap {

    CgminerApiService cgminerApiService
    NotificationsService notificationsService

    def init = { servletContext ->
        cgminerApiService.startUpdating();
        notificationsService.startUpdating();
    }
    def destroy = {
    }
}
