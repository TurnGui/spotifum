package com.spotifum.api.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "songs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String artist;

    @Column(nullable = false)
    private String genre;

    @Column(nullable = false)
    private int durationSeconds;

    private String label;

    @Column(columnDefinition = "TEXT")
    private String lyrics;

    @Column(nullable = false)
    private int playCount = 0;

    @ManyToOne
    @JoinColumn(name = "album_id")
    private Album album;
}