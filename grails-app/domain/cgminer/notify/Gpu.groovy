package cgminer.notify

class Gpu {

    static constraints = {
    }

    String status;
    long when;
    int code;
    String msg;
    String description;
    String call;
    String enabled;
    String gpuStatus;
    float temperature;
    int fanSpeed;
    int fanPercent;
    int gpuClock;
    int memoryClock;
    float gpuVoltage;
    int gpuActivity;
    int powertune;
    float mhsav;
    float mhs5s;
    int accepted;
    int rejected;
    int hardwareErrors;
    float utility;
    int intensity;
    int lastSharePool;
    long lastShareTime;
    float totalMH;
    int diff1Work;
    float difficultyAccepted;
    float difficultyRejected;
    float lastShareDifficulty;
    int lastValidWork;
    long timestamp;

}
