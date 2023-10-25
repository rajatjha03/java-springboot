package com.project.rampcards.service.impl;

import com.project.rampcards.dto.QuickbookDto;
import com.project.rampcards.entity.Quickbook;
import com.project.rampcards.exceptionhandler.QuickbookNotFoundException;
import com.project.rampcards.repository.QuickbookRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QuickbookServiceImplTest {
    @Mock
    private QuickbookRepository quickbookRepository;
    @Spy
    private ModelMapper modelMapper;
    private QuickbookServiceImpl quickbookServiceImpl;
    AutoCloseable autoCloseable;
    @Mock
    QuickbookDto quickbookDto;
    @Mock
    Quickbook quickbook;
    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        this.quickbookServiceImpl = new QuickbookServiceImpl(this.quickbookRepository,this.modelMapper);
        quickbookDto = new QuickbookDto(1,"Hotels");
        quickbook = new Quickbook(1,"Hotels");
    }

    @AfterEach
    void tearDown() throws Exception{
        autoCloseable.close();
    }

    @Test
    void givenQuickbookInfoWhenSaveQuickbookThenSaveQuickbookStatus()
    {
        when(quickbookRepository.save(quickbook)).thenReturn(quickbook);
        assertThat(quickbookServiceImpl.saveQuickbook(quickbookDto)).isEqualTo(quickbook);
    }

    @Test
    void whenFindAllQuickbookThenGetQuickbookStatus() {
        when(quickbookRepository.findAll()).thenReturn( new ArrayList<Quickbook>(Collections.singleton(quickbook)));
        assertThat(quickbookServiceImpl.getQuickbook().get(0).getQuickbookId()).isEqualTo(quickbookDto.getQuickbookId());
        assertThat(quickbookServiceImpl.getQuickbook().get(0).getCategory()).isEqualTo(quickbookDto.getCategory());
    }

    @Test
    void givenQuickbookIdWhenDeleteQuickbookThenDeleteQuickbookStatus() throws QuickbookNotFoundException {

        when(quickbookRepository.findById(1)).thenReturn(Optional.of(quickbook));

        String result = quickbookServiceImpl.deleteQuickbook(1);
        verify(quickbookRepository).deleteById(1);
        assertThat(result).isEqualTo("Success");
    }
}
