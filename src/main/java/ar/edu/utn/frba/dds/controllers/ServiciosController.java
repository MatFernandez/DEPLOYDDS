package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.models.entidades.common.Usuario;
import ar.edu.utn.frba.dds.models.entidades.servicios.EscaleraMecanica;
import ar.edu.utn.frba.dds.models.entidades.servicios.Servicio;
import ar.edu.utn.frba.dds.models.repositorios.RepositorioDeServicios;
import ar.edu.utn.frba.dds.server.exceptions.AccessDeniedException;
import ar.edu.utn.frba.dds.server.utils.ICrudViewsHandler;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ServiciosController extends Controller implements ICrudViewsHandler {
  private RepositorioDeServicios repositorioDeServicios;

  public ServiciosController(RepositorioDeServicios repositorioDeServicios) {
    this.repositorioDeServicios = repositorioDeServicios;
  }

  @Override
  public void index(Context context) {
    Map<String, Object> model = new HashMap<>();
    List<Servicio> servicios = this.repositorioDeServicios.buscarTodos();
    model.put("servicios", servicios);
    context.render("servicios/servicios.hbs", model);
  }

  @Override
  public void show(Context context) {
    Servicio servicio = (Servicio) this.repositorioDeServicios.buscar(Long.parseLong(context.pathParam("id")));
    Map<String, Object> model = new HashMap<>();
    model.put("servicio", servicio);
    context.render("servicios/servicio.hbs", model);
  }

  @Override
  public void create(Context context) {
    Usuario usuarioLogueado = super.usuarioLogueado(context);

    if(usuarioLogueado == null || !usuarioLogueado.getRol().tenesPermiso("crear_servicios")) {
      throw new AccessDeniedException();
    }

    Servicio servicio = null;
    Map<String, Object> model = new HashMap<>();
    model.put("servicio", servicio);
    context.render("servicios/servicio.hbs", model);
  }

  @Override
  public void save(Context context) {
    Servicio servicio = new EscaleraMecanica();
    this.asignarParametros(servicio, context);
    this.repositorioDeServicios.guardar(servicio);
    context.status(HttpStatus.CREATED);
    context.redirect("/servicios");
  }

  @Override
  public void edit(Context context) {
    Servicio servicio = (Servicio) this.repositorioDeServicios.buscar(Long.parseLong(context.pathParam("id")));
    Map<String, Object> model = new HashMap<>();
    model.put("servicio", servicio);
    context.render("servicios/servicio.hbs", model);
  }

  @Override
  public void update(Context context) {
    Servicio servicio = (Servicio) this.repositorioDeServicios.buscar(Long.parseLong(context.pathParam("id")));
    this.asignarParametros(servicio, context);
    this.repositorioDeServicios.actualizar(servicio);
    context.redirect("/servicios");
  }

  @Override
  public void delete(Context context) {
    Servicio servicio = (Servicio) this.repositorioDeServicios.buscar(Long.parseLong(context.pathParam("id")));
    this.repositorioDeServicios.eliminar(servicio);
    context.redirect("/servicios");
  }

  private void asignarParametros(Servicio servicio, Context context) {
    if(!Objects.equals(context.formParam("nombre"), "")) {
      servicio.setNombreServicio(context.formParam("nombre"));
    }
  }
}