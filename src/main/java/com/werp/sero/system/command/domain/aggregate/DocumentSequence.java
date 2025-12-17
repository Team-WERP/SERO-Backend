package com.werp.sero.system.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(
        name = "document_sequence",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"prefix", "base_date"})
        }
)
@NoArgsConstructor
@Entity
public class DocumentSequence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "prefix", nullable = false)
    private String prefix;

    @Column(name = "base_date", nullable = false, length = 8)
    private String baseDate;

    @Column(name = "current_seq", nullable = false)
    private int currentSeq;

    public DocumentSequence(String prefix, String baseDate) {
        this.prefix = prefix;
        this.baseDate = baseDate;
        this.currentSeq = 0;
    }

    public int nextSeq() {
        this.currentSeq++;
        return this.currentSeq;
    }
}