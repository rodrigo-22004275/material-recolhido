package pt.ulusofona.aed.deisiRockstar2021;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class LoadDetails {

    public static void loadDetails(String filenameDetails) {
        try {
            File ficheiro = new File(filenameDetails);
            FileInputStream fis = new FileInputStream(ficheiro);
            Scanner leitorFicheiro = new Scanner(fis);
            // enquanto o ficheiro tiver linhas não-lidas
            while(leitorFicheiro.hasNextLine()) {
                boolean ignorar = false;
                // ler uma linha do ficheiro
                String linhaDetails = leitorFicheiro.nextLine();
                // partir a linha no caractere separador
                String[] dadosDetailsTemp = linhaDetails.split("@");
                String[] dadosDetails = new String[dadosDetailsTemp.length];

                if (dadosDetailsTemp.length != 7) {
                    Main.pDetails.ignoradas++;
                    continue;
                }

                for(int i = 0; i < dadosDetailsTemp.length; i++) {
                    dadosDetails[i] = dadosDetailsTemp[i].trim();
                    if (dadosDetails[i].isEmpty()) {
                        Main.pDetails.ignoradas++;
                        ignorar = true;
                        break;
                    }
                }

                if (!ignorar) {
                    ignorar = true;
                    String id = dadosDetailsTemp[0].trim();
                    if (Main.songs.containsKey(id) && (Integer.parseInt(dadosDetailsTemp[2].trim()) == 0 || Integer.parseInt(dadosDetailsTemp[2].trim()) == 1)) {
                        if (Main.songs.get(id).duracao == -1) {
                            Main.songs.get(id).duracao = Integer.parseInt(dadosDetailsTemp[1].trim());
                            Main.songs.get(id).explicita = Integer.parseInt(dadosDetailsTemp[2].trim()) != 0;
                            Main.songs.get(id).popularidade = Integer.parseInt(dadosDetailsTemp[3].trim());
                            Main.songs.get(id).dancabilidade = Double.parseDouble(dadosDetailsTemp[4].trim());
                            Main.songs.get(id).vivacidade = Double.parseDouble(dadosDetailsTemp[5].trim());
                            Main.songs.get(id).volume = Double.parseDouble(dadosDetailsTemp[6].trim());
                            ignorar = false;
                        }
                    }
                    if (ignorar) {
                        Main.pDetails.ignoradas++;
                    } else {
                        Main.pDetails.ok++;
                    }
                }
            }
            leitorFicheiro.close();
        } catch(FileNotFoundException exception) {
            String mensagem = "Erro: o ficheiro " + filenameDetails + " não foi encontrado.";
            System.out.println(mensagem);
        }
    }
}
