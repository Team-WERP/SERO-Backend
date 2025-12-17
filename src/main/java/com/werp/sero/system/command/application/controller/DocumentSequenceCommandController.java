package com.werp.sero.system.command.application.controller;

import com.werp.sero.system.command.application.service.DocumentSequenceCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/document-sequence")
@RequiredArgsConstructor
public class DocumentSequenceCommandController {
    private final DocumentSequenceCommandService documentSequenceCommandService;

    @PostMapping("/generate")
    public String generateDocumentSequence(@RequestParam String docTypeCode) {
        return documentSequenceCommandService.generateDocumentCode(docTypeCode);
    }
}
