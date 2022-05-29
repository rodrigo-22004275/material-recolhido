package pt.ulusofona.aed.deisiRockstar2021;

import java.util.ArrayList;

public class QueryOBG {

    public static ArrayList<Artista> quickSortArtistas (ArrayList<Artista> lista){
        if(lista.isEmpty()){
            return lista;
        }
        Artista j;
        ArrayList<Artista> ordenado;
        ArrayList<Artista> maior = new ArrayList<>();
        ArrayList<Artista> menor = new ArrayList<>();
        Artista pivot = lista.get(0);
        for (int i = 1; i<lista.size(); i++){
            j = lista.get(i);
            if (j.nome.compareTo(pivot.nome)<0){
                menor.add(j);
            }else{
                maior.add(j);
            }
        }
        menor = quickSortArtistas (menor);
        maior = quickSortArtistas (maior);
        menor.add(pivot);
        menor.addAll(maior);
        ordenado = menor;
        return ordenado;
    }
    public static int songs_count_year(int ano){
        int count = 0;
        for(Song song : Main.songs.values()){
            if(song.ano == ano){
                count++;
            }
        }
        return count;
    }
    public static String get_artists_for_tag (String tag){
        StringBuilder result = new StringBuilder();
        ArrayList<Artista> artistas = new ArrayList<>();
        for (Tag d : Main.tagsList) {
            if (d.nome.equals(tag)) {
                artistas = d.artistas;
                break;
            }
        }

        ArrayList<Artista> res = QueryOBG.quickSortArtistas(artistas);

        if (res.size() == 0) {
            result = new StringBuilder("No results");
        } else {
            int count = 0;
            for (Artista art : res) {
                if (count > 0) {
                    result.append(";");
                }
                result.append(art.nome);
                count++;
            }
        }

        return result.toString();
    }
    public static String get_most_dancable (int anoInicio, int anoFim, int num) {
        StringBuilder result = new StringBuilder();
        ArrayList<Song> filtered = new ArrayList<>();

        for (Song song : Main.songs.values()) {
            if(song.ano >= anoInicio && song.ano <= anoFim) {
                filtered.add(song);
            }
        }

        filtered.sort((o1, o2) -> Double.compare(o2.dancabilidade, o1.dancabilidade));
        filtered.subList(num, filtered.size()).clear();

        for(Song song : filtered) {
            result.append(song.titulo).append(" : ").append(song.ano).append(" : ").append(song.dancabilidade).append("\n");
        }

        return result.toString();
    }
    public static String get_tags_for_artist(Artista artista) {
        int count = 0;
        StringBuilder result = new StringBuilder(artista.nome + " | ");
        for (Tag tag : Main.tagsList) {
            for(Artista art : tag.artistas) {
                if (art.nome.equals(artista.nome)) {
                    if (count > 0) {
                        result.append(",");
                    }
                    result.append(tag.nome);
                    count++;
                    break;
                }
            }
        }
        if (count == 0) {
            result.append("No tags");
        }
        return result.toString();
    }
    public static boolean add_tag(Artista artista, ArrayList<Tag> tags){
        boolean temp;
        int count = 0;
        for(Artista artist : Main.artistas.values()){
            if(artist.nome.equals(artista.nome)){
                count ++;
            }
        }
        if(count == 0){
            return false;
        }
        count = 0;
        for (Tag tag : tags) {
            temp = false;
            // Procurar no arrayList por esta tag
            for(Tag d : Main.tagsList){
                // Se encontrar, adicionar artista a esta tag
                if(d.nome.equals(tag.nome)) {
                    temp = true;
                    // Verificar se o artista já foi adicionado anteriormente
                    for (Artista art : d.artistas) {
                        if (art.nome.equals(artista.nome)) {
                            count++;
                            break;
                        }
                    }
                    if (count == 0) {
                        d.artistas.add(artista);
                    } else {
                        count = 0;
                    }
                    break;
                }
            }
            // Se não tiver encontrado essa tag, será criada
            if (!temp) {
                Main.tagsList.add(tag);
            }
        }
        return true;
    }
    public static boolean remove_tag (Artista artista, String[] tags){
        int count = 0;
        for(Artista artist : Main.artistas.values()){ //procurar se o artista existe
            if(artist.nome.equals(artista.nome)){
                count ++;
            }
        }
        if(count == 0){
            return false;
        }
        for (String tagName : tags) {
            // Procurar no arrayList por esta tag
            for (Tag tag : Main.tagsList) {
                // Se encontrar, remover artista desta tag
                if (tag.nome.equals(tagName)) {
                    for (int c = 0; c < tag.artistas.size(); c++) {
                        if (tag.artistas.get(c).nome.equals(artista.nome)) {
                            tag.artistas.remove(c);
                        }
                    }
                    break;
                }
            }
        }
        return true;
    }

    public static String[] readTagsRemove(String command){
        String[] info = command.split(";");
        Artista art = new Artista(info[0].replace("REMOVE_TAGS ", "").trim());
        String[] tags = new String[info.length - 1]; // Guardar tags numa String[] para enviar para a Query
        for (int i = 1, c = 0; i < info.length; i++, c++) {
            tags[i - 1] = info[i].trim().toUpperCase();
        }
        return tags;
    }

    public static ArrayList<Tag> readTagsAdd(String command){
        String[] info = command.split(";");
        Artista art = new Artista(info[0].replace("ADD_TAGS ", "").trim());
        ArrayList<Tag> tags = new ArrayList<>(); // Guardar tags num ArrayList<Tag> para enviar para a Query
        for (int i = 1, c = 0; i < info.length; i++, c++) {
            if (!info[i].trim().equals("")) {
                tags.add(new Tag(info[i].trim().toUpperCase(), art));
            }
        }
        return tags;
    }
}
