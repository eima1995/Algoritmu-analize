import java.util.*;

public class Deikstra {
	private Grafas grafas;
	private int atstumasXY;
	public static void main(String [] args){
		Grafas g = new Grafas();
		
		g.pridetiBriauna('A', Arrays.asList(new Virsune('B', 6), new Virsune('D', 1)));
		g.pridetiBriauna('B', Arrays.asList(new Virsune('A', 6), new Virsune('D', 2), new Virsune('C', 5)));
		g.pridetiBriauna('C', Arrays.asList(new Virsune('B', 5), new Virsune('E', 5)));
		g.pridetiBriauna('D', Arrays.asList(new Virsune('A', 1), new Virsune('E', 1), new Virsune('B',2)));
		g.pridetiBriauna('E', Arrays.asList(new Virsune('D', 1), new Virsune('B', 2), new Virsune('C',5)));
   		
		Deikstra d = new Deikstra(g);
		
		System.out.println("Iveskite x:" );
		Scanner sc = new Scanner(System.in);
		char pradzia = sc.next().charAt(0);
		
		System.out.println("Iveskite y:" );
		char pabaiga = sc.next().charAt(0);
		
	    System.out.println("Kelias " + d.gautiTrumpiausiaKelia(pradzia, pabaiga) + " atstumas " + d.atstumasXY);
	}
	
	Deikstra(Grafas grafas){
		this.grafas = grafas;
		
	}
	
	public List<Character> gautiTrumpiausiaKelia(Character pradzia, Character pabaiga) {
    	PriorityQueue<Virsune> eile = new PriorityQueue<Virsune>();
        final Map<Character, Integer> atstumai = new HashMap<Character, Integer>();
        //aprašo trumpiausio keliu medi, ji reikia isvyniuoti nuo galo
        final Map<Character, Virsune> trumpKelias = new HashMap<Character, Virsune>();
        

        //i eile patalpiname virsune ir priskiriame pradinei virsune 0
        //likusiom virsunem begalybe (musu atveju Integer.MAX_VALUE
        for(Character virsune : grafas.getVirsunes().keySet()) {
            if (virsune == pradzia) {
                atstumai.put(virsune, 0);
                eile.add(new Virsune(virsune, 0));
            } else {
                atstumai.put(virsune, Integer.MAX_VALUE);
                eile.add(new Virsune(virsune, Integer.MAX_VALUE));
            }
            trumpKelias.put(virsune, null);
        }
        System.out.println("Is viso eileje virsuniu " + eile.size());
        //nagrinejame virsune kol eile nebus tuscia
        while (!eile.isEmpty()) {
            Virsune v = eile.poll(); //is eile paimame virsune su maziausiu atstumu
            System.out.println("Is eiles paimta virsune: " + v.toString());
            
            //jei atejome iki reikiamos virsunes baigiame darba ir graziname kelia
            if (v.getVirsunePavadinimas() == pabaiga) {
            	setAtstumasXY(v.getAtstumas());
                final List<Character> kelias = new ArrayList<Character>();
                while (trumpKelias.get(v.getVirsunePavadinimas()) != null) {
                    kelias.add(v.getVirsunePavadinimas());
                    v = trumpKelias.get(v.getVirsunePavadinimas());
                }
                kelias.add(v.getVirsunePavadinimas());
                return kelias;
            }
            
            //pabaigiame darba, tai reiskia, kad pries tai buvusios virsunes neturejo kaimyniu
            if (atstumai.get(v.getVirsunePavadinimas()) == Integer.MAX_VALUE) {
                break;
            }
            
            //einame per visas virsunes kaimynes ir skaiciuojame trumpiausia atstuma
            //jei randame atnaujiname eile ismeta su senu atstumu virsune ir idedame su nauju
            for (Virsune kaimyne : grafas.getVirsunes().get(v.getVirsunePavadinimas())) {
                Integer naujasAtstumas = atstumai.get(v.getVirsunePavadinimas()) + kaimyne.getAtstumas();
                System.out.println("Kaimyne: " + kaimyne.getVirsunePavadinimas() + " atstumas " + kaimyne.getAtstumas());
                if (naujasAtstumas < atstumai.get(kaimyne.getVirsunePavadinimas())) {
                    atstumai.put(kaimyne.getVirsunePavadinimas(), naujasAtstumas);
                    trumpKelias.put(kaimyne.getVirsunePavadinimas(), v);

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

	public int getAtstumasXY() {
		return atstumasXY;
	}

	public void setAtstumasXY(int atstumasXY) {
		this.atstumasXY = atstumasXY;
	}
}
