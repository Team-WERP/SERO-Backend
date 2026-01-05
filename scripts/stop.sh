#!/bin/bash

REPOSITORY=/home/ec2-user/app

echo "> [$(date)] 기존 애플리케이션 종료 시작" >> $REPOSITORY/deploy.log

# 실행 중인 Spring Boot 애플리케이션 찾기
CURRENT_PID=$(pgrep -f 'java.*application.jar')

if [ -z "$CURRENT_PID" ]; then
  echo "> [$(date)] 현재 실행 중인 애플리케이션이 없습니다." >> $REPOSITORY/deploy.log
else
  echo "> [$(date)] 실행 중인 애플리케이션 PID: $CURRENT_PID" >> $REPOSITORY/deploy.log
  echo "> [$(date)] 애플리케이션 종료 중..." >> $REPOSITORY/deploy.log

  # Graceful shutdown (SIGTERM)
  kill -15 $CURRENT_PID

  # 프로세스가 종료될 때까지 최대 30초 대기
  for i in {1..30}; do
    if ! ps -p $CURRENT_PID > /dev/null 2>&1; then
      echo "> [$(date)] 애플리케이션이 정상적으로 종료되었습니다." >> $REPOSITORY/deploy.log
      exit 0
    fi
    sleep 1
  done

  # 30초 후에도 종료되지 않으면 강제 종료
  if ps -p $CURRENT_PID > /dev/null 2>&1; then
    echo "> [$(date)] 강제 종료를 시도합니다." >> $REPOSITORY/deploy.log
    kill -9 $CURRENT_PID
    sleep 2
  fi

  echo "> [$(date)] 애플리케이션 종료 완료" >> $REPOSITORY/deploy.log
fi

exit 0
