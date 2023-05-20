package swiat.organizmy.rosliny;

import swiat.Swiat;
import swiat.organizmy.Organizm;
import swiat.organizmy.Pozycja;
import swiat.organizmy.Roslina;

import java.util.ArrayList;
import java.util.Collections;

public class BarszczSosnowskiego extends Roslina {
    public BarszczSosnowskiego(Pozycja pozycja, Swiat swiat) {
        super(10, 0, swiat.getTura(), true, false, false, 'b', pozycja, pozycja, swiat, Swiat.TypyOrganizmow.BARSZCZ_SOSNOWSKIEGO);
    }

    @Override
    public void akcja() {
        if(this.turaUrodzenia!=swiat.getTura()){
            rozsiewanie();
            pozabijajSasiadow(this.pozycja);
        }

    }

    public void pozabijajSasiadow(Pozycja mojaPozycja){
        Pozycja nowaPozycja = new Pozycja(0,0);
        ArrayList<Pozycja> kierunki = new ArrayList<Pozycja>();
        kierunki.add(new Pozycja(0,-1));
        kierunki.add(new Pozycja(0,1));
        kierunki.add(new Pozycja(1,0));
        kierunki.add(new Pozycja(-1,0));

        for (int i = 0; i < 4; i++) {

            nowaPozycja.setX(mojaPozycja.getX()+kierunki.get(i).getX());
            nowaPozycja.setY(mojaPozycja.getY()+kierunki.get(i).getY());

            if (swiat.czyPoleZajetePrzezOrganizm(nowaPozycja)) {
                if(swiat.zwrocOrganizmNaPozycja(nowaPozycja).isCzyJestZwierzeciem()) {
                    swiat.getNarrator().DopiszFragmentHistorii("Barszcz zabil sasiada: " + swiat.zwrocOrganizmNaPozycja(nowaPozycja).toString());
                    swiat.zwrocOrganizmNaPozycja(nowaPozycja).setCzyZyje(false);
                }
            }
        }
    }
}
