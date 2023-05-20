package swiat.organizmy.zwierzeta;

import swiat.Swiat;
import swiat.organizmy.Organizm;
import swiat.organizmy.Pozycja;
import swiat.organizmy.Zwierze;

import java.util.Random;

public class Antylopa extends Zwierze {
    public Antylopa(Pozycja pozycja, Swiat swiat) {
        super(4, 4, swiat.getTura(), true, true, false, 'A', pozycja, new Pozycja(0,0), swiat, Swiat.TypyOrganizmow.ANTYLOPA);
    }

    @Override
    public void akcja() {
        for(int i=0;i<2;i++) {
            if (this.czyZyje) {
                if (this.turaUrodzenia != swiat.getTura()) {
                    Pozycja nowaPozycja = getSwiat().zwrocDostepneMiejsceObok(pozycja);

                    if (!pozycja.equals(nowaPozycja)) {
                        if (swiat.czyPoleZajetePrzezOrganizm(nowaPozycja)) {
                            swiat.zwrocOrganizmNaPozycja(nowaPozycja).kolizja(nowaPozycja, this);
                        } else {
                            ruch(nowaPozycja);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void kolizja(Pozycja nowaPozycja, Organizm nadchodzacyOrganizm) {
        Random los = new Random();
        if (this.getClass() != nadchodzacyOrganizm.getClass()) {
            if(los.nextInt(2)==0) {
                walka(nadchodzacyOrganizm, nowaPozycja);
            }else{
                swiat.getNarrator().DopiszFragmentHistorii("Antylopa uciekla");
                this.ruch(swiat.zwrocWolneMiejsceObok(this.pozycja));
            }
        }
        else{
            if(!this.czyRozmozylSieWTejTurze) {
                rozmnazanie(nowaPozycja);
            }
        }
    }
}
