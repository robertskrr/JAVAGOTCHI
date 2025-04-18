# 🐕 JAVAGOTCHI 🐈
## 🐶 Simulador de mascotas virtuales desarrollado en Java y MySQL 🐱

## 🐦OBJETIVO DEL PROYECTO 
Desarrollar un **sistema de gestión de mascotas virtuales** que permita a los usuarios (dueños) 
interactuar con sus mascotas a través de acciones como jugar, alimentar y bañar. El sistema
gestionará el registro, consulta y eliminación de usuarios y mascotas, actualizando las estadísticas 
de cada mascota según las interacciones, todo almacenado en una base de datos.

## 🦴 FUNCIONALIDADES

### 1. Menú principal con las siguientes opciones:
- **Crear usuario**
- **Listar usuarios** ordenados por *fecha de registro*
- **Buscar usuario**
- **Seleccionar usuario (accedes con el `userName` y la `contraseña` del usuario) --> Abre el submenú:** 
  - **JUGAR** 
    1. Dentro del usuario seleccionado, podrás elegir una mascota específica
    2. Interactuar con la mascota seleccionada
         - Tendremos un submenú con las interacciones de la mascota (ver [*punto 10*](#10-representación-gráfica-de-estadísticas))
  - **Listar mascotas** ordenadas por *nombre, edad o felicidad*
  - **Buscar mascota**
  - **Crear mascota**
  - **Eliminar mascota**
  - **Modificar tu usuario**
  - **Eliminar tu usuario** (eliminando a todas tus mascotas)
  - **Volver al menú principal**

### 2. Creación y eliminación de usuarios  
Para crear un usuario, se deberán de poner los datos correspondientes, teniendo en cuenta que el `userName` es único y no puede repetirse.
Para eliminar un usuario, el usuario debe confirmar su eliminación, lo cual implica también la eliminación de sus mascotas.

### 3. Opciones del usuario
El usuario elegido puede **eliminar sus datos** de la aplicación, **modificarlos**, **ver a sus mascotas ordenadas** por *nombre, edad o felicidad*,
**crear mascotas**, s**eleccionar una mascota** para interactuar con ella o **eliminarla**. 

### 4. Creación de mascotas
Para agregar una nueva mascota, el usuario debe autenticarse correctamente con su `userName` y `contraseña`. 
Luego, podrá crear una o más mascotas que se asociarán a su cuenta.

### 5. Sistema de mascotas:
- Cada mascota tendrá un **color aleatorio** asignado a su arte ASCII al momento de su creación (ver [*punto 9*](#9-visualización-de-las-mascotas)).
- **Tipos de mascotas disponibles:** Perro, Gato y Pájaro.
- **Sexo de la mascota:** Macho o Hembra.
- **Estadísticas:** Cada mascota contará con las siguientes estadísticas:
  - **Edad** *(cada día real equivale a un año).*
  - **Nutrición** *(valor entre 0 y 10).*
  - **Limpieza** *(valor entre 0 y 10).*
  - **Felicidad** *(valor entre 0 y 10).*

- Las estadísticas **Nutrición** y **Limpieza** se generarán **aleatoriamente** al crear la mascota.
- La **Edad** aumentará con el tiempo desde la fecha de creación de la mascota (cada día que pase equivale a un año).
- La **Felicidad** se actualizará en base a las interacciones del usuario y las otras estadísticas.

> 🐾 Al ser un juego optimista, las mascotas **no pueden morir**. Vivirán indefinidamente a menos que:
> - El dueño las elimine manualmente.
> - El dueño elimine su cuenta de la aplicación.

### 6. Interacciones con la mascota
- **Observar mascota:** Se mostrará de manera gráfica la mascota “moviéndose” mediante frames creados con ASCII.
- **Alimentar:** Aumenta la nutrición de la mascota. *Hay distintos tipos de comida con valores nutricionales diferentes.*
- **Jugar:** Disminuye la nutrición y limpieza de la mascota. *Hay distintos tipos de juegos*. **Si la mascota tiene mucha hambre**, no querrá jugar, y el sistema mostrará un mensaje como: *"¡Tu mascota tiene mucha hambre! No tiene ganas de jugar."*
- **Bañar:** Aumenta la limpieza al máximo y no afecta a la nutrición.
  
### 7. Nivel de felicidad
La **felicidad** es una estadística dinámica que refleja el estado anímico de la mascota. Esta puede **aumentar o disminuir** dependiendo de las acciones del usuario y del estado general de la mascota.

#### 🔼 ¿Cómo se incrementa la felicidad?
- **Jugar con la mascota**: cada tipo de juego puede aumentar la felicidad en mayor o menor medida.
- **Alimentarla**: si la mascota tiene hambre y, dependiendo del hambre que tenga, alimentarla con el tipo de comida adecuado aumentará su felicidad.
- **Bañarla**: también mejora su estado anímico.

#### 🔽 ¿Cuándo disminuye la felicidad?
- Si la mascota está **muy sucia** *(limpieza baja)*.
- Si la mascota tiene **mucha hambre** *(nutrición baja)*.

### 8. Alertas de estado
- Si alguna estadística de la mascota llega a un **nivel crítico (0)**, el sistema mostrará alertas para que el dueño reaccione:
  - **Ejemplo de alerta de nutrición:** *"¡Tu mascota tiene mucha hambre! ¡Aliméntala pronto!"*
  - **Ejemplo de alerta de limpieza:** *"¡Tu mascota está muy sucia! ¡Báñala cuánto antes!"*
  - **Ejemplo de alerta de felicidad:** *"¡Tu mascota está muy triste! ¡Revisa qué es lo que necesita!”*
### 9. Visualización de las mascotas
Las mascotas se mostrarán de **manera gráfica** en la consola mediante dibujos hechos con **código ASCII**, lo que hace una experiencia más interactiva.
#### Ejemplo:
```
─────▄█▄█─────────────
────█████▄▄▄──────────
──────███████▄────────
──────█▀█▀█████───────
─────▄█▄█─▄████▄▄▀────

         ／＞　 フ
        | 　_　_| 
      ／` ミ＿xノ 
     /　　　　 |
    /　 ヽ　　 ﾉ         
    │　　|　|　|       
／￣|　　|　|　|         
(￣ヽ＿_ヽ_)__)  
＼二)⠀

    /""\      ,
   <>^  L____/|
    `) /`   , /
     \ `---' /
      `'";\)`
        _/_Y
```
### 10. Representación gráfica de estadísticas
En el **menú de interacción de la mascota** se verá a la misma junto a sus estadísticas. Estas se mostrarán de manera más visual utilizando **barras gráficas** en lugar de unidades numéricas *(del 0 al 10)*. 
#### Ejemplo:
- **Nombre:** JAGGER
- **Tipo:** Gato
- **Hembra**
- **Edad:** 3 años
- **Nutrición:** 🟧🟧🟧🟧⬜⬜⬜⬜⬜⬜
- **Limpieza:** 🟨🟨🟨🟨🟨🟨🟨⬜⬜⬜
- **Felicidad:** 🟩🟩🟩🟩🟩🟩🟩🟩🟩⬜
```
                  ／＞　 フ
                 | 　_　_| 
               ／` ミ＿xノ 
              /　　　　 |
             /　 ヽ　　 ﾉ         
             │　　|　|　|       
         ／￣|　　|　|　|         
         (￣ヽ＿_ヽ_)__)  
         ＼二)⠀
```
1. Observar mascota.
2. Alimentar.
3. Jugar
4. Bañar.
5. Volver al menú de usuario

## 🕸️TECNOLOGÍAS UTILIZADAS🕸️
- Java
- MySQL
- JDBC
- JUnit
- Git y GitHub
