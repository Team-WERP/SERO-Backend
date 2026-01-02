package com.werp.sero.employee.command.domain.aggregate;

import com.fasterxml.jackson.annotation.JsonIgnore; // [추가] JSON 변환 시 순환참조 방지
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "employee")
@NoArgsConstructor
@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "emp_code", nullable = false, unique = true)
    private String empCode;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String contact;

    @Column(nullable = false, columnDefinition = "varchar(100) default 'ES_ACT'")
    private String status;

    @Column(name = "position_code", nullable = false)
    private String positionCode;

    @Column(name = "rank_code", nullable = false)
    private String rankCode;

    @Column(name = "start_date", nullable = false)
    private String startDate;

    @Column(name = "created_at", nullable = false)
    private String createdAt;

    // ▼▼▼ [핵심 수정 부분] ▼▼▼
    @ManyToOne(fetch = FetchType.LAZY) // 1. 지연 로딩: 필요할 때만 조회 (쿼리 폭탄 방지)
    @JoinColumn(name = "dept_id")
    // @JsonIgnore // 2. JSON 무시: 직렬화 시 부서 정보를 쳐다보지 않음 (무한 루프 방지)
    private Department department;
}