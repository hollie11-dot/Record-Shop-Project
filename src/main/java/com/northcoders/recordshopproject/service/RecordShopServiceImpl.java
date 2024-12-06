package com.northcoders.recordshopproject.service;

import com.northcoders.recordshopproject.model.Album;
import com.northcoders.recordshopproject.repository.RecordShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        return recordShopRepository.save(album);
    }

    @Override
    public Optional<Album> getAlbumById(Long albumID) {
        return recordShopRepository.findById(albumID);
    }

    @Override
    public Album updateAlbum (Album album, Long albumID){
        Album albumToUpdate = recordShopRepository.findById(albumID).get();
        albumToUpdate.setArtist(album.getArtist());
        albumToUpdate.setGenre(album.getGenre());
        albumToUpdate.setDateReleased(album.getDateReleased());
        albumToUpdate.setPrice(album.getPrice());
        albumToUpdate.setStock(album.getStock());
        return recordShopRepository.save(albumToUpdate);
    }
}
