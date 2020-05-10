import java.util.stream.IntStream;



abstract class Rute implements Runnable{
  protected int kolonne;
  protected int rad;
  protected String status;
  protected Lenkeliste<Rute> naboListe = new Lenkeliste<Rute>();;
  protected Labyrint rutensLabyrint;
  protected int hvitRuteTeller;
  protected String rutensKolonneRad;
  protected int hviteNaboRuter;
  protected Thread nyTraad;


  public Rute(int kolonne, int rad) {
    this.kolonne = kolonne;
    this.rad = rad;
  }

  public void gaa(String kolonneRad)  { //gaa() skal gå til neste mulige rute
    return;
  }

  public void run()  { //denne metoden kan ikke kalles, men må igangsettes av start();
    return;
  }

  public void finnUtvei()  { //kaller på gaa() for å finne utveien.
    gaa("("+ kolonne + ", "+ rad + ")");
  }

  public void printNaboListe(){
    for (int i = 0 ; i < naboListe.stoerrelse(); i++){
      System.out.print(naboListe.hent(i).tilTegn());
    }
    System.out.println("");
  }

  public void denneLabyrinten(Labyrint denne){
    rutensLabyrint = denne;
  }

  public abstract char tilTegn();


  @Override
  public String toString() {
    return status;
  }

}
