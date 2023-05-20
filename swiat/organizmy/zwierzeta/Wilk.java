package swiat.organizmy.zwierzeta;

import swiat.Swiat;
import swiat.organizmy.Pozycja;
import swiat.organizmy.Zwierze;

public class Wilk extends Zwierze {
    public Wilk(Pozycja pozycja, Swiat swiat) {
        super(9, 5, swiat.getTura(), true, true, false, 'W', pozycja, new Pozycja(0,0), swiat, Swiat.TypyOrganizmow.WILK);
    }


}
