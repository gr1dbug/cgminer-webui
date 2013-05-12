<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>cgminer ui</title>
    <script src="http://code.jquery.com/jquery.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/raphael.js" type="text/javascript" charset="utf-8"></script>
    <script src="js/ico.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="angular.js" type="text/javascript" charset="utf-8"></script>
    <script type="text/javascript">
        function $gbid(id) {
            return document.getElementById(id);
        }

        function $R(min, max) {
            var a = [], i;
            for (i = min; i < max; i++) {
                a.push(i);
            }
            return a;
        }

        function $map(min, max, method) {
            var a = [], i;
            for (i = min; i < max; i++) {
                a.push(method.apply(this, [i]));
            }
            return a;
        }

        function $random(min, max) {
            return $map(min, max, function() { return Math.random(); });
        }
    </script>


    <link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
    <style type="text/css" media="screen">
    html, body, div, ul, ol, li, dl, dt, dd, h1, h2, h3, h4, h5, h6, pre, form, p, blockquote, fieldset, input { margin: 0; padding: 0; }
    h1, h2, h3, h4, h5, h6, pre, code, address, caption, cite, code, em, strong, th { font-size: 1em; font-weight: normal; font-style: normal; }
    fieldset, img { border: none; }
    body { font-family: "Lucida Grande", Helvetica, sans-serif; font-size: 12px; background-color: #333; padding: 0; margin: 10px 5%; color: #000 }
    h1, h2 { font-size: 2em; margin: 0.5em 0; }

    p { margin: 1em 0; }
    p.example { color: #555; }

    .sparkline { display: inline-block; height: 14px; margin: 0 4px }
    .sparkline div { display: inline-block; }

    .linegraph, .linegraph_dark, .linegraph_small { width: 400px; height: 150px; background-color: #fff; margin-bottom: 20px; }
    .linegraph_dark { background-color: #999; }
    .linegraph_small { width: 400px; height: 200px; }

    .highlight { color: red; }
    </style>
</head>
<body>
<h1>aoesthuaoetnsuh: <%= gpu0.temperature %></h1>

<div id="linegraph" class="linegraph_dark"></div>

<script type="text/javascript">
    var graph = null;


    window.setInterval(function() {
        $.ajax({
            url: "cgmapi/gpuvalue?gpu=0&value=accepted",
            dataType: "text"
        }).complete(function(data, status, jqxhr) {
                    $gbid('linegraph').innerHTML = '';
                    graph = new Ico.LineGraph($gbid('linegraph'), { one: eval(data.responseText)}, { markers: 'circle', colours: { one: '#990000'}, labels: ['one', 'two', 'three', 'four', 'five', 'six', 'seven', 'eight', 'nine'], meanline: true, grid: true, stroke_width: '2' });
        })}, 1000);

</script>
</body>
</html>



<!--

<%--
  Created by IntelliJ IDEA.
  User: craig
  Date: 5/11/13
  Time: 2:09 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
  <title>cgminer web ui</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
    <style type="text/css" media="screen">
    html, body, div, ul, ol, li, dl, dt, dd, h1, h2, h3, h4, h5, h6, pre, form, p, blockquote, fieldset, input { margin: 0; padding: 0; }
    h1, h2, h3, h4, h5, h6, pre, code, address, caption, cite, code, em, strong, th { font-size: 1em; font-weight: normal; font-style: normal; }
    fieldset, img { border: none; }
    body { font-family: "Lucida Grande", Helvetica, sans-serif; font-size: 12px; background-color: #fff; padding: 0; margin: 10px 5%; color: #000 }
    h1, h2 { font-size: 2em; margin: 0.5em 0; }

    p { margin: 1em 0; }
    p.example { color: #555; }

    .sparkline { display: inline-block; height: 14px; margin: 0 4px }
    .sparkline div { display: inline-block; }

    .linegraph, .linegraph_dark, .linegraph_small { width: 600px; height: 300px; background-color: #fff; margin-bottom: 20px; }
    .linegraph_dark { background-color: #999; }
    .linegraph_small { width: 400px; height: 200px; }

    .highlight { color: red; }
    </style>

    <script src="js/prototype.js"></script>
    <script src="http://code.jquery.com/jquery.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/raphael.js"></script>
    <script src="js/ico.js"></script>
</head>
<body>
<h1>aoesthuaoetnsuh: <%= gpu0.temperature %></h1>

<div id="linegraph" class="linegraph"></div>



<script type="text/javascript">
/*
    $.ajax({
        url: "cgmapi/gputemps",
        dataType: "text"
    }).complete(function(data, status, jqxhr) {
                alert(data.responseText);
    });
*/

    var graph = new Ico.LineGraph($('linegraph'), { one: [30, 5, 1, 10, 15, 18, 20, 25, 1], two: [10, 9, 3, 30, 1, 10, 5, 33, 33], three: [5, 4, 10, 1, 30, 11, 33, 12, 22]}, { markers: 'circle', colours: { one: '#990000', two: '#009900', three: '#000099'}, labels: ['one', 'two', 'three', 'four', 'five', 'six', 'seven', 'eight', 'nine'], meanline: true, grid: true, stroke_width: '0' });

</script>
</body>
</html>

-->