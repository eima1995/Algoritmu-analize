import java.util.*;

public class Deikstra {
	public static void main(String [] args){
		Grafas g = new Grafas();
	
		g.pridetiBriauna('A', Arrays.asList(new Virsune('B', 6), new Virsune('D', 1)));
		g.pridetiBriauna('B', Arrays.asList(new Virsune('A', 6), new Virsune('D', 2), new Virsune('E', 5),
	    new Virsune('F', 1)));
		g.pridetiBriauna('C', Arrays.asList(new Virsune('B', 5), new Virsune('E', 4)));
		g.pridetiBriauna('D', Arrays.asList(new Virsune('A', 1), new Virsune('E', 1), new Virsune('B',2)));
		g.pridetiBriauna('E', Arrays.asList(new Virsune('D', 1), new Virsune('B', 5), new Virsune('C',4)));
        g.pridetiBriauna('F', Arrays.asList(new Virsune('C', 1)));

	    System.out.println(g.gautiTrumpiausiaKelia('A', 'C'));
	}
}
