import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
		
		sugeneruotiGrafa();
		/*
		if(args.length <= 2){
			if(args.length == 0){
				//vykdome abu algortimus sugeneruotais duomenimis
				g = sugeneruotiGrafa();
			}else if(args.length == 1){
				if(args[0].equals(1)){
					//vykdome Dieikstra algoritma 
				}else if(args[1].equals(2)){
					//vykdome Sedgewick-Vitter algoritma
				}else{
					throw new Exception("Klaidingai ivesti parametrai");
				}
			}else{
				if((args[0].equals("1")) && (args[1].equals("2"))){
					Deikstra d = new Deikstra(skaitytiIsFailo()); 
				    System.out.println("Iveskite x:" );
					Scanner sc = new Scanner(System.in);
					Integer pradzia = sc.nextInt();
					
					System.out.println("Iveskite y:" );
					Integer pabaiga = sc.nextInt();
				    
					time = System.currentTimeMillis();
				    System.out.println("Kelias " + d.gautiTrumpiausiaKelia(pradzia, pabaiga) + " atstumas " + d.atstumasXY);
				    
				    System.out.println("Vykdymo laikas " + (System.currentTimeMillis() - time) + " ms");
				    
				    sc.close();
				}else{
					throw new Exception("Klaidingai ivesti parametrai");
				}
			}
		}else{
			throw new Exception("Klaidingai ivesti parametrai");
		}
			*/
	
		//Sukuriame virsunes su ju koordinatemis
		/*
		Virsune A = new Virsune('A', Arrays.asList(0,6)); 
		Virsune B = new Virsune('B', Arrays.asList(3,6));
		Virsune C = new Virsune('C', Arrays.asList(1,4));
		Virsune D = new Virsune('D', Arrays.asList(7,9));
		Virsune E = new Virsune('E', Arrays.asList(7,4));
		*/
		//Sukuriame briaunas
		/*
		g.pridetiBriauna('A', Arrays.asList(C,B));
		g.pridetiBriauna('B', Arrays.asList(A,D));
		g.pridetiBriauna('C', Arrays.asList(A,E));
		g.pridetiBriauna('D', Arrays.asList(E,B));
		g.pridetiBriauna('E', Arrays.asList(C,D));
   		*/
		
		
		
		
		Virsune v1 = new Virsune(1, Arrays.asList(1,2));
		Virsune v2 = new Virsune(2, Arrays.asList(1,2));
		
        
		g.pridetiBriauna(1, new LinkedList(Arrays.asList()));
		g.pridetiBriauna(2, new LinkedList(Arrays.asList(v2)));
		//System.out.println("Virsunes" + g.getVirsunes());
		//g.pridetiBriauna(2,  Arrays.asList());
		
		
		///g.pridetiBriauna(1,  Arrays.asList(v1,v2));
		System.out.println("Virsunes pries" + g.getVirsunes());
		g.pridetiBriauna(1, 2, v1, v2);
		System.out.println("Virsunes" + g.getVirsunes());
		
		
		Deikstra d = new Deikstra(g);
		
		System.out.println("Iveskite x:" );
		Scanner sc = new Scanner(System.in);
		Integer pradzia = sc.nextInt();
		
		System.out.println("Iveskite y:" );
		Integer pabaiga = sc.nextInt();
	    
		time = System.currentTimeMillis();
	    System.out.println("Kelias " + d.gautiTrumpiausiaKelia(pradzia, pabaiga) + " atstumas " + d.atstumasXY);
	    
	    System.out.println("Vykdymo laikas " + (System.currentTimeMillis() - time) + " ms");
	    
	    sc.close();
	}
	
	Deikstra(Grafas grafas){
		this.grafas = grafas;
		
	}
	
	public List<Integer> gautiTrumpiausiaKelia(Integer pradzia, Integer pabaiga) throws Exception {
		gautiVirs(pabaiga);
    	PriorityQueue<Virsune> eile = new PriorityQueue<Virsune>();
        final Map<Integer, Double> atstumai = new HashMap<Integer, Double>();
        //aprašo trumpiausia kelia, ji reikia isvyniuoti nuo galo
        final Map<Integer, Virsune> trumpKelias = new HashMap<Integer, Virsune>();
        final List<Integer> aplankytos = new ArrayList<Integer>(); //isnagrinetos virsune

        //i eile patalpiname virsune ir priskiriame pradinei virsune 0
        //likusiom virsunem begalybe (musu atveju Double.MAX_VALUE
        for(Integer virsune : grafas.getVirsunes().keySet()) {
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
                final List<Integer> kelias = new ArrayList<Integer>();
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
	
	public boolean arAplankyta(Integer virsunePav, List<Integer> aplankytos){
		for(Integer c: aplankytos){
			if(virsunePav.equals(c)){
				return true;
			}
		}
		return false;
	}
	
	public Virsune gautiVirs(Integer virsune) throws Exception{
		boolean virsuneNeturiKaimyniu = true;
		Virsune ieskomaVirsune = new Virsune();
		Iterator<Map.Entry<Integer, List<Virsune>>> it = grafas.getVirsunes().entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<Integer, List<Virsune>> pair = it.next();
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
	
	public List<Integer> gautiTrumpiausiaKelia2(Integer pradzia, Integer pabaiga) throws Exception {
		Virsune pabVirs = gautiVirs(pabaiga);
    	PriorityQueue<Virsune> eile = new PriorityQueue<Virsune>();
        final Map<Integer, Double> atstumai = new HashMap<Integer, Double>();
        //aprašo trumpiausio kelia, ji reikia isvyniuoti nuo galo
        final Map<Integer, Virsune> trumpKelias = new HashMap<Integer, Virsune>();
        final List<Integer> aplankytos = new ArrayList<Integer>();
        
        //i eile patalpiname virsune ir priskiriame pradinei virsune 0
        //likusiom virsunem begalybe (musu atveju Double.MAX_VALUE)
        for(Integer virsune : grafas.getVirsunes().keySet()) {
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
                final List<Integer> kelias = new ArrayList<Integer>();
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
                        final List<Integer> kelias = new ArrayList<Integer>();
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
        throw new Exception("Neimanoma pasiekti virsunes" + pabaiga); 
    }
	
	public double getAtstumasXY() {
		return atstumasXY;
	}

	public void setAtstumasXY(double atstumasXY) {
		this.atstumasXY = atstumasXY;
	}
	
	public static Grafas sugeneruotiGrafa(){
		int n;
		int m;
		Grafas g = new Grafas();
		ArrayList<Virsune> virsunes = new ArrayList<Virsune>();
		System.out.println("Iveskite virsuniu sk");
		Scanner sc = new Scanner(System.in);
		n = sc.nextInt();
		System.out.println("Iveskite briaunu sk");
		m = sc.nextInt();
		
		//sukuriame virsunes su koordinatemis
		for(int i = 1; i <= n; i++){
			Virsune v = new Virsune(i, Arrays.asList(0,i - 1));
			virsunes.add(v);
		}
		System.out.println("Prideta virsuniu: " + virsunes.size());
	
		for(int i = 1; i <= n; i++){	
			g.pridetiBriauna(i, new LinkedList(Arrays.asList()));
		}
		
		//System.out.println(g.getVirsunes());
		
		Random random1 = new Random();
		Random random = new Random();
		///System.out.println("Paimu is listo virsune" + virsunes.get(9));
        
		System.out.println("Sugeneruotos virsunes:");
		for (int j = 1; j <= (m / n); j++){
            for (int i = 1; i <= n; i++) {
            	int randNum = random1.nextInt((n - 1) + 1);
            	while(randNum == i || randNum == 0){
            		randNum = random1.nextInt((n - 1) + 1);
            	}
                System.out.println("(" + i + ", " + randNum + ")");
               // System.out.println(g.getVirsunes());
                g.pridetiBriauna(randNum, i, virsunes.get(randNum - 1), virsunes.get(i - 1));
            }
            
		}
		
		System.out.println(g.getVirsunes());
		System.out.println("AS CIA");
		return g;
	}
	
	public static Grafas skaitytiIsFailo() throws FileNotFoundException, IOException{
		Grafas g = new Grafas();
		ArrayList<Virsune> virsunes = new ArrayList<Virsune>();
		BufferedReader br = new BufferedReader(new FileReader("failas.txt"));
		StringBuilder sb = new StringBuilder();
		String dabEilute = br.readLine();
		String temp = "";
		int n = 0; //virsuniu sk
		while (dabEilute != null) {
			ArrayList<Integer> cord = new ArrayList<Integer>();
			n++;
			//System.out.println(dabEilute);
			if(dabEilute.startsWith(("BRIAUNOS"))){
				for(Virsune v : virsunes){
					ArrayList<Virsune> virs = new ArrayList<Virsune>();
					//System.out.println("Idetos grafe virsunes" + g.getVirsunes());
			    	while ((dabEilute = br.readLine()) != null){
			    		String [] duom =  dabEilute.split("|");
			    		//System.out.println("Parsinimas" + Integer.parseInt(duom[0]));
			    		//System.out.println(virsunes.size());
			    		/*
			    		g.pridetiBriauna(Integer.parseInt(duom[0]), Integer.parseInt(duom[1]), virsunes);
			    		g.pridetiBriauna(Integer.parseInt(duom[1]), Integer.parseInt(duom[0]), virsunes);
			    		*/
				    }
				}
			
		    }else{
		    	sb.append(dabEilute);
				String [] duom =  dabEilute.split(",");
				
				for(int i = 0; i < duom.length; i++){
					cord.add(Integer.parseInt(duom[i]));
				}
				Virsune v = new Virsune(n, cord);
				System.out.println("Virsune " +v.getVirsunePavadinimas() + " " + v.getCord());
				//sukuriame virsune
				
				virsunes.add(v);
			    dabEilute = br.readLine();
		    }
		 }
		    return g;
		
	}
}
