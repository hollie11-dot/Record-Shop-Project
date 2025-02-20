package com.northcoders.recordshopproject.service;

import com.northcoders.recordshopproject.exceptionhandling.AlbumNotFoundException;
import com.northcoders.recordshopproject.exceptionhandling.InvalidInputsException;
import com.northcoders.recordshopproject.model.Album;
import com.northcoders.recordshopproject.model.Genre;
import com.northcoders.recordshopproject.repository.RecordShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecordShopServiceImpl implements RecordShopService {

    @Autowired
    RecordShopRepository recordShopRepository;

    @Override
    public List<Album> getAllAlbums() {
        List<Album> albumList = new ArrayList<>();
        recordShopRepository.findAll().forEach(albumList::add);
        return albumList;
    }

    @Override
    public Album addAlbum(Album album) {
        if(album.getArtist() != null && album.getTitle() != null && album.getGenre() != null && album.getDateReleased() != null && album.getPrice() != null && album.getStock() != null) {
            return recordShopRepository.save(album);
        }
        throw new InvalidInputsException("One ore more incorrect inputs. Please try again.");
    }

    @Override
    @Cacheable("albums")
    public Album getAlbumById(Long albumID) {
        Album albumFound = recordShopRepository.findById(albumID)
                .orElseThrow(() -> new AlbumNotFoundException("There is not an album with that ID. Please try again."));
                return albumFound;
        }

    @Override
    @CacheEvict(cacheNames = "albums", key = "#albumID")
    public Album updateAlbum(Album album, Long albumID) {
        if(!recordShopRepository.existsById(albumID)){
            throw new AlbumNotFoundException("There is not an album with that ID. Please try again.");
        }
        if(album.getArtist() != null && album.getTitle() != null && album.getGenre() != null && album.getDateReleased() != null && album.getPrice() != null && album.getStock() != null) {
            Album albumToUpdate = recordShopRepository.findById(albumID).get();
            albumToUpdate.setTitle(album.getTitle());
            albumToUpdate.setGenre(album.getGenre());
            albumToUpdate.setArtist(album.getArtist());
            albumToUpdate.setDateReleased(album.getDateReleased());
            albumToUpdate.setPrice(album.getPrice());
            albumToUpdate.setStock(album.getStock());
            return recordShopRepository.save(albumToUpdate);
        }
        throw new InvalidInputsException("One ore more incorrect inputs. Please try again.");
    }

    @Override
    public void deleteAlbum(Long albumID) {
        if (recordShopRepository.existsById(albumID)) {
            recordShopRepository.deleteById(albumID);
        } else {
            throw new AlbumNotFoundException("Unable to delete. There is not an album with that ID.");
        }
    }

    @Override
    public List<Album> filterAlbums(String artist, Integer year, Genre genre) {
        List<Album> albumList = new ArrayList<>();
        if (artist != null) {
            albumList.addAll(recordShopRepository.findByArtist(artist));
        } else if (year != null) {
            albumList.addAll(recordShopRepository.findByDateReleased(year));
        } else if (genre != null) {
            albumList.addAll(recordShopRepository.findByGenre(genre));
        }
        if(albumList.isEmpty()){
            throw new AlbumNotFoundException("There are no matching albums.");
        }
        return albumList;
    }
}
