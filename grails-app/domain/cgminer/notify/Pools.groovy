package cgminer.notify

class Pools {

    static constraints = {
    }

    static hasMany = [pools : Pool]

    static mapping = {
        pools lazy: false
    }

    String status;
    long when;
    int code;
    String msg;
    String description;
    long timestamp;

}
