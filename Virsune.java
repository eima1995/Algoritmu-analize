import java.util.*;
public class Virsune implements Comparable<Virsune>{
	
	private Integer virsunesPavadinimas;
	private List<Integer> cord = new ArrayList<Integer>();
    private double atstumas; 
    
	Virsune(){
    	
    }
    
    Virsune(Integer virsunesPavadinimas, List<Integer> cord){
    	this.setCord(cord);
    	this.virsunesPavadinimas = virsunesPavadinimas;
    }
    
    Virsune(Integer virsunesPavadinimas, double atstumas){
    	this.virsunesPavadinimas = virsunesPavadinimas;
    	this.atstumas = atstumas;
    }
    
	public Integer getVirsunePavadinimas() {
		return virsunesPavadinimas;
	}
	
	public void setVirsunePavadinimas(Integer virsunePavadinimas) {
		this.virsunesPavadinimas = virsunePavadinimas;
	}
	
	public Double getAtstumas() {
		return atstumas;
	}
	
	public void setAtstumas(Double atstumas) {
		this.atstumas = atstumas;
	}
	
	public String toString() {
        return "Virsune [id =" + virsunesPavadinimas + ", dist=" + atstumas + "]";
    }
	
	@Override
	public int compareTo(Virsune v) {
		if (this.atstumas < v.atstumas)
			return -1;
		else if (this.atstumas > v.atstumas)
			return 1;
	    else
	    	return this.getVirsunePavadinimas().compareTo(v.getVirsunePavadinimas());
	}

	public List<Integer> getCord() {
		return cord;
	}

	public void setCord(List<Integer> cord) {
		this.cord = cord;
	}
	
	public double paskaiciuotiAtstuma(Virsune v){
		double atstumas = 0;
		
		for(int i = 0; i < v.getCord().size(); i++){
			atstumas += Math.pow(v.getCord().get(i) - this.cord.get(i), this.cord.size());
		}
		
		v.setAtstumas(Math.sqrt(atstumas));
		return Math.sqrt(atstumas);
	}	
	
	public double numanomasAts(Virsune v){
		double atstumas = 0;
		
		for(int i = 0; i < v.getCord().size(); i++){
			atstumas += Math.pow(v.getCord().get(i) - this.cord.get(i), this.cord.size());
		}
		return Math.sqrt(atstumas);
	}	
}
