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

    def serviceMethod() {

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

    def sendTest(not) {
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
}
