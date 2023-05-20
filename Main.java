import swiat.OdczytRozmiaruMapy;
import swiat.Swiat;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        OdczytRozmiaruMapy odczytRozmiaruMapy = new OdczytRozmiaruMapy();
        odczytRozmiaruMapy.wczytajRozmiar();
        while (!odczytRozmiaruMapy.isZebranoDane()) {
            Thread.sleep(100);
        }
        Swiat swiat = new Swiat(odczytRozmiaruMapy.getRozmiarX(),odczytRozmiaruMapy.getRozmiarY());
        swiat.rysujSwiat();
        swiat.generujSwiat();

        while (!(swiat.getCzyJestKoniecGry())){
            swiat.wykonajTure();
        }
    }
}