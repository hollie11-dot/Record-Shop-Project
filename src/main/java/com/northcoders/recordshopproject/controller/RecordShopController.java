package com.northcoders.recordshopproject.controller;

import com.northcoders.recordshopproject.model.Album;
import com.northcoders.recordshopproject.service.RecordShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/recordshop")
public class RecordShopController {

    @Autowired
    private RecordShopService recordShopService;

    @GetMapping
    public ResponseEntity<List<Album>> getAllAlbums(){
        return new ResponseEntity<>(recordShopService.getAllAlbums(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Album> postAlbum(@RequestBody Album album){
        Album addedAlbum = recordShopService.addAlbum(album);
        return new ResponseEntity<>(addedAlbum, HttpStatus.CREATED);
    }

    @GetMapping("{albumID}")
    public ResponseEntity<Album> getAlbum(@PathVariable Long albumID){
        return new ResponseEntity<>(recordShopService.getAlbumById(albumID), HttpStatus.OK);
    }

    @PutMapping ("{albumID}")
    public ResponseEntity<Album> updateAlbum(@PathVariable Long albumID, @RequestBody Album album){
        return new ResponseEntity<>(recordShopService.updateAlbum(album, albumID), HttpStatus.CREATED);
    }

}
