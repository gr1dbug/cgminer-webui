/**
 * Created with IntelliJ IDEA.
 * User: craig
 * Date: 5/12/13
 * Time: 12:54 AM
 * To change this template use File | Settings | File Templates.
 */

angular.module("cgmui", []).config(["$routeProvider", function($routeProvider) {
        $routeProvider.when("/ui", {templateUrl: "partials/ui.html", controller: UIController}).
            when("/notifications", {templateUrl: "partials/notifications.html", controller: NotificationsController}).
            when("/newnotification", {templateUrl: "partials/newnotification.html", controller: NewNotificationController}).
            otherwise({redirectTo: "/ui"})
    }])
    .directive("cgmchart", function() {
        return {
            restrict: "E",
            replace: true,
            transclude: true,
            template: "<div style='width: 290px; height: 115px; display: inline-block' class='span4'></div>",
            link: function(scope, element, attrs) {
                scope.chart(scope.gpu, element, attrs.value, attrs.label);
            }
        }
    }
);

function UIController($scope, $http) {
    console.log("uic inst!");
};

function NotificationsController($scope, $http) {
    console.log("notif inst!");

    $scope.fetch = function() {
        $http.get('notifications/list').success(function(data) {
            $scope.notifications = data;
        });
    };

    $scope.fetch();
};

function NewNotificationController($scope, $http) {
    console.log("nnotif inst!");
};


function NotificationButtonsController($scope, $http, $location) {
    $scope.latestgpus = {};
    $scope.latestpools = {};

    $scope.fetch = function() {
        $http.get('cgmapi/gpucount').success(function(data) {
            $scope.gpucount = data.count;
            for (var i = 0; i < $scope.gpucount; i++) {
                $http.get('cgmapi/gpu?gpu='+i).success(function(data) {
                    // gpu returns an array of gpuresults that represent a time series of gpu results for an individual gpu
                    // the latest result in the last one
                    // the "msg" field in gpuresult is a unique id for the gpu, i.e. "GPU1"
                    $scope.latestgpus[data[0].msg] = data[data.length-1];
                });
            }
        });

        $http.get('cgmapi/pools').success(function(data) {
            $scope.pools = data;
            for (var i = 0; i < data[data.length-1].pools.length; i++) {
                $scope.latestpools[data[data.length-1].pools[i].call] = data[data.length-1].pools[i];
            }
        });
    };

    $scope.fetch();

    $scope.getpoolclickfn = function(poolnum) {
        return new function () {
            console.log("cl: " + poolnum)
        }
    }

};

function GpuController($scope, $http) {
    $scope.charts = {};
    $scope.latestgpus = {};
    $scope.gpus = {};
    $scope.series = {};
    $scope.updatetimers = {};

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
            out.push([parseInt($scope.gpus[gpu][i]["when"])*1000, $scope.gpus[gpu][i][key]]);
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

    $scope.chart = function(gpu, elt, value, label) {
        $scope.series[gpu.msg+value] = [{
            data: $scope.getTimeValueSeries(gpu.msg, value),
            label: label,
            lines: {
                fill: false
            }
        }]

        $scope.legend = {
            backgroundColor: "#333333",
            position: "nw"
        }

        $scope.colors = ["#18f51c"]

        $scope.xaxis = {
            show: true,
            position: "bottom",
            color: "#cccccc",
            tickColor: "#cccccc",
            mode: "time",
            timezone: "browser",
            ticks: 2
        };

        $scope.yaxis = {
            show: true,
            position: "left",
            color: "#cccccc",
            ticks: 2,
            tickFormatter: function(num) { return num.toFixed(2) }
        };

        $scope.grid = {
            color: "#cccccc"
        };

        $scope.charts[gpu.msg] = $.plot(elt, $scope.series[gpu.msg+value], { xaxis: $scope.xaxis, yaxis: $scope.yaxis, grid: $scope.grid, colors: $scope.colors, legend: $scope.legend});

        if ($scope.updatetimers[gpu.msg] == null) {
            $scope.updatetimers[gpu.msg] = setInterval(function updatedata() {
                $scope.updategpu(gpu);
                $scope.updatechart(gpu, elt, value);
            }, 500);
        }
    };

    $scope.updategpu = function(gpu) {
        $http.get('cgmapi/gpu?gpu='+gpu.call).success(function(data) {
            $scope.gpus[data[0].msg] = data;
            $scope.latestgpus[data[0].msg] = data[data.length-1];
        });
    };

    $scope.updatechart = function(gpu, elt, value) {
        $scope.series[gpu.msg+value][0].data = $scope.getTimeValueSeries(gpu.msg, value);
        $scope.charts[gpu.msg] = $.plot(elt, $scope.series[gpu.msg+value], { xaxis: $scope.xaxis, yaxis: $scope.yaxis, grid: $scope.grid, colors: $scope.colors, legend: $scope.legend });
        $scope.charts[gpu.msg].draw();
    }
};

function CoinController($scope, $http) {
    $http.get('cgmapi/coin').success(function(data) {
        $scope.coin = data;
    });
};

function PoolController($scope, $http) {

    $scope.latestpools = {};

    $scope.fetch = function() {
        $http.get('cgmapi/pools').success(function(data) {
            $scope.pools = data;
            for (var i = 0; i < data[data.length-1].pools.length; i++) {
                $scope.latestpools[data[data.length-1].pools[i].call] = data[data.length-1].pools[i];
            }
        });
    };


    $scope.fetch();
    if ($scope.updatetimer == null) {
        $scope.updatetimer = setInterval(function updatedata() {
            $scope.fetch();
        }, 500);
    }

}