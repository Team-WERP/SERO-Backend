package com.werp.sero.production.command.application.service;

import com.werp.sero.employee.command.domain.aggregate.Employee;
import com.werp.sero.production.command.application.dto.WorkOrderCreateRequestDTO;
import com.werp.sero.production.command.application.dto.WorkOrderEndRequest;
import com.werp.sero.production.command.application.dto.WorkOrderResultPreviewRequestDTO;
import com.werp.sero.production.command.application.dto.WorkOrderResultPreviewResponseDTO;

public interface WOCommandService {

    void createWorkOrder(WorkOrderCreateRequestDTO request, Employee currentEmployee);

    void start(int woId, String note, Employee worker);

    void pause(int woId, String note);

    void resume(int woId, String note);

    void end(int woId, WorkOrderEndRequest request, Employee currentEmployee);

    WorkOrderResultPreviewResponseDTO previewResult(int woId, WorkOrderResultPreviewRequestDTO request);
}
