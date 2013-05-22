package cgminer.notify

import com.google.code.jgntp.Gntp;
import com.google.code.jgntp.GntpApplicationInfo
import com.google.code.jgntp.GntpClient
import com.google.code.jgntp.GntpNotificationInfo

import java.util.concurrent.TimeUnit

class NotificationsService {

    def cgminerApiService

    GntpApplicationInfo info
    GntpNotificationInfo summaryNotificationInfo
    GntpNotificationInfo payoutNotificationInfo
    GntpClient client

    private Runnable notifier;

    def isUpdating() {
        return (notifier != null);
    }

    def startUpdating() {
        notifier = new NotifierRunnable(this);
        notifier.start();
    }

    def stopUpdating() {
        notifier.stop();
        notifier = null;
    }


    def initialize() {
        if (info == null) info = Gntp.appInfo("cgminer").build()
        if (summaryNotificationInfo == null) summaryNotificationInfo = Gntp.notificationInfo(info, "Summary").build()
        if (payoutNotificationInfo == null) payoutNotificationInfo = Gntp.notificationInfo(info, "Payout").build()
        if (client == null) {
            client = Gntp.client(info).forHost("localhost").build()
            client.register()
        }
    }

    def notifications() {
        return Notification.findAll()
    }

    def sendNot(not) {
        initialize()

        StringBuilder builder = new StringBuilder()
        if (not.pools != null && not.pools.length() > 0) {
            String[] pools = not.pools.split(",")
            for (String p : pools) builder.append(cgminerApiService.latestpool(p)).append("\r\n\r\n")
        }

        if (not.gpus != null && not.gpus.length() > 0) {
            String[] gpus = not.gpus.split(",")
            for (String g : gpus) builder.append(cgminerApiService.latestgpu(g)).append("\r\n\r\n")
        }

        client.notify(Gntp.notification(summaryNotificationInfo, "cgminer summary").text(builder.toString()).build(), 5, TimeUnit.SECONDS)
    }

    def setNotification(not) {
        not.save(flush: true)
    }


}

class NotifierRunnable implements Runnable {
    private static final long ONE_HOUR = 60L*60*1000;
    private static final long ONE_DAY = 24L*ONE_HOUR;
    private static final long ONE_WEEK = 7L*ONE_DAY;

    private volatile Thread updatethread
    private NotificationsService notserv

    def NotifierRunnable(notserv) {
        this.notserv = notserv
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
                checkandsend()
                Thread.sleep(60000);
            }
            catch (InterruptedException ie) {
                // we don't want to do anything here, if the thread is interrupted in order to stop it
                // that will happen next time through the while loop
            }
        }
    }

    def checkandsend() {
        for (Notification not : notserv.notifications()) {
            def timediff = new Date().getTime() - not.lastSent.getTime()
            if (not.frequency.equalsIgnoreCase("hourly") && timediff > ONE_HOUR) {
                notserv.sendNot(not)
                not.lastSent = new Date()
                Notification.withTransaction() {
                    not.save()
                }
            }
            else if (not.frequency.equalsIgnoreCase("daily") && timediff > ONE_DAY) {
                notserv.sendNot(not)
                not.lastSent = new Date()
                Notification.withTransaction() {
                    not.save()
                }
            }
            else if (not.frequency.equalsIgnoreCase("weekly") && timediff > ONE_WEEK) {
                notserv.sendNot(not)
                not.lastSent = new Date()
                Notification.withTransaction() {
                    not.save()
                }
            }
        }
    }
}
