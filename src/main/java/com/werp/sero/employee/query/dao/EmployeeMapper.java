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
     * 부서별 사원 목록 조회 (부서 정보 포함)
     */
    List<Employee> findByDepartmentIdWithDepartment(@Param("departmentId") int departmentId);
}
