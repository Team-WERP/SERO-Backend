# 아키텍처 결정 사항 (Architecture Decision Records)

## ADR-001: CQRS 패턴 채택

**날짜**: 2024-12-15
**상태**: 승인됨
**결정자**: 개발팀

### 컨텍스트
- 조회 쿼리가 복잡해지고 있음
- JPA로는 최적화된 조회 쿼리 작성이 어려움
- 변경 로직은 JPA의 편리함이 필요함

### 결정
Command(변경)와 Query(조회)를 패키지 레벨에서 분리하는 CQRS 패턴 도입

### 결과
**장점**:
- 조회: MyBatis로 복잡한 SQL 최적화 가능
- 변경: JPA로 Entity 기반 편리한 개발
- 책임 분리로 유지보수성 향상

**단점**:
- 패키지 구조 복잡도 증가
- 개발자 학습 곡선

**마이그레이션 계획**:
1. 새 프로젝트 생성
2. Entity/Repository는 command/domain에 배치
3. 조회 Service/Controller를 query로 이동
4. MyBatis Mapper 작성

---

## ADR-002: 권한 시스템 - 어노테이션 기반 AOP

**날짜**: 2024-12-15
**상태**: 승인됨
**결정자**: 개발팀

### 컨텍스트
- JWT 토큰에 권한 정보 포함
- 각 API마다 권한 검증 필요
- Spring Security는 팀원이 추가 예정

### 결정
`@RequirePermission` 어노테이션 + AOP 방식 채택

### 구현
```java
@GetMapping
@RequirePermission(
    menu = "MM_MAT",
    authorities = {"AC_SYS", "AC_SAL"},
    accessType = AccessType.READ
)
public List<MaterialListResponseDTO> getMaterials() {
    // ...
}
```

### 결과
**장점**:
- 선언적 권한 관리
- 코드 가독성 향상
- Spring Security와 독립적으로 동작
- 쉽게 활성화/비활성화 가능 (SECURITY_ENABLED 플래그)

**주의사항**:
- Spring Security 추가 후 PermissionAspect와 통합 필요
- JWT 파싱 로직이 쉼표 구분 권한을 처리해야 함

---

## ADR-003: Repository 위치 - domain/repository

**날짜**: 2024-12-15
**상태**: 승인됨
**결정자**: 개발팀

### 컨텍스트
- CQRS 도입으로 command/query 분리
- Repository를 어디에 둘지 고민

### 결정
Repository(JpaRepository)는 **command/domain/repository**에 유지

### 이유
1. Repository는 Entity와 밀접하게 연관 (Domain 계층)
2. Command와 Query 모두 같은 Repository 사용
3. DDD 원칙에 따라 Domain에 위치

### 구조
```
command
├── domain
│   ├── aggregate (Entity)
│   │   ├── Material.java
│   │   └── Bom.java
│   └── repository
│       ├── MaterialRepository.java  ← 여기!
│       └── BomRepository.java
```

**DAO는 별도**:
- 복잡한 조회 전용 DAO는 `query/dao`에 배치
- MyBatis Mapper Interface

---

## ADR-004: Entity nullable 필드 타입 - Integer 사용

**날짜**: 2024-12-15
**상태**: 승인됨
**결정자**: 개발팀

### 컨텍스트
- DB 컬럼이 nullable인데 Entity 필드가 int (primitive)
- null 값 처리 불가능

### 결정
nullable 컬럼은 **Integer, Long 등 Wrapper 타입** 사용

### 변경 사항
```java
// Before
private int moq;
private int cycleTime;

// After
private Integer moq;
private Integer cycleTime;
```

### 영향받는 코드
- Material Entity
- MaterialSearchResponseDTO
- MaterialDetailResponseDTO
- MaterialListResponseDTO
- MaterialServiceImpl

---

## ADR-005: BOM 정전개 API - GET Method

**날짜**: 2024-12-15
**상태**: 승인됨
**결정자**: 개발팀

### 컨텍스트
- BOM 정전개가 POST로 구현되어 있었음
- 하지만 실제로는 조회 기능 (데이터 변경 없음)

### 결정
POST → GET으로 변경, RequestBody → RequestParam으로 변경

### 변경 내용
```java
// Before
@PostMapping("/explosion")
public BomExplosionResponseDTO calculateExplosion(@RequestBody BomExplosionRequestDTO request)

// After
@GetMapping("/explosion")
public BomExplosionResponseDTO calculateExplosion(
    @RequestParam int materialId,
    @RequestParam int quantity)
```

### 이유
- REST 원칙: 조회는 GET, 변경은 POST/PUT/DELETE
- 캐싱 가능
- URL로 직접 테스트 가능

---

## ADR-006: 재고 관리 권한 - 물류팀 전용

**날짜**: 2024-12-15
**상태**: 승인됨
**결정자**: 개발팀

### 컨텍스트
- 재고 관리는 물류팀만 접근해야 하는 페이지
- 다른 팀의 접근 제한 필요

### 결정
WarehouseStockController는 **AC_SYS, AC_WHS만** 허용

```java
@RequirePermission(
    menu = "MM_WHS",
    authorities = {"AC_SYS", "AC_WHS"},  // 물류팀만
    accessType = AccessType.READ
)
```

### 예외
- 시스템 관리자(AC_SYS)는 모든 페이지 접근 가능

---

## ADR-007: MyBatis 조회 전환 계획

**날짜**: 2024-12-15
**상태**: 계획 중
**결정자**: 개발팀

### 컨텍스트
- JPA로 복잡한 조회 쿼리 작성이 어려움
- 성능 최적화 필요

### 결정
조회(Query) 기능을 MyBatis로 전환

### 계획
1. **Mapper Interface 작성** (`query/dao`)
   ```java
   @Mapper
   public interface MaterialQueryDao {
       List<MaterialListResponseDTO> findAllMaterials(
           @Param("type") String type,
           @Param("keyword") String keyword
       );
   }
   ```

2. **XML Mapper 작성** (`resources/mapper`)
   ```xml
   <mapper namespace="com.werp.sero.material.query.dao.MaterialQueryDao">
       <select id="findAllMaterials" resultType="MaterialListResponseDTO">
           SELECT id, name, material_code, ...
           FROM material
           WHERE 1=1
           <if test="type != null">
               AND type = #{type}
           </if>
       </select>
   </mapper>
   ```

3. **Service 수정**
   - JpaRepository → MyBatis DAO 사용

### 예상 효과
- 복잡한 JOIN 쿼리 최적화
- N+1 문제 해결
- 동적 쿼리 작성 용이

---

## ADR-008: 권한 Entity 위치 - employee domain

**날짜**: 2024-12-15
**상태**: 승인됨
**결정자**: 개발팀

### 컨텍스트
- Menu, Permission, EmployeePermission 등 권한 Entity
- common/security vs employee 어디에 둘지 고민

### 결정
권한 Entity는 **employee/command/domain/aggregate**에 유지

### 이유
1. 권한은 인사/조직 도메인의 핵심 개념
2. Employee와 EmployeePermission은 밀접한 aggregate 관계
3. DDD 원칙: 데이터는 도메인이 소유

### 분리
- **employee domain**: 권한 데이터 관리 (Entity, Repository)
- **common/security**: 권한 검증 로직 (Annotation, AOP)

---

## 향후 고려사항

### 1. 이벤트 기반 아키텍처
- Domain Event 도입 검토
- 예: 재고 변경 시 알림 이벤트

### 2. 캐싱 전략
- 조회 빈도가 높은 데이터 캐싱
- Redis 도입 검토

### 3. 페이징 표준화
- 모든 목록 조회 API에 페이징 적용
- PageRequest, PageResponse 표준화

### 4. API 버저닝
- /api/v1, /api/v2 구조 검토
- 하위 호환성 유지 전략
