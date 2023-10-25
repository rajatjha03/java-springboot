package com.project.rampcards.controller;

import com.project.rampcards.dto.QuickbookDto;
import com.project.rampcards.entity.Quickbook;
import com.project.rampcards.exceptionhandler.QuickbookNotFoundException;
import com.project.rampcards.exceptionhandler.TransactionNotFoundException;
import com.project.rampcards.service.QuickbookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QuickbookControllerTest {
    @InjectMocks
    private QuickbookController quickbookController;
    @Mock
    private QuickbookService quickbookService;
    int quickbookId;
    String message;
    List<QuickbookDto> quickbookDtoList;
    QuickbookDto quickbookDto;
    Quickbook quickbook;



    AutoCloseable autoCloseable;

    @BeforeEach
    public void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        quickbookDtoList = new ArrayList<>();
        quickbookDtoList.add(new QuickbookDto(1,"Hotels"));
        quickbookDtoList.add(new QuickbookDto(2,"Travel"));
        quickbookDto = new QuickbookDto(1,"Hotels");
        quickbook = new Quickbook(1, "Hotels");
        quickbookId = 1;
        message = "Success";
    }

    @Test
    void whenShowAllQuickbookThenGetAllQuickbookStatus() {
        when(quickbookService.getQuickbook()).thenReturn(quickbookDtoList);

        ResponseEntity<List<QuickbookDto>> response = quickbookController.showQuickbook();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(quickbookDtoList).isEqualTo(response.getBody());
    }

    @Test
    void givenQuickbookInfoWhenAddQuickbookThenAddQuickbookStatus(){
        when(quickbookService.saveQuickbook(quickbookDto)).thenReturn(quickbook);

        ResponseEntity<Quickbook> response = quickbookController.addQuickbook(quickbookDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(quickbook, response.getBody());
    }

    @Test
    void givenQuickbookIdWhenRemoveQuickbookThenRemoveQuickbookStatus() throws QuickbookNotFoundException  {
        when(quickbookService.deleteQuickbook(quickbookId)).thenReturn(message);

        ResponseEntity<String> response = quickbookController.deleteQuickbook(quickbookId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(message).isEqualTo(response.getBody());
    }

    //negative Test case
    @Test
    void givenWrongQuickbookIdWhenRemoveQuickbookThenRemoveQuickbookStatus() throws QuickbookNotFoundException  {
        int quickbookId = 8;
        when(quickbookService.deleteQuickbook(quickbookId)).thenThrow(QuickbookNotFoundException.class);

        assertThrows(QuickbookNotFoundException.class, () -> {
            quickbookController.deleteQuickbook(quickbookId);});
    }
}
