package com.northcoders.recordshopproject.service;

import com.northcoders.recordshopproject.model.Album;

import java.util.List;

public interface RecordShopService {

    List<Album> getAllAlbums();
    Album addAlbum(Album album);
}
