package mk.ukim.finki.wp.lab.bootstrap;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import mk.ukim.finki.wp.lab.model.Album;
import mk.ukim.finki.wp.lab.model.Artist;
import mk.ukim.finki.wp.lab.model.Song;
import mk.ukim.finki.wp.lab.repository.InMemoryAlbumRepository;
import mk.ukim.finki.wp.lab.repository.InMemoryArtistRepository;
import mk.ukim.finki.wp.lab.repository.jpa.AlbumRepository;
import mk.ukim.finki.wp.lab.repository.jpa.ArtistRepository;
import mk.ukim.finki.wp.lab.repository.jpa.SongRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class DataInitializer {
    private  final SongRepository songRepository;
    private final ArtistRepository artistRepository;
    private final AlbumRepository albumRepository;

    @PostConstruct
    public void init(){

        albumRepository.save(new Album(1L, "Thriller", "Pop", "1982"));
        albumRepository.save(new Album(2L, "Back in Black", "Rock", "1980"));
        albumRepository.save(new Album(3L, "The Dark Side of the Moon", "Progressive Rock", "1973"));
        albumRepository.save(new Album(4L, "Abbey Road", "Rock", "1969"));
        albumRepository.save(new Album(5L, "Hotel California", "Rock", "1976"));
        artistRepository.save(new Artist(1L, "Toshe", "Proeski", "Bio1"));
        artistRepository.save(new Artist(2L, "Marija", "Sherifovic", "Bio2"));
        artistRepository.save(new Artist(3L, "Emily", "Johnson", "Bio3"));
        artistRepository.save(new Artist(4L, "Michael", "Jackson", "Bio4"));
        artistRepository.save(new Artist(5L, "Bethoven", " ", "Bio5"));
        List<Artist> allArtists = artistRepository.findAll();
        List<Album> allAlbums = albumRepository.findAll();
        songRepository.save(new Song(1L, "Studena", "Pop", 2003, List.of(allArtists.get(0)), allAlbums.get(0)));
        songRepository.save(new Song(2L, "Molitva", "Pop", 2007, List.of(allArtists.get(1)), allAlbums.get(1)));
        songRepository.save(new Song(3L, "Rhythmic Waves", "Pop", 2020, List.of(allArtists.get(2)), allAlbums.get(2)));
        songRepository.save(new Song(4L, "Eye of the tiger", "Rock", 2022, List.of(allArtists.get(3)), allAlbums.get(3)));
        songRepository.save(new Song(5L, "Soul Voyage", "Soul", 2017, List.of(allArtists.get(4)), allAlbums.get(4)));

    }
}
