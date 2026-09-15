package com.spotifum.api;

import com.spotifum.api.model.Album;
import com.spotifum.api.model.Song;
import com.spotifum.api.repository.AlbumRepository;
import com.spotifum.api.repository.SongRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    private final AlbumRepository albumRepository;
    private final SongRepository songRepository;

    public DataSeeder(AlbumRepository albumRepository, SongRepository songRepository) {
        this.albumRepository = albumRepository;
        this.songRepository = songRepository;
    }

    @Override
    public void run(String... args) {
        if (albumRepository.count() > 0) return;

        Album album1 = new Album();
        album1.setTitle("Dark Side of the Moon");
        album1.setArtist("Pink Floyd");
        album1.setYear(1973);
        albumRepository.save(album1);

        Album album2 = new Album();
        album2.setTitle("Thriller");
        album2.setArtist("Michael Jackson");
        album2.setYear(1982);
        albumRepository.save(album2);

        Song s1 = new Song();
        s1.setTitle("Money");
        s1.setArtist("Pink Floyd");
        s1.setGenre("Rock");
        s1.setDurationSeconds(382);
        s1.setLabel("Harvest");
        s1.setAlbum(album1);
        songRepository.save(s1);

        Song s2 = new Song();
        s2.setTitle("Time");
        s2.setArtist("Pink Floyd");
        s2.setGenre("Rock");
        s2.setDurationSeconds(421);
        s2.setLabel("Harvest");
        s2.setAlbum(album1);
        songRepository.save(s2);

        Song s3 = new Song();
        s3.setTitle("Thriller");
        s3.setArtist("Michael Jackson");
        s3.setGenre("Pop");
        s3.setDurationSeconds(358);
        s3.setLabel("Epic");
        s3.setAlbum(album2);
        songRepository.save(s3);

        Song s4 = new Song();
        s4.setTitle("Billie Jean");
        s4.setArtist("Michael Jackson");
        s4.setGenre("Pop");
        s4.setDurationSeconds(294);
        s4.setLabel("Epic");
        s4.setAlbum(album2);
        songRepository.save(s4);

        Song s5 = new Song();
        s5.setTitle("Lose Yourself");
        s5.setArtist("Eminem");
        s5.setGenre("Hip-Hop");
        s5.setDurationSeconds(326);
        songRepository.save(s5);
    }
}