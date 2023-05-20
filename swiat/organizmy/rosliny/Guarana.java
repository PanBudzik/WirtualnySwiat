package swiat.organizmy.rosliny;

import swiat.Swiat;
import swiat.organizmy.Organizm;
import swiat.organizmy.Pozycja;
import swiat.organizmy.Roslina;

public class Guarana extends Roslina {
    public Guarana(Pozycja pozycja, Swiat swiat) {
        super(0, 0, swiat.getTura(), true, false, false, 'g', pozycja, pozycja, swiat, Swiat.TypyOrganizmow.GUARANA);
    }

    @Override
    public void kolizja(Pozycja nowaPozycja, Organizm nadchodzacyOrganizm) {
            this.czyZyje=false;
            nadchodzacyOrganizm.setSila(nadchodzacyOrganizm.getSila()+3);
            nadchodzacyOrganizm.ruch(nowaPozycja);
            swiat.getNarrator().DopiszFragmentHistorii("Guarana wzmocnila organizm: "+nadchodzacyOrganizm.toString());
    }
}
