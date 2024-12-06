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
    public Album getAlbumById(Long albumID) {
        Optional<Album> album = recordShopRepository.findById(albumID);
        return album.orElse(null);
    }
}
