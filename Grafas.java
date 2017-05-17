import java.util.*;

public class Grafas {

    private  Map<Integer, List<Virsune>> virsunes; //saugome visas virsunes su ju kaimynemis

    public Grafas() {
        this.virsunes = new HashMap<Integer, List<Virsune>>();
    }

    public void pridetiBriauna(Integer pav, List<Virsune> briaunos) {
        this.virsunes.put(pav, briaunos);
    }
/*    
    public void pridetiBriauna(Integer v1, Integer v2, ArrayList<Virsune> v){
    	//randu ta virsune ir pridedu briauna
    	Iterator<Map.Entry<Integer, List<Virsune>>> it = this.virsunes.entrySet().iterator();
        while (it.hasNext()) {
        	Map.Entry<Integer, List<Virsune>> pair = it.next();
            if(pair.getKey().equals(v1)){
            	System.out.println("Virsune v1 " + v1);
            	List<Virsune> virsunes = pair.getValue();
            	
            }
            if(pair.equals(v2)){
            	System.out.println("Virsune v2 " + v2);
            	List<Virsune> virsunes = pair.getValue();
            	virsunes.add(v.get(v2));
            }
            it.remove(); // avoids a ConcurrentModificationException
        }
    	
        System.out.println("Virsunes" + virsunes.containsKey(v2));

    }
  */  
    
    public void pridetiBriauna(Integer v1, Integer v2, Virsune virs1, Virsune virs2){
    	//randu ta virsune ir pridedu briauna
    	Iterator<Map.Entry<Integer, List<Virsune>>> it = this.virsunes.entrySet().iterator();
        while (it.hasNext()) {
        	Map.Entry<Integer, List<Virsune>> pair = it.next();
            if(pair.getKey().equals(v1)){
            	//System.out.println("Virsune v1 " + v1);
            	//System.out.println(virs2);
            	pair.getValue().add(virs2);
            }
            if(pair.getKey().equals(v2)){
            	//System.out.println("Virsune v2 " + v2);
            	pair.getValue().add(virs1);
            }
            //it.remove(); // avoids a ConcurrentModificationException
        }
    }
    public Map<Integer, List<Virsune>> getVirsunes(){
    	return virsunes;
    }
}
