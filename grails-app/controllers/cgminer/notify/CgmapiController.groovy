package cgminer.notify

import com.gridbuglabs.cgminer.api.CGMinerApi
import com.gridbuglabs.cgminer.api.DevsResult
import com.gridbuglabs.cgminer.api.GpuResult
import grails.converters.JSON

class CgmapiController {

    CGMinerApi api = new CGMinerApi()
    DevsResult devs
    GpuResult gpu0

    def index() {
        devs = api.devs()
        gpu0 = api.gpu(0)
    }

    def gpuvalue() {
        def gpunum = Integer.parseInt(params["gpu"])

        // we want to get the temp reading for the indicated gpu
        GpuResult result = api.gpu(gpunum)

        def value = params["value"];
        if (session[value] == null) session[value] = []

        // add our new temp reading to the end of the list
        session[value].add(result[value]);

        // if we've hit the # samples limit, remove the oldest sample
        if (session[value].size() > 25) session[value].remove(0);

        // and return the temps as a json array
        render(session[value]) as JSON;

    }
}
