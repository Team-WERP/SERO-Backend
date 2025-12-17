package com.werp.sero.employee.query.dao;

import com.werp.sero.employee.command.domain.aggregate.Department;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

/**
 * 부서 Query MyBatis Mapper 인터페이스 (조회 전용)
 */
@Mapper
public interface DepartmentMapper {

    /**
     * ID로 부서 조회
     */
    Optional<Department> findById(@Param("id") int id);
}
