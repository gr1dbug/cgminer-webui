package cgminer.notify

import com.google.gson.Gson
import com.gridbuglabs.cgminer.api.CGMinerApi
import com.gridbuglabs.cgminer.api.CoinResult
import com.gridbuglabs.cgminer.api.DevsResult
import com.gridbuglabs.cgminer.api.GpuCountResult
import com.gridbuglabs.cgminer.api.GpuResult
import com.gridbuglabs.cgminer.api.PoolsResult
import grails.converters.JSON
import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.Method
import org.json.simple.JSONArray

class CgmapiController {

    CgminerApiService cgminerApiService
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
        render(gson.toJson(cgminerApiService.gpu(gpunum)))
    }

    def gpucount() {
        GpuCountResult result = api.gpucount()
        render(gson.toJson(result))
    }

    def coin() {
        CoinResult result = api.coin()
        render(gson.toJson(result))
    }

    def pools() {
        PoolsResult result = api.pools()

        storeInSession(result, "pools", 25)

        render(gson.toJson(session["pools"]))
    }

    def poolstats() {
        def poolurl = params["poolurl"];
        def apikey = params["apikey"];

        def http = new HTTPBuilder(poolurl)
        http.request(Method.GET, ContentType.JSON) {
            uri.path = "/api"
            uri.query = [api_key: apikey]
            response.success = { resp, json  -> render(json) }
            response.failure = { resp -> render("api call failed") }
        }
    }
}
