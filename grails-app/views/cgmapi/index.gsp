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
            <div style="float: right"><a href="cgmapi#/ui"><strong style="margin-left: 50px" class="brand">web ui</strong></a></div>
            <div style="float: right"><a href="cgmapi#/notifications"><strong style="margin-left: 50px" class="brand">growl</strong></a></div>
        </div>
    </div>

<div ng-view></div>

</body>
</html>
