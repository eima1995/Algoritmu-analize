
public class Virsune implements Comparable<Virsune>{
	
	private Character virsunesPavadinimas;
    private Integer atstumas;
    
    Virsune(Character virsunesPavadinimas, Integer atstumas){
    	this.atstumas = atstumas;
    	this.virsunesPavadinimas = virsunesPavadinimas;
    }
    
	public Character getVirsunePavadinimas() {
		return virsunesPavadinimas;
	}
	
	public void setVirsunePavadinimas(Character virsunePavadinimas) {
		this.virsunesPavadinimas = virsunesPavadinimas;
	}
	
	public Integer getAtstumas() {
		return atstumas;
	}
	
	public void setAtstumas(Integer atstumas) {
		this.atstumas = atstumas;
	}
	
	public String toString() {
        return "Virsune [id =" + virsunesPavadinimas + ", atstumas=" + atstumas + "]";
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
	
}
