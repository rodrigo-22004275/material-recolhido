package pt.ulusofona.aed.deisiRockstar2021;

import java.util.*;

public class CreativeQuery {

    public static String top_5_songs_vivacity(int ano, double vivacidade) { // pede o ano e a vivacida e retorna, em ordem decrescente, as 5 músicas mais populares com vivacidade igula o superior à indicada
        StringBuilder result = new StringBuilder();
        HashMap<String, Integer> musicasPopulares = new HashMap<>();
        int count = 0;

        for(Song song : Main.songs.values()){
            if(song.ano == ano && song.vivacidade >= vivacidade){
                musicasPopulares.put(song.titulo, song.popularidade);
            }
        }

        List<Map.Entry<String, Integer>> list = new ArrayList<>(musicasPopulares.entrySet());
        list.sort(Map.Entry.comparingByValue());
        Collections.reverse(list);

        for (Map.Entry<String, Integer> entry : list) {
            if (count < 5) {
                result.append(entry.getKey()).append(" | ").append(entry.getValue()).append("\n");
                count++;
            }
        }

        if(list.isEmpty()){
            return "No results";
        }

        return result.toString();
    }
}
