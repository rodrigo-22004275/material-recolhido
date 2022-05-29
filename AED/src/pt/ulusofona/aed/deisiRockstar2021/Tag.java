package pt.ulusofona.aed.deisiRockstar2021;

import java.util.ArrayList;

public class Tag {

    String nome;
    ArrayList<Artista> artistas ;

    Tag(){}

    Tag(String nome, Artista artista){
        this.nome = nome;
        this.artistas = new ArrayList<>();
        this.artistas.add(artista);
    }

    Tag(String nome, ArrayList<Artista> artistas){
        this.nome = nome;
        this.artistas = artistas;
    }


}
