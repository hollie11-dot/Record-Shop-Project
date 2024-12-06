package com.northcoders.recordshopproject.service;

import com.northcoders.recordshopproject.model.Album;

import java.util.List;
import java.util.Optional;

public interface RecordShopService {

    List<Album> getAllAlbums();
    Album addAlbum(Album album);
    Album getAlbumById(Long albumID);
}
