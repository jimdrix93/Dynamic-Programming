package ti4.afinidad.bt;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ti4.afinidad.Cliente;
import ti4.afinidad.ProblemaAfinidad;
import us.lsi.bt.EstadoBT;
import us.lsi.bt.ProblemaBT;

public class ProblemaAfinidadBT  implements ProblemaBT<Map<String, String>, Integer>{
	
	public static ProblemaBT<Map<String, String>, Integer> create(){
		return new ProblemaAfinidadBT();
	}
	
	public us.lsi.bt.ProblemaBT.Tipo getTipo(){
		return Tipo.Max;
	}
	
	public EstadoBT<Map<String, String>,Integer> getEstadoInicial(){
		return new EstadoAfinidadBT();
	}
}
