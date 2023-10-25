package com.project.rampcards.service.impl;

import com.project.rampcards.dto.QuickbookDto;
import com.project.rampcards.entity.Quickbook;
import com.project.rampcards.exceptionhandler.QuickbookNotFoundException;
import com.project.rampcards.repository.QuickbookRepository;
import com.project.rampcards.service.QuickbookService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuickbookServiceImpl implements QuickbookService {

    private QuickbookRepository quickbookRepository;

    private ModelMapper modelMapper;

    public QuickbookServiceImpl(QuickbookRepository quickbookRepository, ModelMapper modelMapper) {
        this.quickbookRepository = quickbookRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<QuickbookDto> getQuickbook() {
        List<Quickbook> quickbookList = quickbookRepository.findAll();
        return quickbookList.stream().map(quickbook->modelMapper.map(quickbook,QuickbookDto.class)).collect(Collectors.toList());
    }

    @Override
    public Quickbook saveQuickbook(QuickbookDto quickbookDto) {
         return quickbookRepository.save(this.modelMapper.map(quickbookDto,Quickbook.class));
    }

    @Override
    public String deleteQuickbook(int id) throws QuickbookNotFoundException {
        if(quickbookRepository.findById(id).isPresent())
        {
            QuickbookDto quickbookDto = modelMapper.map(quickbookRepository.findById(id).get(), QuickbookDto.class);
            quickbookRepository.deleteById(quickbookDto.getQuickbookId());
            return "Success";
        }
        throw new QuickbookNotFoundException("Quickbook with id "+ id +" not found");
    }
}
