package com.werp.sero.common.security;


import com.werp.sero.system.command.domain.aggregate.MenuPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MenuPermissionRepository extends JpaRepository<MenuPermission, Integer> {

    /**
     * 메뉴 코드와 권한 코드로 메뉴 권한 조회
     *
     * @param menuCode       메뉴 코드 (예: MM_MAT)
     * @param permissionCode 권한 코드 (예: AC_SAL)
     * @return 메뉴 권한 목록
     */
    @Query("SELECT mp FROM MenuPermission mp " +
           "JOIN FETCH mp.menu m " +
           "JOIN FETCH mp.permission p " +
           "WHERE m.code = :menuCode AND p.code = :permissionCode")
    List<MenuPermission> findByMenuCodeAndPermissionCode(
        @Param("menuCode") String menuCode,
        @Param("permissionCode") String permissionCode
    );
}
