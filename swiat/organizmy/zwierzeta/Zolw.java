package swiat.organizmy.zwierzeta;

import swiat.Swiat;
import swiat.organizmy.Organizm;
import swiat.organizmy.Pozycja;
import swiat.organizmy.Zwierze;

import java.util.Random;

public class Zolw extends Zwierze {
    public Zolw(Pozycja pozycja, Swiat swiat) {
        super(2, 1, swiat.getTura(), true, true, false, 'Z', pozycja, new Pozycja(0,0), swiat, Swiat.TypyOrganizmow.ZOLW);
    }

    @Override
    public void ruch(Pozycja nowaPozycja){
        Random losCzySiePoruszy = new Random();
        if(losCzySiePoruszy.nextInt(4)==3) {
            poprzedniaPozycja.setX(pozycja.getX());
            poprzedniaPozycja.setY(pozycja.getY());
            pozycja.setX(nowaPozycja.getX());
            pozycja.setY(nowaPozycja.getY());
            swiat.getNarrator().DopiszFragmentHistorii("Przemieszczenie z ("+ poprzedniaPozycja.getX() +"," + poprzedniaPozycja.getY() + ") do (" + pozycja.getX() + "," + pozycja.getY() + "): " + this.toString());
        }
    }

    @Override
    public void kolizja(Pozycja nowaPozycja, Organizm nadchodzacyOrganizm) {
        if (this.getClass() != nadchodzacyOrganizm.getClass()) {
            if(nadchodzacyOrganizm.getSila()>5) {
                walka(nadchodzacyOrganizm, nowaPozycja);
            }
            else{
                swiat.getNarrator().DopiszFragmentHistorii("Zolw obronil sie przed atakiem");
            }
        }
        else{
            if(!this.czyRozmozylSieWTejTurze) {
                rozmnazanie(nowaPozycja);
            }
        }
    }
}
