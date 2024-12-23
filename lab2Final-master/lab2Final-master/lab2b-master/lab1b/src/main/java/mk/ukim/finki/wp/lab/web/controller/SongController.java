package mk.ukim.finki.wp.lab.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import mk.ukim.finki.wp.lab.model.Album;
import mk.ukim.finki.wp.lab.model.Artist;
import mk.ukim.finki.wp.lab.model.Song;
import mk.ukim.finki.wp.lab.model.exceptions.InvalidAlbumIdException;
import mk.ukim.finki.wp.lab.service.AlbumService;
import mk.ukim.finki.wp.lab.service.ArtistService;
import mk.ukim.finki.wp.lab.service.SongService;
import org.h2.engine.Mode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class SongController {

    private final SongService songService;
    private final AlbumService albumService;
    private final ArtistService artistService;

    public SongController(SongService songService, AlbumService albumService, ArtistService artistService) {
        this.songService = songService;
        this.albumService = albumService;
        this.artistService = artistService;
    }

    @GetMapping("/songs")
    public String getSongsPage(@RequestParam(required = false) String error, Model model) {

        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }


        model.addAttribute("songs", this.songService.listSongs());

        return "listSongs";
    }


    @GetMapping("/songs/add-form")
    public String getAddSongPage(Model model){
        List<Artist> artists = this.artistService.listArtists();
        List<Album> albums = this.albumService.findAll();
        model.addAttribute("artists", artists);
        model.addAttribute("albums", albums);
        return "add-song";
    }

    @PostMapping("/songs/add")
    public String saveSong(@RequestParam(required = false) String title,
                           @RequestParam(required = false) Long trackId,
                           @RequestParam(required = false) String genre,
                           @RequestParam(required = false) Integer releaseYear,
                           @RequestParam(required = false) Long albumId){

        Album album = albumService.findById(albumId).orElseThrow(() -> new InvalidAlbumIdException(albumId));

        if(trackId == null){
            // Adding new song
            songService.save(title, genre, releaseYear, album);
            return "redirect:/songs";
        }

        // Updating existing song
        Song song = songService.findSongById(trackId);
        song.setTitle(title);
        song.setGenre(genre);
        song.setReleaseYear(releaseYear);
        song.setAlbum(album);
        songService.save(song);  // Save the updated song
        return "redirect:/songs";
    }

    @GetMapping("/songs/delete/{id}")
    public String deleteSong(@PathVariable Long id){
        this.songService.deleteById(id);
        return "redirect:/songs";
    }

    @GetMapping("/songs/edit-form/{id}")
    public String getEditSongForm(@PathVariable Long id, Model model){
        Song song = this.songService.findSongById(id);
        List<Artist> artists = this.artistService.listArtists();
        List<Album> albums = this.albumService.findAll();
        model.addAttribute("song", song); // Pre-populate form with song details
        model.addAttribute("artists", artists);
        model.addAttribute("albums", albums);
        return "add-song";
    }

    @PostMapping("/songs/edit/{songId}")
    public String editSong(@PathVariable Long songId,
                           @RequestParam(required = false) String title,
                           @RequestParam(required = false) String genre,
                           @RequestParam(required = false) Integer releaseYear,
                           @RequestParam(required = false) Long albumId){
        Song song = this.songService.findSongById(songId);
        song.setTitle(title);
        song.setGenre(genre);
        song.setReleaseYear(releaseYear);
        song.setAlbum(albumService.findById(albumId).orElseThrow(() -> new InvalidAlbumIdException(albumId)));
        songService.save(song);  // Save the updated song
        return "redirect:/songs";
    }
    @PostMapping("/songDetails")
    public String songDetails(@RequestParam Long trackId, @RequestParam Long artistId, Model model) {

        Song song = songService.findSongById(trackId);
        Artist artist = artistService.findById(artistId)
                .orElseThrow(() -> new RuntimeException("Artist not found with id: " + artistId));

        song.addArtist(artist);
        System.out.println("Saving song: " + song.getTitle() + " with artist: " + artist.getFirstName());
        songService.save(song);

        model.addAttribute("selectedSong", song);
        return "songDetails";
    }

    @PostMapping("/songs/search")
    public String searchSongs(@RequestParam(name = "albumName")String albumName, Model model){
        List<Song> founded = songService.searchSongsByAlbum(albumName);
        model.addAttribute("songs", founded);
        return "listSongs";
    }

    @PostMapping("/songs/searchYear")
    public String searchSongsYear(@RequestParam(name = "albumYear")Integer albumYear, Model model){
        List<Song> founded = songService.searchSongsByReleaseYear(albumYear);
        model.addAttribute("songs", founded);
        return "listSongs";
    }


}
