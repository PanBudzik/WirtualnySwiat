package swiat.organizmy.rosliny;

import swiat.Swiat;
import swiat.organizmy.Pozycja;
import swiat.organizmy.Roslina;

public class Trawa extends Roslina {
    public Trawa(Pozycja pozycja, Swiat swiat) {
        super(0, 0, swiat.getTura(), true, false, false, 't', pozycja, pozycja, swiat, Swiat.TypyOrganizmow.TRAWA);
    }
}
