package com.werp.sero.approval.command.application.service;

public interface ApprovalRefCodeValidator {
    boolean supports(final String targetType);

    Object validate(final String refCode);
}