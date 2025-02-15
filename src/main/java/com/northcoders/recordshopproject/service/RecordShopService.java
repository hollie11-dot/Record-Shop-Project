package com.northcoders.recordshopproject.service;

import com.northcoders.recordshopproject.model.Album;

import java.util.List;

public interface RecordShopService {

    List<Album> getAllAlbums(String artist);
    Album addAlbum(Album album);
    Album getAlbumById(Long albumID);
    Album updateAlbum(Album album, Long albumID);
    void deleteAlbum(Long albumID);
}
