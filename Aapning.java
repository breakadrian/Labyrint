class Aapning extends HvitRute {

  public Aapning(int kolonne, int rad){
    super(kolonne, rad);
    status = ".";
  }

  /* Aapning trenger ikke lage noen nye tråder, da det er spesifisert at det ikke skal være flere åpninger ved siden
  * av hverandre. Men en tråd må kunne opprettes med Aapning-rute
  **/
  public void run()  {
    gaa(rutensKolonneRad);
    }

  public void gaa(String kolonneRad) { //gaa() skal gå til neste rute
    rutensLabyrint.lagretKolonneRad = kolonneRad;
    hvitRuteTeller++;

    //med denne løkken bytter vi ut denne ruten i nabolistene med en sort rute.
    // Deretter kjører vi gaa-metoden fordi det er bare en vei fra aapningen
    for (int nesteRute = 0; nesteRute < naboListe.stoerrelse(); nesteRute++) {
        for (int listeIndeks = 0; listeIndeks<naboListe.hent(nesteRute).naboListe.stoerrelse(); listeIndeks++) {
          if (naboListe.hent(nesteRute).naboListe.hent(listeIndeks).equals(this)){
            SortRute erstatt = new SortRute(this.kolonne, this.rad);
            naboListe.hent(nesteRute).naboListe.sett(listeIndeks, erstatt);
          }
        }
          naboListe.hent(nesteRute).gaa(kolonneRad + " --> ("+ naboListe.hent(nesteRute).kolonne
          + ", "+ naboListe.hent(nesteRute).rad + ")");
      }
      //tilbakestiller hvitruteteller
      hvitRuteTeller = 0;
      rutensLabyrint.antallVeier++;
      rutensLabyrint.utveiListe.leggTil("Utskrift nr. " + rutensLabyrint.antallVeier + ": " + kolonneRad);
      return;
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
