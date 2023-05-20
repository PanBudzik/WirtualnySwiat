package swiat.organizmy.rosliny;

import swiat.Swiat;
import swiat.organizmy.Pozycja;
import swiat.organizmy.Roslina;

public class Mlecz extends Roslina {
    public Mlecz(Pozycja pozycja, Swiat swiat) {
        super(0, 0, swiat.getTura(), true, false, false, 'm', pozycja, pozycja, swiat, Swiat.TypyOrganizmow.MLECZ);
    }

    @Override
    public void akcja() {
        if(this.turaUrodzenia!=swiat.getTura()){
            for(int i=0; i<3; i++) {
                rozsiewanie();
            }
        }
    }
}
