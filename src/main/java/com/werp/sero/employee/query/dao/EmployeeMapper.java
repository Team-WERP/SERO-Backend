package com.werp.sero.employee.query.dao;

import com.werp.sero.employee.command.domain.aggregate.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

/**
 * 직원 MyBatis Mapper 인터페이스
 */
@Mapper
public interface EmployeeMapper {

    /**
     * 부서별 사원 목록 조회 (부서 정보 포함)
     */
    List<Employee> findByDepartmentIdWithDepartment(@Param("departmentId") int departmentId);

    /**
     * 전체 사원 목록 조회 (부서 정보 포함)
     */
    List<Employee> findAllWithDepartment();

    /**
     * ID로 직원 조회
     */
    Optional<Employee> findById(@Param("id") int id);
}
