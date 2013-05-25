cgminer-webui
=============

A new UI for cgminer, with configurable growl notifications built in.

This is prototype quality. Limitations include that it only works with GPU (no CPU or ASIC), it's read only - you can't
change any of the stuff like intensity, etc., you have to install it on each miner - it doesn't consolidate all your
miners into one UI, and it could use some testing. No security guarantees! I would like to fix these issues plus add
more analytics and more customizable notifications.

To use,
* Install cgminer, run it with you usual parameters plus: --api-listen --api-allow W:127.0.0.1
* Install Java 7
* Install [growl](http://www.growlforwindows.com/gfw/)
* Optionally, you can install Prowl on your phone and set up forwarding in growl to get the notifications on your phone
* Get [this file](https://dl.dropboxusercontent.com/u/8425729/mining-webui-jetty.zip), unzip, open command prompt, ensure java 7
is in your path, change do directory where you unzipped it, run "java -jar start.jar". If you get a windows firewall
thing (you will if it's enabled, because this is just a distribution of jetty with the grails web app already included
in the correct place), hit allow
* Open browser to http://localhost:8080/cgmapi (probably chrome or firefox only, not tested with IE)

[screenshots](http://imgur.com/a/0JpDX)
