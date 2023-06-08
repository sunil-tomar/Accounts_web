#!/bin/bash

WRAPPER_FILE_NAME="accounts.sh"
BOOT_JAR_NAME="accounts"
SPRING_BOOT_JAR="../bin/${BOOT_JAR_NAME}-*.jar"
BG_PROCESS=${BG_PROCESS:-' &'}

#It will provide the path up to accounts folder.
PARENT_PATH=$( cd -- "$(dirname "$0")"; pwd -P )
#It will provide the path for tmp folder to store tomcate tmp files.
ROOT_PATH_FOR_TMP="$( dirname ${PARENT_PATH})/appdata/tmp"

JAVA_OPTS="-Djava.io.tmpdir=${ROOT_PATH_FOR_TMP}"

PATH=${JAVA_HOME}/bin:${PATH};
SHUTDOWN_WAIT=20; # before issuing kill -9 on process.

#Here we execute setenv if exist for taking environment variables.
if [ -f ../conf/setenv.sh ]; then
    . ../conf/setenv.sh
fi

export PATH JAVA_HOME

# Try to get PID of spring jar/war
PID=$(ps ax | grep java | grep ${SPRING_BOOT_JAR} | awk '{print $1}')
export PID


# Function: start
start() {
  pid=${PID};
  if [ -n "${pid}" ] ;  then
    echo "accounts is already running (pid: ${pid})";
  else
    # Start screener ms
    echo "starting ${BOOT_JAR_NAME}"

    nohup sh -c "java -jar ${JAVA_OPTS} ${SPRING_BOOT_JAR} --spring.config.location=file:../conf/  --logging.config=file:../conf/logback-spring.xml ${BG_PROCESS}"

    echo "${BOOT_JAR_NAME} is started."
   fi;
  return 0;
}

# Function: stop
stop() {
  pid=${PID}
  if [ -n "${pid}" ] ; then

    kill -TERM $pid

    echo "stopping ${BOOT_JAR_NAME}";

    kwait=${SHUTDOWN_WAIT};

    count=0;
    while kill -0 ${pid} 2>/dev/null && [ ${count} -le ${kwait} ]; do
      printf ".";
      sleep 1;
      (( count++ ));
    done;

    ## remove the temp directory created during startup
    rm  -rf ${ROOT_PATH_FOR_TMP}/*
    echo  "${BOOT_JAR_NAME} is stopped";

    if [ ${count} -gt ${kwait} ]; then
      printf "process is still running after %d seconds, killing process" \
    ${SHUTDOWN_WAIT};
      kill ${pid};
      sleep 3;

      # if it's still running use kill -9
      #
      if [ kill -0 ${pid} 2>/dev/null ]; then
        echo "process is still running, using kill -9";
        kill -9 ${pid}
        sleep 3;
       fi;
     fi;

  else
      echo "${BOOT_JAR_NAME} is not running";
  fi;

  return 0;
}

header(){
  echo "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"
  echo "                    ACCOUNTS                             "
  echo "                                                                        "

}

footer(){
  USER=$(whoami)
  DATE=$(date +%Y%m%d:%H:%M:%S)
  echo "                                                                        "
  echo "Action performed by ${USER} at ${DATE}"
  echo "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"
}

status(){
 if [ -z "${PID}" ]; then
   echo "${BOOT_JAR_NAME} is not running"
   exit 1
 fi
 echo `curl http://localhost:10010/accounts/actuator/health`
}



# Main Code

case $1 in
  start)
    header
    start
    footer
    ;;
  stop)
    header
    stop
    footer
    ;;
  restart)
    header
    stop
    sleep 2
    start
    footer
    ;;
  status)
    header
    status
    footer
    ;;
   *)
    echo "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"
    echo "                                                                        "
    echo "!! ERROR : Script usage is ${WRAPPER_FILE_NAME} start | stop | restart | status"
    echo "                                                                        "
    echo "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"
  ;;


esac

exit 0;