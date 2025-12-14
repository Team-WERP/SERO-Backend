-- =====================================================
-- 권한 데이터 삽입
-- =====================================================
INSERT INTO permission (name, code) VALUES
('시스템 관리자', 'AC_SYS'),
('영업', 'AC_SAL'),
('생산', 'AC_PRO'),
('물류', 'AC_WHS'),
('고객', 'AC_CLI');

-- =====================================================
-- 메뉴 데이터 삽입
-- =====================================================

-- 기준정보 > 자재·BOM 관리
INSERT INTO menu (code, name, url, sort_order, is_activated, parent_id) VALUES
('MM_MAT', '자재·BOM 관리', '/master/materials', 50, 1, NULL);

-- 기준정보 > 기업정보 관리
INSERT INTO menu (code, name, url, sort_order, is_activated, parent_id) VALUES
('MM_CORP', '기업정보 관리', '/master/company', 51, 1, NULL);

-- 기준정보 > 사원정보 조회
INSERT INTO menu (code, name, url, sort_order, is_activated, parent_id) VALUES
('MM_EMP', '사원정보 조회', '/master/employees', 52, 1, NULL);

-- =====================================================
-- 메뉴 권한 매핑 (자재·BOM 관리)
-- =====================================================

-- 본사직원 전체 - 자재 목록 조회/검색, 자재 상세조회, 자재별 BOM 조회, 소요량 조회, 역전개
-- 시스템 관리자: 읽기 + 쓰기
INSERT INTO menu_permission (menu_id, permission_id, read_permission, write_permission)
SELECT m.id, p.id, true, true
FROM menu m, permission p
WHERE m.code = 'MM_MAT' AND p.code = 'AC_SYS';

-- 영업팀: 읽기 + 쓰기 (자재 엑셀 업로드, 등록, 수정, 삭제, 비활성화)
INSERT INTO menu_permission (menu_id, permission_id, read_permission, write_permission)
SELECT m.id, p.id, true, true
FROM menu m, permission p
WHERE m.code = 'MM_MAT' AND p.code = 'AC_SAL';

-- 생산팀: 읽기 + 쓰기 (자재 엑셀 업로드, 등록, 수정, 삭제, 비활성화, BOM 관리)
INSERT INTO menu_permission (menu_id, permission_id, read_permission, write_permission)
SELECT m.id, p.id, true, true
FROM menu m, permission p
WHERE m.code = 'MM_MAT' AND p.code = 'AC_PRO';

-- 물류팀: 읽기 전용 (자재 목록 조회, 상세조회, BOM 조회, 소요량 조회, 역전개)
INSERT INTO menu_permission (menu_id, permission_id, read_permission, write_permission)
SELECT m.id, p.id, true, false
FROM menu m, permission p
WHERE m.code = 'MM_MAT' AND p.code = 'AC_WHS';

-- =====================================================
-- 메뉴 권한 매핑 (기업정보 관리)
-- =====================================================

-- 본사직원 전체 - 기업 기본 정보 조회, 배송지 리스트 조회
-- 시스템 관리자: 읽기 + 쓰기
INSERT INTO menu_permission (menu_id, permission_id, read_permission, write_permission)
SELECT m.id, p.id, true, true
FROM menu m, permission p
WHERE m.code = 'MM_CORP' AND p.code = 'AC_SYS';

-- 영업팀: 읽기 전용
INSERT INTO menu_permission (menu_id, permission_id, read_permission, write_permission)
SELECT m.id, p.id, true, false
FROM menu m, permission p
WHERE m.code = 'MM_CORP' AND p.code = 'AC_SAL';

-- 생산팀: 읽기 전용
INSERT INTO menu_permission (menu_id, permission_id, read_permission, write_permission)
SELECT m.id, p.id, true, false
FROM menu m, permission p
WHERE m.code = 'MM_CORP' AND p.code = 'AC_PRO';

-- 물류팀: 읽기 전용
INSERT INTO menu_permission (menu_id, permission_id, read_permission, write_permission)
SELECT m.id, p.id, true, false
FROM menu m, permission p
WHERE m.code = 'MM_CORP' AND p.code = 'AC_WHS';

-- =====================================================
-- 메뉴 권한 매핑 (사원정보 조회)
-- =====================================================

-- 본사직원 전체 - 사원 정보 조회(인사/조직)
-- 시스템 관리자: 읽기 + 쓰기
INSERT INTO menu_permission (menu_id, permission_id, read_permission, write_permission)
SELECT m.id, p.id, true, true
FROM menu m, permission p
WHERE m.code = 'MM_EMP' AND p.code = 'AC_SYS';

-- 영업팀: 읽기 전용
INSERT INTO menu_permission (menu_id, permission_id, read_permission, write_permission)
SELECT m.id, p.id, true, false
FROM menu m, permission p
WHERE m.code = 'MM_EMP' AND p.code = 'AC_SAL';

-- 생산팀: 읽기 전용
INSERT INTO menu_permission (menu_id, permission_id, read_permission, write_permission)
SELECT m.id, p.id, true, false
FROM menu m, permission p
WHERE m.code = 'MM_EMP' AND p.code = 'AC_PRO';

-- 물류팀: 읽기 전용
INSERT INTO menu_permission (menu_id, permission_id, read_permission, write_permission)
SELECT m.id, p.id, true, false
FROM menu m, permission p
WHERE m.code = 'MM_EMP' AND p.code = 'AC_WHS';
