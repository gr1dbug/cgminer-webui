package cgminer.notify

import com.google.gson.Gson
import com.gridbuglabs.cgminer.api.CGMinerApi
import com.gridbuglabs.cgminer.api.DevsResult
import com.gridbuglabs.cgminer.api.GpuCountResult
import com.gridbuglabs.cgminer.api.GpuResult
import grails.converters.JSON

class CgmapiController {

    CGMinerApi api = new CGMinerApi()
    Gson gson = new  Gson();
    DevsResult devs
    GpuResult gpu0

    def index() {
        devs = api.devs()
        gpu0 = api.gpu(0)
    }

    private def storeInSession(Object value, String valname, int limit) {
        if (session[valname] == null) session[valname] = []

        // add our new temp reading to the end of the list
        session[valname].add(value);

        // if we've hit the # samples limit, remove the oldest sample
        if (session[valname].size() > limit) session[valname].remove(0);
    }

    def gpu() {
        def gpunum = Integer.parseInt(params["gpu"])

        GpuResult result = api.gpu(gpunum)

        // result.msg is the name of this gpu, i.e. "GPU1" or "GPU0", etc
        storeInSession(result, result.msg, 25)

        // and return the temps as a json array
        render(gson.toJson(session[result.msg]))
    }

    def gpuvalue() {
        def gpunum = Integer.parseInt(params["gpu"])
                                                             ;
        // we want to get the indicated value reading for the indicated gpu
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

    def gpucount() {
        GpuCountResult result = api.gpucount();
        render(gson.toJson(result))
    }
}
