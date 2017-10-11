package ti4.afinidad.pd;

import java.util.Map;

import ti4.afinidad.ProblemaAfinidad;
import us.lsi.pd.ProblemaPD;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ProblemaAfinidad.create("ficheros/afinidad_test2.txt");
		
		ProblemaPD<Map<String,String>, Integer> p= ProblemaAfinidadPD.create();//TODO
		AlgunosTestsPD.test2(p);
	}

}
