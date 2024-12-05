package com.northcoders.recordshopproject.service;

import com.northcoders.recordshopproject.model.Album;
import com.northcoders.recordshopproject.repository.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AlbumManagerServiceImpl implements AlbumManagerService{

    @Autowired
    AlbumRepository albumRepository;

    @Override
    public List<Album> getAllAlbums() {
        List<Album> albumList = new ArrayList<>();
        albumRepository.findAll().forEach(albumList::add);
        return albumList;
    }
}
