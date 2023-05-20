package swiat.organizmy.rosliny;

import swiat.Swiat;
import swiat.organizmy.Organizm;
import swiat.organizmy.Pozycja;
import swiat.organizmy.Roslina;

public class WilczeJagody extends Roslina {
    public WilczeJagody(Pozycja pozycja, Swiat swiat) {
        super(99, 0, swiat.getTura(), true, false, false, 'j', pozycja, pozycja, swiat, Swiat.TypyOrganizmow.WILCZE_JAGODY);
    }

    @Override
    public void kolizja(Pozycja nowaPozycja, Organizm nadchodzacyOrganizm) {
            nadchodzacyOrganizm.setCzyZyje(false);
            swiat.getNarrator().DopiszFragmentHistorii("Zwierze zatrute wilczymi jadogami: "+nadchodzacyOrganizm.toString());
    }
}
