package com.werp.sero.notice.exception;

import com.werp.sero.common.error.ErrorCode;
import com.werp.sero.common.error.exception.BusinessException;

public class NoticeAccessDeniedException extends BusinessException {
    public NoticeAccessDeniedException() {
        super(ErrorCode.NOTICE_ACCESS_DENIED);
    }
}