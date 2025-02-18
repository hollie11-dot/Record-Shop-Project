package com.northcoders.recordshopproject.repository;

import com.northcoders.recordshopproject.model.Album;
import com.northcoders.recordshopproject.model.Genre;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecordShopRepository extends CrudRepository<Album, Long> {
    List<Album> findByArtist (String artist);
    List<Album> findByDateReleased (Integer year);
    List<Album> findByGenre (Genre genre);
}
