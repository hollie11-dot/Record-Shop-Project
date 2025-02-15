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
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

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
        albums.add(new Album("Alum One", Genre.DANCE, "Artist One", 2020, 11, 40));
        albums.add(new Album("Alum Two", Genre.DANCE, "Artist Two", 2000, 15, 50));
        albums.add(new Album("Alum Three", Genre.DANCE, "Artist Three", 1952, 30, 10));

        when(mockRecordShopRepository.findAll()).thenReturn(albums);

        List<Album> actualResult = mockRecordShopService.getAllAlbums(null);

        assertThat(actualResult).hasSize(3);
        assertThat(actualResult).isEqualTo(albums);
    }

    @Test
    @DisplayName("addAlbum() adds an album")

    public void testAddAlbum(){
        Album album = new Album("Alum One", Genre.DANCE, "Artist One", 2020, 11, 40);

        when(mockRecordShopRepository.save(album)).thenReturn(album);

        Album actualResult = mockRecordShopService.addAlbum(album);

        assertThat(actualResult).isEqualTo(album);
    }

    @Test
    @DisplayName("getAlbumByID returns an album corresponding to passed ID")
    public void testGetAlbumByID(){
        Album album = new Album(4L, "Alum One", Genre.DANCE, "Artist One", 2020, 11, 40);

        Long id = 4L;

        when(mockRecordShopRepository.findById(id)).thenReturn(Optional.of(album));

        Album actualResult = mockRecordShopService.getAlbumById(id);

        assertThat(actualResult).isEqualTo(album);
    }

    @Test
    @DisplayName("updateAlbum returns an updated album when passed an albumID and an Album")
    public void testUpdateAlbum(){
        Album testAlbum = new Album(1L,"Nevermind", Genre.ROCK, "Nirvana", 1991, 15, 100);

        when(mockRecordShopRepository.findById(1L)).thenReturn(Optional.of(testAlbum));
        when(mockRecordShopRepository.save(any(Album.class))).thenReturn(testAlbum);

        Album actualResult = mockRecordShopService.updateAlbum(testAlbum, 1L);

        Assertions.assertNotNull(actualResult);
        Assertions.assertEquals(1L, actualResult.getId());
        Assertions.assertEquals("Nevermind", actualResult.getTitle());
        Assertions.assertEquals(Genre.ROCK, actualResult.getGenre());

        verify(mockRecordShopRepository, times(1)).findById(1L);

    }

    @Test
    @DisplayName("deleteAlbum deletes album when passed an albumID")
    public void testDeleteAlbum(){
    Album testAlbum = new Album();
    testAlbum.setId(1L);

    when(mockRecordShopRepository.findById(1L)).thenReturn(Optional.of(testAlbum));

    mockRecordShopRepository.deleteById(1L);

    verify(mockRecordShopRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("getAllAlbums(artist) returns a list of albums by passed artist")
    public void testGetAllAlbumsByArtist(){

        List<Album> albums = new ArrayList<>();
        Album albumOne = new Album("Alum One", Genre.DANCE, "Artist One", 2020, 11, 40);
        Album albumTwo = new Album("Alum Two", Genre.DANCE, "Artist One", 2000, 15, 50);
        Album albumThree = new Album("Alum Three", Genre.DANCE, "Artist Two", 1952, 30, 10);

        albums.add(albumOne);
        albums.add(albumTwo);
        albums.add(albumThree);

        when(mockRecordShopRepository.findByArtist("Artist One")).thenReturn(List.of(albumOne, albumTwo));

        List<Album> actualResult = mockRecordShopService.getAllAlbums("Artist One");

        assertThat(actualResult).hasSize(2);
        assertThat(actualResult).isEqualTo(List.of(albumOne, albumTwo));
    }
}
