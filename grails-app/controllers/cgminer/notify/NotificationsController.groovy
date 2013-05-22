package cgminer.notify

class NotificationsController {

    def notificationsService

    def index() {}

    def makenot(params) {
        Notification not = new Notification();
        not.frequency = params.get("scale")
        not.day = params.get("day")
        not.pools = params.get("pools")
        not.gpus = params.get("gpus")
        not.lastSent = new Date(0)
        return not
    }

    def setnotification() {
        Notification not = makenot(params)
        notificationsService.setNotification(not)
        render("notification set!");
    }

    def testnotification() {
        Notification not = makenot(params)
        notificationsService.sendNot(not)
        render("test notification sent!");
    }
}
