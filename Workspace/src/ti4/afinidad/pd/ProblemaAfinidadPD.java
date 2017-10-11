package ti4.afinidad.pd;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ti4.afinidad.Cliente;
import ti4.afinidad.ProblemaAfinidad;
import us.lsi.pd.AlgoritmoPD.Sp;
import us.lsi.pd.ProblemaPD;

public class ProblemaAfinidadPD  implements ProblemaPD<Map<String, String>, Integer> {
	
	private ProblemaAfinidad problema;
	
	private int indice;
	private double nAfinidad;
	private Map<String,Set<Integer>> trabajadoresOcupados;
	private Map<String,String> asignacion;
	
	public static ProblemaAfinidadPD create(int indice, double nAfinidad, 
			Map<String,Set<Integer>> trabajadoresOcupados, Map<String,String> asignacion) {
		return new ProblemaAfinidadPD(indice,nAfinidad,trabajadoresOcupados, asignacion);
	}
	
	public static ProblemaAfinidadPD create(){
		return new ProblemaAfinidadPD();
	}
	
	public ProblemaAfinidadPD(){
		super();
		this.indice = 0;
		this.nAfinidad = 0;
		this.trabajadoresOcupados = new HashMap<String,Set<Integer>>();
		this.asignacion = new HashMap<String,String>();
	}
	
	public ProblemaAfinidadPD(int indice, double nAfinidad, 
			Map<String,Set<Integer>> trabajadoresOcupados, Map<String,String> asignacion){
		super();
		this.indice = indice;
		this.nAfinidad = nAfinidad;
		this.trabajadoresOcupados = trabajadoresOcupados;
		this.asignacion = asignacion;
	}
	
	//Tamaño del problema
	public int size(){
		return getClientes().size()-this.indice;
	}
	
	public us.lsi.pd.ProblemaPD.Tipo getTipo(){
		return Tipo.Max;
	}
	
	public boolean esCasoBase(){
		
		return this.indice==getClientes().size();
	}
	
	public Sp<Integer> getSolucionCasoBase(){
		Sp<Integer> sol=Sp.create(null, 0.0);
		
		return sol;
	}
	public List<Integer> getAlternativas(){
		
		List<Integer> alt = new ArrayList<Integer>();
		Map<String,Set<Integer>> copiaTrab = new HashMap<>(trabajadoresOcupados);

		for(int i=0; i<getTrabajadores().size();i++){
			if(!copiaTrab.containsKey(getTrabajadores().get(i))){
				alt.add(i);
			}else{
				if(copiaTrab.get(getTrabajadores().get(i)).size() < 3
						&& !copiaTrab.get(getTrabajadores().get(i)).contains(getClientes().get(this.indice).franjaHoraria)){
					alt.add(i);
				}
			}
		}
		return alt;
	}
	
	public Sp<Integer> seleccionaAlternativa(List<Sp<Integer>> ls){
		return ls.stream().max(Comparator.naturalOrder()).orElse(null);
	}
	
	public ProblemaPD<Map<String,String>,Integer> getSubProblema(Integer a, int i){
		
		ProblemaPD<Map<String,String>,Integer> p;
		
		Map<String,Set<Integer>> copia = new HashMap<>(trabajadoresOcupados);
		Map<String,String> copia2 = new HashMap<>(asignacion);
		int in = indice;
		double af = nAfinidad;
		in++;
		af++;
		copia2.put(getClientes().get(indice).nombre, getTrabajadores().get(a));
		
		
		copia.put(getTrabajadores().get(a), new HashSet<Integer>());
		copia.get(getTrabajadores().get(a)).add(getClientes().get(indice).franjaHoraria);
		
		
		
		if(getClientes().get(indice).trabajadoresAfines.contains(getTrabajadores().get(a))){
			
			p = new ProblemaAfinidadPD(in, af,copia, copia2);
			
			
		}else{
			p = new ProblemaAfinidadPD(in,nAfinidad,copia, copia2);
			
		}
		
		return p;
	}

	public Sp<Integer> combinaSolucionesParciales(Integer a, List<Sp<Integer>> ls) {
		Sp<Integer> sp = ls.get(0);
		Double num = sp.propiedad;
		if(getClientes().get(this.indice).trabajadoresAfines.contains(getTrabajadores().get(a))){
			num++;
		}
		
		return Sp.create(a, num);
	}

	@Override
	public int getNumeroSubProblemas(Integer a) {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public Map<String, String> getSolucionReconstruida(Sp<Integer> sp) {
		// TODO Auto-generated method stub
		Map<String, String> res = new HashMap<>();
		return res;
	}

	@Override
	public Map<String, String> getSolucionReconstruida(Sp<Integer> sp, List<Map<String, String>> ls) {
		// TODO Auto-generated method stub
		Map<String,String> res = new HashMap<>(ls.get(0));
		asignacion.keySet().stream().forEach(cliente->res.put(cliente, getTrabajadores().get(sp.alternativa)));
		res.put(getClientes().get(this.indice).nombre, getTrabajadores().get(sp.alternativa));
		return res;
	}

	@Override
	public Double getObjetivoEstimado(Integer a) {
		// TODO Auto-generated method stub
		return Double.MAX_VALUE;
	}

	@Override
	public Double getObjetivo() {
		// TODO Auto-generated method stub
		return Double.MIN_VALUE;
	}
	
	private List<Cliente> getClientes(){
		return problema.clientes;
	}
	private List<String> getTrabajadores(){
		return problema.trabajadores;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((asignacion == null) ? 0 : asignacion.hashCode());
		result = prime * result + indice;
		return result;
	}

	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProblemaAfinidadPD other = (ProblemaAfinidadPD) obj;
		if (asignacion == null) {
			if (other.asignacion != null)
				return false;
		} else if (!asignacion.equals(other.asignacion))
			return false;
		if (indice != other.indice)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ProblemaAfinidadPD [indice=" + indice + ", nAfinidad=" + nAfinidad + ", trabajadoresOcupados="
				+ trabajadoresOcupados + ", asignacion=" + asignacion + "]";
	}
	
}
