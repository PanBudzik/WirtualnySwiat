package swiat.organizmy;

import swiat.Swiat;

public abstract class Organizm {
    protected int sila;
    protected int inicjatywa;
    protected int turaUrodzenia;
    protected boolean czyZyje;
    protected boolean czyJestZwierzeciem;
    protected boolean czyRozmozylSieWTejTurze;
    protected char symbol;

    protected Pozycja pozycja;
    protected Pozycja poprzedniaPozycja;
    protected Swiat swiat;
    protected Swiat.TypyOrganizmow typOrganizmu;



    public Organizm(int sila, int inicjatywa, int turaUrodzenia, boolean czyZyje, boolean czyJestZwierzeciem, boolean czyRozmozylSieWTejTurze, char symbol, Pozycja pozycja, Pozycja poprzedniaPozycja, Swiat swiat, Swiat.TypyOrganizmow typOrganizmu) {
        this.sila = sila;
        this.inicjatywa = inicjatywa;
        this.turaUrodzenia = turaUrodzenia;
        this.czyZyje = czyZyje;
        this.czyJestZwierzeciem = czyJestZwierzeciem;
        this.czyRozmozylSieWTejTurze = czyRozmozylSieWTejTurze;
        this.symbol = symbol;
        this.pozycja = pozycja;
        this.poprzedniaPozycja = poprzedniaPozycja;
        this.swiat = swiat;
        this.typOrganizmu = typOrganizmu;
    }

    public abstract void akcja();

    public abstract void kolizja(Pozycja nowaPozycja, Organizm nachodzacyOrganizm);

    public char rysowanie(){
        return symbol;
    }

    public void ruch(Pozycja nowaPozycja){

        poprzedniaPozycja.setX(pozycja.getX());
        poprzedniaPozycja.setY(pozycja.getY());
        pozycja.setX(nowaPozycja.getX());
        pozycja.setY(nowaPozycja.getY());

        swiat.getNarrator().DopiszFragmentHistorii("Przemieszczenie z ("+ poprzedniaPozycja.getX() +"," + poprzedniaPozycja.getY() + ") do (" + pozycja.getX() + "," + pozycja.getY() + "): " + this.getTypOrganizmu());
    }
    public void walka(Organizm organizm, Pozycja nowaPozycja){
        swiat.getNarrator().DopiszFragmentHistorii("Zaczyna sie walka: ");
        swiat.getNarrator().DopiszFragmentHistorii("Organizmy walczace: Obrona-> "+this.getTypOrganizmu()+", Atak-> "+organizm.getTypOrganizmu());
        if(this.getSila() >= organizm.getSila()){
            organizm.setCzyZyje(false);
            swiat.getNarrator().DopiszFragmentHistorii("Wygrywa: " + this.toString());
            swiat.getNarrator().DopiszFragmentHistorii("Przegrywa: " + organizm.toString());
        }
        else{
            this.setCzyZyje(false);
            organizm.ruch(nowaPozycja);
            swiat.getNarrator().DopiszFragmentHistorii("Wygrywa: " + organizm.toString());
            swiat.getNarrator().DopiszFragmentHistorii("Przegrywa: " + this.toString());
        }
    }

    @Override
    public String toString() {
        return "\"" + typOrganizmu + "\" o sile " + sila + " na pozycji " + pozycja.toString();
    }

    public int getSila() {
        return sila;
    }

    public void setSila(int sila) {
        this.sila = sila;
    }

    public int getInicjatywa() {
        return inicjatywa;
    }

    public void setInicjatywa(int inicjatywa) {
        this.inicjatywa = inicjatywa;
    }

    public int getTuraUrodzenia() {
        return turaUrodzenia;
    }

    public void setTuraUrodzenia(int turaUrodzenia) {
        this.turaUrodzenia = turaUrodzenia;
    }

    public boolean getCzyZyje() {
        return czyZyje;
    }

    public void setCzyZyje(boolean czyZyje) {
        this.czyZyje = czyZyje;
    }

    public boolean isCzyJestZwierzeciem() {
        return czyJestZwierzeciem;
    }

    public void setCzyJestZwierzeciem(boolean czyJestZwierzeciem) {
        this.czyJestZwierzeciem = czyJestZwierzeciem;
    }

    public boolean isCzyRozmozylSieWTejTurze() {
        return czyRozmozylSieWTejTurze;
    }

    public void setCzyRozmozylSieWTejTurze(boolean czyRozmozylSieWTejTurze) {
        this.czyRozmozylSieWTejTurze = czyRozmozylSieWTejTurze;
    }

    public char getSymbol() {
        return symbol;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    public Pozycja getPozycja() {
        return pozycja;
    }

    public void setPozycja(Pozycja pozycja) {
        this.pozycja = pozycja;
    }

    public Pozycja getPoprzedniaPozycja() {
        return poprzedniaPozycja;
    }

    public void setPoprzedniaPozycja(Pozycja poprzedniaPozycja) {
        this.poprzedniaPozycja = poprzedniaPozycja;
    }

    public Swiat getSwiat() {
        return swiat;
    }

    public void setSwiat(Swiat swiat) {
        this.swiat = swiat;
    }

    public Swiat.TypyOrganizmow getTypOrganizmu() {
        return typOrganizmu;
    }

    public void setTypOrganizmu(Swiat.TypyOrganizmow typOrganizmu) {
        this.typOrganizmu = typOrganizmu;
    }

    public boolean isCzyZyje() {
        return czyZyje;
    }
}
