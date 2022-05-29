package pt.ulusofona.aed.deisiRockstar2021;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class LoadArtists {

    public static void loadArtists(String filenameArtists) {
        try {
            File ficheiro = new File(filenameArtists);
            FileInputStream fis = new FileInputStream(ficheiro);
            Scanner leitorFicheiro = new Scanner(fis);
            // enquanto o ficheiro tiver linhas não-lidas
            while(leitorFicheiro.hasNextLine()) {
                // ler uma linha do ficheiro
                String linha = leitorFicheiro.nextLine();
                // partir a linha no caractere separador
                String[] dados = linha.split("@");

                // Variáveis de verificação
                boolean ignorar = false;

                // Guardar dados
                String id = dados[0].trim();
                String[] artistas;
                if (dados.length == 2 && !dados[1].contains("''")) {

                    dados[1] = dados[1].replace("',",",");
                    dados[1] = dados[1].replace(",'",",");
                    dados[1] = dados[1].replace("' ,",",");
                    dados[1] = dados[1].replace(", '",",");
                    artistas = dados[1].split(",");

                    for (int i = 0; i < artistas.length; i++) {
                        artistas[i] = artistas[i].trim();

                        artistas[i] = artistas[i].replace("\"['","");
                        artistas[i] = artistas[i].replace("']\"","");
                        artistas[i] = artistas[i].replace("'[\"","");
                        artistas[i] = artistas[i].replace("\"]'","");
                        artistas[i] = artistas[i].replace("['","");
                        artistas[i] = artistas[i].replace("']","");
                        artistas[i] = artistas[i].replace("'[","[");
                        artistas[i] = artistas[i].replace("]'","]");
                        artistas[i] = artistas[i].replace("\"[","");
                        artistas[i] = artistas[i].replace("]\"","");

                        artistas[i] = artistas[i].replace("\"\"'","");
                        artistas[i] = artistas[i].replace("\"'","");
                        artistas[i] = artistas[i].replace("'\"\"","");
                        artistas[i] = artistas[i].replace("'\"","");
                        artistas[i] = artistas[i].replace("\"\"\"","\"");
                        artistas[i] = artistas[i].replace("\"\"","");

                        if (Main.songs.containsKey(id)) {
                            int count = 0;
                            for (Artista artista : Main.songs.get(id).artistas) {
                                if (artista.nome.equals(artistas[i].trim())) {
                                    count++;
                                    break;
                                }
                            }
                            if (count == 0) {
                                Main.songs.get(id).artistas.add(new Artista(artistas[i].trim()));
                                if (!Main.artistas.containsKey(artistas[i].trim()) && !Main.temasDosArtistas.containsKey(artistas[i].trim())) {
                                    Main.artistas.put(artistas[i].trim(), new Artista(artistas[i].trim()));
                                    Main.temasDosArtistas.put(artistas[i].trim(), 1);
                                } else {
                                    Main.temasDosArtistas.put(artistas[i].trim(), (Main.temasDosArtistas.get(artistas[i].trim()) + 1));
                                }
                            }
                        } else {
                            ignorar = true;
                        }
                    }
                } else {
                    ignorar = true;
                }

                if (ignorar) {
                    Main.pArtists.ignoradas++;
                } else {
                    Main.pArtists.ok++;
                }
            }
            leitorFicheiro.close();
        } catch(FileNotFoundException exception) {
            String mensagem = "Erro: o ficheiro " + filenameArtists + " não foi encontrado.";
            System.out.println(mensagem);
        }
    }

}
