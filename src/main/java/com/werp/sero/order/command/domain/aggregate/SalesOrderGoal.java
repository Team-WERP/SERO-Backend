package com.werp.sero.order.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Table(name = "sales_order_goal")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Builder
public class SalesOrderGoal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "goal_year", nullable = false)
    private int goalYear;

    @Column(name = "goal_month", nullable = false)
    private int goalMonth;

    @Column(name = "goal_amount", nullable = false)
    private long goalAmount;

    @Column(name = "created_at", nullable = false)
    private String createdAt;

    @Column(name = "updated_at", nullable = true)
    private String updatedAt;

    public void updateAmount(final long goalAmount) {
        this.goalAmount = goalAmount;
    }
}
