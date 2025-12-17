package com.werp.sero.system.command.application.service;

import com.werp.sero.system.command.domain.aggregate.DocumentSequence;
import com.werp.sero.system.command.domain.repository.DocumentSequenceRepository;
import com.werp.sero.system.query.service.CommonCodeQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class DocumentSequenceCommandServiceImpl implements DocumentSequenceCommandService {

    private final DocumentSequenceRepository documentSequenceRepository;
    private final CommonCodeQueryService commonCodeQueryService;

    @Transactional
    @Override
    public String generateDocumentCode(String docTypeCode) {
        String prefix = commonCodeQueryService.getName("DOC_TYPE", docTypeCode);
        String today = LocalDate.now()
                .format(DateTimeFormatter.BASIC_ISO_DATE);

        DocumentSequence sequence = documentSequenceRepository
                .findForUpdate(prefix, today)
                .orElseGet(() ->
                        documentSequenceRepository.save(
                                new DocumentSequence(prefix, today)
                        )
                );
        int seq = sequence.nextSeq();
        return documentCodeFormat(prefix, today, seq);
    }

    private String documentCodeFormat(String prefix, String date, int seq) {
        return String.format(
                "%s-%s-%03d",
                prefix,
                date,
                seq
        );
    }
}
