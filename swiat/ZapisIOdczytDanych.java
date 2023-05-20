package swiat;

import swiat.organizmy.Organizm;
import swiat.organizmy.Pozycja;
import swiat.organizmy.zwierzeta.Czlowiek;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ZapisIOdczytDanych{

    public void zapiszDaneDoPliku(String filePath, JFrame frame, Swiat swiat) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            String dane = "";
            dane += swiat.getRozmiarX()+" ";
            dane += swiat.getRozmiarY()+" ";
            dane += swiat.getTura()+" ";
            dane += (swiat.isCzyCzlowiekZyje()? 1 : 0)+" ";
            dane += (swiat.getCzyJestKoniecGry()? 1 : 0)+" ";
            dane += "\n";

            for(int i=0; i<swiat.getOrganizmy().size(); i++) {
                Organizm organizm = swiat.getOrganizmy().get(i);
                dane += organizm.getSila()+" ";
                dane += organizm.getTuraUrodzenia()+" ";
                dane += (organizm.getCzyZyje()? 1 : 0)+" ";
                dane += (organizm.isCzyRozmozylSieWTejTurze()? 1 : 0)+" ";
                dane += organizm.getPozycja().getX()+" "+organizm.getPozycja().getY()+" ";
                dane += organizm.getPoprzedniaPozycja().getX()+" "+organizm.getPoprzedniaPozycja().getY()+" ";
                dane += (organizm.getTypOrganizmu().getID());

                if(organizm instanceof Czlowiek)
                {
                    Czlowiek czlowiek = (Czlowiek) organizm;
                    dane += " "+czlowiek.getCooldownEliksir()+" ";
                    dane += czlowiek.getTuraDzialaniaEliksiru()+" ";
                }
                dane += "\n";
            }

            writer.write(dane);
            System.out.println("Dane zapisane do pliku: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Blad przy zapisie do pliku.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public List<String> odczytajDaneZPliku(String filePath, JFrame frame, Swiat swiat) {
        swiat.getNarrator().ZacznijPisacHistorieOdNowa();

        List<String> linie = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean czyPierwszaLinia = true;
            int numerOrganizmu = 0;
            swiat.getOrganizmy().clear();
            while ((line = reader.readLine()) != null) {
                String[] podzielonaLinia = line.split(" ");
                if(czyPierwszaLinia)
                {
                        swiat.setRozmiarX(Integer.parseInt(podzielonaLinia[0]));
                        swiat.setRozmiarY(Integer.parseInt(podzielonaLinia[1]));
                        swiat.setPlansza(new char[swiat.getRozmiarY()][swiat.getRozmiarX()]);

                        for (int i = 0; i < swiat.getRozmiarY(); i++) {
                            for (int j = 0; j < swiat.getRozmiarX(); j++) {
                                swiat.getPlansza()[i][j] = ' ';
                            }
                        }
                        swiat.setTura(Integer.parseInt(podzielonaLinia[2]));
                        swiat.setCzyCzlowiekZyje(podzielonaLinia[3].equals("1"));
                        swiat.setCzyJestKoniecGry(podzielonaLinia[4].equals("1"));

                        czyPierwszaLinia=false;
                }
                else{
                    int sila = Integer.parseInt(podzielonaLinia[0]);
                    int turaUrodzenia = Integer.parseInt(podzielonaLinia[1]);
                    boolean czyZyje = podzielonaLinia[2].equals("1");
                    boolean czyRozmnozylSieWTejTurze = podzielonaLinia[3].equals("1");
                    Pozycja aktualnaPozycja = new Pozycja(Integer.parseInt(podzielonaLinia[4]),Integer.parseInt(podzielonaLinia[5]));
                    Pozycja poprzedniaPozycja = new Pozycja(Integer.parseInt(podzielonaLinia[6]),Integer.parseInt(podzielonaLinia[7]));
                    Swiat.TypyOrganizmow typOrganizmu = Swiat.TypyOrganizmow.fromID(Integer.parseInt(podzielonaLinia[8])) ;
                    swiat.getFabrykaOrganizmow().stworzOrganizm(typOrganizmu, aktualnaPozycja, swiat);
                    swiat.getOrganizmy().get(numerOrganizmu).setSila(sila);
                    swiat.getOrganizmy().get(numerOrganizmu).setTuraUrodzenia(turaUrodzenia);
                    swiat.getOrganizmy().get(numerOrganizmu).setCzyZyje(czyZyje);
                    swiat.getOrganizmy().get(numerOrganizmu).setCzyRozmozylSieWTejTurze(czyRozmnozylSieWTejTurze);
                    swiat.getOrganizmy().get(numerOrganizmu).setPoprzedniaPozycja(poprzedniaPozycja);

                    if(swiat.getOrganizmy().get(numerOrganizmu) instanceof Czlowiek)
                    {
                        Czlowiek czlowiek = (Czlowiek) swiat.getOrganizmy().get(numerOrganizmu);
                        czlowiek.setCooldownEliksir(Integer.parseInt(podzielonaLinia[9]));
                        czlowiek.setTuraDzialaniaEliksiru(Integer.parseInt(podzielonaLinia[10]));
                        czlowiek.ustawFocusKlawiatury();
                        swiat.setNastepnaTuraKliknieta(true);
                    }
                    numerOrganizmu++;
                }

            }
            swiat.sortujOrganizmy();
            swiat.aktualizujSwiat();
            swiat.odswiezPlansze();
            swiat.setZawartoscPoleNarratora("");

            System.out.println("Dane wczytane z pliku: " + filePath);
            linie.add("Wczytane");
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Blad przy wczytywaniu z pliku.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return linie;
    }
}