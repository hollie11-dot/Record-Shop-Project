package com.northcoders.recordshopproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.northcoders.recordshopproject.model.Album;
import com.northcoders.recordshopproject.model.Genre;
import com.northcoders.recordshopproject.service.RecordShopService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static com.northcoders.recordshopproject.model.Album.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@AutoConfigureMockMvc
@SpringBootTest
public class RecordShopManagerControllerTests {

    @Mock
    private RecordShopService recordShopService;

    @InjectMocks
    private RecordShopController recordShopController;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(recordShopController).build();
    }

    @Test
    @DisplayName("Returns all albums present in database")
    public void testGetAllAlbums() throws Exception{
        List<Album> testAlbums = new ArrayList<>();

        Album albumOne = new Album("Nevermind", Genre.ROCK, "Nirvana", 1991, 10, 100);
        Album albumTwo= new Album("Kind of Blue", Genre.JAZZ, "Miles Davis", 1959, 5, 19);

        testAlbums.add(albumOne);
        testAlbums.add(albumTwo);

        when(recordShopService.getAllAlbums()).thenReturn(testAlbums);

        this.mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/recordshop"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("Nevermind"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].artist").value("Nirvana"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].dateReleased").value(1991))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].genre").value("ROCK"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].title").value("Kind of Blue"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].artist").value("Miles Davis"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].dateReleased").value(1959))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].genre").value("JAZZ"));
    }

    @Test
    @DisplayName("Returns posted album when receives a post request")
    public void testAddAlbum() throws Exception{
        Album albumOne = new Album("Teenage Dream",Genre.POP, "Katy Perry", 2010, 20, 49);

        when(recordShopService.addAlbum(any(Album.class))).thenReturn(albumOne);

        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/recordshop")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(albumOne)))
                        .andExpect(MockMvcResultMatchers.status().isCreated())
                        .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Teenage Dream"))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.genre").value("POP"))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.artist").value("Katy Perry"))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.dateReleased").value(2010))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(20))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.stock").value(49));

        verify(recordShopService, times(1)).addAlbum(any(Album.class));


    }
}
