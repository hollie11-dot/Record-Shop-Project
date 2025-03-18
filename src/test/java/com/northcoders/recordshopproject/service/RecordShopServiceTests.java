package com.northcoders.recordshopproject.service;

import com.northcoders.recordshopproject.exceptionhandling.AlbumNotFoundException;
import com.northcoders.recordshopproject.exceptionhandling.InvalidInputsException;
import com.northcoders.recordshopproject.model.Album;
import com.northcoders.recordshopproject.model.Genre;
import com.northcoders.recordshopproject.repository.RecordShopRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
        albums.add(new Album("Alum One", Genre.DANCE, "Artist One", 2020, 11, 40, null));
        albums.add(new Album("Alum Two", Genre.DANCE, "Artist Two", 2000, 15, 50, null));
        albums.add(new Album("Alum Three", Genre.DANCE, "Artist Three", 1952, 30, 10, null));

        when(mockRecordShopRepository.findAll()).thenReturn(albums);

        List<Album> actualResult = mockRecordShopService.getAllAlbums();

        assertThat(actualResult).hasSize(3);
        assertThat(actualResult).isEqualTo(albums);
    }

    @Test
    @DisplayName("addAlbum() adds an album")
    public void testAddAlbum(){
        Album album = new Album("Alum One", Genre.DANCE, "Artist One", 2020, 11, 40, null);

        when(mockRecordShopRepository.save(album)).thenReturn(album);

        Album actualResult = mockRecordShopService.addAlbum(album);

        assertThat(actualResult).isEqualTo(album);
    }

    @Test
    @DisplayName("getAlbumByID returns an album corresponding to passed ID")
    public void testGetAlbumByID(){
        Album album = new Album(4L, "Alum One", Genre.DANCE, "Artist One", 2020, 11, 40, null);

        Long id = 4L;

        when(mockRecordShopRepository.findById(id)).thenReturn(Optional.of(album));

        Album actualResult = mockRecordShopService.getAlbumById(id);

        assertThat(actualResult).isEqualTo(album);
    }

    @Test
    @DisplayName("updateAlbum returns an updated album when passed an albumID and an Album")
    public void testUpdateAlbum(){
        Album existingAlbum = new Album(1L, "Nevermind", Genre.ROCK, "Nirvana", 1991, 15, 101, null);
        when(mockRecordShopRepository.existsById(1L)).thenReturn(true);
        Album updatedAlbum = new Album("Nevermind", Genre.ROCK, "Nirvana", 1991, 15, 100, null);
        updatedAlbum.setId(1L);

        when(mockRecordShopRepository.findById(1L)).thenReturn(Optional.of(existingAlbum));
        when(mockRecordShopRepository.save(any(Album.class))).thenReturn(updatedAlbum);

        Album actualResult = mockRecordShopService.updateAlbum(updatedAlbum, existingAlbum.getId());

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
    @DisplayName("filterAlbums returns a list of albums by passed artist")
    public void testFilterAlbumsByArtist(){

        List<Album> albums = new ArrayList<>();
        Album albumOne = new Album("Alum One", Genre.DANCE, "Artist One", 2020, 11, 40, null);
        Album albumTwo = new Album("Alum Two", Genre.DANCE, "Artist One", 2000, 15, 50, null);
        Album albumThree = new Album("Alum Three", Genre.DANCE, "Artist Two", 1952, 30, 10, null);

        albums.add(albumOne);
        albums.add(albumTwo);
        albums.add(albumThree);

        when(mockRecordShopRepository.findByArtist("Artist One")).thenReturn(List.of(albumOne, albumTwo));

        List<Album> actualResult = mockRecordShopService.filterAlbums("Artist One", null, null);

        assertThat(actualResult).hasSize(2);
        assertThat(actualResult).isEqualTo(List.of(albumOne, albumTwo));
    }

    @Test
    @DisplayName("filterAlbums returns a list of albums by passed release year")
    public void testFilterAlbumsByReleaseYear(){

        List<Album> albums = new ArrayList<>();
        Album albumOne = new Album("Alum One", Genre.DANCE, "Artist One", 2020, 11, 40, null);
        Album albumTwo = new Album("Alum Two", Genre.DANCE, "Artist Two", 2020, 15, 50, null);
        Album albumThree = new Album("Alum Three", Genre.DANCE, "Artist Three", 1952, 30, 10, null);

        albums.add(albumOne);
        albums.add(albumTwo);
        albums.add(albumThree);

        when(mockRecordShopRepository.findByDateReleased(2020)).thenReturn(List.of(albumOne, albumTwo));

        List<Album> actualResult = mockRecordShopService.filterAlbums(null, 2020, null);

        assertThat(actualResult).hasSize(2);
        assertThat(actualResult).isEqualTo(List.of(albumOne, albumTwo));
    }

    @Test
    @DisplayName("filterAlbums returns a list of albums by passed genre")
    public void testFilterAlbumsByGenre(){

        List<Album> albums = new ArrayList<>();
        Album albumOne = new Album("Alum One", Genre.DANCE, "Artist One", 2020, 11, 40, null);
        Album albumTwo = new Album("Alum Two", Genre.DANCE, "Artist Two", 2020, 15, 50, null);
        Album albumThree = new Album("Alum Three", Genre.POP, "Artist Three", 1952, 30, 10, null);

        albums.add(albumOne);
        albums.add(albumTwo);
        albums.add(albumThree);

        when(mockRecordShopRepository.findByGenre(Genre.valueOf("DANCE"))).thenReturn(List.of(albumOne, albumTwo));

        List<Album> actualResult = mockRecordShopService.filterAlbums(null, null, Genre.valueOf("DANCE"));

        assertThat(actualResult).hasSize(2);
        assertThat(actualResult).isEqualTo(List.of(albumOne, albumTwo));
    }

    @Test
    @DisplayName("addAlbum method throws an exception when passed null values")
    public void testAddAlbumThrowsException(){
        Album album = new Album(null, Genre.DANCE, "Artist One", 2020, 11, 40, null);

        when(mockRecordShopRepository.save(album)).thenReturn(album);

        InvalidInputsException exception = assertThrows(InvalidInputsException.class, () -> mockRecordShopService.addAlbum(album));
        assertEquals("One ore more incorrect inputs. Please try again.", exception.getMessage());
    }

    @Test
    @DisplayName("getAlbumByID throws an exception when passed an ID of album not present")
    public void testGetAlbumByIDThrowsException(){
        Long id = 3L;
        doThrow(new AlbumNotFoundException("There is not an album with that ID. Please try again.")).when(mockRecordShopRepository).findById(id);

        AlbumNotFoundException exception = assertThrows(AlbumNotFoundException.class, () -> mockRecordShopService.getAlbumById(id));
        assertEquals("There is not an album with that ID. Please try again.", exception.getMessage());
    }

    @Test
    @DisplayName("updateAlbum throws an exception when passed an incorrect album")
    public void testUpdateAlbumThrowsExceptionWithInvalidInput(){
        Album existingAlbum = new Album(1L, "Nevermind", Genre.ROCK, "Nirvana", 1991, 15, 101, null);
        when(mockRecordShopRepository.existsById(1L)).thenReturn(true);
        Album updatedAlbum = new Album(null, Genre.ROCK, "Nirvana", 1991, 15, 100, null);
        updatedAlbum.setId(1L);

        when(mockRecordShopRepository.findById(1L)).thenReturn(Optional.of(existingAlbum));
        when(mockRecordShopRepository.save(any(Album.class))).thenReturn(updatedAlbum);

        InvalidInputsException exception = assertThrows(InvalidInputsException.class, () -> mockRecordShopService.updateAlbum(updatedAlbum, 1L));
        assertEquals("One ore more incorrect inputs. Please try again.", exception.getMessage());

    }

    @Test
    @DisplayName("updateAlbum throws an exception when passed an invalid ID")
    public void testUpdateAlbumThrowsExceptionWithInvalidID(){
        when(mockRecordShopRepository.existsById(1L)).thenReturn(false);
        Album updatedAlbum = new Album("Nevermind", Genre.ROCK, "Nirvana", 1991, 15, 100, null);
        updatedAlbum.setId(1L);

        doThrow(new AlbumNotFoundException("There is not an album with that ID. Please try again.")).when(mockRecordShopRepository).findById(1L);

        AlbumNotFoundException exception = assertThrows(AlbumNotFoundException.class, () -> mockRecordShopService.updateAlbum(updatedAlbum, 1L));
        assertEquals("There is not an album with that ID. Please try again.", exception.getMessage());
    }

    @Test
    @DisplayName("deleteAlbum throws exception when passed an invalid albumID")
    public void testDeleteAlbumThrowsException(){

        doThrow(new AlbumNotFoundException("Unable to delete. There is not an album with that ID.")).when(mockRecordShopRepository).findById(1L);

        AlbumNotFoundException exception = assertThrows(AlbumNotFoundException.class, () -> mockRecordShopService.deleteAlbum(1L));
        assertEquals("Unable to delete. There is not an album with that ID.", exception.getMessage());
    }

    @Test
    @DisplayName("filterAlbums throws an exception when no album found")
    public void testFilterAlbumsByDateReleasedThrowsException(){

        doThrow(new AlbumNotFoundException("There are no matching albums.")).when(mockRecordShopRepository).findByDateReleased(null);

        AlbumNotFoundException exception = assertThrows(AlbumNotFoundException.class, () -> mockRecordShopService.filterAlbums("Test", null, Genre.ROCK));
        assertEquals("There are no matching albums.", exception.getMessage());
    }

    @Test
    @DisplayName("filterAlbums throws an exception when no album found")
    public void testFilterAlbumsByGenreThrowsException(){

        doThrow(new AlbumNotFoundException("There are no matching albums.")).when(mockRecordShopRepository).findByGenre(null);

        AlbumNotFoundException exception = assertThrows(AlbumNotFoundException.class, () -> mockRecordShopService.filterAlbums("test", 1990, null));
        assertEquals("There are no matching albums.", exception.getMessage());
    }

    @Test
    @DisplayName("filterAlbums throws an exception when no album found")
    public void testFilterAlbumsByArtistThrowsException(){

        doThrow(new AlbumNotFoundException("There are no matching albums.")).when(mockRecordShopRepository).findByArtist(null);

        AlbumNotFoundException exception = assertThrows(AlbumNotFoundException.class, () -> mockRecordShopService.filterAlbums(null, 1990, Genre.ROCK));
        assertEquals("There are no matching albums.", exception.getMessage());
    }
}
