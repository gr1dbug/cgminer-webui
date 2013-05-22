package cgminer.notify

class Pool {

    static constraints = {
        proxy nullable: true
        proxyType nullable: true
        stratumUrl nullable: true
    }

//    static belongsTo = [poolsEntry: Pools]
    
    String call;
    String url;
    String status;
    int priority;
    String longPoll;
    int getworks;
    int accepted;
    int rejected;
    int discarded;
    int stale;
    int getFailures;
    int remoteFailures;
    String user;
    long lastShareTime;
    int diff1Shares;
    String proxyType;
    String proxy;
    float diffAccepted;
    float diffRejected;
    float diffStale;
    float lastShareDiff;
    boolean hasStratum;
    boolean stratumActive;
    String stratumUrl;
    boolean hasGbt;
    int bestShare;

    @Override
    public String toString() {
        return "PoolRecord{" +
                "call='" + call + '\'' +
                ", url='" + url + '\'' +
                ", status='" + status + '\'' +
                ", priority=" + priority +
                ", longPoll='" + longPoll + '\'' +
                ", getworks=" + getworks +
                ", accepted=" + accepted +
                ", rejected=" + rejected +
                ", discarded=" + discarded +
                ", stale=" + stale +
                ", getFailures=" + getFailures +
                ", remoteFailures=" + remoteFailures +
                ", user='" + user + '\'' +
                ", lastShareTime=" + lastShareTime +
                ", diff1Shares=" + diff1Shares +
                ", proxyType='" + proxyType + '\'' +
                ", proxy='" + proxy + '\'' +
                ", diffAccepted=" + diffAccepted +
                ", diffRejected=" + diffRejected +
                ", diffStale=" + diffStale +
                ", lastShareDiff=" + lastShareDiff +
                ", hasStratum=" + hasStratum +
                ", stratumActive=" + stratumActive +
                ", stratumUrl='" + stratumUrl + '\'' +
                ", hasGbt=" + hasGbt +
                ", bestShare=" + bestShare +
                '}';
    }
}
