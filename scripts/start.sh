#!/bin/bash

REPOSITORY=/home/ec2-user/app

echo "> [$(date)] 애플리케이션 시작 준비" >> $REPOSITORY/deploy.log

# JAR 파일 찾기 (plain.jar 제외)
JAR_FILE=$(find $REPOSITORY/build/libs -name "*.jar" ! -name "*plain*" | head -n 1)

if [ -z "$JAR_FILE" ]; then
  echo "> [$(date)] ERROR: JAR 파일을 찾을 수 없습니다." >> $REPOSITORY/deploy.log
  exit 1
fi

JAR_NAME=$(basename $JAR_FILE)
echo "> [$(date)] 실행할 JAR: $JAR_NAME" >> $REPOSITORY/deploy.log

# 로그 디렉토리 생성
mkdir -p $REPOSITORY/logs

# 애플리케이션 시작
echo "> [$(date)] 애플리케이션 시작..." >> $REPOSITORY/deploy.log

nohup java -jar \
  -Dserver.port=5000 \
  $JAR_FILE \
  > $REPOSITORY/logs/application.log 2>&1 &

NEW_PID=$!
echo "> [$(date)] 애플리케이션 시작됨 (PID: $NEW_PID)" >> $REPOSITORY/deploy.log

# 프로세스 시작 확인 (최대 10초 대기)
sleep 3

if ps -p $NEW_PID > /dev/null 2>&1; then
  echo "> [$(date)] 애플리케이션이 정상적으로 시작되었습니다." >> $REPOSITORY/deploy.log
  echo $NEW_PID > $REPOSITORY/app.pid
  exit 0
else
  echo "> [$(date)] ERROR: 애플리케이션 시작 실패" >> $REPOSITORY/deploy.log
  echo "> [$(date)] 로그 확인: tail -n 50 $REPOSITORY/logs/application.log" >> $REPOSITORY/deploy.log
  exit 1
fi
