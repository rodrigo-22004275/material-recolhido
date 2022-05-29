package pt.ulusofona.aed.deisiRockstar2021;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class LoadSongs {

    public static void loadSongs(String filenameSongs) {
        boolean emBranco = false, ignorar;
        try {
            FileReader fr = new FileReader(filenameSongs);
            BufferedReader reader = new BufferedReader(fr);
            String linha;
            while((linha = reader.readLine()) != null) {
                // partir a linha no caractere separador
                String[] dados = linha.split("@");
                ignorar = false;

                // Ver se tem campos em branco
                for (String dado : dados) {
                    if (dado.trim().equals("")) {
                        emBranco = true;
                        break;
                    }
                }

                if (dados.length == 3
                        && Integer.parseInt(dados[2].trim()) <= 2021
                        && Integer.parseInt(dados[2].trim()) >= 0
                        && dados[2].trim().matches("[-+]?\\d*\\.?\\d+")
                        && !emBranco) {
                    // Guardar dados
                    String id = dados[0].trim();
                    String titulo = dados[1].trim();
                    int ano = Integer.parseInt(dados[2].trim());

                    if (Main.songs.containsKey(id)) {
                        ignorar = true;
                    } else if(titulo.startsWith("\"\"") && titulo.endsWith("\"\"")) {
                        titulo.replace("\"\"", "");
                    } else if(titulo.startsWith("\"\"\"") && titulo.endsWith("\"\"\"")){
                        titulo.replace("\"\"\"", "\"");
                    }

                    if (ignorar) {
                        Main.pSongs.ignoradas++;
                    } else {
                        // criar o obj pt.ulusofona.aed.deisiRockstar2021.Song
                        Song song = new Song(id, titulo, new ArrayList<>(), ano, -1, false, 0, 0,0 ,0);

                        //Adicionar à lista de songs
                        Main.songs.put(id, song);
                        Main.pSongs.ok++;
                    }

                } else {
                    Main.pSongs.ignoradas++;
                }
            }
            reader.close();
        } catch(FileNotFoundException e) {
            System.out.println("Ficheiro não encontrado");
        } catch(IOException e) {
            System.out.println("Ocorreu um erro durante a leitura.");
        }
    }
}
