package cgminer.notify

class NotificationsController {

    def notificationsService

    def index() {}

    def setnotification() {
        println("set!")
        render("set!");
    }

    def testnotification() {
        println("test!")
        println("scale: " + params.get("scale"))
        println("day: " + params.get("day"))
        println("pe: " + params.get("pools"))
        println("ge: " + params.get("gpus"))
        render("test!");
        Notification not = new Notification();
        not.frequency = params.get("scale")
        not.day = params.get("day")
        not.pools = params.get("pools")
        not.gpus = params.get("gpus")
        notificationsService.sendTest(not)
    }
}
