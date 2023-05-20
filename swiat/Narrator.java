package swiat;

public class Narrator {
    private String historia;

    public void DopiszFragmentHistorii(String rozdzial){
        this.setHistoria(this.getHistoria() + rozdzial + "\n");
    }

    public void OpowiedzHistorie(){
        System.out.println(this.getHistoria());
    }

    void ZacznijPisacHistorieOdNowa()
    {
        this.setHistoria("");
    }

    public String getHistoria() {
        return historia;
    }

    public void setHistoria(String historia) {
        this.historia = historia;
    }
}
