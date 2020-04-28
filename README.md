## UNICEN, Tandil.

# Programación Orientada a Objetos

19 de Julio de 2019 

## Software Administración de Consorcios

```
Trabajo final para la cátedra Programación Orientada a Objetos de la carrera Ingeniería de
Sistemas, Universidad Nacional del Centro de la Provincia de Buenos Aires, Tandil.
```
### Alumno:
```
De Fiore, Ignacio Fermin
```

## Introducción
```
En el siguiente informe se describen y analizan los componentes y las decisiones de
diseño utilizadas en el software de administración de consorcios presentado como
trabajo final.
Este software es un prototipo de versión reducida de un programa de administración,
para ser corrido en una plataforma de escritorio. En cuanto a la parte técnica del
mismo, como paradigma de programación se utiliza el orientado a objetos, una interfaz
para la comunicación con el usuario y una base de datos relacional en conjunto con un
herramienta de mapeo objeto-relacional (ORM) para la plataforma Java.
```

## Detalle de las tecnologías utilizadas:

●**Ambiente de desarrollo:**
El entorno de desarrollo integrado utilizado fue ​IntelliJ IDEA​. Una plataforma
provista por JetBrains, permite trabajar con distintos lenguajes de programación,
en nuestro caso Java, y que también provee asistencia automatizada para la
creación y uso de interfaces de usuario (GUI Form).

●**Tipo de proyecto:**
Utilizamos Maven como herramienta para la construcción del proyecto, es de fácil
configuración mediante un archivo en formato XML en donde se incluyen las
dependencias que sean necesarias.

●**Acceso a datos:**
Para la independización del acceso utilizamos ​Hibernate​, que hace que el modelo de
java se comunique con el DAO y pueda intercambiar los datos necesarios por la
aplicación, que pueden ser desde, una instancia de un Consorcio con su
correspondiente información, hasta algo más simple como una lista de los
nombres y dni de los propietarios.
Básicamente es una herramienta de mapeo objeto-relacional que facilita el mapeo
de atributos entre una base de datos tradicional y el modelo de objetos de una
aplicación trabajando mediante el uso de anotaciones en las clases del modelo
como por ejemplo: @Entity para denotar entidades a persistir, @Id para identificar
los id que funcionaran como clave en la persistencia, entre otras propiedades que
por ejemplo señalan la cardinalidad entre las relaciones.

●**Persistencia de la información:**
Para persistir la información de nuestro software entre las distintas sesiones de
uso optamos por utilizar ​H2 Database​, un sistema administrador de base de datos
relacionales de facil configuracion, que no requiere por ejemplo pasar por una
conexión a través de sockets.

●**Patrón de diseño:**
El patrón de diseño utilizado e implementado para completar cada nueva
funcionalidad fue ​Modelo–vista–controlador​, el cual permite que las interacciones
con el usuario, la lógica, las peticiones de datos y la utilización de los componentes
del modelo, para cada vista distinta de la aplicación quede separada, evitando así
tener una clase con demasiadas responsabilidades.

●**Modelo resultante:**
Al ser un software para administracion de consorcios, tuvimos que adquirir algunos
conocimientos previos para la construcción del modelo, relacionados al tema del
software. Finalmente concluimos en distintas clases como por ejemplo un
Consorcio, que se compone de distintas Unidades Funcionales, las cuales tienen un
propietario dueño, etc.-
A continuación se deja una imagen que representa la vista estática de las clases y
sus relaciones. Si bien la mayoría son fáciles de comprender, se explicaran algunos
funcionamientos particulares que tienen que ver con decisiones de diseño o están
relacionadas directamente con el ámbito raíz de la aplicación o límites de las
herramientas.

●**Organización de paquetes:**
La organización de las clases se divide en
paquetes de manera distribuida, según las
distintas funcionalidades, dentro del
directorio principal “​ _src/main/java”​_.
Por un lado las clases que conforman el modelo,
que tienen relación directa con el software y en
su mayoría, que son persistidas.
En otro package aparte, las clases utilizadas para el filtrado de
información en las vistas.
Dentro del paquete “mvc”, tres sub paquetes más que contienen las distintas clases
para los modelos, vista y controladores de las distintas funcionalidades del sistema.
Y finalmente un paquete de “utils” que alberga clases de
distinto funcionamiento y uso como por ejemplo:

● **EventBusFactory: ​** esta clase utiliza el patrón de
diseño Singleton, y es la encargada de proveer el
EventBus que se utiliza para la comunicación en las
respuestas de los eventos que suceden en las vistas
con sus controladores y modelos, según corresponda.
En cuanto al EventBus esta importado en el maven, es una clase provista por
google, que permite a un objeto suscribirse a eventos que otras clases
publiquen. Se deja el link para no entrar en detalle:
https://github.com/google/guava/wiki/EventBusExplained

● **DAOmanager​** : se encarga de hacer las query a la base de datos y proveer a
los modelos los objetos que están almacenados, información de los mismos
o también, tanto, persistir como actualizar información en la base.

● **JPAUtility​** : se encarga de abrir la conexión a la base de datos y pone a
nuestra disposición los distintos EntityManager utilizados en la clase
DAOmanager.
Ambas clases, DAOmanager, como JPAUtility pueden parecerse a un Singleton, pero
no lo son 100%. Para no hacer más extenso en informe, en el siguiente link se
puede encontrar más información del funcionamiento de las mismas:
https://www.arquitecturajava.com/entitymanager-y-entitymanagerfactory/

## Aclaraciones

● Para la utilización de Gastos, se optó por diseñar una clase abstracta padre
llamada “Gasto” de la cual extienden las clases “Gasto Simple” y “Gasto
Compuesto”. Básicamente un gasto compuesto podra tener una lista de
gastos. Esto permite trabajar con por ejemplo un gasto compuesto “Obra de
exterior”, y dentro del mismo distintos gastos del estilo, “Pintura”,
“Materiales”, etc, con sus posibles combinaciones.
● Un consorcio tendrá una liquidación vigente, correspondiente al corriente
mes, el manejo del atributo “periodo” correspondiente a la liquidación, se
optó por manejar automáticamente e internamente para liquidaciones
mensuales, esto hace que el usuario no tenga que informar a qué periodo
pertenece una liquidación, sino que al cerrar una liquidación, se creará una
nueva con la fecha del próximo mes a la cerrada.
● La clase “LiquidacionesHistoricas“ almacenará un HashMap para el cual la
clave de acceso será el ID de un consorcio y devolverá una instancia de la
clase “LiquidacionesGrupo”, esta clase tuvo que realizarse forzosamente, ya
que Hibernate no permite tener una entidad (LiquidacionesHistoricas) que
tenga un HashMap el cual almacene otra entidad, lo que en un principio era
HashMap<Intenger,List<Liquidacion>>. Se optó entonces hacer que
“LiquidacionesGrupo” funcione como un wrapper de la lista de liquidaciones.
Al almacenar un Hash para todos los consorcios, será tratada de forma
similar a un singleton, siendo de única instancia pero manejada por los
modelos que extienden la clase “ModeloManejadorHistoricas”, la cual
implementa un método que se encarga de comprobar si hay una instancia
creada, básicamente pidiendo al DAOmanager el objeto almacenado y en
caso de no existir, lo crea y lo manda a persistir.
● El atributo “Coeficiente” dentro de una unidad funcional, hace referencia al
porcentaje del total de los gastos que le corresponda pagar al propietario de
esa Uf, la suma de los coeficientes de las unidades funcionales debería dar 1
como resultado, esto no lo controla el programa, sino que se deja al usuario
para no agregar dificultad al momento de probarlo.


## Pendientes

A futuro, si el sistema fuese a ser utilizado realmente, por un usuario
con un propósito, se podrían tener en cuenta distintos factores:
● Embellecimiento de las interfaces.
● Validaciones de campos más específicas que las que hay actualmente, ya que
son demasiados generales, hechas dentro de bloques try-catch de forma
desprolija pero para que sea legible el código base y no ensuciarlo con
validaciones de tipos que no hacen la diferencia en cuanto al enfoque del
trabajo hacia la cátedra.
● Se podría haber planteado distintos enfoques en cuanto al periodo de una
Liquidación, como dejar que el usuario elija para la utilización de
liquidaciones bimestrales, trimestrales, etc y no obligarlo a que sean
mensuales.
● La parte de generación de reportes, que no salgan en archivos.txt y que
tengan un formato determinado.
● Que los pagos tengan una fecha de realización.
● Agregar la posibilidad de realizar modificación en los datos, tanto para
consorcios, unidades funcionales, propietarios, gastos, entre otros para
lograr cumplir con los requisitos de un programa de este estilo. No fue
tenido en cuenta ya que era una parte del programa que iba a tener un
comportamiento similar a agregar items pero iba a insumir mucho tiempo
para desarrollarla y no terminaba de ser realmente necesario para esta
presentación.


## Conclusion

Aparte del paradigma orientado a objetos, enseñado en la cátedra y utilizado para
realizar el trabajo, aprendimos nuevas tecnologías como hibernate, patrones de
diseño como Singleton, MVC y el uso de base de datos como H2, cada uno tanto de
manera individual como también, y muy importante, ensamblar cada parte para
utilizarla en conjunto con todas las demás. Logrando un óptimo funcionamiento
que asegure una correcta división de responsabilidades entre las partes y
escalabilidad a futuro, brindando facilidad para agregar funcionalidades a la
aplicación.
Obviamente al ser un prototipo para la cátedra, con motivo de que no sea haga
demasiado extenso, algunas funcionalidades están acotadas, pero se le podría
agregar funcionalidades en caso de querer llevarlo a producción.


## Uso del Software

Se adjunta un link a un drive el cual contiene:
● Una carpeta con el código fuente del programa
● Una carpeta llamada “Ejecutable” en donde se encuentran el .jar que inicia la aplicación
junto con la carpeta DBInicFile que contiene un archivo para inicializar la base con valores
precargados y la carpeta Reportes, que en caso de que se soliciten generarlos, los mismos
serán almacenados en ese directorio.
**Pantalla principal del sistema:**
Los botones “Borrar base de datos” y “Inicializar con casos de prueba” están puestos de modo que
faciliten el testeo para el profesor, y deberían ser removidos si el programa fuese a ser utilizado en
producción. El primero eliminará todos los datos de la base y el segundo la cargará con datos
predeterminados para que se pueda testear el funcionamiento del programa sin que sea tediosa la
carga masiva de información.
En caso de no existan datos almacenados y se intente usar alguna funcionalidad que utilice los
datos almacenados se mostrará un mensaje de error pidiendo que se agreguen datos
correspondientes.


**Nuevo Gasto: ​** En esta parte del sistema se podrán agregar los gastos a cada consorcio. Como se
explicó anteriormente se podrán dar cuatro posibilidades:

1. Agregar un gasto simple: Con el segundo combo conteniendo “Nuevo Gasto” y sin tildar el
    checkbox de compuesto, deberá tener un monto numérico con el “.” como separador de
    decimales y un concepto alfanumérico.
2. Agregar un gasto compuesto: Con el segundo combo conteniendo “Nuevo Gasto” y habiendo
    tildado el checkbox de compuesto, no se podrá ingresar un monto solo llevará un concepto,
    ya que sera contenedor de otros gastos, y su monto será la suma de los contenidos.
3. Agregar un gasto simple dentro de un gasto compuesto: En este caso en el combo se deberá
    seleccionar el gasto compuesto padre al que se agregara un nuevo gasto simple, lo demás
    quedará igual que en 1.
4. Agregar un gasto compuesto dentro de otro compuesto: Este último caso, el combo
    seleccionara el gasto compuesto padre al que se agregara el nuevo gasto compuesto, lo
    demás quedará igual que en 2.
Tanto para 3, como para 4, el combo para seleccionar un gasto mostrará la tupla
<IdGastoCompuesto, Concepto>.


**Agregar Pagos:​** Esta ventana permite agregar un pago a una unidad funcional, de la cual se
mostrará <Id, PisoNumero, Nombre y apellido del propietario>, solamente se deberá escribir un
valor para el monto, pudiendo ser positivo en caso de que sea a favor del propietario, o negativos si
fuese una deuda, y presionar el botón de guardar pago para que quede almacenado.
**Cerrar Liquidación: ​** Aquí solamente se deberá seleccionar el consorcio al cual se le hará el cierre de
liquidación, al confirmar el cierre se sumarán todos los gastos de la liquidación actual de ese
consorcio, se imputará a cada unidad funcional el costo a pagar según corresponda con su
porcentaje y se creará una nueva liquidación con la fecha del próximo mes a la actual.
Si se confirma el checkbox, se genera un informe en la carpeta Reportes, que contendrá el detalle de
los gastos de la liquidación cerrada y los saldos de los propietarios luego de imputar los gastos.


**Liquidación vigente: Gastos y Saldos parciales​** : esta ventana permite visualizar la lista de gastos
de la liquidación vigente para cada consorcio mediante el botón “Ver lista de gastos”. Los gastos
compuestos pertenecientes a compuestos se mostraran anidados en el mismo renglón.
También permite visualizar los saldos parciales de los propietarios de cada consorcio, se hace
referencia a que son parciales ya que al haber una liquidación mensual activa, los gastos de la
misma no serán imputados hasta el momento en que se cierre. En cambio, sí se verán reflejados en
los saldos si se realizaran pagos. Los saldos negativos indican que la unidad funcional está debiendo
al consorcio, y los positivos que la misma tiene dinero a favor.
**Liquidaciones Históricas:​** esta sección permite generar reportes de liquidaciones históricas
anteriores para cada consorcio. Estos reportes contendrán la lista de gastos perteneciente a la
liquidación seleccionada en el segundo combo.
**Agregar Datos: ​** aquí el usuario podrá agregar datos al programa, tanto consorcios nuevos, como
unidades funcionales dentro de un consorcio, como también propietarios que luego figuraban como
dueños de unidades funcionales, cada campo muestra un ejemplo de cómo se debería ingresar el
dato de manera correcta.
Como en las demás partes del programa, si se ingresa un dato de manera incorrecta o se dejará en
blanco el programa podría mostrar un mensaje de error.
**Datos propietarios y saldos:​** última ventana del sistema, en la misma se podrá filtrar entre los
datos de los propietarios, por si es necesario acceder a una dirección de mail o de teléfono,
mediante dos campos. Esto funciona de manera que se contemplan uno o ambos campos y permite
buscar por cualquier secuencia de caracteres contenida en la información de la persona.


En cuanto a la parte de filtro por saldos, ni bien se abra se mostrarán los saldos de todas las
unidades funcionales y su propietario, correspondientes a todos los consorcios del programa. Y
permitirá aplicar un filtro basado en un monto ingresado según el saldo buscado sea menor, igual o
mayor al mismo.

