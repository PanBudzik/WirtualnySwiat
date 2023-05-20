package swiat.organizmy.zwierzeta;

import swiat.Swiat;
import swiat.organizmy.Pozycja;
import swiat.organizmy.Zwierze;

public class Owca extends Zwierze {
    public Owca(Pozycja pozycja, Swiat swiat) {
        super(4, 4, swiat.getTura(), true, true, false, 'O', pozycja, new Pozycja(0,0), swiat, Swiat.TypyOrganizmow.OWCA);
    }
}
