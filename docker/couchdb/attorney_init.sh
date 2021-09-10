#!/bin/bash -x

progname=$(basename $0)
progdir=$(dirname $0)
progdir=${progdir:=.}

host=cfcdb
while :
do
  curl $host:5984 && break
  sleep 1
done
curl -u admin:password -X PUT http://$host:5984/_users
curl -u admin:password -X PUT http://$host:5984/outcarcerate-attorney
#curl -u admin:password -X PUT http://$host:5984/outcarcerate-attorney/_design/ddoc --data @$progdir/attorney_design.json -H'Content-Type:application/json'
