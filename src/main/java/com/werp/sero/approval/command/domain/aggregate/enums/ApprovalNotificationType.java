package com.werp.sero.approval.command.domain.aggregate.enums;

import com.werp.sero.approval.command.domain.aggregate.Approval;
import lombok.Getter;

@Getter
public enum ApprovalNotificationType {
    REQUEST {
        @Override
        public String getTitle(final Approval approval) {
            return approval.getTitle() + " 결재 요청";
        }

        @Override
        public String getContent(final Approval approval) {
            return approval.getApprovalCode() + "에 대한 결재가 요청되었습니다. 확인 후 결재를 진행해주세요.";
        }
    },
    APPROVED {
        @Override
        public String getTitle(final Approval approval) {
            return approval.getTitle() + " 승인 완료";
        }

        @Override
        public String getContent(final Approval approval) {
            return approval.getApprovalCode() + "가 최종 승인되었습니다.";
        }
    },
    REJECTED {
        @Override
        public String getTitle(final Approval approval) {
            return approval.getTitle() + " 반려";
        }

        @Override
        public String getContent(final Approval approval) {
            return approval.getApprovalCode()
                    + "가 반려되었습니다. 결재 의견을 확인해주세요.";
        }
    };

    public abstract String getTitle(final Approval approval);

    public abstract String getContent(final Approval approval);
}