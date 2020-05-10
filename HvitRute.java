
public class HvitRute extends Rute {

  public HvitRute(int kolonne, int rad){
    super(kolonne, rad);
    status = ".";
    //System.out.println(skrivUtNaboliste());
  }

  /*logikken her er at vi kaller run med start() når vi ønsker å lage nye tråder. Disse skal ikke gjøre noe annet
  *enn andre tråder, og derfor er "alt" run() gjør å kalle gaa-metoden
  */
  public void run()  {
    gaa(rutensKolonneRad);
    }

  public void gaa(String kolonneRad) {
    rutensLabyrint.lagretKolonneRad = kolonneRad;
    hvitRuteTeller++;

    //med denne løkken sjekker vi om vi skal til en rute vi har vært i før, samt å bytte ut denne ruten i nabolistene
    // med en sort rute,
    for (int nesteRute = 0; nesteRute < naboListe.stoerrelse(); nesteRute++) {
      if (naboListe.hent(nesteRute).hvitRuteTeller > 0){ //sjekker at vi ikke går til en rute vi allerede har besøkt
        return;
      }
      for (int listeIndeks = 0; listeIndeks<naboListe.hent(nesteRute).naboListe.stoerrelse(); listeIndeks++) {
        if (naboListe.hent(nesteRute).naboListe.hent(listeIndeks).equals(this)){
          SortRute erstatt = new SortRute(this.kolonne, this.rad);
          naboListe.hent(nesteRute).naboListe.sett(listeIndeks, erstatt);
        }
      }

      //etter løkken over kan vi sjekke hvor mange hvite naboruter vi har, for så å starte nye tråder i alle untatt en,
      //som vi deretter går videre med i samme tråd (denne tråden)
      if (naboListe.hent(nesteRute).status == "."){
        hviteNaboRuter++;
      }
    }
    /**  SVAR PÅ SPØRSMÅL I OPPGAVETEKST
    *Det er her jeg sørger for at vi besøker alle andre mulige hvite ruter med nye tråder, før vi fortsetter på den siste
    *av dem med den samme tråden vi nå er på. Gjør vi ikke dette vil ikke programmet kjøre tråder i paralell, men
    *vente på at den første/eldste tråden kjører ferdig.
    */
      for (int nesteRute = 0; nesteRute < naboListe.stoerrelse(); nesteRute++) {
        if (hviteNaboRuter > 1 && naboListe.hent(nesteRute).status == "."){
          naboListe.hent(nesteRute).rutensKolonneRad = kolonneRad + " --> ("+ naboListe.hent(nesteRute).kolonne
          + ", "+ naboListe.hent(nesteRute).rad + ")";
          Runnable nyRuteTraad = naboListe.hent(nesteRute);
          Thread nyTraad = new Thread(nyRuteTraad);
          nyTraad.start();
          hviteNaboRuter--;
          //her trenger vi å fange InterruptedException da .join() skaper potensielt et slikt avbrudd
          try{
            nyTraad.join();
          }
          catch (InterruptedException e){
            System.out.println("Fanget unntak: " + e);
          }
        }
        else if (hviteNaboRuter == 1 && naboListe.hent(nesteRute).status == "."){
          naboListe.hent(nesteRute).gaa(kolonneRad + " --> ("+ naboListe.hent(nesteRute).kolonne
          + ", "+ naboListe.hent(nesteRute).rad + ")");
          hviteNaboRuter--;
        }
      }
      //tilbakestiller hvitruteteller
      hvitRuteTeller = 0;
  }


  @Override
  public char tilTegn(){
    char tegn = status.charAt(0);
    return tegn;
  }


  @Override
  public String toString() {
    return status;
    }
}
