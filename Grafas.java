import java.util.*;

public class Grafas {

    private final Map<Character, List<Virsune>> virsunes; //saugome visas virsunes su ju kaimynemis

    public Grafas() {
        this.virsunes = new HashMap<Character, List<Virsune>>();
    }

    public void pridetiBriauna(Character pav, List<Virsune> briaunos) {
        this.virsunes.put(pav, briaunos);
    }

    public List<Character> gautiTrumpiausiaKelia(Character pradzia, Character pabaiga) {
    	PriorityQueue<Virsune> eile = new PriorityQueue<Virsune>();
        final Map<Character, Integer> atstumai = new HashMap<Character, Integer>();
        final Map<Character, Virsune> previous = new HashMap<Character, Virsune>();
        

        //i eile patalpiname virsune ir priskiriame pradinei virsune 0
        //likusiom virsunem begalybe (musu atveju Integer.MAX_VALUE
        for(Character virsune : virsunes.keySet()) {
            if (virsune == pradzia) {
                System.out.println("Idedame elementa");
                atstumai.put(virsune, 0);
                eile.add(new Virsune(virsune, 0));
            } else {
                atstumai.put(virsune, Integer.MAX_VALUE);
                eile.add(new Virsune(virsune, Integer.MAX_VALUE));
            }
            previous.put(virsune, null);
        }
        System.out.println("Eileje elementu " + eile.size());
        //nagrinejame virsune kol eile nebus tuscia
        while (!eile.isEmpty()) {
            Virsune v = eile.poll(); //is eile paimame virsune su maziausiu atstumu
            System.out.println("Is eiles paimtas elementas: " + v.toString());
            
            //jei atejome iki reikiamos virsunes baigiame darba ir graziname kelia
            if (v.getVirsunePavadinimas() == pabaiga) {
                final List<Character> kelias = new ArrayList<Character>();
                while (previous.get(v.getVirsunePavadinimas()) != null) {
                    kelias.add(v.getVirsunePavadinimas());
                    v = previous.get(v.getVirsunePavadinimas());
                }
                return kelias;
            }
            
            //pabaigiame darba, tai reiskia, kad pries tai buvusios virsunes neturejo kaimyniu
            if (atstumai.get(v.getVirsunePavadinimas()) == Integer.MAX_VALUE) {
                break;
            }
            
            //einame per visas virsunes kaimynes ir skaiciuojame trumpiausia atstuma
            //jei randame atnaujiname eile ismeta su senu atstumu virsune ir idedame su nauju
            for (Virsune kaimyne : virsunes.get(v.getVirsunePavadinimas())) {
                Integer naujasAtstumas = atstumai.get(v.getVirsunePavadinimas()) + kaimyne.getAtstumas();
                System.out.println("Kaimyne: " + kaimyne.getVirsunePavadinimas() + "atstumas" + kaimyne.getAtstumas());
                if (naujasAtstumas < atstumai.get(kaimyne.getVirsunePavadinimas())) {
                    atstumai.put(kaimyne.getVirsunePavadinimas(), naujasAtstumas);
                    previous.put(kaimyne.getVirsunePavadinimas(), v);

                    //eileje ieskome kaimynes ir atnaujiname atstuma
                    forloop:
                    for(Virsune vv : eile) {
                        if (vv.getVirsunePavadinimas() == kaimyne.getVirsunePavadinimas()) {
                            eile.remove(vv);
                            vv.setAtstumas(naujasAtstumas);
                            eile.add(vv);
                            break forloop;
                        }
                    }
                }
            }
        }
        return null; 
    }

}
