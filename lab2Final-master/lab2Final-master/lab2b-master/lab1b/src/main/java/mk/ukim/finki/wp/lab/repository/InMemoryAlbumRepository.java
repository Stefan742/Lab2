package mk.ukim.finki.wp.lab.repository;

import jakarta.annotation.PostConstruct;
import mk.ukim.finki.wp.lab.model.Album;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class InMemoryAlbumRepository {
    static List<Album> albums = new ArrayList<Album>(5);

    @PostConstruct
    public void init(){
        albums.add(new Album(1L, "Thriller", "Pop", "1982"));
        albums.add(new Album(2L, "Back in Black", "Rock", "1980"));
        albums.add(new Album(3L, "The Dark Side of the Moon", "Progressive Rock", "1973"));
        albums.add(new Album(4L, "Abbey Road", "Rock", "1969"));
        albums.add(new Album(5L, "Hotel California", "Rock", "1976"));
    }
    public List<Album> findAll(){
        return albums;
    }

    public Album findById(Long id){
        return albums.stream().filter(album -> album.getId().equals(id)).findFirst().orElse(null);
    }
}
