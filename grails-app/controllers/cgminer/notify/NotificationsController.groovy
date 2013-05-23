package cgminer.notify

import com.google.gson.Gson
import grails.converters.JSON

class NotificationsController {

    def notificationsService
    Gson gson = new  Gson();

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

    def list() {
        render(gson.toJson(notificationsService.notifications()))
    }

    def delete() {
        Notification.findById(params.get("id")).delete()
        render("deleted!")
    }
}
