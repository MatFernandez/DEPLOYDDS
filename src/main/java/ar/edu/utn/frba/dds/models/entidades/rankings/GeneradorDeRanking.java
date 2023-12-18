package ar.edu.utn.frba.dds.models.entidades.rankings;

import ar.edu.utn.frba.dds.models.repositorios.RepositorioEntidades;
import ar.edu.utn.frba.dds.models.entidades.serviciospublicos.Entidad;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class GeneradorDeRanking {
  public List<Entidad> entidadesConMayorPromedioDeCierre(){
    System.out.println("ENTRO AL RANKINGG");
    List<Entidad> entidadesOrdenadas = RepositorioEntidades.getInstance().getEntidades().stream()
        .sorted(Comparator.comparingDouble(Entidad::promedioCierreIncidentesEnMinutos)) // Ordenar por cantidad de incidentes
        .collect(Collectors.toList());
    System.out.println(entidadesOrdenadas.get(0).getNombre() + " " + "ENTIDAD DEL RANKING");
    System.out.println(entidadesOrdenadas.get(0).promedioCierreIncidentesEnMinutos() + " " + "PROMEDIO CIERRE");
    return this.primerosTres(entidadesOrdenadas);
    //return entidadesOrdenadas;
  }

  public List<Entidad> entidadesConMayorCantidadDeIncidentes(){
    System.out.println(RepositorioEntidades.getInstance().getEntidades().get(0).getIncidentes().size());
    System.out.println(RepositorioEntidades.getInstance().getEntidades().get(1).getIncidentes().size());
    System.out.println(RepositorioEntidades.getInstance().getEntidades().get(2).getIncidentes().size());
    System.out.println(RepositorioEntidades.getInstance().getEntidades().get(3).getIncidentes().size());
    System.out.println(RepositorioEntidades.getInstance().getEntidades().get(4).getIncidentes().size());

    List<Entidad> entidadesOrdenadas = RepositorioEntidades.getInstance().getEntidades().stream()
        .sorted(Comparator.comparingInt(Entidad::cantidadIncidentesEnLaSemana).reversed())
        .collect(Collectors.toList());
    return this.primerosTres(entidadesOrdenadas);
        //return entidadesOrdenadas;

  }

  public List<Entidad> entidadesConMayorImpacto(){

    List<Entidad> entidadesOrdenadas = RepositorioEntidades.getInstance().getEntidades().stream()
        .sorted(Comparator.comparingInt(Entidad::gradoDeProblematica).reversed())
        .collect(Collectors.toList());
    return this.primerosTres(entidadesOrdenadas);
        //return entidadesOrdenadas;

  }

  public List<Entidad> primerosTres(List<Entidad> entidades){
    List<Entidad> primerostres = new ArrayList<>();
    primerostres.add(entidades.get(0));
    primerostres.add(entidades.get(1));
    primerostres.add(entidades.get(2));
    return primerostres;
  }
}
