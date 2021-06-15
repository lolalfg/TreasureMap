/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package treasuremap;

import dto.Adventurer;
import dto.Mountain;
import dto.Treasure;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author LLAFAGE
 */
public class TreasureMap {

    //Carte
    public static String map;
    public static int mapWidth;
    public static int mapHeight;

    //Liste des montagnes
    public static ArrayList<Mountain> mountainsList = new ArrayList<Mountain>();
    //Liste des trésors
    public static ArrayList<Treasure> treasuresList = new ArrayList<Treasure>();
    //Liste des aventuriers
    public static ArrayList<Adventurer> adventurersList = new ArrayList<Adventurer>();

    public static void main(String[] args) throws IOException {
        //On lit le fichier permettant de récupérer les valeurs
        ReadFile();
        //On initialise le nombre d'aventurier ayant terminé à 0
        int adventurerFinish = 0;

        //Tant que tous les aventuriers non pas terminés leur parcours
        while (adventurerFinish != adventurersList.size()) {
            for (Adventurer adventurer : adventurersList) {
                //On récupère la prochaine action
                String action = adventurer.GetNextAction();
                //Si son parcours n'est pas fini
                if (action != "") {
                    //Si l'action est égale à "A"
                    if (action.equals("A")) {
                        //On récupère la prochaine position
                        int[] nextPosition = adventurer.GetNextPosition();
                        //On vérifie le nombre d'item présent aux coordonnées passées en paramètre
                        List<String> checkFuturePlace = CheckPlace(nextPosition[1], nextPosition[0]);

                        //On vérifie si le prochain emplacement peut être utilisé par un aventurier
                        if (checkFuturePlace.size() == 0) {
                            //On fait bouger notre aventurier
                            adventurer.Move();
                        } else if (checkFuturePlace.contains("T") && !checkFuturePlace.contains("A") && !checkFuturePlace.contains("M")) {
                            //On récupère l'index du trésor
                            int tIndex = IndexOfTreasure(treasuresList, nextPosition[0], nextPosition[1]);
                            //On retire un trésor 
                            treasuresList.get(tIndex).TakeOffTreasure();
                            //On ajoute un trésor à l'aventurier
                            adventurer.AddTreasure();
                            //Si le trésor ne possède plus de trésor alors on le supprime
                            if (treasuresList.get(tIndex).getTreasureCount() == 0) {
                                treasuresList.remove(tIndex);
                            }
                            //On déplace notre aventurier
                            adventurer.Move();
                        }
                    } else {
                        //On fait faire une rotation à notre aventurier
                        adventurer.Rotation(action.equals("D") ? true : false);
                    }
                    //On ajoute l'action réalisé au parcours effectué de l'aventurier
                    adventurer.setEndSequence(adventurer.getEndSequence() + action);
                } else {
                    //On incrémente la variable de compteur afin de savoir si tous les aventurier ont fini leurs parcours
                    adventurerFinish++;
                }
            }
        }
        //Affiche la carte finale
        DisplayMap();
        //Génère le fichier de fin
        GenerateEndFile();
    }

//Méthode permettant d'afficher la dimension
    public static void DisplayMap() {
        for (int i = 0; i < mapHeight; i++) {
            for (int j = 0; j < mapWidth; j++) {
                System.out.print(GetLabelPosition(j, i) + "   ");
            }
            System.out.println();
        }
    }

    //Retourne la liste des items présents aux coordonnées passées en paramètre
    private static List<String> CheckPlace(int horizontalX, int verticalY) {
        List<String> array = new ArrayList<String>();
        //On vérifie les montagnes
        for (Mountain m : mountainsList) {
            if (m.getHorizontalX() == horizontalX && m.getVerticalY() == verticalY) {
                array.add("M");
            }
        }
        //On vérifie les trésors
        for (Treasure t : treasuresList) {
            if (t.getHorizontalX() == horizontalX && t.getVerticalY() == verticalY) {
                array.add("T");
            }
        }
        //On vérifie les aventuriers
        for (Adventurer a : adventurersList) {
            if (a.getHorizontalX() == horizontalX && a.getVerticalY() == verticalY) {
                array.add("A");
            }
        }
        //On retourne le tableau complet
        return array;
    }
    
    //Permet de récupérer l'index d'un trésor dans la liste
    private static int IndexOfTreasure(ArrayList<Treasure> list, int Y, int X) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getHorizontalX() == X && list.get(i).getVerticalY() == Y) {
                return i;
            }
        }
        throw new NullPointerException();
    }
    
    //Récupère le label selon les positions de chaque items
    private static String GetLabelPosition(int horizontalX, int verticalY) {
        String label = "";
        List<String> array = new ArrayList<String>();

        for (Mountain m : mountainsList) {
            if (m.getHorizontalX() == horizontalX && m.getVerticalY() == verticalY) {
                array.add("M");
            }
        }

        for (Treasure t : treasuresList) {
            if (t.getHorizontalX() == horizontalX && t.getVerticalY() == verticalY) {
                array.add("T(" + t.getTreasureCount() + ")");
            }
        }

        for (Adventurer a : adventurersList) {
            if (a.getHorizontalX() == horizontalX && a.getVerticalY() == verticalY) {
                array.add("A(" + a.getName() + ")");
            }
        }

        if (array.size() > 0) {
            for (String s : array) {
                if (!label.equals("")) {
                    label = label + " - ";
                }
                label = label + s;

            }
        } else {
            label = ".";
        }

        return label;
    }
    
    //Génère le fichier de sortie
    private static void GenerateEndFile() throws IOException {
        try {
            File file = new File("endFile.txt");
            FileWriter writer = new FileWriter("endFile.txt");
            writer.write("C - " + mapWidth + " - " + mapHeight);

            for (Mountain m : mountainsList) {
                writer.write("\r\n");
                writer.write("M - " + m.getHorizontalX() + " - " + m.getVerticalY());
            }

            for (Treasure t : treasuresList) {
                writer.write("\r\n");
                writer.write("T - " + t.getHorizontalX() + " - " + t.getVerticalY() + " - " + t.getTreasureCount());
            }

            for (Adventurer a : adventurersList) {
                writer.write("\r\n");
                writer.write("A - " + a.getName() + " - " + a.getHorizontalX() + " - " + a.getVerticalY() + " - " + a.getOrientation() + " - " + a.getTreasureCounter());
            }

            writer.close();

        } catch (IOException ex) {
            System.out.println("Une erreur s'est produite");
        }
    }
    
    //Permet de lire les données reçu dans le fichier d'entré
    public static void ReadFile() throws FileNotFoundException {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("startFile.txt"));
            String line = reader.readLine();
            while (line != null) {
                if (line.startsWith("C")) {
                    map = "C - 3 - 4";
                    mapWidth = Integer.parseInt(map.split(" - ")[1]);
                    mapHeight = Integer.parseInt(map.split(" - ")[2]);
                } else if (line.startsWith("M")) {
                    Mountain newMountain = new Mountain(Integer.parseInt(line.split(" - ")[1]), Integer.parseInt(line.split(" - ")[2]));
                    mountainsList.add(newMountain);
                } else if (line.startsWith("T")) {
                    Treasure newTreasure = new Treasure(Integer.parseInt(line.split(" - ")[1]), Integer.parseInt(line.split(" - ")[2]), Integer.parseInt(line.split(" - ")[3]));
                    treasuresList.add(newTreasure);
                } else if (line.startsWith("A")) {
                    Adventurer newAdventurer = new Adventurer(line.split(" - ")[1], Integer.parseInt(line.split(" - ")[2]), Integer.parseInt(line.split(" - ")[3]), line.split(" - ")[4], line.split(" - ")[5], "", 0);
                    adventurersList.add(newAdventurer);
                }
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
