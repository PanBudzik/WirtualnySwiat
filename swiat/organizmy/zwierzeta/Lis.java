package swiat.organizmy.zwierzeta;

import swiat.Swiat;
import swiat.organizmy.Organizm;
import swiat.organizmy.Pozycja;
import swiat.organizmy.Zwierze;

public class Lis extends Zwierze {
    public Lis(Pozycja pozycja, Swiat swiat) {
        super(3, 7, swiat.getTura(), true, true, false, 'L', pozycja, new Pozycja(0,0), swiat, Swiat.TypyOrganizmow.LIS);
    }

    @Override
    public void akcja() {
        if(this.turaUrodzenia!=swiat.getTura()){
            Pozycja nowaPozycja = getSwiat().zwrocDostepneMiejsceObok(pozycja);

            if (!pozycja.equals(nowaPozycja))
            {
                if (swiat.czyPoleZajetePrzezOrganizm(nowaPozycja))
                {
                    if(swiat.zwrocOrganizmNaPozycja(nowaPozycja).getSila()<=this.sila || this.getClass() == swiat.zwrocOrganizmNaPozycja(nowaPozycja).getClass()) {
                        swiat.zwrocOrganizmNaPozycja(nowaPozycja).kolizja(nowaPozycja, this);
                    }
                }
                else {
                    ruch(nowaPozycja);
                }
            }
        }
    }
}
