GUIA DEL LABORATORIO — Sistema de Biblioteca CUN
POO2 · Ingeniería de Sistemas · tercer semestre
JDK usado para comprobar: 17

Cómo abrir esto en el IDE
1. Crea un proyecto Java llamado BibliotecaCUN (NetBeans, IntelliJ o VS Code).
2. El código fuente va en src. La carpeta física src/com/cun/biblioteca/modelo corresponde al paquete com.cun.biblioteca.modelo. Java exige que la ruta coincida con el package.
3. No hace falta Maven, Gradle ni librerías externas.
4. Compilar y ejecutar desde la carpeta BibliotecaCUN:

   javac -encoding UTF-8 -d out -sourcepath src $(find src -name '*.java')
   java -cp out com.cun.biblioteca.modelo.Main
   java -cp out com.cun.biblioteca.BibliotecaApp

En Windows, si find no existe, marca la carpeta src como source root y ejecuta Main o BibliotecaApp desde el IDE.

Main.java demuestra las partes 2 a 6.
BibliotecaApp.java es el flujo de la parte 7. Ese es el que conviene explicar en la sustentación.


FASE 1 — Requisitos del taller

Parte 0: proyecto, paquete modelo, Libro, Main, constructor, private, getters, toString.
Parte 1: detectar God Class, Prestamo, CalculadoraMultas, reflexiones de cohesión y acoplamiento.
Parte 2: LibroNoDisponibleException (checked), DatosInvalidosException (unchecked), validar Libro, disponible, Prestamo lanza checked, try/catch.
Parte 3: MaterialBiblioteca abstracta, Libro hereda, Revista, DVD, Tesis, getDiasPrestamoPermitidos().
Parte 4: INotificador, tres notificadores, ServicioNotificaciones, polimorfismo.
Parte 5: CatalogoBiblioteca con List, Map, Set y Queue.
Parte 6: Singleton, Factory y Strategy.
Parte 7: BibliotecaApp integra el flujo. Entregable: código + reflexiones.txt.

Conceptos evaluados: encapsulamiento, cohesión, acoplamiento, excepciones, herencia, clase abstracta, interfaz, polimorfismo, colecciones y tres patrones.


FASE 2 — Análisis previo y puntos que debemos vigilar

El taller no es un proyecto nuevo en cada parte. Libro se reescribe. La versión final es una sola.

Inconsistencia detectada — Disponibilidad al devolver
Qué dice el taller: Prestamo.marcarComoDevuelto() solo pone devuelto = true. Antes, el constructor hace libro.setDisponible(false). MaterialBiblioteca tiene marcarComoDevuelto(), que sí pone disponible = true.
Qué ocurre técnicamente: si solo marco el préstamo como devuelto, el libro sigue no disponible y un tercer préstamo vuelve a lanzar LibroNoDisponibleException.
Qué recomiendo: no ocultarlo. En BibliotecaApp se imprime el estado después de marcar el préstamo (sigue false) y después se llama libro.marcarComoDevuelto() aparte, para mostrar que hacen falta las dos acciones. No se mezcló esa lógica dentro de Prestamo porque el taller no la escribió.

Inconsistencia detectada — setDisponible desaparece en la parte 3
Qué dice el taller: la parte 2 agrega setDisponible a Libro. La parte 3, al crear MaterialBiblioteca, muestra marcarComoPrestado y marcarComoDevuelto, pero no setDisponible. Prestamo sigue llamando setDisponible(false).
Qué ocurre técnicamente: si se copia la parte 3 al pie de la letra y se borra setDisponible, Prestamo no compila.
Qué recomiendo: dejar setDisponible en MaterialBiblioteca, junto con los dos métodos del taller. Así se respeta la parte 2 y la parte 3.

Inconsistencia detectada — Validación que se pierde al heredar
Qué dice el taller: la parte 2 valida ISBN, título y año en Libro. La parte 3 solo valida el código en el padre.
Qué ocurre técnicamente: si Libro solo hace super(...) y no valida, una revista podría crearse con título vacío o año 3000.
Qué recomiendo: la validación de código, título y año queda en MaterialBiblioteca. La del autor queda en Libro, porque el autor no existe en el padre. Así no se pierde la parte 2.

Inconsistencia detectada — Tesis de 0 días
Qué dice el taller: Tesis retorna 0 porque es consulta en sala.
Qué ocurre técnicamente: ninguna clase revisa ese 0. Prestamo solo recibe Libro, así que la tesis ni siquiera entra al constructor del préstamo. Se puede prestar cualquier Libro, aunque no se consulte getDiasPrestamoPermitidos.
Qué recomiendo: no inventar una excepción nueva. En BibliotecaApp se imprime el 0 y se explica el límite. El préstamo de Ana usa libro.getDiasPrestamoPermitidos(), que en Libro es 15.

Inconsistencia detectada — Tres formas de calcular multa
Qué dice el taller: CalculadoraMultas usa 2000 fijo. ConfiguracionBiblioteca también tiene 2000. Luego aparecen MultaEstandar (2000), MultaEstudiante (1000) y MultaDocente (1500).
Qué ocurre técnicamente: no están conectadas. Cambiar config.setMultaPorDia no cambia la calculadora ni las estrategias.
Qué recomiendo: dejarlas separadas, como las presenta el taller, y decirlo en la sustentación. La parte 7 pide calcular con estrategias. CalculadoraMultas sobre un préstamo de hoy da 0, porque la fecha esperada es hoy + días y el constructor siempre usa LocalDate.now(). No se alteró el constructor para forzar un retraso.

Inconsistencia detectada — Factory y el orden de argumentos
Qué dice el taller:
LIBRO: código, título, autor, año.
REVISTA: código, título, año, número de edición.
DVD: código, título, año, duración.
TESIS: código, título, año, director.
Qué ocurre técnicamente: todos llegan como String. El año se convierte con Integer.parseInt. Si se invierte el año y la edición, el objeto queda mal o lanza NumberFormatException.
Qué recomiendo: usar exactamente ese orden. En Libro, el primer argumento se usa como código del padre y como isbn.

Inconsistencia detectada — Main crece y luego choca
Qué dice el taller: Main empieza en modelo y se le van pegando ejemplos. La parte 4 declara throws LibroNoDisponibleException aunque ese main no presta. La parte 7 pide BibliotecaApp.
Qué ocurre técnicamente: un solo Main gigante mezcla pruebas viejas con el flujo final y es difícil de explicar.
Qué recomiendo: Main queda como demostración de las partes. BibliotecaApp queda en el paquete com.cun.biblioteca, no en modelo, porque no es un dato de la biblioteca: es el programa. El taller no fija ese paquete; esta es la opción más simple.

Otras trampas de estudiante
- Copiar '\\'' del Word. En Java el carácter comilla simple se escribe '\''.
- Dejar dos Libro.java.
- Olvidar el import de LibroNoDisponibleException en Prestamo.
- Crear Revista sin super(...).
- Pasar un MaterialBiblioteca a Prestamo sin convertirlo a Libro. En BibliotecaApp se hace (Libro) cienAnios porque la factory devuelve el padre.
- HashSet no garantiza el orden de las categorías. Por eso en consola puede salir [REVISTA, LIBRO, DVD, TESIS] y no el orden de inserción. Es normal.
- El mapa no acepta dos valores con la misma clave. La lista sí acepta duplicados. agregarMaterial no compara códigos.


FASE 3 — Árbol final

BibliotecaCUN
├── reflexiones.txt
├── GUIA_LABORATORIO.md
└── src
    └── com
        └── cun
            └── biblioteca
                ├── BibliotecaApp.java
                ├── modelo
                │   ├── MaterialBiblioteca.java
                │   ├── Libro.java
                │   ├── Revista.java
                │   ├── DVD.java
                │   ├── Tesis.java
                │   ├── Prestamo.java
                │   └── Main.java
                ├── servicio
                │   ├── CalculadoraMultas.java
                │   ├── INotificador.java
                │   ├── NotificadorEmail.java
                │   ├── NotificadorSMS.java
                │   ├── NotificadorWhatsApp.java
                │   ├── ServicioNotificaciones.java
                │   ├── CatalogoBiblioteca.java
                │   ├── ConfiguracionBiblioteca.java
                │   ├── MaterialFactory.java
                │   ├── IEstrategiaMulta.java
                │   ├── MultaEstandar.java
                │   ├── MultaEstudiante.java
                │   └── MultaDocente.java
                └── excepcion
                    ├── LibroNoDisponibleException.java
                    └── DatosInvalidosException.java

No hay clases duplicadas. La versión de Libro de la parte 0 ya no existe: fue reemplazada.


PARTE 0
A. Objetivo: crear clase, constructor, encapsulamiento y toString.
B. Archivos: Libro.java y Main.java en com.cun.biblioteca.modelo. Después se modifican.
C. Código inicial que luego se reemplaza: atributos private isbn, titulo, autor, anioPublicacion; constructor con this; getters; toString.
D. this distingue el parámetro del atributo. private impide libro1.titulo = "Otro".
E. Prueba: imprimir Cien Años de Soledad y El Principito.
F. Errores típicos: package distinto de la carpeta, olvidar public class, nombre del archivo distinto de la clase.
G. Reflexión: this, private y acceso directo. Ya está en reflexiones.txt.
Prueba antes de continuar: si Main no encuentra Libro, ambos deben estar en el mismo paquete.


PARTE 1
A. Objetivo: no hacer de Libro una God Class.
B. Se crean Prestamo y CalculadoraMultas. Libro no recibe métodos de multa ni de correo.
C. Prestamo solo guarda libro, usuario, fechas y devuelto. CalculadoraMultas solo calcula.
D. Cohesión: una responsabilidad por clase. Acoplamiento: la calculadora usa getters, no atributos privados.
E. En esta etapa todavía no hay préstamo vencido, así que la multa da 0. Eso no es un error.
F. Error típico: poner CalculadoraMultas en modelo. El taller la pone en servicio.
G. Preguntas de cohesión y de los $2.000, respondidas en reflexiones.txt.


PARTE 2
A. Objetivo: checked para regla de negocio, unchecked para dato mal formado.
B. Se crean las dos excepciones. Se modifican Libro y Prestamo. Main usa try/catch.
C. LibroNoDisponibleException extends Exception. DatosInvalidosException extends RuntimeException.
D. El segundo préstamo del mismo libro entra al catch. El ISBN vacío entra al otro catch y el programa sigue.
E. Salida esperada: "ERROR DE NEGOCIO: El libro '...' no está disponible." y "ERROR DE DATOS: El código no puede estar vacío."
F. Si falta throws en Prestamo, el error de compilación dice que la excepción no está declarada. Si se captura Exception en vez de la específica, funciona, pero se pierde la diferencia que pide el taller.
G. Diferencia checked/unchecked, en reflexiones.txt.


PARTE 3
A. Objetivo: compartir estado con una clase abstracta y variar el comportamiento en las hijas.
B. Se crea MaterialBiblioteca. Libro se reemplaza: ya no declara titulo, anio ni disponible.
   Pasan al padre: codigo, titulo, anioPublicacion, disponible.
   Se quedan en Libro: isbn y autor.
   El isbn se envía al padre como codigo.
C. Revista tiene numeroEdicion y 3 días. DVD tiene duracionMinutos y 5. Tesis tiene director y 0.
D. No se puede hacer new MaterialBiblioteca. El método abstracto obliga a cada hija a definir sus días.
E. El for de la parte 4 imprime 15, 3, 5 y 0. Si una hija no implementa el método, no compila.
F. Error típico: olvidar super(...) como primera línea del constructor.
G. Pregunta de clase abstracta vs interfaz, en reflexiones.txt.


PARTE 4
A. Objetivo: contrato con interfaz e intercambio en ejecución.
B. INotificador, NotificadorEmail, NotificadorSMS, NotificadorWhatsApp, ServicioNotificaciones.
C. Cada notificador solo imprime. No hay correo real ni WhatsApp real.
D. ServicioNotificaciones recibe el canal por constructor y puede cambiarlo con setNotificador.
E. Consola: [EMAIL], [WHATSAPP] y [SMS] con el texto del taller.
F. Si una clase implements INotificador y le falta un método, Java dice cuál falta.
G. Herencia vs interfaz, en reflexiones.txt.


PARTE 5
A. Objetivo: usar cada colección para lo que sirve.
B. CatalogoBiblioteca.
C. ArrayList mantiene orden. HashMap busca por código. HashSet guarda categorías únicas. LinkedList como Queue atiende en orden de llegada.
D. listarTodos devuelve una copia, para que quien reciba la lista no dañe la interna.
E. Atendiendo a: Ana. Reservas pendientes: 2.
F. Si se usa el nombre de la clase como categoría, "Libro" y "LIBRO" serían distintas si no se hace toUpperCase. El taller usa toUpperCase.
G. Preguntas de HashMap, remove(0) y las cuatro colecciones, en reflexiones.txt.


PARTE 6
A. Objetivo: Singleton, Factory y Strategy sin frameworks.
B. ConfiguracionBiblioteca, MaterialFactory, IEstrategiaMulta y tres multas.
C. El constructor privado impide new ConfiguracionBiblioteca() fuera de la clase. synchronized evita dos instancias si dos hilos entran a la vez. En este taller basta con entender eso: una sola configuración.
D. Factory: "LIBRO" usa 4 datos y el año va de último. "REVISTA" usa año y luego edición. No es el mismo orden.
E. Multa estudiante por 5 días: 5000.0.
F. Si el tipo no está en el mapa: IllegalArgumentException. Si el año no es número: NumberFormatException.
G. Preguntas de synchronized, Map vs switch y Strategy, en reflexiones.txt.


PARTE 7
BibliotecaApp hace este recorrido:
1. Pide la configuración y comprueba que getInstancia() devuelve el mismo objeto.
2. Crea libro, revista, DVD y tesis con MaterialFactory.
3. Los agrega al catálogo.
4. Lista materiales, categorías y busca L-001.
5. Presta Cien Años de Soledad a Ana Gómez por 15 días.
6 y 7. Intenta prestárselo a Carlos Ruiz y captura LibroNoDisponibleException.
8. Notifica por email, WhatsApp y SMS.
9 y 10. Calcula 5 días con estándar ($10000), estudiante ($5000) y docente ($7500). También muestra que CalculadoraMultas da 0 en un préstamo de hoy.
11. Imprime el estado final.

La devolución se muestra a propósito en dos pasos, para no tapar el hueco del taller.


FASE 12
reflexiones.txt responde todas las autoevaluaciones en primera persona, con ejemplos del proyecto.


FASE 13 — Auditoría del proyecto antes de entregar

Comprobado al compilar con JDK 17 y ejecutar las dos clases main:
- Paquetes coinciden con las carpetas.
- Imports usados: excepciones, modelo y java.time. No hay librerías externas.
- Una sola Libro, una sola Prestamo, una sola Main, una sola BibliotecaApp.
- Libro, Revista, DVD y Tesis extienden MaterialBiblioteca.
- Los tres notificadores y las tres multas implementan su interfaz.
- getDiasPrestamoPermitidos tiene @Override.
- Prestamo declara throws LibroNoDisponibleException.
- Main y BibliotecaApp capturan la checked. La unchecked también se captura en Main.
- Colecciones: ArrayList, HashMap, HashSet, LinkedList.
- Singleton con constructor private y getInstancia synchronized.
- Factory con el orden de argumentos del taller.
- Strategy intercambiable.
- No quedan llamadas a métodos borrados en la refactorización. setDisponible sigue existiendo porque Prestamo lo necesita.
- reflexiones.txt está en la raíz del proyecto.


FASE 14 — Lista final de comprobación

[ ] El proyecto abre en el IDE y el source root es src.
[ ] java -version muestra 17 o superior.
[ ] Compila sin errores.
[ ] Main imprime materiales, notificaciones, error de datos, catálogo, Ana en la cola, singleton, factory, multa estudiante y error de negocio.
[ ] BibliotecaApp recorre los 11 puntos y se puede explicar en ese orden.
[ ] reflexiones.txt va en la entrega.
[ ] No se entrega la carpeta out ni archivos .class si el profesor pide solo fuente.
[ ] Sé explicar por qué la tesis no lanza excepción, por qué la multa de CalculadoraMultas da 0 y por qué devolver el préstamo no libera el libro solo.


Qué debo saber explicar si el profesor me pregunta

Encapsulamiento: titulo es private. Main no puede hacer libro1.titulo = "Otro". Usa getTitulo().
Cohesión: Libro no manda correos ni calcula multas. Cada clase hace una cosa.
Acoplamiento: si la multa cambia, no abro Libro. Abro la clase de la multa.
Checked: LibroNoDisponibleException. El compilador obliga a manejarla. Ejemplo: segundo préstamo de Cien Años de Soledad.
Unchecked: DatosInvalidosException. ISBN vacío. No obliga a declararla.
Clase abstracta: MaterialBiblioteca no se instancia y obliga a definir los días de préstamo.
Herencia: Libro es un MaterialBiblioteca. Recibe código, título, año y disponible.
Interfaz: INotificador no tiene datos. Email, SMS y WhatsApp cumplen el mismo contrato.
Polimorfismo: el for trata a todos como MaterialBiblioteca y cada uno responde sus días. El servicio trata a todos los canales como INotificador.
Inyección: ServicioNotificaciones no crea el canal. Se lo pasan en el constructor.
List: orden del catálogo. Set: categorías sin repetir. Queue: Ana llega primero y sale primero. Map: buscar L-001 directo.
Singleton: una sola ConfiguracionBiblioteca. Constructor private.
Factory: crear("LIBRO", ...) sin que el main arme el new de cada tipo.
Strategy: la misma cuenta de 5 días cambia de 10000 a 5000 a 7500 según el objeto que ponga en la variable.
