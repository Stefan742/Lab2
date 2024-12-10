package mk.ukim.finki.wp.lab.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mk.ukim.finki.wp.lab.model.Artist;
import mk.ukim.finki.wp.lab.model.Song;
import mk.ukim.finki.wp.lab.service.ArtistService;
import mk.ukim.finki.wp.lab.service.SongService;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;
import java.util.List;

@WebServlet(name="AllSongsServlet", urlPatterns = "/allSongs")
public class AllSongsServlet extends HttpServlet {
    private final SpringTemplateEngine springTemplateEngine;
    private final SongService songService;
    private final ArtistService artistService;

    public AllSongsServlet(SpringTemplateEngine springTemplateEngine, SongService songService, ArtistService artistService) {
        this.springTemplateEngine = springTemplateEngine;
        this.songService = songService;
        this.artistService = artistService;
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String artistId;
        if (req.getParameter("artistId") != null) {
            artistId = req.getParameter("artistId");
        } else {
            artistId = "no artistId";
        }

        Artist a = artistService.findById(Long.valueOf(artistId)).orElse(null);
        List<Song> songs = songService.listSongs().stream().filter(s -> s.getPerformers().contains(a)).toList();



        IWebExchange iWebExchange = JakartaServletWebApplication
                .buildApplication(req.getServletContext())
                .buildExchange(req, resp);
        WebContext context = new WebContext(iWebExchange);
        context.setVariable("selectedArtist", a);
        context.setVariable("songs", songs);
        springTemplateEngine.process("allSongs.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String artistId;
        if (req.getParameter("artistId") != null) {
            artistId = req.getParameter("artistId");
        } else {
            artistId = "no artistId";
        }

        Artist a = artistService.findById(Long.valueOf(artistId)).orElse(null);
        List<Song> songs = songService.listSongs().stream().filter(s -> s.getPerformers().contains(a)).toList();



        IWebExchange iWebExchange = JakartaServletWebApplication
                .buildApplication(req.getServletContext())
                .buildExchange(req, resp);
        WebContext context = new WebContext(iWebExchange);
        context.setVariable("selectedArtist", a);
        context.setVariable("songs", songs);
        springTemplateEngine.process("allSongs.html", context, resp.getWriter());
    }
}
