package com.northcoders.recordshopproject.service;

import com.northcoders.recordshopproject.model.Album;
import com.northcoders.recordshopproject.model.Genre;
import com.northcoders.recordshopproject.repository.RecordShopRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class RecordShopServiceTests {

    @Mock
    private RecordShopRepository mockRecordShopRepository;

    @InjectMocks
    private RecordShopServiceImpl mockRecordShopService;

    @Test
    @DisplayName("getAllAlbums() returns a list of albums")
    public void testGetAllAlbums(){

        List<Album> albums = new ArrayList<>();
        albums.add(new Album("Alum One", Genre.DANCE, "Atist One", 2020, 11, 40));
        albums.add(new Album("Alum Two", Genre.DANCE, "Atist Two", 2000, 15, 50));
        albums.add(new Album("Alum Three", Genre.DANCE, "Atist Three", 1952, 30, 10));

        when(mockRecordShopRepository.findAll()).thenReturn(albums);

        List<Album> actualResult = mockRecordShopService.getAllAlbums();

        assertThat(actualResult).hasSize(3);
        assertThat(actualResult).isEqualTo(albums);
    }

    @Test
    @DisplayName("addAlbum() adds an album to the repository")

    public void testAddAlbum(){
        Album album = new Album("Alum One", Genre.DANCE, "Atist One", 2020, 11, 40);

        when(mockRecordShopRepository.save(album)).thenReturn(album);

        Album actualResult = mockRecordShopService.addAlbum(album);

        assertThat(actualResult).isEqualTo(album);
    }
}
