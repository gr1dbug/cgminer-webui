/**
 * Created with IntelliJ IDEA.
 * User: craig
 * Date: 5/12/13
 * Time: 12:54 AM
 * To change this template use File | Settings | File Templates.
 */

angular.module("cgmui", [])
    .directive("cgmchart", function() {
        return {
            restrict: "E",
            replace: true,
            transclude: true,
            template: "<div style='width: 300px; height: 150px;'></div>",
            link: function(scope, element, attrs) {
                scope.chart(scope.gpu, element, attrs.value);
            }
        }
    }
);

function GpuController($scope, $http) {
    $scope.charts = {};
    $scope.latestgpus = {};
    $scope.gpus = {};

    $http.get('cgmapi/gpucount').success(function(data) {
        $scope.gpucount = data.count;
        for (var i = 0; i < $scope.gpucount; i++) {
            $http.get('cgmapi/gpu?gpu='+i).success(function(data) {
                // gpu returns an array of gpuresults that represent a time series of gpu results for an individual gpu
                // the latest result in the last one
                // the "msg" field in gpuresult is a unique id for the gpu, i.e. "GPU1"
                $scope.latestgpus[data[0].msg] = data[data.length-1];
                $scope.gpus[data[0].msg] = data;
            });
        }
    });

    $scope.getValueSeries = function(gpu, key) {
        return $.map($scope.gpus[gpu], function(v, k) {
            return v[key];
        });
    };

    $scope.getTimeValueSeries = function(gpu, key) {
        var out = [];
        for (var i = 0; i < $scope.gpus[gpu].length; i++) {
            out.push([$scope.gpus[gpu][i]["timestamp"], $scope.gpus[gpu][i][key]]);
        }
        return out;
    }

    $scope.zipseries = function(data) {
        var ret = [];
        for (var i = 0; i < data.length; i++) {
            ret.push([i, data[i]]);
        }
        return ret;
    }

    $scope.chart = function(gpu, elt, value) {
        $scope.series = [{
            data: $scope.getTimeValueSeries(gpu.msg, value),
            lines: {
                fill: false
            }
        }]

        $scope.xaxis = {
            show: true,
                position: "bottom",
                mode: "time",
                timezone: "browser",
                ticks: 2
        };

        $scope.yaxis = {
            show: true,
                position: "left"
        };

        $scope.charts[gpu.msg] = $.plot(elt, $scope.series, { xaxis: $scope.xaxis, yaxis: $scope.yaxis });

        setInterval(function updatedata() {
            $scope.updategpu(gpu);
            $scope.updatechart(gpu, elt, value);
        }, 1000);
    };

    $scope.updategpu = function(gpu) {
        $http.get('cgmapi/gpu?gpu='+gpu.call).success(function(data) {
            $scope.gpus[data[0].msg] = data;
        });
    };

    $scope.updatechart = function(gpu, elt, value) {
        $scope.series[0].data = $scope.getTimeValueSeries(gpu.msg, value);
        $scope.charts[gpu.msg] = $.plot(elt, $scope.series, { xaxis: $scope.xaxis, yaxis: $scope.yaxis });
        $scope.charts[gpu.msg].draw();
    }
}