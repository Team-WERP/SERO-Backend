package com.werp.sero.employee.controller;

import com.werp.sero.employee.dto.EmployeeByDepartmentResponseDTO;
import com.werp.sero.employee.service.EmployeeService;
import com.werp.sero.order.dto.OrderResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Employee", description = "본사 직원 관련 API")
@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;


    @Operation(summary = "부서별 본사 직원 목록 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "부서별 본사 직원 목록 조회", content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(
                            schema = @Schema(implementation = OrderResponseDTO.class)
                    )
            )),
            @ApiResponse(responseCode = "404", content = @Content(mediaType = "application/json", examples = {
                    @ExampleObject(name = "EMPLOYEE_LIST_NOT_FOUND", value = """
                            {
                                "code": "EMPLOYEE001",
                                "message": "Employee list not found"
                            }
                            """)
            }))
    })
    @GetMapping
    public ResponseEntity<List<EmployeeByDepartmentResponseDTO>> getEmployeesByDept(@RequestParam(value = "deptCode", required = true) String deptCode) {

        final List<EmployeeByDepartmentResponseDTO> employees = employeeService.findEmployeesByDeptCode(deptCode);

        return ResponseEntity.ok(employees);
    }
}