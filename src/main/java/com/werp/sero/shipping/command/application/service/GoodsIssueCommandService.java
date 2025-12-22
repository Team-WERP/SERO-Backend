package com.werp.sero.shipping.command.application.service;

<<<<<<< HEAD
public interface GoodsIssueCommandService {


    void completeGoodsIssue(String giCode);
=======
import com.werp.sero.employee.command.domain.aggregate.Employee;
import com.werp.sero.shipping.command.application.dto.GICreateRequestDTO;

public interface GoodsIssueCommandService {
    String createGoodsIssue(GICreateRequestDTO requestDTO, Employee drafter);
>>>>>>> origin/develop
}
