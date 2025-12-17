package com.werp.sero.employee.query.dao;

import com.werp.sero.employee.command.domain.aggregate.Employee;
import com.werp.sero.employee.query.dto.DepartmentWithEmployeesDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EmployeeMapper {

    List<Employee> findAllEmployeesByDeptCode(final String deptCode);
}
