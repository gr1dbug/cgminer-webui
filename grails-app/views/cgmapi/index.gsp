<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html ng-app="cgmui">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>cgminer ui</title>

    <script src="http://code.jquery.com/jquery.js"></script>
    <script src="js/jquery.flot.js"></script>
    <script src="js/bootstrap.min.js"></script>
<!--    <script src="js/raphael.js" type="text/javascript" charset="utf-8"></script>
    <script src="js/ico.min.js" type="text/javascript" charset="utf-8"></script>    -->
    <script src="js/angular.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="js/app.js" type="text/javascript" charset="utf-8"></script>

    <link href="css/bootstrap.min.css" rel="stylesheet" media="screen">

</head>
<body>
<h1>aoesthuaoetnsuh: <%= gpu0.temperature %></h1>

<div id="linegraph" ng-controller="GpuController">
    <div ng-repeat="gpu in latestgpus">
        <div id="{{gpu.msg}}" style="width: 200px; height: 125px; margin: 50px">
            <h3>{{gpu.msg}}</h3>
            <cgmchart value="mhs5s"></cgmchart>
        </div>
    </div>
</div>

<script type="text/javascript">
/*
    window.setInterval(function() {
        $.ajax({
            url: "cgmapi/gpuvalue?gpu=0&value=accepted",
            dataType: "text"
        }).complete(function(data, status, jqxhr) {
                    $gbid('linegraph').innerHTML = '';
                    graph = new Ico.LineGraph($gbid('linegraph'), { one: eval(data.responseText)}, { markers: 'circle', colours: { one: '#990000'}, labels: ['one', 'two', 'three', 'four', 'five', 'six', 'seven', 'eight', 'nine'], meanline: true, grid: true, stroke_width: '2' });
        })}, 1000);
*/
</script>
</body>
</html>
