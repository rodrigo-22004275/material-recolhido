package pt.ulusofona.aed.deisiRockstar2021;

import static org.junit.Assert.*;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

public class TestesMain {

    @Test
    public void testeParseInfo() throws IOException {
        Main.loadFiles("test-files/songs.txt", "test-files/song_details.txt", "test-files/song_artists.txt");
        String real = Main.songs.get("79STtcr7TWvQ5ab9aixtVI").toString();
        String esperado = "79STtcr7TWvQ5ab9aixtVI | Beatles (George Harrison) - Original Album Track | 1984 | 6:45 | 38.0 | Stars On 45 | (1)";
        assertEquals("Resultado deveria ser '79STtcr7TWvQ5ab9aixtVI | Beatles (George Harrison) - Original Album Track | 1984 | 6:45 | 38.0 | Stars On 45 | (1)'", esperado, real);
    }

    @Test
    public void testeParseInfo2() throws IOException {
        Main.loadFiles("test-files/songs.txt", "test-files/song_details.txt", "test-files/song_artists.txt");
        String real = Main.songs.get("4pMaLIF1KEGEUvKpRoqbvc").toString();
        String esperado = "4pMaLIF1KEGEUvKpRoqbvc | Kapitel 125 - Der Page und die Herzogin | 1926 | 2:6 | 1.0 | Georgette Heyer / Irina Salkow | (1 / 1)";
        assertEquals("Resultado deveria ser '4pMaLIF1KEGEUvKpRoqbvc | Kapitel 125 - Der Page und die Herzogin | 1926 | 2:6 | 1.0 | Georgette Heyer / Irina Salkow | (1 / 1)'", esperado, real);
    }

    @Test
    public void teste_songs_count_year() throws IOException {
        Main.loadFiles("test-files/songs.txt", "test-files/song_details.txt", "test-files/song_artists.txt");
        int real = QueryOBG.songs_count_year(2016);
        int esperado = 1;
        assertEquals("Resultado deveria ser '1'", esperado, real);
    }

    @Test
    public void teste_add_tag() throws IOException {
        Main.loadFiles("test-files/songs.txt", "test-files/song_details.txt", "test-files/song_artists.txt");
        ArrayList<Tag> tags = new ArrayList<>();
        tags.add(new Tag("POP", new Artista("The Partridge Family")));
        QueryOBG.add_tag(new Artista("The Partridge Family"), tags);
        String real = QueryOBG.get_tags_for_artist(new Artista("The Partridge Family"));
        String esperado = "The Partridge Family | POP";
        assertEquals("Resultado deveria ser 'The Partridge Family | POP'", esperado, real);
    }

    @Test
    public void teste_remove_tag() throws IOException {
        Main.loadFiles("test-files/songs.txt", "test-files/song_details.txt", "test-files/song_artists.txt");
        String[] tags = new String[1];
        tags[0] = "POP";
        QueryOBG.remove_tag(new Artista("The Partridge Family"), tags);
        String real = QueryOBG.get_tags_for_artist(new Artista("The Partridge Family"));
        String esperado = "The Partridge Family | No tags";
        assertEquals("Resultado deveria ser 'The Partridge Family | No tags'", esperado, real);
    }

    @Test
    public void teste_get_duplicate_songs_year() throws IOException {
        Main.loadFiles("test-files/songs.txt", "test-files/song_details.txt", "test-files/song_artists.txt");
        String real = Query.get_duplicate_songs_year(1920);
        String esperado = "0";
        assertEquals("Resultado deveria ser '0'", esperado, real);
    }

    @Test
    public void teste_get_artists_for_tag() throws IOException {
        Main.loadFiles("test-files/songs.txt", "test-files/song_details.txt", "test-files/song_artists.txt");
        ArrayList<Tag> tags = new ArrayList<>();
        tags.add(new Tag("POP", new Artista("The Partridge Family")));
        QueryOBG.add_tag(new Artista("The Partridge Family"), tags);
        String real = QueryOBG.get_artists_for_tag("POP");
        String esperado = "The Partridge Family";
        assertEquals("Resultado deveria ser 'The Partridge Family'", esperado, real);
    }

    @Test
    public void teste_get_top_artists_with_songs_between() throws IOException {
        Main.loadFiles("test-files/songs.txt", "test-files/song_details.txt", "test-files/song_artists.txt");
        String real = Query.get_top_artists_with_songs_between(5, 1930, 2016);
        String esperado = "No results";
        assertEquals("Resultado deveria ser 'No results'", esperado, real);
    }

    @Test
    public void get_artists_one_song() throws IOException {
        Main.loadFiles("test-files/songs.txt", "test-files/song_details.txt", "test-files/song_artists.txt");
        String real = Query.get_artists_one_song(1920, 1927);
        String esperado = "Georgette Heyer | Kapitel 125 - Der Page und die Herzogin | 1926\nIrina Salkow | Kapitel 125 - Der Page und die Herzogin | 1926\n";
        assertEquals("Resultado deveria ser" + esperado, esperado, real);
    }

    @Test
    public void teste_get_unique_tags() throws IOException {
        Main.loadFiles("test-files/songs.txt", "test-files/song_details.txt", "test-files/song_artists.txt");
        ArrayList<Tag> tags = new ArrayList<>();
        tags.add(new Tag("POP", new Artista("The Partridge Family")));
        tags.add(new Tag("ROCK", new Artista("Asian Tradition Universe")));
        QueryOBG.add_tag(new Artista("The Partridge Family"), tags);
        String real = Query.get_unique_tags();
        String esperado = "POP 1\nROCK 1\n";
        assertEquals("Resultado deveria ser 'POP 1 [new_line] ROCK 1 [new_line]'", esperado, real);
    }

    @Test
    public void teste_cleanup() throws IOException {
        Main.loadFiles("test-files/songs.txt", "test-files/song_details.txt", "test-files/song_artists.txt");
        String real = Query.cleanup();
        String esperado = "Musicas removidas: 1; Artistas removidos: 1";
        assertEquals("Resultado deveria ser 'Musicas removidas: 1; Artistas removidos: 1'", esperado, real);
    }

    @Test
    public void teste_get_unique_tags_in_between_years() throws IOException {
        Main.loadFiles("test-files/songs.txt", "test-files/song_details.txt", "test-files/song_artists.txt");
        String real = Query.get_unique_tags_in_between_years(1920, 2016);
        String esperado = "No results";
        assertEquals("Resultado deveria ser 'No results'", esperado, real);
    }

    @Test
    public void teste_get_rising_stars() throws IOException {
        Main.loadFiles("test-files/songs.txt", "test-files/song_details.txt", "test-files/song_artists.txt");
        String real = Query.get_rising_stars(1983, 1984, "ASC");
        String esperado = "No results";
        assertEquals("Resultado deveria ser 'No results'", esperado, real);
    }

    @Test
    public void teste_top_5_songs_vivacity() throws IOException {
        Main.loadFiles("test-files/songs.txt", "test-files/song_details.txt", "test-files/song_artists.txt");
        String real = CreativeQuery.top_5_songs_vivacity(1984, 0.100);
        String esperado = "Beatles (George Harrison) - Original Album Track | 38\n";
        assertEquals("Resultado deveria ser 'Beatles (George Harrison) - Original Album Track | 38 [new_line]'", esperado, real);
    }
}
