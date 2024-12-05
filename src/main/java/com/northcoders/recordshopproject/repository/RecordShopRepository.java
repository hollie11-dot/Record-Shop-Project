package com.northcoders.recordshopproject.repository;

import com.northcoders.recordshopproject.model.Album;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordShopRepository extends CrudRepository<Album, Long> {
}
