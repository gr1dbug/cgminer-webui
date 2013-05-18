package cgminer.notify

import com.gridbuglabs.cgminer.api.GpuResult

class ApiTransformerService {

    def serviceMethod() {

    }

    def Gpu gpu(GpuResult result) {
        return new Gpu(status: result.getStatus(), when: result.getWhen(), code: result.getCode(),
                msg: result.getMsg(), description: result.getDescription(), call: result.getCall(),
                enabled: result.getEnabled(), gpuStatus: result.getGpuStatus(), temperature:  result.getTemperature(),
                fanSpeed: result.getFanSpeed(), fanPercent: result.getFanPercent(), gpuClock: result.getGpuClock(),
                memoryClock: result.getMemoryClock(), gpuVoltage: result.getGpuVoltage(),
                gpuActivity: result.getGpuActivity(), powertune: result.getPowertune(), mhsav: result.getMhsav(),
                mhs5s: result.getMhs5s(), accepted: result.getAccepted(), rejected: result.getRejected(),
                hardwareErrors: result.getHardwareErrors(), utility: result.getUtility(),
                intensity: result.getIntensity(), lastSharePool: result.getLastSharePool(),
                lastShareTime: result.getLastShareTime(), totalMH: result.getTotalMH(), diff1Work: result.getDiff1Work(),
                difficultyAccepted: result.getDifficultyAccepted(), difficultyRejected: result.getDifficultyRejected(),
                lastShareDifficulty: result.getLastShareDifficulty(), lastValidWork: result.getLastValidWork(),
                timestamp: new Date().getTime());
    }
}
