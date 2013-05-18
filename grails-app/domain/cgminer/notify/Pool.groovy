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
}
