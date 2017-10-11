package ti4.afinidad.pd;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

import us.lsi.math.Math2;
import us.lsi.pd.AlgoritmoPD.Sp;
import us.lsi.pd.ProblemaPD;

public class AlgunosTestsPD<S, A> {
	/**
	 * Dado un problema se ejecuta la lista de acciones sobre el mismo consiguiendo subproblemas sucesivos 
	 * y posteriormente se calcula la solcuión parcial y la reconstruida
	 * 
	 * @param <S> Tipo de la solución
	 * @param <A> Tipo de la alternativa
	 * @param e Un Estado inicial
	 * @param ls Una lista de acciones
	 */
	public static <S, A> void test1(ProblemaPD<S, A> p, List<A> la) {
		int i = 0;
		List<ProblemaPD<S, A>> lp = Lists.newArrayList();
		System.out.println("Avanza");
		for (A a : la) {
			
			System.out.println(i + "=====");
			System.out.println("Problema = " + p);
			lp.add(p);
			System.out.println("Alternativas = " + p.getAlternativas());
			System.out.println("Contiene la alternativa = "
					+ p.getAlternativas().contains(a));
			i++;
			p = p.getSubProblema(a, 0);
		}
		System.out.println("Final =====");
		System.out.println("Problema = " + p);
		System.out.println("Es caso base = " + p.esCasoBase());
		System.out.println("\n\nRetrocede");
		A a;
		Sp<A> sp = null;
		S s = null;
		if (p.esCasoBase()) {
			sp = p.getSolucionCasoBase();
			List<Sp<A>> lsp = Lists.newArrayList(sp);
			s = p.getSolucionReconstruida(sp);
			List<S> ls = Lists.newArrayList(s);
			for (i = la.size() - 1; i >= 0; i--) {
				a = la.get(i);
				p = lp.get(i);
				System.out.println(i + "=====");
				System.out.println("Problema = " + p);
				System.out.println("Solucion Parcial = " + sp);
				System.out.println("Solucion = " + s);
				sp = p.combinaSolucionesParciales(a, lsp);
				lsp = Lists.newArrayList(sp);
				s = p.getSolucionReconstruida(sp, ls);
				ls = Lists.newArrayList(s);
			}
		}
		System.out.println("Inicial =====");
		System.out.println("Problema = " + p);
		System.out.println("Solucion Parcial = " + sp);
		System.out.println("Solucion = " + s);
	}
	/**
	 * Dado un problema se ejecuta la lista de acciones aleatorias, escogidas
	 * entre las posibles para cada problema,  consiguiendo subproblemas sucesivos 
	 * y posteriormente se calcula la solución parcial y la reconstruida
	 * 
	 * @param <S> Tipo de la solución
	 * @param <A> Tipo de la alternativa
	 * @param e Un Estado inicial
	 * @param ls Una lista de acciones
	 */
	public static <S, A> void test2(ProblemaPD<S, A> p) {
		Math2.rnd = new Random(System.nanoTime());
		int i = 0;
		List<A> la = Lists.newArrayList();
		List<ProblemaPD<S, A>> lp = Lists.newArrayList();
		List<A> alternativas;
		List<A> alternativasEscogidas = Lists.newArrayList();
		A a = null;
		System.out.println("Avanza");
		while (true) {
			
			System.out.println(i + "=====");
			System.out.println("Problema = " + p);
			lp.add(p);
			alternativas = p.getAlternativas();
			System.out.println("Alternativas = " + alternativas);
			if (!alternativas.isEmpty()) {
				Integer n = Math2.getEnteroAleatorio(0, alternativas.size());
				a = alternativas.get(n);
				System.out.println("Alternativa Escogida = " + a);
				alternativasEscogidas.add(a);
				p = p.getSubProblema(a, 0);
			} else {				
				break;
			}
			i++;
		}
		System.out.println("Final =====");
		System.out.println("Problema = " + p);
		System.out.println("Es caso base = " + p.esCasoBase());
		System.out.println("\n\nRetrocede");
		Sp<A> sp = null;
		S s = null;
		if (p.esCasoBase()) {
			sp = p.getSolucionCasoBase();
			List<Sp<A>> lsp = Lists.newArrayList(sp);
			s = p.getSolucionReconstruida(sp);
			List<S> ls = Lists.newArrayList(s);
			for (i = la.size() - 1; i >= 0; i--) {
				a = la.get(i);
				p = lp.get(i);
				System.out.println(i + "=====");
				System.out.println("Problema = " + p);
				System.out.println("Solucion Parcial = " + sp);
				System.out.println("Solucion = " + s);
				sp = p.combinaSolucionesParciales(a, lsp);
				lsp = Lists.newArrayList(sp);
				s = p.getSolucionReconstruida(sp, ls);
				ls = Lists.newArrayList(s);
			}
		}
		System.out.println("Inicial =====");
		System.out.println("Problema = " + p);
		System.out.println("Solucion Parcial = " + sp);
		System.out.println("Solucion = " + s);
	}
}

