package com.northcoders.recordshopproject.service;

import com.northcoders.recordshopproject.model.Album;
import com.northcoders.recordshopproject.repository.RecordShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class RecordShopServiceImpl implements RecordShopService {

    @Autowired
    RecordShopRepository recordShopRepository;

    @Override
    public List<Album> getAllAlbums(String artist, Integer year) {
        List<Album> albumList = new ArrayList<>();
        if(artist != null){
            albumList.addAll(recordShopRepository.findByArtist(artist));
        }
        else if(year != null){
            albumList.addAll(recordShopRepository.findByDateReleased(year));
        }
        else {
        recordShopRepository.findAll().forEach(albumList::add); }
        return albumList;
    }

    @Override
    public Album addAlbum(Album album) {
        return recordShopRepository.save(album);
    }

    @Override
    @Cacheable("albums")
    public Album getAlbumById(Long albumID) {
        return recordShopRepository.findById(albumID)
                .orElseThrow(NoSuchElementException::new);
    }

    @Override
    @CacheEvict(cacheNames = "albums", key= "#albumID")
    public Album updateAlbum(Album album, Long albumID){
        Album albumToUpdate = recordShopRepository.findById(albumID).get();
        albumToUpdate.setTitle(album.getTitle());
        albumToUpdate.setGenre(album.getGenre());
        albumToUpdate.setArtist(album.getArtist());
        albumToUpdate.setDateReleased(album.getDateReleased());
        albumToUpdate.setPrice(album.getPrice());
        albumToUpdate.setStock(album.getStock());
        return recordShopRepository.save(albumToUpdate);
    }

    @Override
    public void deleteAlbum(Long albumID){
        if(recordShopRepository.existsById(albumID)){
            recordShopRepository.deleteById(albumID);
        } else {
            throw new NoSuchElementException();
        }
    }

}
