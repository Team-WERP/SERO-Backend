# 구현 노트

## 작업 완료 목록

### 1. 권한 시스템 (2024-12-15)

#### 생성된 파일
- `common/security/AccessType.java` - READ/WRITE enum
- `common/security/RequirePermission.java` - 권한 체크 어노테이션
- `common/security/PermissionAspect.java` - AOP 권한 검증 로직
- `common/security/exception/UnauthorizedException.java`
- `common/security/exception/PermissionDeniedException.java`

#### 적용된 컨트롤러
- MaterialController
- BomCalculationController
- ClientController
- EmployeeController
- WarehouseStockController (물류팀 전용)

#### 주요 특징
- SECURITY_ENABLED = false (현재 비활성화)
- JWT 토큰 쉼표 구분 파싱 지원 ("AC_SYS,AC_SAL")
- Spring Security 추가 대기 중

---

### 2. API 변경 (2024-12-15)

#### BOM 정전개 API
**변경 전**:
```java
@PostMapping("/bom/explosion")
public BomExplosionResponseDTO calculateExplosion(@RequestBody BomExplosionRequestDTO request)
```

**변경 후**:
```java
@GetMapping("/bom/explosion")
public BomExplosionResponseDTO calculateExplosion(
    @RequestParam int materialId,
    @RequestParam int quantity)
```

**테스트 업데이트**:
- BomCalculationControllerTest 수정
- POST → GET 변경
- RequestBody → RequestParam 변경

---

### 3. Entity 타입 변경 (2024-12-15)

#### Material Entity
**변경 사항**:
```java
// Before
private int moq;
private int cycleTime;
private int conversionRate;

// After
private Integer moq;
private Integer cycleTime;
private Integer conversionRate;
```

#### 영향받은 파일
1. **MaterialSearchResponseDTO**
   - `rawMaterialCount`: int → Integer
   - null 체크 추가

2. **MaterialDetailResponseDTO**
   - `safetyStock`: Integer → int (non-nullable)

3. **MaterialListResponseDTO**
   - `safetyStock`: Integer → int (non-nullable)

4. **Material.update()**
   - nullable 필드 default 값 제거

5. **MaterialServiceImpl.createMaterial()**
   - nullable 필드 default 값 제거
   - rawMaterialCount: 0 → null

---

### 4. Swagger 문서화 (2024-12-15)

#### MaterialController
- 모든 메서드에 Javadoc 추가
- API 엔드포인트 예시 포함
- 기존 Swagger 어노테이션 유지

#### WarehouseStockController
- @Tag, @Operation, @Parameter 추가
- 물류팀 전용 명시
- 상세한 파라미터 설명

#### ClientController & EmployeeController
- @Operation, @Parameter 추가
- API 설명 및 예시 작성

---

### 5. 테스트 코드 (2024-12-15)

#### 추가된 테스트
- **PermissionAspectTest**: 권한 시스템 테스트 (3개)
- **ClientControllerTest**: 고객사 API 테스트 (3개)
- **EmployeeServiceImplTest**: 조직도 서비스 테스트 (2개)

#### 수정된 테스트
- **BomCalculationControllerTest**: GET 방식으로 변경
- **WarehouseStockControllerTest**: URL 수정 (/warehouse/stocks)

#### 테스트 통과
- 총 77개 테스트 모두 통과

---

## 알려진 이슈 및 해결

### 1. UnnecessaryStubbingException
**문제**: Mock stub이 모든 테스트에서 사용되지 않음

**해결**:
```java
// Before
when(department.getId()).thenReturn(id);

// After
lenient().when(department.getId()).thenReturn(id);
```

### 2. Boolean JSON 직렬화
**문제**: `isDefault` 필드가 JSON에서 `default`로 직렬화됨

**해결**:
```java
// 테스트 코드 수정
jsonPath("$.isDefault").value(true)  // Before
jsonPath("$.default").value(true)    // After
```

### 3. Entity Builder 문제
**문제**: Client, Employee Entity에 @Builder 없음

**해결**:
```java
// Before
Client client = Client.builder().build();

// After
Client client = mock(Client.class);
when(client.getId()).thenReturn(1);
```

---

## 코드 품질 개선

### 1. 일관된 DTO 패턴
모든 응답 DTO에 `from()` 정적 팩토리 메서드 적용:
```java
public static MaterialListResponseDTO from(Material material) {
    return MaterialListResponseDTO.builder()
        .id(material.getId())
        .name(material.getName())
        // ...
        .build();
}
```

### 2. Null 안정성
- nullable 필드는 Wrapper 타입 사용
- DTO 변환 시 null 체크
- Optional 사용 검토 (향후)

### 3. 주석 규칙
```java
/**
 * 자재 목록 조회
 *
 * GET /materials?type=MAT_FG&keyword=브레이크
 *
 * @param type 자재 유형 (MAT_FG, MAT_RM, null: 전체)
 * @param keyword 검색어 (자재명 또는 자재코드)
 * @return 자재 목록 (ID, 이름, 코드, 규격, 타입, 상태, 단위, 가격, 안전재고 등)
 */
```

---

## 성능 최적화

### 현재 상태
- JPA fetch join 일부 적용
- N+1 문제 주의 필요

### 향후 계획
1. **조회 쿼리 MyBatis 전환**
   - 복잡한 JOIN 쿼리 최적화
   - 동적 쿼리 개선

2. **인덱스 추가**
   - materialCode (unique)
   - type + status 복합 인덱스
   - 검색 쿼리 성능 향상

3. **캐싱**
   - 공통코드 캐싱
   - 자주 조회되는 데이터 Redis 캐싱

---

## 보안 체크리스트

### 완료
- [x] 권한 어노테이션 모든 API에 적용
- [x] JWT 토큰 파싱 로직 구현
- [x] 예외 처리 (UnauthorizedException, PermissionDeniedException)
- [x] Swagger API 문서화

### 대기 중
- [ ] Spring Security 통합 (팀원 작업)
- [ ] JWT 토큰 검증 활성화
- [ ] HTTPS 설정 (운영 환경)
- [ ] SQL Injection 방지 (MyBatis 적용 시)

---

## 마이그레이션 체크리스트

### 새 프로젝트로 이동 시

#### 1단계: 프로젝트 생성
- [ ] Spring Initializr로 새 프로젝트 생성
- [ ] build.gradle 의존성 복사
- [ ] application.yml 설정 복사

#### 2단계: 패키지 구조 생성
- [ ] common 패키지 생성
- [ ] 도메인별 command/query 구조 생성
- [ ] resources/mapper 폴더 생성

#### 3단계: 공통 모듈 이동
- [ ] common/error 이동
- [ ] common/security 이동
- [ ] config 이동
- [ ] SeroApplication.java 이동

#### 4단계: 도메인별 이동 (material 예시)
- [ ] Entity → command/domain/aggregate
- [ ] Repository → command/domain/repository
- [ ] 수정 Controller/Service → command/application
- [ ] 조회 Controller/Service → query
- [ ] DTO 분리 (요청 DTO → command, 응답 DTO → query)
- [ ] Exception 이동

#### 5단계: MyBatis 설정
- [ ] MyBatis dependency 추가
- [ ] Mapper DAO 인터페이스 작성 (query/dao)
- [ ] XML Mapper 작성 (resources/mapper)
- [ ] Service 수정 (JPA → MyBatis)

#### 6단계: 테스트
- [ ] 테스트 코드 이동
- [ ] 패키지 경로 수정
- [ ] 모든 테스트 실행 및 통과 확인

#### 7단계: .claude 폴더
- [ ] .claude 폴더 복사
- [ ] 경로 참조 업데이트
- [ ] project-context.md 갱신

---

## 문제 해결 가이드

### 무한 로딩 문제
**증상**: 애플리케이션 실행 시 무한 로딩

**체크 항목**:
1. 데이터베이스 연결 확인
2. Entity 순환 참조 확인
3. application.yml 설정 확인
4. 로그 확인 (`./gradlew bootRun --info`)

### 권한 오류
**증상**: 권한이 있는데도 접근 거부

**체크 항목**:
1. SECURITY_ENABLED 플래그 확인
2. JWT 토큰 형식 확인 (쉼표 구분)
3. authorities 배열 확인
4. PermissionAspect 로그 확인

### 테스트 실패
**증상**: 특정 테스트가 실패

**체크 항목**:
1. Mock 설정 확인 (lenient 사용)
2. JSON path 확인 (isDefault vs default)
3. URL 매핑 확인
4. DTO 필드 타입 확인 (int vs Integer)

---

## 참고 자료

### 내부 문서
- [project-context.md](.claude/project-context.md) - 프로젝트 전체 개요
- [architecture-decisions.md](.claude/architecture-decisions.md) - 아키텍처 결정 사항

### 외부 링크
- Spring Boot: https://spring.io/projects/spring-boot
- MyBatis: https://mybatis.org/mybatis-3/
- CQRS Pattern: https://martinfowler.com/bliki/CQRS.html
- DDD: https://martinfowler.com/tags/domain%20driven%20design.html
