package com.werp.sero.notification.command.domain.repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;


    /* 
        SseEmitter 란? 서버에서 클라이언트로 데이터를 보내는 통로.
        1. 연결(Subscribe): 사용자 로그인 시 브라우저가 서버에 알림 받을 준비 완료 요청, 이때 서버가 SseEmitter 객체를 생성해서 리턴함.
        2. 유지: 서버는 이 SseEmitter을 레포시토리에 보관하고 연결을 유지함.
        3. 발행: 어떤 이벤트가 발생하면 서버는 보관 중인 SseEmitter을 꺼내서 .send() 메서드로 알림 데이터를 쏨.
        4. 수신: 사용자의 브라우저는 연결된 통로를 통해 실시간으로 알림을 받아 화면에 띄움.
    */ 
@Repository
public class SseEmitterRepository {


    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();

    // SseEmitter을 레포에 보관하는 메서드
    public void save(String emitterId, SseEmitter emitter) {
        emitters.put(emitterId, emitter);
    }

    public Map<String, SseEmitter> findAllByEmployeeId(String employeeId) {
        // employeeId로 시작하는 모든 emitter 찾기 (한 유저가 여러 탭을 열었을때도 연결을 가져오기 위해서)
        return emitters.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(employeeId + ":"))
                .collect(java.util.stream.Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }


    public void deleteById(String emitterId) {
        emitters.remove(emitterId);
    }


    
}
