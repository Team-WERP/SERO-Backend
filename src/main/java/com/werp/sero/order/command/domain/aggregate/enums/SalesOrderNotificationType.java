package com.werp.sero.order.command.domain.aggregate.enums;

import com.werp.sero.order.command.domain.aggregate.SalesOrder;
import lombok.Getter;

@Getter
public enum SalesOrderNotificationType {
    INPROGRESS{
        @Override
        public String getTitle(final SalesOrder order) {
            return order.getSoCode() + "주문 상태 변경";
        }

        @Override
        public String getContent(final SalesOrder order) {
            return order.getSoCode()
                    + " 주문의 상태가 진행중으로 변경되었습니다.";
        }
    },
    CANCELED {
        @Override
        public String getTitle(final SalesOrder order) {
            return order.getSoCode() + "주문 건 취소";
        }

        @Override
        public String getContent(final SalesOrder order) {
            return order.getSoCode()
                    + "주문이 취소되었습니다. 취소 사유를 확인해주세요.";
        }
    };

    public abstract String getTitle(final SalesOrder order);

    public abstract String getContent(final SalesOrder order);
}
