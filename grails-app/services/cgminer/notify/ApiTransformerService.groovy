package cgminer.notify

import com.gridbuglabs.cgminer.api.GpuResult
import com.gridbuglabs.cgminer.api.PoolsResult

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

    def Pools pools(PoolsResult result) {
        def item = new Pools(status: result.getStatus(), when: result.getWhen(), code: result.getCode(),
                msg: result.getMsg(), description: result.getDescription(),
                timestamp: new Date().getTime(),)

        result.getRecords().each() { prec ->
            item.addToPools(pool(prec))
        }

        return item
    }

    def Pool pool(PoolsResult.PoolRecord result) {
        return new Pool(call: result.getCall(), url: result.getUrl(), status: result.getStatus(),
                priority: result.getPriority(), longPoll: result.getLongPoll(), getworks: result.getGetworks(),
                accepted: result.getAccepted(), rejected: result.getRejected(), discarded: result.getDiscarded(),
                stale: result.getStale(), getFailures: result.getGetFailures(), remoteFailures: result.getRemoteFailures(),
                user: result.getUser(), lastShareTime: result.getLastShareTime(), diff1Shares: result.getDiff1Shares(),
                proxyType: result.getProxyType(), proxy: result.getProxy(), diffAccepted: result.getDiffAccepted(),
                diffRejected: result.getDiffRejected(), diffStale: result.getDiffStale(),
                lastShareDiff: result.getLastShareDiff(), hasStratum: result.getHasStratum(),
                stratumActive: result.getStratumActive(), stratumUrl: result.getStratumUrl(), hasGbt: result.getHasGbt(),
                bestShare: result.getBestShare())
    }

}
