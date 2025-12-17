package com.werp.sero.employee.query.dao;

import com.werp.sero.employee.command.domain.aggregate.Department;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

/**
 * 부서 Query MyBatis Mapper 인터페이스 (조회 전용)
 */
@Mapper
public interface DepartmentMapper {

    /**
     * 전체 부서 목록 조회
     */
    List<Department> findAll();

    /**
     * ID로 부서 조회
     */
    Optional<Department> findById(@Param("id") int id);
}
