package com.northcoders.recordshopproject.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Entity
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    @Column
    private String title;

    @Column
    private Genre genre;

    @Column
    private String artist;

    @Column(name = "release_year")
    private int dateReleased;

    @Column
    private double price;

    @Column
    private int stock;

    public Album(){}

    public Album(String title, Genre genre, String artist, int dateReleased, double price, int stock) {
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

    public int getDateReleased() {
        return dateReleased;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }
}
