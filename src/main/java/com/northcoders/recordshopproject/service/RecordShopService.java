package com.northcoders.recordshopproject.service;

import com.northcoders.recordshopproject.model.Album;
import com.northcoders.recordshopproject.model.Genre;

import java.util.List;

public interface RecordShopService {

    List<Album> getAllAlbums(String artist, Integer year, Genre genre);
    Album addAlbum(Album album);
    Album getAlbumById(Long albumID);
    Album updateAlbum(Album album, Long albumID);
    void deleteAlbum(Long albumID);
}
