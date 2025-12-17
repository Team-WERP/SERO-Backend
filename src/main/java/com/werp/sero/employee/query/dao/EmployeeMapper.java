package com.werp.sero.employee.query.dao;

import com.werp.sero.employee.command.domain.aggregate.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 직원 MyBatis Mapper 인터페이스
 */
@Mapper
public interface EmployeeMapper {

    /**
     * 부서 코드 기준으로 하위 부서 포함 모든 직원 조회
     */
    List<Employee> findAllEmployeesByDeptCode(final String rootDeptCode);

    /**
     * 부서별 사원 목록 조회 (부서 정보 포함)
     */
    List<Employee> findByDepartmentIdWithDepartment(@Param("departmentId") int departmentId);
}
