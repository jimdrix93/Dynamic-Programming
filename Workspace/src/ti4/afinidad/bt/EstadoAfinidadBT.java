package ti4.afinidad.bt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ti4.afinidad.Cliente;
import ti4.afinidad.ProblemaAfinidad;
import us.lsi.bt.EstadoBT;

public class EstadoAfinidadBT implements EstadoBT<Map<String, String>,Integer>{
	
	List<Cliente> clientes;
	List<String> trabajadores;
	Map<String,Set<Integer>> trabajadoresOcupados;
	Map<String,String> solucion;
	private Integer indice;
	private Integer afinidad;
	private Integer cota;
	
	public EstadoAfinidadBT(){
		this.clientes = ProblemaAfinidad.clientes;
		this.trabajadores = ProblemaAfinidad.trabajadores;
		this.trabajadoresOcupados = new HashMap<String,Set<Integer>>();
		this.solucion = new HashMap<String,String>();
		this.indice = 0;
		this.afinidad = 0;
		this.cota = 0;
	}
	
	public void avanza(Integer i){
		//añadir trabajador al set del cliente
		if(!trabajadoresOcupados.containsKey(trabajadores.get(i))){
			trabajadoresOcupados.put(trabajadores.get(i), new HashSet<Integer>());
			trabajadoresOcupados.get(trabajadores.get(i)).add(clientes.get(indice).franjaHoraria);
		}else{
			trabajadoresOcupados.get(trabajadores.get(i)).add(clientes.get(indice).franjaHoraria);
		}
		cota=0;
		solucion.put(clientes.get(indice).nombre, trabajadores.get(i));
		if(clientes.get(indice).trabajadoresAfines.contains(trabajadores.get(i))){
			afinidad++;	
			cota=1;
		}
		indice++;
	}
	
	public void retrocede(Integer i){
		indice--;
		if(trabajadoresOcupados.containsKey(trabajadores.get(i))){
			trabajadoresOcupados.remove(trabajadores.get(i));
		}
		if(clientes.get(indice).trabajadoresAfines.contains(trabajadores.get(i))){
			afinidad--;
		}
		
	}
	
	public List<Integer> getAlternativas(){
		List<Integer> alternativas = new ArrayList<Integer>();
		
		for(int i=0; i<trabajadores.size();i++){
			
			if(trabajadoresOcupados.containsKey(trabajadores.get(i))
				&& !trabajadoresOcupados.get(trabajadores.get(i)).contains(clientes.get(indice).franjaHoraria)){
				
				alternativas.add(i);
				
			}else{
				if(!trabajadoresOcupados.containsKey(trabajadores.get(i))){
					alternativas.add(i);
				}
			}
		}
		return alternativas;
	}
	
	public Map<String,String> getSolucion(){
		Map<String,String> res = new HashMap<String,String>(solucion);
		
		
		return res;
	}
	
	public boolean isFinal(){
		return indice == clientes.size();
	}
	
	public int size(){
		return clientes.size() - indice;
	}
	
	public Double getObjetivo(){
		return (double) afinidad;
	}
	
	public Double getObjetivoEstimado(Integer a){
		return Double.MAX_VALUE;
	}
	
	public Integer getFuncionCota(){
		return size()+afinidad+cota;
	}



}
