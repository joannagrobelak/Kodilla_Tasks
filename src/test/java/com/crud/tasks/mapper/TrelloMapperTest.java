package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TrelloMapperTest {

    @Autowired
    private TrelloMapper trelloMapper;

    @Test
    public void shouldMapToBoards() {
        //Given
        List<TrelloListDto> testTrelloListDto = new ArrayList<>();
        testTrelloListDto.add(new TrelloListDto("001", "listDtoTestName", false));
        List<TrelloBoardDto> testTrelloBoardsDto = new ArrayList<>();
        testTrelloBoardsDto.add(new TrelloBoardDto("1", "boardDtoTestName", testTrelloListDto));
        //When
        List<TrelloBoard> testTrelloBoards = trelloMapper.mapToBoards(testTrelloBoardsDto);
        //Then
        assertEquals("1", testTrelloBoards.get(0).getId());
        assertEquals("boardDtoTestName", testTrelloBoards.get(0).getName());
        assertEquals("001", testTrelloBoards.get(0).getLists().get(0).getId());
        assertEquals("listDtoTestName", testTrelloBoards.get(0).getLists().get(0).getName());
        assertEquals(false, testTrelloBoards.get(0).getLists().get(0).isClosed());
        assertEquals(1, testTrelloBoards.size());
    }

    @Test
    public void shouldMapToBoardsDto() {
        //Given
        List<TrelloList> testTrelloList = new ArrayList<>();
        testTrelloList.add(new TrelloList("001", "listTestName", false));
        List<TrelloBoard> testTrelloBoards = new ArrayList<>();
        testTrelloBoards.add(new TrelloBoard("1", "boardTestName", testTrelloList));
        //When
        List<TrelloBoardDto> testTrelloBoardsDto = trelloMapper.mapToBoardsDto(testTrelloBoards);
        //Then
        assertEquals("1", testTrelloBoardsDto.get(0).getId());
        assertEquals("boardTestName", testTrelloBoardsDto.get(0).getName());
        assertEquals("001", testTrelloBoardsDto.get(0).getLists().get(0).getId());
        assertEquals("listTestName", testTrelloBoardsDto.get(0).getLists().get(0).getName());
        assertEquals(false, testTrelloBoardsDto.get(0).getLists().get(0).isClosed());
        assertEquals(1, testTrelloBoardsDto.size());
    }

    @Test
    public void shouldMapToList() {
        //Given
        List<TrelloListDto> testTrelloListDto = new ArrayList<>();
        testTrelloListDto.add(new TrelloListDto("001", "listDtoTestName", false));
        //When
        List<TrelloList> testTrelloList = trelloMapper.mapToList(testTrelloListDto);
        //Then
        assertEquals("001", testTrelloList.get(0).getId());
        assertEquals("listDtoTestName", testTrelloList.get(0).getName());
        assertEquals(false, testTrelloList.get(0).isClosed());
        assertEquals(1, testTrelloList.size());
    }

    @Test
    public void shouldMapToListDto() {
        //Given
        List<TrelloList> testTrelloList = new ArrayList<>();
        testTrelloList.add(new TrelloList("001", "listDtoTestName", false));
        //When
        List<TrelloListDto> testTrelloListDto = trelloMapper.mapToListDto(testTrelloList);
        //Then
        assertEquals("001", testTrelloListDto.get(0).getId());
        assertEquals("listDtoTestName", testTrelloListDto.get(0).getName());
        assertEquals(false, testTrelloListDto.get(0).isClosed());
        assertEquals(1, testTrelloListDto.size());
    }

    @Test
    public void shouldMapToCard() {
        //Given
        TrelloCardDto testTrelloCardDto = new TrelloCardDto("testCardName", "testCardDescription", "bottom", "001");
        //When
        TrelloCard testTrelloCard = trelloMapper.mapToCard(testTrelloCardDto);
        //Then
        assertEquals("testCardName", testTrelloCard.getName());
        assertEquals("testCardDescription", testTrelloCard.getDescription());
        assertEquals("bottom", testTrelloCard.getPos());
        assertEquals("001", testTrelloCard.getListId());
    }

    @Test
    public void shouldMapToCardDto() {
        //Given
        TrelloCard testTrelloCard = new TrelloCard("testCardName", "testCardDescription", "bottom", "001");
        //When
        TrelloCardDto testTrelloCardDto = trelloMapper.mapToCardDto(testTrelloCard);
        //Then
        assertEquals("testCardName", testTrelloCardDto.getName());
        assertEquals("testCardDescription", testTrelloCardDto.getDescription());
        assertEquals("bottom", testTrelloCardDto.getPos());
        assertEquals("001", testTrelloCardDto.getListId());
    }
}
