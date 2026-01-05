#!/bin/bash

REPOSITORY=/home/ec2-user/app

echo "> [$(date)] 파일 권한 설정 시작" >> $REPOSITORY/deploy.log

# scripts 폴더의 모든 .sh 파일에 실행 권한 부여
chmod +x $REPOSITORY/scripts/*.sh

# jar 파일 찾기
JAR_FILE=$(find $REPOSITORY/build/libs -name "*.jar" ! -name "*plain*" | head -n 1)

if [ -n "$JAR_FILE" ]; then
  chmod +x $JAR_FILE
  echo "> [$(date)] JAR 파일 실행 권한 설정: $JAR_FILE" >> $REPOSITORY/deploy.log
else
  echo "> [$(date)] WARNING: JAR 파일을 찾을 수 없습니다." >> $REPOSITORY/deploy.log
fi

echo "> [$(date)] 파일 권한 설정 완료" >> $REPOSITORY/deploy.log

exit 0
