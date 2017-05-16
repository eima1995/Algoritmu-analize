import java.util.*;

public class Deikstra {
	private Grafas grafas;
	private double atstumasXY;
	public static void main(String [] args) throws Exception{
		Grafas g = new Grafas();
		
		double time = 0;
		//be param vykdo abu su random duomenimis
		//jei vienas parametras irasytas vykdo tik ta nurodyta algoritma,
		//jei du tai abu su generuotais duomenimimis nurodzius n ir m.
		
		
		
		if(args.length <= 2){
			if(args.length == 0){
				//vykdome abu algortimus sugeneruotais duomenimis
			}else if(args.length == 1){
				if(args[0].equals(1)){
					//vykdome Dieikstra algoritma 
				}else if(args[1].equals(2)){
					//vykdome Sedgewick-Vitter algoritma
				}else{
					throw new Exception("Klaidingai ivesti parametrai");
				}
			}else{
				if((args[0].equals(1)) && (args[0].equals(2)) ||
				   (args[0].equals(2)) && (args[0].equals(1))){
					//vykdome abu algoritmus skaituome duomenys is failas.txt
				}else{
					throw new Exception("Klaidingai ivesti parametrai");
				}
			}
		}else{
			throw new Exception("Klaidingai ivesti parametrai");
		}
			
	
		//Sukuriame virsunes su ju koordinatemis
		Virsune A = new Virsune('A', Arrays.asList(0,6)); 
		Virsune B = new Virsune('B', Arrays.asList(3,6));
		Virsune C = new Virsune('C', Arrays.asList(1,4));
		Virsune D = new Virsune('D', Arrays.asList(7,9));
		Virsune E = new Virsune('E', Arrays.asList(7,4));
		
		//Sukuriame briaunas
		g.pridetiBriauna('A', Arrays.asList(C,B));
		g.pridetiBriauna('B', Arrays.asList(A,D));
		g.pridetiBriauna('C', Arrays.asList(A,E));
		g.pridetiBriauna('D', Arrays.asList(E,B));
		g.pridetiBriauna('E', Arrays.asList(C,D));
   		
		Deikstra d = new Deikstra(g);
		
		System.out.println("Iveskite x:" );
		Scanner sc = new Scanner(System.in);
		char pradzia = sc.next().charAt(0);
		
		System.out.println("Iveskite y:" );
		char pabaiga = sc.next().charAt(0);
	    
		time = System.currentTimeMillis();
	    System.out.println("Kelias " + d.gautiTrumpiausiaKelia(pradzia, pabaiga) + " atstumas " + d.atstumasXY);
	    
	    System.out.println("Vykdymo laikas " + (System.currentTimeMillis() - time) + " ms");
	    
	    
	}
	
	Deikstra(Grafas grafas){
		this.grafas = grafas;
		
	}
	
	public List<Character> gautiTrumpiausiaKelia(Character pradzia, Character pabaiga) throws Exception {
		gautiVirs(pabaiga);
    	PriorityQueue<Virsune> eile = new PriorityQueue<Virsune>();
        final Map<Character, Double> atstumai = new HashMap<Character, Double>();
        //aprašo trumpiausia kelia, ji reikia isvyniuoti nuo galo
        final Map<Character, Virsune> trumpKelias = new HashMap<Character, Virsune>();
        final List<Character> aplankytos = new ArrayList<Character>();

        //i eile patalpiname virsune ir priskiriame pradinei virsune 0
        //likusiom virsunem begalybe (musu atveju Double.MAX_VALUE
        for(Character virsune : grafas.getVirsunes().keySet()) {
            if (virsune == pradzia) {
                atstumai.put(virsune, 0d);
                eile.add(gautiVirs(pradzia));
            } else {
                atstumai.put(virsune, Double.MAX_VALUE);
                eile.add(new Virsune(virsune, Double.MAX_VALUE));
            }
            trumpKelias.put(virsune, null);
        }
        
        System.out.println("Is viso eileje virsuniu " + eile.size());
        //nagrinejame virsune kol eile nebus tuscia
        while (!eile.isEmpty()) {
            Virsune v = eile.poll(); //is eile paimame virsune su maziausiu atstumu
            System.out.println("Is eiles paimta virsune: " + v.toString());
           
            if (v.getVirsunePavadinimas() == pabaiga) {
                final List<Character> kelias = new ArrayList<Character>();
                setAtstumasXY(atstumai.get(v.getVirsunePavadinimas()));
                while (trumpKelias.get(v.getVirsunePavadinimas()) != null) {
                    kelias.add(v.getVirsunePavadinimas());
                    v = trumpKelias.get(v.getVirsunePavadinimas());
                }
                kelias.add(v.getVirsunePavadinimas());
                Collections.reverse(kelias);
                return kelias;
            }
            
            
            //pabaigiame darba, tai reiskia, kad pries tai buvusios virsunes neturejo kaimyniu
            if (atstumai.get(v.getVirsunePavadinimas()) == Double.MAX_VALUE) {
                break;
            }
            
            //einame per visas virsunes kaimynes ir skaiciuojame trumpiausia atstuma
            //jei randame atnaujiname eile ismeta su senu atstumu virsune ir idedame su nauju
            for (Virsune kaimyne : grafas.getVirsunes().get(v.getVirsunePavadinimas())) {
            	if(!arAplankyta(kaimyne.getVirsunePavadinimas(), aplankytos)){
            		double atstumasIkiKaimyno = v.paskaiciuotiAtstuma(kaimyne);
                    double naujasAtstumas = atstumai.get(v.getVirsunePavadinimas()) + atstumasIkiKaimyno;
                    System.out.println("Kaimyne: " + kaimyne.getVirsunePavadinimas() + " atstumas " + atstumasIkiKaimyno);
                    
                    if (naujasAtstumas < atstumai.get(kaimyne.getVirsunePavadinimas())) {
                        atstumai.put(kaimyne.getVirsunePavadinimas(), naujasAtstumas);
                        trumpKelias.put(kaimyne.getVirsunePavadinimas(), v);
                        //eileje ieskome kaimynes ir atnaujiname atstuma
                        forloop:
                        for(Virsune vv : eile) {
                            if (vv.getVirsunePavadinimas() == kaimyne.getVirsunePavadinimas()) {
                                eile.remove(vv);
                                kaimyne.setAtstumas(naujasAtstumas);
                                eile.add(kaimyne);
                                break forloop;
                            }
                        }
                    }
            	}
            }
        }
        throw new Exception("Neimano pasiekti virsunes" + pabaiga); 
    }
	
	public boolean arAplankyta(Character virsunePav, List<Character> aplankytos){
		for(Character c: aplankytos){
			if(virsunePav.equals(c.charValue())){
				return true;
			}
		}
		return false;
	}
	
	public Virsune gautiVirs(Character virsune) throws Exception{
		boolean virsuneNeturiKaimyniu = true;
		Virsune ieskomaVirsune = new Virsune();
		Iterator<Map.Entry<Character, List<Virsune>>> it = grafas.getVirsunes().entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<Character, List<Virsune>> pair = it.next();
			List<Virsune> virsunes = pair.getValue();
		    for(Virsune v: virsunes){
		    	if(v.getVirsunePavadinimas().equals(virsune)){
		    		virsuneNeturiKaimyniu = false;	
		    		ieskomaVirsune = v;
		    	}
		    }
		}
		
		if(virsuneNeturiKaimyniu){
			throw new Exception("Neimanoma pasiekti virsunes" + virsune);
		}
		return ieskomaVirsune;
	}
	
	public List<Character> gautiTrumpiausiaKelia2(Character pradzia, Character pabaiga) throws Exception {
		Virsune pabVirs = gautiVirs(pabaiga);
    	PriorityQueue<Virsune> eile = new PriorityQueue<Virsune>();
        final Map<Character, Double> atstumai = new HashMap<Character, Double>();
        //aprašo trumpiausio kelia, ji reikia isvyniuoti nuo galo
        final Map<Character, Virsune> trumpKelias = new HashMap<Character, Virsune>();
        final List<Character> aplankytos = new ArrayList<Character>();
        
        //i eile patalpiname virsune ir priskiriame pradinei virsune 0
        //likusiom virsunem begalybe (musu atveju Double.MAX_VALUE)
        for(Character virsune : grafas.getVirsunes().keySet()) {
            if (virsune == pradzia) {
                atstumai.put(virsune, 0d);
                eile.add(gautiVirs(pradzia));
            } else {
                atstumai.put(virsune, Double.MAX_VALUE);
                eile.add(new Virsune(virsune, Double.MAX_VALUE));
            }
            trumpKelias.put(virsune, null);
        }
        
        System.out.println("Is viso eileje virsuniu " + eile.size());
        //nagrinejame virsune kol eile nebus tuscia
        while (!eile.isEmpty()) {
        	
            Virsune v = eile.poll(); //is eile paimame virsune su maziausiu numatomu atstumu
            aplankytos.add(v.getVirsunePavadinimas());
            System.out.println("Is eiles paimta virsune: " + v.toString());
            
            //jei atejome iki reikiamos virsunes baigiame darba ir graziname kelia
            if (v.getVirsunePavadinimas() == pabaiga) {
                final List<Character> kelias = new ArrayList<Character>();
                setAtstumasXY(atstumai.get(v.getVirsunePavadinimas()));
                while (trumpKelias.get(v.getVirsunePavadinimas()) != null) {
                    kelias.add(v.getVirsunePavadinimas());
                    v = trumpKelias.get(v.getVirsunePavadinimas());
                }
                kelias.add(v.getVirsunePavadinimas());
                Collections.reverse(kelias);
                return kelias;
            }
            
            //pabaigiame darba, tai reiskia, kad pries tai buvusios virsunes neturejo kaimyniu
            if (atstumai.get(v.getVirsunePavadinimas()) == Double.MAX_VALUE) {
                break;
            }
            
            //einame per visas virsunes kaimynes ir skaiciuojame trumpiausia atstuma
            //jei randame atnaujiname eile ismeta su senu atstumu virsune ir idedame su nauju
            for (Virsune kaimyne : grafas.getVirsunes().get(v.getVirsunePavadinimas())) {
            	 	//jei atejome iki reikiamos virsunes baigiame darba ir graziname kelia
                    if (kaimyne.getVirsunePavadinimas() == pabaiga) {
                        final List<Character> kelias = new ArrayList<Character>();
                        setAtstumasXY(atstumai.get(v.getVirsunePavadinimas()));
                        while (trumpKelias.get(v.getVirsunePavadinimas()) != null) {
                            kelias.add(v.getVirsunePavadinimas());
                            v = trumpKelias.get(v.getVirsunePavadinimas());
                        }
                        kelias.add(v.getVirsunePavadinimas());
                        Collections.reverse(kelias);
                        return kelias;
                    }
                    if(!arAplankyta(kaimyne.getVirsunePavadinimas(), aplankytos)){
                    	double numanomasAtstumas = kaimyne.paskaiciuotiAtstuma(pabVirs); //atsumas kaimynes iki pabaigos virsunes, imamas tiesus atstumas
                    	double atstumasIkiKaimyno = v.paskaiciuotiAtstuma(kaimyne);
                        double naujasAtstumas = atstumai.get(v.getVirsunePavadinimas()) + atstumasIkiKaimyno + numanomasAtstumas;
                        System.out.println("Kaimyne: " + kaimyne.getVirsunePavadinimas() + " f " + naujasAtstumas);
                        
                        if (naujasAtstumas < atstumai.get(kaimyne.getVirsunePavadinimas())) {
                            atstumai.put(kaimyne.getVirsunePavadinimas(), naujasAtstumas);
                            trumpKelias.put(kaimyne.getVirsunePavadinimas(), v);
                            //eileje ieskome kaimynes ir atnaujiname atstuma
                            forloop:
                            for(Virsune vv : eile) {
                                if (vv.getVirsunePavadinimas() == kaimyne.getVirsunePavadinimas()) {
                                    eile.remove(vv);
                                    System.out.println("Naujas atstumas" + naujasAtstumas);
                                    kaimyne.setAtstumas(naujasAtstumas);
                                    eile.add(kaimyne);
                                    break forloop;
                                }
                            }
                        }
                    }
            }
        }
        throw new Exception("Neiimano pasiekti virsunes" + pabaiga); 
    }
	
	public double getAtstumasXY() {
		return atstumasXY;
	}

	public void setAtstumasXY(double atstumasXY) {
		this.atstumasXY = atstumasXY;
	}
}
