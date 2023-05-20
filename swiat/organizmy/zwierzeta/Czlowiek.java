package swiat.organizmy.zwierzeta;

import swiat.ObslugaKlawiatury;
import swiat.Swiat;
import swiat.organizmy.Organizm;
import swiat.organizmy.Pozycja;
import swiat.organizmy.Zwierze;

public class Czlowiek extends Zwierze {
    private ObslugaKlawiatury obslugaKlawiatury;
    private boolean strzalkaNacisnieta;
    private int cooldownEliksir=0;
    private int podstawowaDlugoscCooldownu = 5;
    private int podstawowaSila = 5;
    private int silaDodanaPrzezEliksir = 5;
    private int turaDzialaniaEliksiru = 5;
    private final Object lock = new Object();
    private Pozycja nowaPozycja;

    public Czlowiek(Pozycja pozycja, Swiat swiat) {
        super(5, 4, swiat.getTura(), true, true, false, 'H', pozycja, new Pozycja(0,0), swiat, Swiat.TypyOrganizmow.CZLOWIEK);
        this.strzalkaNacisnieta = false;
        this.obslugaKlawiatury = new ObslugaKlawiatury(this);
        this.nowaPozycja = new Pozycja(pozycja.getX(), pozycja.getY());
        swiat.getFrame().addKeyListener(obslugaKlawiatury);
        swiat.getFrame().setFocusable(true);
        swiat.getFrame().requestFocus();
    }

    public Czlowiek(Pozycja pozycja, Swiat swiat, int cooldownEliksir, int turaDzialaniaEliksiru) {
        super(5, 4, swiat.getTura(), true, true, false, 'H', pozycja, new Pozycja(0,0), swiat, Swiat.TypyOrganizmow.CZLOWIEK);
        this.strzalkaNacisnieta = false;
        this.obslugaKlawiatury = new ObslugaKlawiatury(this);
        this.nowaPozycja = new Pozycja(pozycja.getX(), pozycja.getY());
        this.cooldownEliksir = cooldownEliksir;
        this.turaDzialaniaEliksiru = turaDzialaniaEliksiru;
        swiat.getFrame().addKeyListener(obslugaKlawiatury);
        swiat.getFrame().setFocusable(true);
        swiat.getFrame().requestFocus();
    }

    @Override
    public void akcja() {
        synchronized (lock) {
            while (!strzalkaNacisnieta) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        this.strzalkaNacisnieta = false;

        if (!pozycja.equals(this.nowaPozycja))
        {
            if (swiat.czyPoleZajetePrzezOrganizm(this.nowaPozycja))
            {
                swiat.zwrocOrganizmNaPozycja(this.nowaPozycja).kolizja(this.nowaPozycja, this);
            }
            else {
                ruch(this.nowaPozycja);
            }
        }
        if(turaDzialaniaEliksiru!=5){
            turaDzialaniaEliksiru++;
            this.setSila(sila-1);
        }
        if(cooldownEliksir>0&&sila==podstawowaSila){
            swiat.getNarrator().DopiszFragmentHistorii("Cooldown dla umiejetnosci czlowieka (pozostale tury): "+cooldownEliksir);
            this.setCooldownEliksir(cooldownEliksir-1);
        }
    }

    @Override
    public void kolizja(Pozycja nowaPozycja, Organizm nadchodzacyOrganizm) {
        if (this.getClass() != nadchodzacyOrganizm.getClass()) {
            walka(nadchodzacyOrganizm, nowaPozycja);
            if(!this.czyZyje)
            {
                swiat.setCzyCzlowiekZyje(false);
            }
        }
    }

    public void ustawFocusKlawiatury(){
        swiat.getFrame().setFocusable(true);
        swiat.getFrame().requestFocus();
    }

    public boolean getStrzalkaNacisnieta() {
        return strzalkaNacisnieta;
    }

    public void wypijMagicznyEliksir(){
        this.setSila(sila+silaDodanaPrzezEliksir);
        this.setCooldownEliksir(podstawowaDlugoscCooldownu);
        this.setTuraDzialaniaEliksiru(0);
        swiat.getNarrator().DopiszFragmentHistorii("Czlowiek wypil magiczny eliksir, jego sila to teraz "+sila);
    }

    public void setStrzalkaNacisnieta(boolean strzalkaNacisnieta) {
        this.strzalkaNacisnieta = strzalkaNacisnieta;

        synchronized (lock) {
            if (strzalkaNacisnieta) {
                lock.notify();
            }
        }
    }

    public Pozycja getNowaPozycja() {
        return nowaPozycja;
    }

    public void setNowaPozycja(Pozycja nowaPozycja) {
        this.nowaPozycja = nowaPozycja;
    }

    public int getCooldownEliksir() {
        return cooldownEliksir;
    }

    public void setCooldownEliksir(int cooldownEliksir) {
        this.cooldownEliksir = cooldownEliksir;
    }

    public ObslugaKlawiatury getObslugaKlawiatury() {
        return obslugaKlawiatury;
    }

    public void setObslugaKlawiatury(ObslugaKlawiatury obslugaKlawiatury) {
        this.obslugaKlawiatury = obslugaKlawiatury;
    }

    public int getTuraDzialaniaEliksiru() {
        return turaDzialaniaEliksiru;
    }

    public void setTuraDzialaniaEliksiru(int turaDzialaniaEliksiru) {
        this.turaDzialaniaEliksiru = turaDzialaniaEliksiru;
    }
}
