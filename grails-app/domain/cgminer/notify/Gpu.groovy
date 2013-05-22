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


    @Override
    public String toString() {
        return "GpuResult{" +
                "status='" + status + '\'' +
                ", when=" + when +
                ", code=" + code +
                ", msg='" + msg + '\'' +
                ", description='" + description + '\'' +
                ", call='" + call + '\'' +
                ", enabled='" + enabled + '\'' +
                ", gpuStatus='" + gpuStatus + '\'' +
                ", temperature=" + temperature +
                ", fanSpeed=" + fanSpeed +
                ", fanPercent=" + fanPercent +
                ", gpuClock=" + gpuClock +
                ", memoryClock=" + memoryClock +
                ", gpuVoltage=" + gpuVoltage +
                ", gpuActivity=" + gpuActivity +
                ", powertune=" + powertune +
                ", mhsav=" + mhsav +
                ", mhs5s=" + mhs5s +
                ", accepted=" + accepted +
                ", rejected=" + rejected +
                ", hardwareErrors=" + hardwareErrors +
                ", utility=" + utility +
                ", intensity=" + intensity +
                ", lastSharePool=" + lastSharePool +
                ", lastShareTime=" + lastShareTime +
                ", totalMH=" + totalMH +
                ", diff1Work=" + diff1Work +
                ", difficultyAccepted=" + difficultyAccepted +
                ", difficultyRejected=" + difficultyRejected +
                ", lastShareDifficulty=" + lastShareDifficulty +
                ", lastValidWork=" + lastValidWork +
                '}';
    }
}
