package cgminer.notify

class NotificationsController {

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
    }
}
