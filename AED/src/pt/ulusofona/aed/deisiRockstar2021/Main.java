package pt.ulusofona.aed.deisiRockstar2021;

import java.io.*;
import java.util.*;

public class Main {

    static LinkedHashMap<String, Song> songs = new LinkedHashMap<>();
    static ParseInfo pSongs = new ParseInfo();
    static ParseInfo pArtists = new ParseInfo();
    static ParseInfo pDetails = new ParseInfo();

    static ArrayList<Tag> tagsList = new ArrayList<>();
    static LinkedHashMap<String, Artista> artistas = new LinkedHashMap<>();
    static TreeMap<String, Integer> temasDosArtistas = new TreeMap<>();
    // static ArrayList<Artista> artistasList = new ArrayList<>();

    public static void loadFiles(String filenameSongs, String filenameDetails, String filenameArtists) throws IOException {
        songs.clear();
        artistas.clear();
        tagsList.clear();
        temasDosArtistas.clear();
        pSongs = new ParseInfo();
        pArtists = new ParseInfo();
        pDetails = new ParseInfo();

        LoadSongs.loadSongs(filenameSongs);
        LoadArtists.loadArtists(filenameArtists);
        LoadDetails.loadDetails(filenameDetails);
    }

    static void loadFiles() throws IOException {
        loadFiles("songs.txt", "song_details.txt", "song_artists.txt");
    }

    public static ArrayList<Song> getSongs() {
        Collection<Song> sgs = songs.values();
        return new ArrayList<>(sgs);
    }

    public static ParseInfo getParseInfo(String fileName) {
        switch (fileName) {
            case "songs.txt":
                return pSongs;
            case "song_artists.txt":
                return pArtists;
            case "song_details.txt":
                return pDetails;
            default:
                return null;
        }
    }

    public static String execute(String command) {
        String[] data = command.split(" ");
        StringBuilder result = new StringBuilder();
        switch (data[0].trim()) {
            case "COUNT_SONGS_YEAR":
                int ano = Integer.parseInt(data[1].trim()), songCount = QueryOBG.songs_count_year(ano);
                result.append(songCount);
                break;
            case "GET_ARTISTS_FOR_TAG":
                String tag = data[1].trim().toUpperCase();
                result = new StringBuilder(QueryOBG.get_artists_for_tag(tag));
                break;
            case "ADD_TAGS": {
                String[] info = command.split(";");
                Artista art = new Artista(info[0].replace("ADD_TAGS ", "").trim());
                ArrayList<Tag> tags = new ArrayList<>(QueryOBG.readTagsAdd(command));
                if (QueryOBG.add_tag(art, tags)) { // Se as tags tiverem sido adicionadas, mostrar tags do artista
                    result = new StringBuilder(QueryOBG.get_tags_for_artist(art));
                } else { result = new StringBuilder("Inexistent artist");
                }break; }
            case "REMOVE_TAGS": {
                String[] info = command.split(";");
                String[] tags = QueryOBG.readTagsRemove(command);
                Artista art = new Artista(info[0].replace("REMOVE_TAGS ", "").trim());
                if (QueryOBG.remove_tag(art, tags)) { // Se as tags tiverem sido removidas, mostrar tags atuais do artista
                    result = new StringBuilder(QueryOBG.get_tags_for_artist(art));
                } else { result = new StringBuilder("Inexistent artist");}
                break; }
            case "GET_MOST_DANCEABLE":
                int anoInicio = Integer.parseInt(data[1].trim()), anoFim = Integer.parseInt(data[2].trim());
                int top = Integer.parseInt(data[3].trim());
                result = new StringBuilder(QueryOBG.get_most_dancable(anoInicio, anoFim, top));
                break;
            case "COUNT_DUPLICATE_SONGS_YEAR": {
                int year = Integer.parseInt(data[1].trim());
                result = new StringBuilder(Query.get_duplicate_songs_year(year));
                break; }
            case "GET_ARTISTS_ONE_SONG": {
                anoInicio = Integer.parseInt(data[1].trim());
                anoFim = Integer.parseInt(data[2].trim());
                result = new StringBuilder(Query.get_artists_one_song(anoInicio, anoFim));
                break; }
            case "GET_TOP_ARTISTS_WITH_SONGS_BETWEEN": {
                int num = Integer.parseInt(data[1].trim()), a = Integer.parseInt(data[2].trim());
                int b = Integer.parseInt(data[3].trim());
                result = new StringBuilder(Query.get_top_artists_with_songs_between(num, a, b));
                break; }
            case "MOST_FREQUENT_WORDS_IN_ARTIST_NAME": {
                int num = Integer.parseInt(data[1].trim()),temas = Integer.parseInt(data[2].trim());
                int tamanho = Integer.parseInt(data[3].trim());
                result = new StringBuilder(Query.most_frequent_words_in_artist_name(num, temas, tamanho));
                break; }
            case "GET_UNIQUE_TAGS": {
                result = new StringBuilder(Query.get_unique_tags());
                break; }
            case "GET_UNIQUE_TAGS_IN_BETWEEN_YEARS": {
                int ano1 = Integer.parseInt(data[1].trim()), ano2 = Integer.parseInt(data[2].trim());
                result = new StringBuilder(Query.get_unique_tags_in_between_years(ano1, ano2));
                break; }
            case "GET_RISING_STARS": {
                int ano1 = Integer.parseInt(data[1].trim());
                int ano2 = Integer.parseInt(data[2].trim());
                String ord = data[3].trim();
                result = new StringBuilder(Query.get_rising_stars(ano1, ano2, ord));
                break; }
            case "CLEANUP": {
                result = new StringBuilder(Query.cleanup());
                break; }
            case "TOP_5_SONGS_VIVACITY": {
                int year = Integer.parseInt(data[1].trim());
                double vivacidade = Double.parseDouble(data[2].trim());
                result = new StringBuilder(CreativeQuery.top_5_songs_vivacity(year, vivacidade));
                break; }
            default:
                result = new StringBuilder("Illegal command. Try again");
                break; }
        return result.toString(); }


    public static String getCreativeQuery() {
        return"TOP_5_SONGS_VIVACITY";
    }


    public static int getTypeOfSecondParameter() {
        return 3;
    }

    public static String getVideoUrl() {
        return "https://youtu.be/g-zmoM3NnQM";
    }

    public static void main(String[] args) {
        long start, end;

        try {
            loadFiles();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Welcome to DEISI Rockstar!");
        Scanner in = new Scanner(System.in);
        String line = in.nextLine();
        while (line != null && !line.equals("KTHXBYE")) {
            start = System.currentTimeMillis();
            System.out.println(execute(line));
            end = System.currentTimeMillis();
            System.out.println("(took " + (end - start) + " ms)");
            line = in.nextLine();
        }
    }

    /*
     *****************************
     *****************************
     ****** NÃO ABRIR TXTs *******
     ***** DURANTE CODEw/ME ******
     ***** OU EU VOU CHORAR ******
     *****************************
     *****************************
     */
}
