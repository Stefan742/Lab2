package mk.ukim.finki.wp.lab.web.controller;


import mk.ukim.finki.wp.lab.model.Artist;
import mk.ukim.finki.wp.lab.service.ArtistService;
import mk.ukim.finki.wp.lab.service.SongService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.List;
import java.util.Map;

@Controller
public class ArtistController {
    private final ArtistService artistService;
    private final SongService songService;

    public ArtistController(ArtistService artistService, SongService songService) {
        this.artistService = artistService;
        this.songService = songService;
    }

    @GetMapping("/artist")
    public String getArtists(Model model) {
        List<Artist> listedArtists = artistService.listArtists();
        model.addAttribute("listedArtists", listedArtists);
        return "artistList";
    }

    @PostMapping("/artist")
    public String postArtist(@RequestParam(required = false) String trackId,
                             Map<String, Object> model) {
        List<Artist> listedArtists = artistService.listArtists();
        model.put("trackId", trackId);
        model.put("listedArtists", listedArtists);
        return "artistList"; // Refers to the "artistList.html" template
    }}
