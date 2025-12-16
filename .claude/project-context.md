# SERO-Backend 프로젝트 컨텍스트

## 프로젝트 개요
- **프로젝트명**: SERO (Smart Enterprise Resource Optimizer)
- **기술 스택**: Spring Boot 3.5.8, JPA/Hibernate, Java 17
- **아키텍처**: CQRS 패턴 + DDD (Domain-Driven Design)
- **데이터베이스**: MySQL
- **조회**: MyBatis (전환 예정)
- **변경**: JPA

## 현재 패키지 구조 (기존)
```
com.werp.sero
├── common
│   ├── error (예외 처리)
│   └── security (권한 검증)
├── config (Swagger 설정)
├── material (자재 관리)
│   ├── controller
│   ├── dto
│   ├── entity
│   ├── repository
│   ├── service
│   └── exception
├── employee (인사/조직)
├── client (고객사)
└── warehouse (창고/재고)
```

## 새 패키지 구조 (이전 예정)
```
com.werp.sero
├── SeroApplication.java
├── common
│   ├── error (예외 처리)
│   │   ├── ErrorCode.java
│   │   ├── ErrorResponse.java
│   │   ├── GlobalExceptionHandler.java
│   │   └── exception
│   │       ├── BusinessException.java
│   │       └── SystemException.java
│   └── security (권한 검증 - AOP)
│       ├── AccessType.java
│       ├── RequirePermission.java
│       ├── PermissionAspect.java
│       └── exception
│           ├── UnauthorizedException.java
│           └── PermissionDeniedException.java
├── config
│   └── SwaggerConfig.java
├── [도메인명]
│   ├── command (데이터 변경: Create, Update, Delete)
│   │   ├── application
│   │   │   ├── controller
│   │   │   ├── dto (요청 DTO)
│   │   │   └── service (JPA Repository 사용)
│   │   ├── domain
│   │   │   ├── aggregate (JPA Entity)
│   │   │   └── repository (JpaRepository)
│   │   └── infrastructure
│   │       └── service
│   └── query (데이터 조회: Read)
│       ├── controller
│       ├── dao (MyBatis Mapper Interface)
│       ├── dto (응답 DTO)
│       └── service (MyBatis Mapper 사용)
└── resources
    └── mapper (MyBatis XML)
```

## 주요 도메인

### 1. Material (자재 관리)
- **Entity**: Material, Bom
- **기능**:
  - 자재 CRUD (현재 조회만 구현, 수정 기능만 Command로 분리 예정)
  - BOM 정전개/역전개 계산
  - 자재 검색
- **권한**: AC_SYS, AC_SAL, AC_PRO, AC_WHS
- **주요 변경사항**:
  - BOM 정전개 API: POST → GET으로 변경
  - Material nullable 필드: int → Integer로 타입 변경
  - 조회 API는 MyBatis로 전환 예정

### 2. Employee (인사/조직)
- **Entity**: Employee, Department, Menu, Permission, EmployeePermission, MenuPermission
- **기능**:
  - 조직도 조회
  - 부서별 사원 목록 조회
- **권한**: AC_SYS, AC_SAL, AC_PRO, AC_WHS (모든 팀 조회 가능)
- **위치**: 권한 Entity는 employee domain에 유지

### 3. Client (고객사 관리)
- **Entity**: Client, ClientAddress, ClientEmployee, ClientItem, ClientItemPriceHistory
- **기능**:
  - 고객사 기본 정보 조회
  - 고객사 주소 목록 조회
  - 고객사 담당자 목록 조회
- **권한**: AC_SYS, AC_SAL, AC_PRO, AC_WHS

### 4. Warehouse (창고/재고 관리)
- **Entity**: Warehouse, WarehouseStock, WarehouseStockHistory
- **기능**:
  - 창고별 재고 조회 (필터링: 창고ID, 자재타입, 재고상태, 키워드)
  - 재고 상세 조회 (수량 변경 이력 포함)
- **권한**: AC_SYS, AC_WHS (물류팀 전용)

## 권한 시스템

### 권한 코드
- **AC_SYS**: 시스템 관리자 (모든 권한)
- **AC_SAL**: 영업팀
- **AC_PRO**: 생산팀
- **AC_WHS**: 물류팀
- **AC_CLI**: 고객사

### AccessType
- **READ**: 조회 권한 (GET)
- **WRITE**: 변경 권한 (POST, PUT, DELETE)

### JWT 토큰 구조
```json
{
  "auth": "AC_SYS,AC_SAL"  // 쉼표로 구분된 권한 목록
}
```

### 사용 예시
```java
@GetMapping
@RequirePermission(
    menu = "MM_MAT",
    authorities = {"AC_SYS", "AC_SAL", "AC_PRO", "AC_WHS"},
    accessType = AccessType.READ
)
public List<MaterialListResponseDTO> getMaterials(...) {
    // ...
}
```

### 보안 설정
- `PermissionAspect.SECURITY_ENABLED = false` (현재)
- Spring Security 추가 예정 (팀원 작업 중)
- 추가 후 `SECURITY_ENABLED = true`로 변경

## 공통 코드

### 자재 타입 (MAT_TYPE)
- **MAT_FG**: 완제품 (Finished Goods)
- **MAT_RM**: 원부자재 (Raw Material)
- **MAT_RAW**: 원자재
- **MAT_FINISH**: 완제품

### 자재 상태 (MAT_STATUS)
- **MAT_NORMAL**: 정상
- **MAT_STOP**: 중단

### 재고 상태 (STOCK_STATUS)
- **NORMAL**: 정상
- **LOW**: 부족
- **OUT_OF_STOCK**: 품절

## API 엔드포인트

### Material (자재 관리)
- `GET /materials` - 자재 목록 조회
- `GET /materials/{id}` - 자재 상세 조회
- `PUT /materials/{id}` - 자재 수정
- `PUT /materials/{id}/deactivate` - 자재 비활성화

### BOM 계산
- `GET /bom/materials/search` - 자재 검색
- `GET /bom/explosion?materialId={id}&quantity={qty}` - BOM 정전개
- `GET /bom/implosion/{rawMaterialId}` - BOM 역전개

### Employee (인사/조직)
- `GET /employees/organization` - 조직도 조회
- `GET /employees/departments/{departmentId}` - 부서별 사원 목록

### Client (고객사)
- `GET /clients/{id}` - 고객사 기본 정보
- `GET /clients/{id}/addresses` - 고객사 주소 목록
- `GET /clients/{id}/employees` - 고객사 담당자 목록

### Warehouse (창고/재고)
- `GET /warehouse/stocks` - 재고 목록 조회
- `GET /warehouse/stocks/{id}` - 재고 상세 조회

## 최근 작업 내역

### 2024-12-15
1. **BOM 정전개 API 변경**
   - POST → GET으로 변경
   - RequestBody → RequestParam으로 변경
   - 테스트 코드 업데이트 완료

2. **Material Entity 타입 변경**
   - nullable 필드: int → Integer
   - 영향받는 파일 수정:
     - MaterialSearchResponseDTO
     - MaterialListResponseDTO
     - MaterialDetailResponseDTO
     - Material.update() 메서드
     - MaterialServiceImpl.createMaterial()

3. **권한 시스템 구축**
   - @RequirePermission 어노테이션 생성
   - PermissionAspect (AOP) 구현
   - JWT 토큰 파싱 로직 (쉼표 구분 처리)
   - 모든 컨트롤러에 권한 어노테이션 적용
   - 테스트 코드 작성 (77개 테스트 통과)

4. **Swagger 문서화**
   - MaterialController 주석 추가
   - WarehouseStockController 문서화 (물류팀 전용)
   - ClientController, EmployeeController @Parameter 추가

5. **패키지 구조 재설계**
   - CQRS 패턴 적용 계획
   - Command (JPA) / Query (MyBatis) 분리
   - 도메인별 command/query 구조 설계

## 다음 작업 예정

1. **패키지 구조 마이그레이션**
   - 새 프로젝트 생성
   - 기존 코드를 command/query 구조로 재배치
   - Entity는 command/domain/aggregate에 유지
   - Repository는 command/domain/repository에 유지
   - 조회 Service/Controller를 query로 이동
   - 조회 기능 MyBatis로 전환 (DAO + XML Mapper)

2. **Spring Security 통합**
   - 팀원의 Spring Security 작업 완료 대기
   - SECURITY_ENABLED = true로 변경
   - JWT 토큰 검증 활성화

3. **.claude 폴더 이전**
   - 새 프로젝트로 .claude 폴더 복사
   - 경로 참조 업데이트
   - 컨텍스트 파일 갱신

## 개발 가이드

### 새 API 추가 시
1. 조회 기능이면 query 패키지에, 변경 기능이면 command 패키지에 작성
2. @RequirePermission 어노테이션 필수 추가
3. Swagger 문서화 (@Operation, @Parameter)
4. Javadoc 주석 작성 (API 엔드포인트 예시 포함)
5. 테스트 코드 작성 (MockMvc)

### 테스트 실행
```bash
# 전체 테스트
./gradlew test

# 특정 클래스 테스트
./gradlew test --tests MaterialControllerTest

# 빌드 + 테스트
./gradlew clean build
```

### 애플리케이션 실행
```bash
# 개발 모드
./gradlew bootRun

# 상세 로그
./gradlew bootRun --info
```

## 코딩 컨벤션

### Naming
- **Entity**: PascalCase (Material, Bom)
- **DTO**: [목적][Entity명][Request|Response]DTO (MaterialListResponseDTO)
- **Controller**: [Entity명][Command|Query]Controller
- **Service**: [Entity명][Command|Query]Service
- **Repository**: [Entity명]Repository
- **DAO**: [Entity명][Query|Command]Dao

### API Response
- 조회: 직접 DTO 반환
- 변경: void 또는 생성된 ID 반환
- 예외: GlobalExceptionHandler에서 일괄 처리

### 예외 처리
- BusinessException: 비즈니스 로직 오류 (4xx)
- SystemException: 시스템 오류 (5xx)
- [Entity]NotFoundException: 리소스 없음 (404)

## 주의사항

1. **절대 하지 말 것**
   - Entity에 비즈니스 로직 이외의 과도한 메서드 추가
   - Repository를 query/dao로 이동 (domain에 유지!)
   - POST/PUT으로 조회 API 작성
   - 권한 어노테이션 누락

2. **반드시 할 것**
   - 새 API 작성 시 권한 어노테이션 추가
   - 테스트 코드 작성
   - API 문서화 (Swagger + Javadoc)
   - nullable 필드는 Integer/Long 사용

3. **성능 최적화**
   - N+1 문제 주의 (fetch join, @EntityGraph)
   - 불필요한 EAGER 로딩 금지
   - 조회 쿼리는 MyBatis 전환 후 최적화

## 참고 링크
- Spring Boot Docs: https://spring.io/projects/spring-boot
- MyBatis Docs: https://mybatis.org/mybatis-3/
- Swagger/OpenAPI: https://swagger.io/specification/
