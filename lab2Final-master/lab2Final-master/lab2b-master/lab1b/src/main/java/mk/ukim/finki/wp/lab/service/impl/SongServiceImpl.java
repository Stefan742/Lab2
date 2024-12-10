package mk.ukim.finki.wp.lab.service.impl;

import mk.ukim.finki.wp.lab.model.Album;
import mk.ukim.finki.wp.lab.model.Artist;
import mk.ukim.finki.wp.lab.model.Song;
import mk.ukim.finki.wp.lab.repository.jpa.SongRepository;
import mk.ukim.finki.wp.lab.repository.jpa.ArtistRepository;
import mk.ukim.finki.wp.lab.service.SongService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SongServiceImpl implements SongService {
    private final SongRepository songRepository;
    private final ArtistRepository artistRepository;

    public SongServiceImpl(SongRepository songRepository, ArtistRepository artistRepository) {
        this.songRepository = songRepository;
        this.artistRepository = artistRepository;
    }

    @Override
    public List<Song> listSongs() {
        return songRepository.findAll();
    }

    @Override
    public Artist addArtistToSong(Artist artist, Song song) {
        // Set the artist for the song
        song.setArtist(artist);  // Assuming that `setArtist` is a valid method in `Song`
        songRepository.save(song);  // Save the updated song
        return artist;  // Return the artist after adding
    }

    @Override
    public Song findSongById(Long trackId) {
        // Use the default `findById` method to fetch the song
        return songRepository.findById(trackId).orElse(null);  // Returns null if not found
    }
    @Override
    public Song save(Song song) {
        return songRepository.save(song);  // Save the song object
    }
    @Override
    public Optional<Song> save(String title, String genre, Integer releaseYear, Album album) {
        // Create a new song and save it
        Song song = new Song();
        song.setTitle(title);
        song.setGenre(genre);
        song.setReleaseYear(releaseYear);
        song.setAlbum(album);
        return Optional.of(songRepository.save(song));  // Save the new song and return it
    }

    @Override
    public void deleteById(Long id) {
        songRepository.deleteById(id);  // Delete song by ID
    }
}
