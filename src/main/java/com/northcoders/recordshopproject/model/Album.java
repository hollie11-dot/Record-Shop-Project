package com.northcoders.recordshopproject.model;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;

@Entity
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    @Column (nullable = false)
    private String title;

    @Column (nullable = false)
    private Genre genre;

    @Column (nullable = false)
    private String artist;

    @Column(name = "release_year", nullable = false)
    private Integer dateReleased;

    @Column (nullable = false)
    private Double price;

    @Column (nullable = false)
    private Integer stock;

    public Album(){}

    public Album(String title, Genre genre, String artist, int dateReleased, double price, int stock) {
        this.title = title;
        this.genre = genre;
        this.artist = artist;
        this.dateReleased = dateReleased;
        this.price = price;
        this.stock = stock;
    }

    public Album(Long id, String title, Genre genre, String artist, int dateReleased, double price, int stock) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.artist = artist;
        this.dateReleased = dateReleased;
        this.price = price;
        this.stock = stock;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Genre getGenre() {
        return genre;
    }

    public String getArtist() {
        return artist;
    }

    public Integer getDateReleased() {
        return dateReleased;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setDateReleased(int dateReleased) {
        this.dateReleased = dateReleased;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
