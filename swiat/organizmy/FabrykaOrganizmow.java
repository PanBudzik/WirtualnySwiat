package swiat.organizmy;
import swiat.Swiat;
import swiat.organizmy.rosliny.*;
import swiat.organizmy.zwierzeta.*;

public class FabrykaOrganizmow  {
    public Organizm stworzOrganizm(Swiat.TypyOrganizmow typOrganizmu, Pozycja pozycja, Swiat swiat) {
        switch (typOrganizmu) {
            case OWCA:
                swiat.dodajOrganizm(new Owca(pozycja, swiat));
                break;
            case WILK:
                swiat.dodajOrganizm(new Wilk(pozycja, swiat));
                break;
            case LIS:
                swiat.dodajOrganizm(new Lis(pozycja, swiat));
                break;
            case ZOLW:
                swiat.dodajOrganizm(new Zolw(pozycja, swiat));
                break;
            case ANTYLOPA:
                swiat.dodajOrganizm(new Antylopa(pozycja, swiat));
                break;
            case TRAWA:
                swiat.dodajOrganizm(new Trawa(pozycja, swiat));
                break;
            case MLECZ:
                swiat.dodajOrganizm(new Mlecz(pozycja, swiat));
                break;
            case GUARANA:
                swiat.dodajOrganizm(new Guarana(pozycja, swiat));
                break;
            case WILCZE_JAGODY:
                swiat.dodajOrganizm(new WilczeJagody(pozycja, swiat));
                break;
            case BARSZCZ_SOSNOWSKIEGO:
                swiat.dodajOrganizm(new BarszczSosnowskiego(pozycja, swiat));
                break;
            case CZLOWIEK:
                swiat.dodajOrganizm(new Czlowiek(pozycja, swiat));
                break;
            default:
                return null;
        }
        return null;
    }
    }
