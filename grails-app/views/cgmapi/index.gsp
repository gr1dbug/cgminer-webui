<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html ng-app="cgmui">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>cgminer ui</title>

    <script src="http://code.jquery.com/jquery.js"></script>
    <script src="js/jquery.flot.js"></script>
    <script src="js/jquery.flot.time.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/angular.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="js/app.js" type="text/javascript" charset="utf-8"></script>

    <link href="css/bootstrap.css" rel="stylesheet" media="screen">
    <style type="text/css">
        body {
            background: #222222;
        }

        .flot-x-axis {
            color: #18f51c;
        }

        .flot-y-axis {
            color: #18f51c;
        }

        .lighttext {
            color: #cccccc;
        }

        .greentext {
            color: #18f51c;
        }

    </style>

</head>
<body class="lighttext">

    <div class="navbar navbar-inverse navbar-fixed-top">
        <div class="container" ng-controller="CoinController">
            <strong style="margin-left: 75px" class="brand">{{coin.description}}</strong>
            <div style="float: right"><strong style="margin-left: 50px" class="brand">web ui</strong></div>
            <div style="float: right"><strong style="margin-left: 50px" class="brand">growl</strong></div>
        </div>
    </div>


    <div class="container">

        <div ng-controller="PoolController">
            <div ng-repeat="pool in latestpools" class="span12" style="width: 100%; margin-top: 40px; margin-left: 65px">
                <h3>pool #{{pool.call}}: <span class="greentext">{{pool.url}}</span></h3>
                <table class="table">
                    <thead>
                        <tr>
                            <td>priority</td>
                            <td>accepted</td>
                            <td>rejected</td>
                            <td>discarded</td>
                            <td>stale</td>
                            <td>get fail</td>
                            <td>remote fail</td>
                            <td>user</td>
                        </tr>
                    </thead>
                    <tbody>
                        <tr class="greentext">
                            <td><strong>{{pool.priority}} </strong></td>
                            <td><strong>{{pool.accepted}} shr</strong></td>
                            <td><strong>{{pool.rejected}} shr</strong></td>
                            <td><strong>{{pool.discarded}} shr</strong></td>
                            <td><strong>{{pool.stale}} shr</strong></td>
                            <td><strong>{{pool.getFailures}} </strong></td>
                            <td><strong>{{pool.remoteFailures}} </strong></td>
                            <td><strong>{{pool.user}} </strong></td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>

        <div id="linegraph" ng-controller="GpuController" class="span12">
            <div ng-repeat="gpu in latestgpus" class="span12">
                <div id="{{gpu.msg}}" style="width: 100%; margin: 25px;" class="span12">
                    <h3>{{gpu.msg}}</h3>
                    <cgmchart value="temperature" label="temp"></cgmchart>
                    <cgmchart value="mhsav" label="MH/s av"></cgmchart>
                    <cgmchart value="mhs5s" label="MH/s 5s"></cgmchart>
                </div>

                <div class="span12">
                    <table class="table">
                        <thead>
                        <tr>
                            <td>temp</td>
                            <td>fan speed</td>
                            <td>fan %</td>
                            <td>core clk</td>
                            <td>mem clk</td>
                            <td>volt</td>
                            <td>activity</td>
                            <td>powertune</td>
                            <td>mhs avg</td>
                            <td>mhs 5s</td>
                            <td>acc</td>
                            <td>rej</td>
                            <td>hw err</td>
                            <td>utility</td>
                            <td>intensity</td>
                            <td>last diff</td>
                        </tr>
                        </thead>
                        <tbody>
                        <tr class="greentext">
                            <td><strong id="temperature">{{gpu.temperature}} °c</strong></td>
                            <td><strong id="fanSpeed">{{gpu.fanSpeed}} rpm</strong></td>
                            <td><strong id="fanPercent">{{gpu.fanPercent}} %</strong></td>
                            <td><strong id="gpuClock">{{gpu.gpuClock}} mhz</strong></td>
                            <td><strong id="memoryClock">{{gpu.memoryClock}} mhz</strong></td>
                            <td><strong id="gpuVoltage">{{gpu.gpuVoltage}} v</strong></td>
                            <td><strong id="gpuActivity">{{gpu.gpuActivity}} %</strong></td>
                            <td><strong id="powertune">{{gpu.powertune}} </strong></td>
                            <td><strong id="mhsav">{{gpu.mhsav}} mh/s</strong></td>
                            <td><strong id="mhs5s">{{gpu.mhs5s}} mh/s</strong></td>
                            <td><strong id="accepted">{{gpu.accepted}} shr</strong></td>
                            <td><strong id="rejected">{{gpu.rejected}} shr</strong></td>
                            <td><strong id="hardwareErrors">{{gpu.hardwareErrors}} </strong></td>
                            <td><strong id="utility">{{gpu.utility}} </strong></td>
                            <td><strong id="intensity">{{gpu.intensity}} </strong></td>
                            <td><strong id="lastShareDifficulty">{{gpu.lastShareDifficulty}} </strong></td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
