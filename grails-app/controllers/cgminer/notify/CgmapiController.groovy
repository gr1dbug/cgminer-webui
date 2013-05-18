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

    def index() {
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
        render(gson.toJson(cgminerApiService.pools()))
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
