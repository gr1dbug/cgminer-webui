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

    $scope.zipseries = function(data) {
        var ret = [];
        for (var i = 0; i < data.length; i++) {
            ret.push([i, data[i]]);
        }
        return ret;
    }

    $scope.chart = function(gpu, elt, value) {
        $scope.series = [{
            data: $scope.zipseries($scope.getValueSeries(gpu.msg, value)),
            lines: {
                fill: true
            }
        }]

        $scope.charts[gpu.msg] = $.plot(elt, $scope.series, {
            xaxis: {
                show: true,
                position: "bottom"
            },

            yaxis: {
                show: true,
                position: "left"
            }
        });

        $scope.charts[gpu.msg].draw();
        setInterval(function updatedata() {
            $scope.updategpu(gpu);
            $scope.updatechart(gpu, value);
        }, 1000);
    };

    $scope.updategpu = function(gpu) {
        $http.get('cgmapi/gpu?gpu='+gpu.call).success(function(data) {
            $scope.gpus[data[0].msg] = data;
        });
    };

    $scope.updatechart = function(gpu, value) {
        $scope.series[0].data = $scope.zipseries($scope.getValueSeries(gpu.msg, value));
        $scope.charts[gpu.msg].setData($scope.series);
        $scope.charts[gpu.msg].draw();
    }
}