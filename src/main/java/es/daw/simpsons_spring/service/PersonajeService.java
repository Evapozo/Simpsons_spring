package es.daw.simpsons_spring.service;

import es.daw.simpsons_spring.model.Personaje;
import es.daw.simpsons_spring.repository.PersonajeRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

/**
 * Lógica de negocio
 */
@Service
public class PersonajeService {


    // En Spring no usamos new, 2 pasos:
    private final PersonajeRepository repositorio ;

    public PersonajeService(PersonajeRepository repositorio) {
        this.repositorio = repositorio;
    }


        public List<Personaje> buscar(String lugar,
                                      Integer edadMax,
                                      String ocupacion, //NUEVO
                                      String ordenarPor, // pendiente
                                      boolean descendente, // pendiente
                                      Integer limite,
                                      boolean soloFamilia
        ) {


            return repositorio.findAll().stream()
                    //filter deja pasar solo los que cumplen la condición
                    .filter( p -> lugar == null || lugar.isBlank() || p.lugar().equalsIgnoreCase(lugar))
                    .filter(p -> edadMax == null || p.edad() <= edadMax)
                    //MEJORA 1
                    .filter(p -> ocupacion == null || ocupacion.isBlank() || p.ocupacion().equalsIgnoreCase(ocupacion))
                    //MEJORA 3
                    .filter(p-> !soloFamilia || p.principal())
                    //.sorted( (p1, p2) -> p1.nombre().compareTo(p2.nombre()))
                    .sorted(crearComparador(ordenarPor, descendente))
                    //.sorted(Comparator.comparing(Personaje::nombre))
                    .limit(limite == null? Integer.MAX_VALUE : limite)
                    .toList();
        }

        /**
         *
         * @return
         */
        public List<String> lugaresDisponibles(){
            // FORMA 1: funcional
            return repositorio.findAll().stream()
                    //.map(p -> p.lugar())
                    .map(Personaje::lugar)
                    .distinct()
                    .sorted()
                    .toList();
            //.collect(Collectors.toList());
            // FORMA 2: IMPERATIVO

            // FORMA 3: convierto a Set (conjunto no repetido..)

        }

        /**
         *
         * @return
         */
        public List<String> ocupacionesDisponibles(){
            return repositorio.findAll().stream()
                    .map(Personaje::ocupacion)
                    .distinct()
                    .sorted()
                    .toList();
        }

        /**
         *
         * @param ordenarPor
         * @param descendente
         * @return
         */
        private Comparator<Personaje> crearComparador(String ordenarPor, boolean descendente) {
            Comparator<Personaje> comparator = switch(ordenarPor == null ? "" : ordenarPor){

                //case "edad" -> Comparator.comparingInt(p -> p.edad()); lambda
                case "edad" -> Comparator.comparingInt(Personaje::edad).thenComparing(Personaje::nombre);
                case "apellido" -> Comparator.comparing(Personaje::apellido).thenComparing(Personaje::nombre);

                //MEJORA 2 Añadir "Ordenar por lugar" (y, si empatan, por nombre).
                case "lugar" -> Comparator.comparing(Personaje::lugar).thenComparing(Personaje::nombre);
                default -> Comparator.comparing(Personaje::nombre);

            };
            return descendente ? comparator.reversed() : comparator;
        }


}
