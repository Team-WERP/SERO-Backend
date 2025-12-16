package com.werp.sero.production.command.domain.aggregate;

import com.werp.sero.production.command.domain.aggregate.enums.Action;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "work_order_history")
@NoArgsConstructor
@Entity
public class WorkOrderHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "wo_code", nullable = false)
    private String woCode;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Action action;

    @Column(name = "acted_at", nullable = false)
    private String actedAt;

    private String note;
}