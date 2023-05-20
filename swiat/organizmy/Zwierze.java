package swiat.organizmy;
import swiat.Swiat;

public class Zwierze extends Organizm{

    public Zwierze(int sila, int inicjatywa, int turaUrodzenia, boolean czyZyje, boolean czyJestZwierzeciem, boolean czyRozmozylSieWTejTurze, char symbol, Pozycja pozycja, Pozycja poprzedniaPozycja, Swiat swiat, Swiat.TypyOrganizmow typOrganizmu) {
        super(sila, inicjatywa, turaUrodzenia, czyZyje, czyJestZwierzeciem, czyRozmozylSieWTejTurze, symbol, pozycja, poprzedniaPozycja, swiat, typOrganizmu);
    }

    @Override
    public void akcja() {
        if(this.turaUrodzenia!=swiat.getTura()){
            Pozycja nowaPozycja = getSwiat().zwrocDostepneMiejsceObok(pozycja);

            if (!pozycja.equals(nowaPozycja))
            {
                if (swiat.czyPoleZajetePrzezOrganizm(nowaPozycja))
                {
                    swiat.zwrocOrganizmNaPozycja(nowaPozycja).kolizja(nowaPozycja, this);
                }
                else {
                    ruch(nowaPozycja);
                }
            }
        }
    }

    @Override
    public void kolizja(Pozycja nowaPozycja, Organizm nadchodzacyOrganizm) {
        if (this.getClass() != nadchodzacyOrganizm.getClass()) {
            walka(nadchodzacyOrganizm, nowaPozycja);
        }
        else{
            if(!this.czyRozmozylSieWTejTurze) {
                rozmnazanie(nowaPozycja);
            }
        }
    }
    public void rozmnazanie(Pozycja nowaPozycja){
        Pozycja pozycjaNowegoZwierzaka = swiat.zwrocWolneMiejsceObok(nowaPozycja);
        if(!nowaPozycja.equals(pozycjaNowegoZwierzaka)) {
            swiat.getFabrykaOrganizmow().stworzOrganizm(this.getTypOrganizmu(), pozycjaNowegoZwierzaka, this.getSwiat());
            this.setCzyRozmozylSieWTejTurze(true);
            swiat.zwrocOrganizmNaPozycja(nowaPozycja).setCzyRozmozylSieWTejTurze(true);
        }
        swiat.getNarrator().DopiszFragmentHistorii("Pojawil sie nowy organizm: " + swiat.zwrocOrganizmNaPozycja(pozycjaNowegoZwierzaka).toString());
    }

}
