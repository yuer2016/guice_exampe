#!/usr/bin/env bash

BASE_DIR=""
JAVA_BIN=""
JAVA_OPTS=""
CLASSPATH=""
CONFIG_DIR=""
LOG_DIR=""
PROFILE_TYPE=""
PID_FILE="consumer.pid"
PID_FILE_STORE_PATH=""
JAR_FILE_TO_EXECUTE="alert-consumer.jar"
SILENT_MODE="false"

echo ""
echo "#######################################################################"
echo "#"
echo "#  OneAPM \"Alert System - Consumer Module\" Bootstrap Script"
echo "#  Use \"-h\" to get detailed usage introduction"
echo "#"
echo "#  OneAPM Alert System Team      Copyright © 2008-2017"
echo "#"
echo "#######################################################################"
echo ""

while [ $# -gt 0 ]; do
  COMMAND=$1
  case $COMMAND in
    -c)
      shift
      CONFIG_DIR=$1
      shift
      ;;
    --config-dir)
      shift
      CONFIG_DIR=$1
      shift
      ;;
    -p)
      shift
      PROFILE_TYPE=$1
      shift
      ;;
    --profile)
      shift
      PROFILE_TYPE=$1
      shift
      ;;
    -l)
      shift
      LOG_DIR=$1
      shift
      ;;
    --log-dir)
      shift
      LOG_DIR=$1
      shift
      ;;
    -s)
      SILENT_MODE="true"
      shift
      ;;
    --silent)
      SILENT_MODE="true"
      shift
      ;;
    -h)
      print_usage
      shift
      ;;
    --help)
      print_usage
      shift
      ;;
    *)
      break
      ;;
  esac
done

function print_usage() {
  echo "start-alert-consumer.sh [OPTION] [PARAMETER]"
  echo -e "\t-h, --help\tShow this tips"
  echo -e "\t-c, --config [config dir]\tDefine configuration directory"
  echo -e "\t-s, --silent\tSilent mode, all configuration would be default, just for docker integration"
  echo -e "\t-p, --profile\tSpring boot profile define"
  echo -e "\t-l, --log-dir\tSet log directory, other wise the default log directory would be used"
  exit 1
}

function generate_basedir() {
  # No relative path
  cd "$(dirname $0)/.."
  BASE_DIR=`pwd`
}

function generate_silent_config() {
  if [ "$SILENT_MODE" = "true" ]; then
    PROFILE_TYPE="dev"
  fi
}

function check_config_dir() {
  if [ "x$CONFIG_DIR" = "x" ]; then
    CONFIG_DIR="${BASE_DIR}/config"
  fi
}

# check logs path
function check_default_logs_dir() {
  if [ ! -d "${BASE_DIR}/logs"  ]; then
    mkdir ${BASE_DIR}/logs
  fi
}

function custom_log_config() {
  if [ "x$LOG_DIR" != "x" ]; then
    if [ ! -d "${LOG_DIR}"  ]; then
      mkdir ${LOG_DIR}
    fi

    check_config_dir

    if [ ! -f "${CONFIG_DIR}/logback.xml"  ]; then
      echo "No logback placed in ${CONFIG_DIR}!"
      exit 1
    fi
    # replace the / to \/
    LOG_DIR=${LOG_DIR//\//\\/}
    sed -i "s/<property name=\"LOG_HOME\" value=\"\+\"\/>/<property name=\"LOG_HOME\" value=\"${LOG_DIR}\"\/>/g" ${CONFIG_DIR}/logback.xml
  fi
}

function generate_java_opts() {
  if [ "x$BASE_DIR" = "x" ]; then
    generate_basedir
  fi
  check_default_logs_dir

  # TODO auto resize heap size, or documented it
  JAVA_OPTS="$JAVA_OPTS -server"
  JAVA_OPTS="$JAVA_OPTS -Xms2g -Xmx4g"
  JAVA_OPTS="$JAVA_OPTS -XX:+UseG1GC -XX:MaxGCPauseMillis=200 -XX:InitiatingHeapOccupancyPercent=35 -XX:+DisableExplicitGC"
  JAVA_OPTS="$JAVA_OPTS -XX:+UseTLAB -XX:+ResizeTLAB"

  # GC LOG
  JAVA_OPTS="$JAVA_OPTS \
  -verbose:gc \
  -Xloggc:${BASE_DIR}/logs/gc.$(date '+%Y%m%d_%H%M%S').log \
  -XX:+PrintGCDetails \
  -XX:+PrintGCTimeStamps \
  -XX:+PrintGCDateStamps \
  -XX:+PrintHeapAtGC \
  -XX:+PrintPromotionFailure \
  -XX:+PrintClassHistogram \
  -XX:+PrintTenuringDistribution \
  -XX:+PrintGCApplicationStoppedTime \
  -XX:+UseGCLogFileRotation \
  -XX:NumberOfGCLogFiles=10 \
  -XX:GCLogFileSize=10M"

  JAVA_OPTS="$JAVA_OPTS -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=${BASE_DIR}/logs"
  JAVA_OPTS="$JAVA_OPTS -XX:ErrorFile=${BASE_DIR}/logs/err.log"

  JAVA_OPTS="$JAVA_OPTS -Dfile.encoding=UTF-8"
  JAVA_OPTS="$JAVA_OPTS -Djava.awt.headless=true"
  JAVA_OPTS="$JAVA_OPTS -Dsun.net.inetaddr.ttl=0"
  JAVA_OPTS="$JAVA_OPTS -Djava.net.preferIPv4Stack=true"
  JAVA_OPTS="$JAVA_OPTS -Djava.security.egd=file:/dev/./urandom"
}

function select_app_profile() {
  echo "Please select the profile to execute application:"
  echo "1. For production environment"
  echo "2. For test environment"
  read -p "Just enter the number of the item (Default: 2):" targetEnvironment

  case ${targetEnvironment} in
    1)
    PROFILE_TYPE="prod"
    ;;
    2)
    PROFILE_TYPE="dev"
    ;;
    *)
    PROFILE_TYPE="dev"
    ;;
  esac
  echo -e "The Profile type you choose is ${PROFILE_TYPE}\n####################################"

  get_char() {
    SAVEDSTTY=`stty -g`
    stty -echo
    stty cbreak
    dd if=/dev/tty bs=1 count=1 2> /dev/null
    stty -raw
    stty echo
    stty $SAVEDSTTY
  }
  echo ""
  echo "Press any key to start...or Press Ctrl+C to cancel"
  char=`get_char`
}

# which java to use
function select_java() {
  if [ -z "${JAVA_HOME}" ]; then
    echo -e "No \033[41;37m\$JAVA_HOME\033[0m defined, would try to find java in your \033[41;37m\$PATH\033[0m.\nIf this fails, try defining \033[41;37m\$JAVA_HOME\033[0m"
    command -v java >/dev/null 2>&1 || { echo >&2 "\033[41;37mRequire java but couldn't find it from \$PATH. Aborting.\033[0m"; exit 1; }
    JAVA_BIN="java"
  else
    JAVA_BIN="${JAVA_HOME}/bin/java"
  fi
}

function ensure_pid_path() {
  if [ "x$PID_FILE_STORE_PATH" = "x" ]; then
    PID_FILE_STORE_PATH="${BASE_DIR}/bin"
  fi
}

function check_pid_file() {
  ensure_pid_path
  if [ -f "${PID_FILE_STORE_PATH}/${PID_FILE}"  ]; then
    if ps -p `cat "${PID_FILE_STORE_PATH}/${PID_FILE}"` > /dev/null ; then
      echo "Application is running! Shutdown application before start new application."
      exit 1
    fi
  fi
}

function generate_classpath() {
  for file in ${BASE_DIR}/lib/*.jar;
  do
    CLASSPATH=${CLASSPATH}:${file}
  done
}

function bootstrap() {
  check_pid_file
  generate_classpath

  exec ${JAVA_BIN} ${JAVA_OPTS} -cp ${CLASSPATH} -Dlogging.config=${CONFIG_DIR}/logback.xml -Dspring.profiles.active=${PROFILE_TYPE} -jar ${BASE_DIR}/lib/${JAR_FILE_TO_EXECUTE} --spring.config.location=file:${CONFIG_DIR} <&- &

  retval=$?
  PID=$!
  [ ${retval} -eq 0 ] || exit ${retval}
  sleep 7
  if ! ps -p ${PID} > /dev/null ; then
      echo "Failed to start application."
    exit 1
  fi

  printf "%d" ${PID} > "${PID_FILE_STORE_PATH}/${PID_FILE}"
  echo "Successfully launched Application, PID: ${PID}"
  exit 0
}



## Main Cycle Logic
select_java
generate_java_opts
custom_log_config
generate_silent_config
# Need profile select ?
if [ "x$PROFILE_TYPE" = "x" ]; then
  select_app_profile
fi
bootstrap