package com.werp.sero.approval.command.domain.repository;

import com.werp.sero.approval.command.domain.aggregate.ApprovalAttachment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApprovalAttachmentRepository extends JpaRepository<ApprovalAttachment, Integer> {
}