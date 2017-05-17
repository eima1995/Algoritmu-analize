import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Deikstra {
	private Grafas grafas;
	private double atstumasXY;
	static int pradzia;
	static int pabaiga;
	public static void main(String [] args) throws Exception{
		Grafas g = new Grafas();
		
		double time = 0;
		//be param vykdo abu su random duomenimis
		//jei vienas parametras irasytas vykdo tik ta nurodyta algoritma,
		//jei du tai abu su generuotais duomenimimis nurodzius n ir m.
		
		
		
		if(args.length <= 2){
			if(args.length == 0){
				//vykdome abu algortimus sugeneruotais duomenimis
				Deikstra d = new Deikstra(sugeneruotiGrafa()); 
				 int pradzia = 1;
				 System.out.println("Deikstros algoritmo rezultatai:");
			     time = System.currentTimeMillis();					
				 System.out.println("Kelias " + d.gautiTrumpiausiaKelia(pradzia, pabaiga));
				 System.out.println("Vykdymo laikas " + (System.currentTimeMillis() - time) + " ms");
				 System.out.println("-----------------------------------------------------------------------------");
				 time = System.currentTimeMillis();
				 System.out.println("Sedwick-Vitter algoritmo rezultatai:");
				 System.out.println("Kelias " + d.gautiTrumpiausiaKelia2(pradzia, pabaiga));
				    System.out.println("Vykdymo laikas " + (System.currentTimeMillis() - time) + " ms");
			}else if(args.length == 1){
				if(args[0].equals("1")){
					Deikstra d = new Deikstra(skaitytiIsFailo());
					ivedimasXY();
					System.out.println("Deikstros algoritmo rezultatai:");
				    time = System.currentTimeMillis();					
					System.out.println("Kelias " + d.gautiTrumpiausiaKelia(pradzia, pabaiga));
					System.out.println("Vykdymo laikas " + (System.currentTimeMillis() - time) + " ms");
				}else if(args[0].equals("2")){
					Deikstra d = new Deikstra(skaitytiIsFailo());
					ivedimasXY();
					System.out.println("Sedgewick algoritmo rezultatai:");
				    time = System.currentTimeMillis();					
					System.out.println("Kelias " + d.gautiTrumpiausiaKelia2(pradzia, pabaiga));
					System.out.println("Vykdymo laikas " + (System.currentTimeMillis() - time) + " ms");
				}else{
					throw new Exception("Klaidingai ivesti parametrai");
				}
			}else{
				if((args[0].equals("1")) && (args[1].equals("2"))){
					//abu algoritmai vykdomi su nuskaitytais duomeniimas
					Deikstra d = new Deikstra(skaitytiIsFailo()); 
					ivedimasXY();
					
					System.out.println("Deikstros algoritmo rezultatai:");
				    time = System.currentTimeMillis();					
					System.out.println("Kelias " + d.gautiTrumpiausiaKelia(pradzia, pabaiga));
					System.out.println("Vykdymo laikas " + (System.currentTimeMillis() - time) + " ms");
					System.out.println("-----------------------------------------------------------------------------");
					System.out.println("Sedgewick algoritmo rezultatai:");
				    time = System.currentTimeMillis();					
					System.out.println("Kelias " + d.gautiTrumpiausiaKelia2(pradzia, pabaiga));
					System.out.println("Vykdymo laikas " + (System.currentTimeMillis() - time) + " ms");
				    
				}else{
					throw new Exception("Klaidingai ivesti parametrai");
				}
			}
		}else{
			throw new Exception("Klaidingai ivesti parametrai");
		}
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
        
        //System.out.println("Is viso eileje virsuniu " + eile.size());
        //nagrinejame virsune kol eile nebus tuscia
        while (!eile.isEmpty()) {
            Virsune v = eile.poll(); //is eile paimame virsune su maziausiu atstumu
           // System.out.println("Is eiles paimta virsune: " + v.toString());
           
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
                    //System.out.println("Kaimyne: " + kaimyne.getVirsunePavadinimas() + " atstumas " + atstumasIkiKaimyno);
                    
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
        
        //System.out.println("Is viso eileje virsuniu " + eile.size());
        //nagrinejame virsune kol eile nebus tuscia
        while (!eile.isEmpty()) {
        	
            Virsune v = eile.poll(); //is eile paimame virsune su maziausiu numatomu atstumu
            aplankytos.add(v.getVirsunePavadinimas());
            //System.out.println("Is eiles paimta virsune: " + v.toString());
            
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
                        kelias.add(pabaiga);
                        return kelias;
                    }
                    if(!arAplankyta(kaimyne.getVirsunePavadinimas(), aplankytos)){
                    	double numanomasAtstumas = kaimyne.paskaiciuotiAtstuma(pabVirs); //atsumas kaimynes iki pabaigos virsunes, imamas tiesus atstumas
                    	double atstumasIkiKaimyno = v.paskaiciuotiAtstuma(kaimyne);
                        double naujasAtstumas = atstumai.get(v.getVirsunePavadinimas()) + atstumasIkiKaimyno + numanomasAtstumas;
                        //System.out.println("Kaimyne: " + kaimyne.getVirsunePavadinimas() + " f " + naujasAtstumas);
                        
                        if (naujasAtstumas < atstumai.get(kaimyne.getVirsunePavadinimas())) {
                            atstumai.put(kaimyne.getVirsunePavadinimas(), naujasAtstumas);
                            trumpKelias.put(kaimyne.getVirsunePavadinimas(), v);
                            //eileje ieskome kaimynes ir atnaujiname atstuma
                            forloop:
                            for(Virsune vv : eile) {
                                if (vv.getVirsunePavadinimas() == kaimyne.getVirsunePavadinimas()) {
                                    eile.remove(vv);
                                   // System.out.println("Naujas atstumas" + naujasAtstumas);
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
		pabaiga = n;
		System.out.println("Iveskite briaunu sk");
		m = sc.nextInt();
		
		//sukuriame virsunes su koordinatemis
		for(int i = 1; i <= n; i++){
			Virsune v = new Virsune(i, Arrays.asList(0,i - 1));
			virsunes.add(v);
		}
		//System.out.println("Prideta virsuniu: " + virsunes.size());
	
		for(int i = 1; i <= n; i++){	
			g.pridetiBriauna(i, new LinkedList(Arrays.asList()));
		}
		
		//System.out.println(g.getVirsunes());
		
		Random random1 = new Random();
		Random random = new Random();
		///System.out.println("Paimu is listo virsune" + virsunes.get(9));
        
		//System.out.println("Sugeneruotos virsunes:");
		for (int j = 1; j <= (m / n); j++){
            for (int i = 1; i <= n; i++) {
            	int randNum = random1.nextInt((n - 1) + 1);
            	while(randNum == i || randNum == 0){
            		randNum = random1.nextInt((n - 1) + 1);
            	}
                //System.out.println("(" + i + ", " + randNum + ")");
               // System.out.println(g.getVirsunes());
                g.pridetiBriauna(randNum, i, virsunes.get(randNum - 1), virsunes.get(i - 1));
            }
            
		}
		
		System.out.println(g.getVirsunes());
		return g;
	}
	
	public static Grafas skaitytiIsFailo() throws Exception{
		Grafas g = new Grafas();
		ArrayList<Virsune> virsunes = new ArrayList<Virsune>();
		BufferedReader br = new BufferedReader(new FileReader("failas.txt"));
		StringBuilder sb = new StringBuilder();
		String dabEilute = br.readLine();
		String temp = "";
		int n = 0; //virsuniu sk
		while (dabEilute != null) {
			ArrayList<Integer> cord = new ArrayList<Integer>();
			n++;    /// tai kelinta virsune jos numeris
			//System.out.println(dabEilute);
			if(dabEilute.startsWith(("BRIAUNOS"))){
				for(Virsune v : virsunes){
					ArrayList<Virsune> virs = new ArrayList<Virsune>();
					//System.out.println("Idetos grafe virsunes" + g.getVirsunes());
					dabEilute = br.readLine();
			    	while (dabEilute != null){
			    		
			    		String [] duom =  dabEilute.split(",");
			    		int[] ints = new int[duom.length];
			    		for(int i=0; i< duom.length; i++){
			    			ints[i] = Integer.parseInt(duom[i]);   
			    			System.out.println("PARSINASI" + ints[i]);
			    		}
			    		g.pridetiBriauna(ints[0], ints[1] , virsunes.get(ints[0] - 1), virsunes.get(ints[1] - 1));
			    		dabEilute = br.readLine();
				    }
				}
			
		    }else{
				String [] duom =  dabEilute.split(",");
				
				for(int i = 0; i < duom.length; i++){
					cord.add(Integer.parseInt(duom[i]));
				}
				Virsune v = new Virsune(n, cord);
				System.out.println("Virsune " +v.getVirsunePavadinimas() + " " + v.getCord());
				//sukuriame virsune
				
				g.pridetiBriauna(n, new LinkedList(Arrays.asList()));				
				virsunes.add(v);
			    dabEilute = br.readLine();
		    }
		 }
		    return g;
		
	}
	public static void ivedimasXY(){
		System.out.println("Iveskite x:" );
		Scanner sc = new Scanner(System.in);
		pradzia = sc.nextInt();
		
		System.out.println("Iveskite y:" );
		pabaiga = sc.nextInt();
		sc.close();
		
	}
}
