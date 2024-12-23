package mk.ukim.finki.wp.lab.service;

import mk.ukim.finki.wp.lab.model.Album;
import mk.ukim.finki.wp.lab.model.Artist;
import mk.ukim.finki.wp.lab.model.Song;

import java.util.List;
import java.util.Optional;

public interface SongService {
    List<Song> listSongs();
    Artist addArtistToSong(Artist artist, Song song);
    Song findSongById(Long trackId);

    public Optional<Song> save(String title, String genre, Integer releaseYear, Album album);

    void deleteById(Long id);

    Song save(Song song);

    List<Song> searchSongsByAlbum(String albumName);

    List<Song> searchSongsByReleaseYear(Integer albumYear);
}
