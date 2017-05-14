import java.util.*;

public class Grafas {

    private final Map<Character, List<Virsune>> virsunes; //saugome visas virsunes su ju kaimynemis

    public Grafas() {
        this.virsunes = new HashMap<Character, List<Virsune>>();
    }

    public void pridetiBriauna(Character pav, List<Virsune> briaunos) {
        this.virsunes.put(pav, briaunos);
    }
    
    public Map<Character, List<Virsune>> getVirsunes(){
    	return virsunes;
    }
}
