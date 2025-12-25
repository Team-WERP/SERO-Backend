package com.werp.sero.notification.command.application.service;

import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.werp.sero.notification.command.domain.repository.SseEmitterRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SseCommandServiceImpl implements SseCommandService{
    
    private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60; // 60분
    private final SseEmitterRepository emitterRepository;
    

    @Override
    @Transactional
    public SseEmitter createEmitter(int employeeId) {
        
        String emitterId = employeeId + ":" + System.currentTimeMillis();
        SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);  

        emitterRepository.save(emitterId, emitter);

        // 연결 종료 시 삭제
        emitter.onCompletion(() -> emitterRepository.deleteById(emitterId));
        emitter.onTimeout(() -> emitterRepository.deleteById(emitterId));

        // 연결 직후 더미 이벤트 전송 (503 에러 방지)
        try {
            // 1. "connect" 라는 이름의 이벤트를 생성 해서 "Connected" 라는 메시지를 보낸다.
            // 2. 이렇게 되면 HTTP 응답 본문에 데이터가 실리면서 503 에러가 안뜸! (503 에러 = Service Unavailable)
            emitter.send(SseEmitter.event()
                    .name("connect")
                    .data("Connected"));
        } catch (IOException e) {
            // 만약 메시지를 보냈는데 연결이 끊겨있다면 해당 Emitter를 삭제한다.

            emitterRepository.deleteById(emitterId);
        }


        return emitter;
    }

    @Override
    @Transactional
    public void sendToUser(int employeeId, Object data) {
        String employeeIdStr = String.valueOf(employeeId);
        Map<String, SseEmitter> emitters =emitterRepository.findAllByEmployeeId(employeeIdStr);
    
        emitters.forEach((emitterId, emitter) -> {  
            try {
                emitter.send(SseEmitter.event()
                        .name("notification")
                        .data(data));
        } catch (IOException e) {
            emitterRepository.deleteById(emitterId);
            log.warn("SSE 전송 실패 (연결 끊김): emitterId={}", emitterId);
        }
    });

    }
    
}
