package com.crud.tasks.validator;

import com.crud.tasks.domain.TrelloBoard;
import com.crud.tasks.domain.TrelloCard;
import com.crud.tasks.trello.validator.TrelloValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class TrelloValidatorTest {

    @InjectMocks
    private TrelloValidator trelloValidator;

    @Test
    public void shouldPrintMessageForTests() {
        //Given
        TrelloCard trelloCard = new TrelloCard("testName", "testDescription", "testPosition", "testListId");
        //When
        trelloValidator.validateCard(trelloCard);
        //Then
    }

    @Test
    public void shouldFilterBoardsWithNamesDifferentThanTest() {
        //Given
        List<TrelloBoard> trelloBoards = new ArrayList<>();
        trelloBoards.add(new TrelloBoard("001", "test", new ArrayList<>()));
        trelloBoards.add(new TrelloBoard("002", "another_test_name", new ArrayList<>()));
        trelloBoards.add(new TrelloBoard("003", "one_more_name", new ArrayList<>()));
        //When
        List<TrelloBoard> filteredList = trelloValidator.validateTrelloBoards(trelloBoards);
        //Then
        assertEquals(2, filteredList.size());
    }
}
