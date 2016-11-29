#!/bin/sh
git pull origin master
#sleep 5
mvn clean install

APP_NAME=light-comes

tpid=`ps -ef|grep $APP_NAME|grep -v grep|grep -v kill|awk '{print $2}'`
if [ ${tpid} ]; then
    echo 'Stop Process...'
    kill -15 $tpid
fi
sleep 3
tpid=`ps -ef|grep $APP_NAME|grep -v grep|grep -v kill|awk '{print $2}'`
if [ ${tpid} ]; then
    echo 'Kill Process!'
    kill -9 $tpid
else
    echo 'Stop Success!'
fi

rm -f tpid
nohup java -jar target/light-comes-1.0-SNAPSHOT.jar > light-comes.log 2>&1 &
echo $! > tpid
echo Start Success!
