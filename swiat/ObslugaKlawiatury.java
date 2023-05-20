package swiat;

import swiat.organizmy.Pozycja;
import swiat.organizmy.zwierzeta.Czlowiek;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ObslugaKlawiatury implements KeyListener {
    private Czlowiek czlowiek;

    public ObslugaKlawiatury(Czlowiek czlowiek) {
        this.czlowiek = czlowiek;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        Pozycja nowaPozycja = czlowiek.getNowaPozycja();
        Pozycja staraPozycja = czlowiek.getPozycja();

        if (key == KeyEvent.VK_LEFT) {
            if(staraPozycja.getX()!=0){
                nowaPozycja.setX(staraPozycja.getX()-1);
                czlowiek.setStrzalkaNacisnieta(true);
            }
        } else if (key == KeyEvent.VK_RIGHT) {
            if(staraPozycja.getX()!=czlowiek.getSwiat().getRozmiarX()-1){
                nowaPozycja.setX(staraPozycja.getX()+1);
                czlowiek.setStrzalkaNacisnieta(true);
            }
        } else if (key == KeyEvent.VK_UP) {
            if(staraPozycja.getY()!=0){
                nowaPozycja.setY(staraPozycja.getY()-1);
                czlowiek.setStrzalkaNacisnieta(true);
            }
        } else if (key == KeyEvent.VK_DOWN) {
            if(staraPozycja.getY()!=czlowiek.getSwiat().getRozmiarY()-1){
                nowaPozycja.setY(staraPozycja.getY()+1);
                czlowiek.setStrzalkaNacisnieta(true);
            }
        }   else if (key == KeyEvent.VK_U) {
            if(czlowiek.getCooldownEliksir()==0){
                czlowiek.wypijMagicznyEliksir();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}
