![Duoc UC](https://www.duoc.cl/wp-content/uploads/2022/09/logo-0.png)
# 🧠 Evaluación Sumativa Semana 8 – Desarrollo Orientado a Objetos II

## 👤 Autor del proyecto
- **Nombre completo:** Gabriela Goycochea Romero
- **Sección:** 002A
- **Carrera:** Analista Programador Computacional
- **Sede:** Duoc Online

---

## 📘 Descripción general del sistema

El sistema FastFoodAppInterfazBD es una interfaz, vinculada a una base de datos sql, donde refleja un menu de iteración con el usuario, es posible realizar 5 acciones:
- Registrar Pedido
- Registrar Entrega (manual)
- Lista de Pedidos
- Asignar repartidor e Iniciar Entrega
- Salir


---

## 🧱 Estructura general del proyecto

```plaintext

 FastFoodAppInterfazBD
 ├── .idea/
 ├── .mvn
 ├── src/
 │    ├── main
 │    │     ├── java
 │    │     │    └──com.duoc.Sistema.FastFood.Interfaz.App
 │    │     │             ├──Conexion
 │    │     │             │     └──ConexionBBDD
 │    │     │             │     
 │    │     │             │
 │    │     │             ├── Gestor
 │    │     │             │      ├──DAO   
 │    │     │             │      │   ├──EntregaDAO 
 │    │     │             │      │   ├──PedidoDAO   
 │    │     │             │      │   └──RepartidorDAO   
 │    │     │             │      │      
 │    │     │             │      └──ControladorPedido
 │    │     │             │
 │    │     │             ├── Model
 │    │     │             │     ├──EstadoPedido
 │    │     │             │     ├──Pedido
 │    │     │             │     ├──PedidoComida
 │    │     │             │     ├──PedidoEnvio
 │    │     │             │     ├──PedidoExpress
 │    │     │             │     ├──Repartidor
 │    │     │             │     └──Requerimiento
 │    │     │             │     
 │    │     │             │       
 │    │     │             └── UI                  
 │    │     │                  ├── Main
 │    │     │                  ├──VentanaAsignarRepartidor
 │    │     │                  ├──VentanaListaPedidos
 │    │     │                  ├──VentanaPrincipal  
 │    │     │                  ├──VentanaRegistroEntrega
 │    │     │                  └──VentanaRegistroPedido
 │    │     │ 
 │    │     │
 │    │     ├──resources    
 │    │     └──README.md                  
 │    │
 │    └──test
 │
 └──  .gitignore
   

````

# com.duoc.Sistema.FastFood.Interfaz.App

El sistema contiene 4 paquetes Conexion - Gestor - Model - UI

Conexión genera la conexión a la base de datos sql y conecta la sincronización de los pedidos según se van actualizando
Esto permite que se visualice el estado del pedido tanto en la app como en la base de datos en tiempo real.

En gestor especificamente se controlan los pedidos gestionar la lista y repartidores de la interfaz.
En la carpeta DAO están los datos para agregarlos a las hojas de las bases de datos y registrarlas dentro de ellas.
Permite el acceso manual de repartidores y automaticos

La clase Repartidor permite ingreso de datos y validar los requerimientos para validar entregas.

Luego tenemos Pedido y los tipos pedidos que cada uno tiene sus caracteristicas y heredan desde pedido lo requerido.
EstadoPedido y Requerimientos muestras el estado en el que se encuentra el pedido y requerimiento es para verificar que cumpla
lo necesario para el reparto.

La clase UI es la que tiene las gestion de ventanas y el main.
Cada una de las ventanas aplica a un despliegue para la misma, en total son 5 ventanas separadas para modificar.
El Main sirve para ejecutar el proyecto completo.

Aplicacion funciona vinculando la información a una base de datos SQL

---

## ⚙️ Instrucciones para clonar y ejecutar el proyecto

1. Clona el repositorio desde GitHub:

```bash
git clone https://github.com/GabrielaGoycochea/FastFoodAppInterfazBBDD.git
```

2. Abre el proyecto en IntelliJ IDEA.

3. Ejecuta el archivo `Main.java` desde el paquete `UI` para resultados en consola.

4. Sigue las instrucciones en consola o en la interfaz gráfica (si corresponde).


---

**Repositorio GitHub:** https://github.com/GabrielaGoycochea/FastFoodAppInterfazBBDD.git
**Fecha de entrega:** 22/02/2026

---

© Duoc UC | Escuela de Informática y Telecomunicaciones |  