#!/bin/bash

REPOSITORY=/home/ec2-user/app
PORT=5000

echo "> [$(date)] 애플리케이션 헬스체크 시작" >> $REPOSITORY/deploy.log

# 최대 30초 동안 헬스체크 재시도
for i in {1..30}; do
  # 프로세스 실행 확인
  if ! pgrep -f 'java.*application.jar' > /dev/null; then
    echo "> [$(date)] ERROR: 애플리케이션 프로세스가 실행 중이 아닙니다." >> $REPOSITORY/deploy.log
    exit 1
  fi

  # HTTP 헬스체크 (Spring Boot Actuator가 있다면 /actuator/health, 없으면 루트 경로)
  HTTP_STATUS=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:$PORT/actuator/health 2>/dev/null)

  # Actuator가 없는 경우 루트 경로로 재시도
  if [ "$HTTP_STATUS" == "000" ] || [ "$HTTP_STATUS" == "404" ]; then
    HTTP_STATUS=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:$PORT/ 2>/dev/null)
  fi

  # 200-299 또는 300-399 응답이면 성공
  if [ "$HTTP_STATUS" -ge 200 ] && [ "$HTTP_STATUS" -lt 400 ]; then
    echo "> [$(date)] 헬스체크 성공 (HTTP $HTTP_STATUS)" >> $REPOSITORY/deploy.log
    echo "> [$(date)] 배포 완료!" >> $REPOSITORY/deploy.log
    exit 0
  fi

  echo "> [$(date)] 헬스체크 재시도 중... ($i/30) - HTTP $HTTP_STATUS" >> $REPOSITORY/deploy.log
  sleep 1
done

# 30초 동안 성공하지 못하면 실패
echo "> [$(date)] ERROR: 헬스체크 실패 - 애플리케이션이 정상적으로 시작되지 않았습니다." >> $REPOSITORY/deploy.log
echo "> [$(date)] 로그 확인: tail -n 100 $REPOSITORY/logs/application.log" >> $REPOSITORY/deploy.log
exit 1
