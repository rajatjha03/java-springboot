package com.project.rampcards.service;

import com.project.rampcards.dto.QuickbookDto;
import com.project.rampcards.entity.Quickbook;
import com.project.rampcards.exceptionhandler.QuickbookNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

public interface QuickbookService {

    public List<QuickbookDto> getQuickbook();

    public Quickbook saveQuickbook(QuickbookDto quickbookDto);

    public String deleteQuickbook(int id) throws QuickbookNotFoundException;
}
