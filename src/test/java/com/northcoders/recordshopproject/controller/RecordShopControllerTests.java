package com.northcoders.recordshopproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.northcoders.recordshopproject.model.Album;
import com.northcoders.recordshopproject.model.Genre;
import com.northcoders.recordshopproject.service.RecordShopService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class RecordShopControllerTests {

    @Mock
    private RecordShopService recordShopService;

    @InjectMocks
    private RecordShopController recordShopController;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(recordShopController).build();
    }

    @Test
    @DisplayName("Returns all albums present in database")
    public void testGetAllAlbums() throws Exception {
        List<Album> testAlbums = new ArrayList<>();

        Album albumOne = new Album("Nevermind", Genre.ROCK, "Nirvana", 1991, 10, 100);
        Album albumTwo = new Album("Kind of Blue", Genre.JAZZ, "Miles Davis", 1959, 5, 19);

        testAlbums.add(albumOne);
        testAlbums.add(albumTwo);

        when(recordShopService.getAllAlbums()).thenReturn(testAlbums);

        this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/v1/recordshop"))
                .andExpect(status().isOk())
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
    public void testAddAlbum() throws Exception {
        Album albumOne = new Album("Teenage Dream", Genre.POP, "Katy Perry", 2010, 20, 49);

        when(recordShopService.addAlbum(any(Album.class))).thenReturn(albumOne);

        this.mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/v1/recordshop")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(albumOne)))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Teenage Dream"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.genre").value("POP"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.artist").value("Katy Perry"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dateReleased").value(2010))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(20))
                .andExpect(MockMvcResultMatchers.jsonPath("$.stock").value(49));

        verify(recordShopService, times(1)).addAlbum(any(Album.class));
    }

    @Test
    @DisplayName("Returns album details when passed an albumID")
    public void testGetAlbum() throws Exception {

        Album expectedAlbum = new Album(1L, "AlbumOne", Genre.ROCK, "ArtistTwo", 1995, 15, 100);

        when(recordShopService.getAlbumById(1L)).thenReturn(expectedAlbum);

        ResponseEntity<Album> response = recordShopController.getAlbum(1L);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(expectedAlbum, response.getBody());
    }

    @Test
    @DisplayName("Updates an album when passed an ID and Album details")
    public void testUpdateAlbum() throws Exception {

        Album existingAlbum = new Album(1L, "Nevermind", Genre.POP, "Nirvana", 1995, 15, 100);

        Album updatedAlbum = new Album("Nevermind", Genre.ROCK, "Nirvana", 1991, 15, 100);

        when(recordShopService.updateAlbum(updatedAlbum, existingAlbum.getId())).thenReturn(updatedAlbum);

        ResponseEntity<Album> response = recordShopController.updateAlbum(existingAlbum.getId(), updatedAlbum);

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertEquals(1991, response.getBody().getDateReleased());
        Assertions.assertEquals(Genre.ROCK, response.getBody().getGenre());
        Assertions.assertEquals("Nevermind", response.getBody().getTitle());

//        this.mockMvc.perform(
//                        MockMvcRequestBuilders.put("/api/v1/recordshop/{albumID}")
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .content(mapper.writeValueAsString(updatedAlbum)))
//                .andExpect(status().isCreated())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Nevermind"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.genre").value(updatedAlbum.getGenre().toString()))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.dateReleased").value(updatedAlbum.getDateReleased()));

    }

}
