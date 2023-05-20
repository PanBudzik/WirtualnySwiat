package swiat.organizmy;

import swiat.Swiat;

import java.util.Random;

public class Roslina extends Organizm{

    public Roslina(int sila, int inicjatywa, int turaUrodzenia, boolean czyZyje, boolean czyJestZwierzeciem, boolean czyRozmozylSieWTejTurze, char symbol, Pozycja pozycja, Pozycja poprzedniaPozycja, Swiat swiat, Swiat.TypyOrganizmow typOrganizmu) {
        super(sila, inicjatywa, turaUrodzenia, czyZyje, czyJestZwierzeciem, czyRozmozylSieWTejTurze, symbol, pozycja, poprzedniaPozycja, swiat, typOrganizmu);
    }

    @Override
    public void akcja() {
        if(this.turaUrodzenia!=swiat.getTura()){
                    rozsiewanie();
            }
        }

    @Override
    public void kolizja(Pozycja nowaPozycja, Organizm nadchodzacyOrganizm) {
            walka(nadchodzacyOrganizm, nowaPozycja);
    }

    public void rozsiewanie(){
        Random random = new Random();
        if(random.nextInt(2)==0) {
            Pozycja nowaPozycja = getSwiat().zwrocWolneMiejsceObok(pozycja);
            if (!pozycja.equals(nowaPozycja)) {
                swiat.getFabrykaOrganizmow().stworzOrganizm(this.typOrganizmu, nowaPozycja, this.swiat);
                swiat.getNarrator().DopiszFragmentHistorii("Pojawil sie nowy organizm: " + swiat.zwrocOrganizmNaPozycja(nowaPozycja).toString());
            }
        }
    }
}
