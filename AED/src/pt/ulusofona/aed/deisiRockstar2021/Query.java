package pt.ulusofona.aed.deisiRockstar2021;

import java.util.*;

public class Query {

    public static String get_duplicate_songs_year(int ano) {
        int count = 0;
        HashMap<String, Integer> songs = new HashMap<>();
        for (Song song : Main.songs.values()) {
            if (song.ano == ano && songs.containsKey(song.titulo)) {
                count++;
            } else if (song.ano == ano && !songs.containsKey(song.titulo)) {
                songs.put(song.titulo, 0);
            }
        }
        return String.valueOf(count);
    }

    public static String get_artists_one_song (int anoInicio, int anoFim) {
        StringBuilder result = new StringBuilder();
        TreeMap<String, Song> artists = new TreeMap<>();
        TreeMap<String, Song> blacklist = new TreeMap<>();

        for (Song song : Main.songs.values()) {
            if (song.ano >= anoInicio && song.ano <= anoFim) {
                for (Artista artist : song.artistas) {
                    if (!artists.containsKey(artist.nome) && !blacklist.containsKey(artist.nome)) {
                        artists.put(artist.nome, song);
                    } else if (artists.containsKey(artist.nome)) {
                        blacklist.put(artist.nome, song);
                        artists.remove(artist.nome);
                    }
                }
            }
        }
        for (Map.Entry<String, Song> res : artists.entrySet()) {
                result.append(res.getKey()).append(" | ").append(res.getValue().titulo).append(" | ").append(res.getValue().ano).append("\n");
        }
        return result.toString();
    }

    public static String get_top_artists_with_songs_between (int n, int a, int b) {
        StringBuilder result = new StringBuilder();
        int count = 0;

        List<Map.Entry<String, Integer>> list = new ArrayList<>(Main.temasDosArtistas.entrySet());
        list.sort(Map.Entry.comparingByValue());
        Collections.reverse(list);

        for (Map.Entry<String, Integer> entry : list) {
            if (count < n && entry.getValue() >= a && entry.getValue() <= b) {
                result.append(entry.getKey()).append(" ").append(entry.getValue()).append("\n");
                count++;
            } else if(count >= n) {
                break;
            }
        }

        if (count == 0) {
            result = new StringBuilder("No results");
        }

        return result.toString();
    }
    /*olha a puta*/
    public static String most_frequent_words_in_artist_name(int num, int temas, int tamanho) {
        StringBuilder result = new StringBuilder();
        TreeMap<String, Integer> palavras = new TreeMap<>();

        for (Artista artista : Main.artistas.values()) {
            String nome = artista.nome;
            if (Main.temasDosArtistas.get(nome) >= temas) {
                String[] dados = nome.split(" ");
                for (String palavra : dados) {
                    palavra = palavra.trim();
                    if (palavras.containsKey(palavra) && palavra.length() >= tamanho) {
                        palavras.put(palavra, (palavras.get(palavra) + 1));
                    } else if(palavra.length() >= tamanho) {
                        palavras.put(palavra, 1);
                    }
                }
            }
        }

        List<Map.Entry<String, Integer>> list = new ArrayList<>(palavras.entrySet());
        list.sort(Map.Entry.comparingByKey());
        Collections.reverse(list);
        list.sort(Map.Entry.comparingByValue());
        Collections.reverse(list);

        if (list.size() >= num) {
            list.subList(num, list.size()).clear();
        }

        list.sort(Map.Entry.comparingByKey());
        list.sort(Map.Entry.comparingByValue());

        for (Map.Entry<String, Integer> entry : list) {
            result.append(entry.getKey()).append(" ").append(entry.getValue()).append("\n");
        }

        return result.toString();
    }

    public static String get_unique_tags() {
        StringBuilder result = new StringBuilder();
        HashMap<String, Integer> map = new HashMap<>();
        for (Tag tag : Main.tagsList) {
            map.put(tag.nome, tag.artistas.size());
        }
        List<Map.Entry<String, Integer>> list = new ArrayList<>(map.entrySet());
        list.sort(Map.Entry.comparingByValue());
        for (Map.Entry<String, Integer> entry : list) {
            result.append(entry.getKey()).append(" ").append(entry.getValue()).append("\n");
        }
        return result.toString();
    }

    public static String cleanup() {
        int artistas = 0;
        int musicas = 0;
        HashMap<String, Song> deleteLater = new HashMap<>();
        for (Song song : Main.songs.values()) {
            if (song.artistas.isEmpty() || song.duracao == -1) {
                for (Artista art : song.artistas) {
                    if(Main.temasDosArtistas.containsKey(art.nome)) {
                        if (Main.temasDosArtistas.get(art.nome) <= 1) {
                            Main.artistas.remove(art.nome);
                            Main.temasDosArtistas.remove(art.nome);
                            artistas++;
                        } else {
                            Main.temasDosArtistas.put(art.nome, (Main.temasDosArtistas.get(art.nome) - 1));
                        }
                    }
                }
                musicas++;
                deleteLater.put(song.id, song);
            }
        }
        for (Song song : deleteLater.values()) {
            Main.songs.remove(song.id);
        }
        return "Musicas removidas: " + musicas + "; Artistas removidos: " + artistas;
    }

    public static String get_unique_tags_in_between_years(int ano1, int ano2) {
        StringBuilder result = new StringBuilder();
        TreeMap<String, Integer> map = new TreeMap<>();
        HashMap<String, Artista> artistas = new HashMap<>();
        for (Song song : Main.songs.values()) {
            if (song.ano >= ano1 && song.ano <= ano2) {
                for (Artista artista : song.artistas) {
                    artistas.put(artista.nome, artista);
                }
            }
        }
        for(Tag tag: Main.tagsList){
            int count = 0;
            for(Artista art : tag.artistas){
                if(artistas.containsKey(art.nome)){
                    count++;
                }
            }
            if(count > 0) {
                map.put(tag.nome, count);
            }
        }

        List<Map.Entry<String, Integer>> list = new ArrayList<>(map.entrySet());
        list.sort(Map.Entry.comparingByValue());
        Collections.reverse(list);

        for (Map.Entry<String, Integer> entry : list) {
                result.append(entry.getKey()).append(" ").append(entry.getValue()).append("\n");

        }
        if(list.isEmpty()){
            return "No results";
        }
        return result.toString();
    }

    static String get_rising_stars(int ano1, int ano2, String ordenacao) {
        String res = "";
        TreeMap<String, ArrayList<Song>> artistasComSongs = new TreeMap<>();
        TreeMap<String, Double> artistasFinal = new TreeMap<>();
        HashMap<String, Integer> artistasAnos = new HashMap<>();
        for (Song song : Main.songs.values()) { if (song.ano >= ano1 && song.ano <= ano2) {
            for (Artista artista : song.artistas) {
                    if (artistasComSongs.containsKey(artista.nome)) { artistasComSongs.get(artista.nome).add(song); } else {
                        ArrayList songs = new ArrayList<Song>();
                        songs.add(song);
                        artistasComSongs.put(artista.nome, songs); }}}}
        HashSet<String> deleteLater = new HashSet<>();
        for (Map.Entry<String, ArrayList<Song>> entry : artistasComSongs.entrySet()) {
            String artista = entry.getKey();
            ArrayList<Song> songs = entry.getValue();
            songs.sort(Comparator.comparingInt(o -> o.ano));
            int periodo = (ano2-ano1) + 1;
            Double[] popularidadesMedias = new Double[periodo];
            int[] popularidadesIndividuais = new int[songs.size()];
            Arrays.fill(popularidadesMedias, 0.0);
            int anoAtual = ano1, index = 0, count = 0, songsDesteAno = 0;
            for (Song song : songs) {
                if (song.ano != anoAtual && artistasAnos.containsKey(artista)) {
                    if (song.ano - anoAtual == 1 && artistasAnos.get(artista) == (song.ano-1)) {
                        popularidadesMedias[index] = popularidadesMedias[index] / songsDesteAno;
                        anoAtual++;
                        index++;
                        songsDesteAno = 0;
                        artistasAnos.put(artista, song.ano);
                    } else {
                        deleteLater.add(artista);
                        artistasAnos.remove(artista);
                        break; } }
                if (popularidadesMedias[index] != 0.0) { popularidadesMedias[index]  = (popularidadesMedias[index] + song.popularidade);
                } else { popularidadesMedias[index] = Double.parseDouble(String.valueOf(song.popularidade)); }
                if (count == songs.size() - 1) {
                    songsDesteAno++;
                    popularidadesMedias[index] = popularidadesMedias[index] / songsDesteAno; }
                artistasAnos.put(artista, song.ano);
                popularidadesIndividuais[count] = song.popularidade;
                count++;
                songsDesteAno++; }
            double popularidade = 0.0;
            if (!deleteLater.contains(artista)) {
                for (int i = 0; i < popularidadesMedias.length; i++) {
                    if (i > 0) { if (popularidadesMedias[i] <= popularidadesMedias[i - 1]) {
                            deleteLater.add(artista);
                            break;
                        } }
                }
                double sum = 0;
                for (int i = 0; i < popularidadesIndividuais.length; i++) { sum += popularidadesIndividuais[i]; }
                popularidade = sum / popularidadesIndividuais.length;
            }
            if (!deleteLater.contains(artista)) { artistasFinal.put(artista, popularidade); }
        }
        if (artistasFinal.size() == 0) { res = "No results"; } else {
            List<Map.Entry<String, Double>> list = new ArrayList<>(artistasFinal.entrySet());
            list.sort(Map.Entry.comparingByKey());
            Collections.reverse(list);
            list.sort((o1, o2) -> Double.compare((int) Math.round(o2.getValue()), (int) Math.round(o1.getValue())));
            if (list.size() >= 15) { list.subList(15, list.size()).clear(); }
            if (ordenacao.equals("ASC")) {
                list.sort(Map.Entry.comparingByKey());
                list.sort(Comparator.comparingDouble(o -> (int) Math.round(o.getValue())));
            }
            for (Map.Entry<String, Double> entry : list) { res += entry.getKey() + " <=> " + entry.getValue() + "\n"; }
        }
        return res;
    }
}