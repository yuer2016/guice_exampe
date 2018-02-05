#!/usr/bin/env bash

case "$1" in

  start)
    nohup bin/access-server 2551 -J-server -J-Xms4096m -J-Xmx4096m -J-XX:ErrorFile=/var/log/java/java_error_%p.log >/dev/null 2>&1 &
    echo $!>access-server.pid
  ;;
  stop)
    kill `cat access-server.pid`
    rm -rf access-server.pid
  ;;

esac

exit 0