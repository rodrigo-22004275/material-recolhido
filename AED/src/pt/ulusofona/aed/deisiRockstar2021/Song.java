package pt.ulusofona.aed.deisiRockstar2021;

import java.util.ArrayList;

public class Song {
    String id;
    String titulo;
    ArrayList<Artista> artistas;
    int ano;
    int duracao;
    boolean explicita;
    int popularidade;
    double dancabilidade;
    double vivacidade;
    double volume;

    public Song(String id, String titulo, ArrayList<Artista> artistas, int ano, int duracao, boolean explicita, int popularidade, double dancabilidade, double vivacidade, double volume) {
        this.id = id;
        this.titulo = titulo;
        this.artistas = artistas;
        this.ano = ano;
        this.duracao = duracao;
        this.explicita = explicita;
        this.popularidade = popularidade;
        this.dancabilidade = dancabilidade;
        this.vivacidade = vivacidade;
        this.volume = volume;
    }

    public String toString() {
        int count = 0;

        long minutes = (duracao / 1000) / 60;
        long seconds = (duracao / 1000) % 60;
        String tempo = minutes + ":" + seconds;

        String res = id + " | " + titulo + " | " + ano + " | " + tempo + " | " +  popularidade + ".0 | " ;
        String temas = "(";

        for (Artista artista: artistas) {
            if (count > 0) {
                res += " / ";
                temas += " / ";
            }
            res += artista.nome;
            temas += Main.temasDosArtistas.get(artista.nome);
            count++;
        }
        return res + " | " + temas + ")";
    }
}
