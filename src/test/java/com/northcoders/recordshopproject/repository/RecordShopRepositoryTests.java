package com.northcoders.recordshopproject.repository;

import com.northcoders.recordshopproject.model.Album;
import com.northcoders.recordshopproject.model.Genre;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class RecordShopRepositoryTests {

    @Autowired
    RecordShopRepository recordShopRepository;

    private Album testAlbum;

    @BeforeEach
    public void setUpAlbum() {
        testAlbum = new Album("Nevermind", Genre.ROCK, "Nirvana", 1991, 15, 100);
    }

    @Test
    @DisplayName("recordShopRepository.save() method saves an Album")
    public void testRecordShopRepositorySaveMethod(){
        recordShopRepository.save(testAlbum);
        assertNotNull(testAlbum);
        assertEquals("Nevermind", testAlbum.getTitle());
        assertEquals(Genre.ROCK, testAlbum.getGenre());
        assertEquals("Nirvana", testAlbum.getArtist());
        assertEquals(1991, testAlbum.getDateReleased());
        assertEquals(15, testAlbum.getPrice());
        assertEquals(100, testAlbum.getStock());
    }

    @Test
    @DisplayName("recordShopRepository.findAll() method returns all Albums ")
    public void testRecordShopRepositoryFindAllMethod(){
        Album testAlbum2 = new Album("Kind of Blue", Genre.JAZZ, "Miles Davis", 1959, 5, 19);
        recordShopRepository.save(testAlbum);
        recordShopRepository.save(testAlbum2);
        List<Album> allAlbums = (List<Album>) recordShopRepository.findAll();
        assertNotNull(allAlbums);
        assertEquals(2, allAlbums.size());
        assertTrue(allAlbums.contains(testAlbum));
        assertTrue(allAlbums.contains(testAlbum2));
    }

    @Test
    @DisplayName("recordShopRepository.findById() method returns Album of given id")
    public void testRecordShopRepositoryFindByIdMethod(){
        recordShopRepository.save(testAlbum);
        Optional<Album> getAlbumById = recordShopRepository.findById(testAlbum.getId());
        Album albumFound = getAlbumById.get();
        assertNotNull(albumFound);
        assertEquals("Nevermind", albumFound.getTitle());
        assertEquals(Genre.ROCK, albumFound.getGenre());
        assertEquals("Nirvana", albumFound.getArtist());
        assertEquals(1991, albumFound.getDateReleased());
        assertEquals(15, albumFound.getPrice());
        assertEquals(100, albumFound.getStock());
        assertEquals(testAlbum.getId(), albumFound.getId());
    }

    @Test
    @DisplayName("recordShopRepository.deleteById() method deletes Album of given id")
    public void testRecordShopRepositoryDeleteByIdMethod(){
        recordShopRepository.save(testAlbum);
        recordShopRepository.deleteById(testAlbum.getId());
        Optional<Album> deletedAlbum = recordShopRepository.findById(testAlbum.getId());
        assertThat(deletedAlbum.isEmpty());
    }
}


