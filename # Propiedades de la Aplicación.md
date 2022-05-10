# **MANUAL DE USUARIO**


# Propiedades de la Aplicación 


## Funcionalidad 

El objetivo de la aplicación es una fácil implementación de varios contactos en la aplicación con una interfaz cómoda que respalde los datos introducidos en la base de datos ubicada localmente para la persistencia de estos una vez se cierre la aplicación, y permite no solamente crear registros para añadir usuarios sino también seguir lo que en informática se denomina el término CRUD buscando registros, borrándolos o actualizándolos. 
 

Los datos que la app puede guardar son los necesarios para generar una buena agenda básica: 

- Nombre  

- Apellidos 

- Teléfono 

- Dirección 

- Ciudad 

- Código Postal 

- Fecha de Nacimiento 


Además, también asignará automáticamente un ID a cada uno de los nuevos contactos en la Agenda para una rápida distinción y búsqueda. 
 

## Requerimientos 

Los requerimientos para que la Agenda funcione son: 

- Descarga e instalación de MariaDB, la base de datos que utiliza la App. 

- Creación de la Tabla en la que los registros se guardarán, que podrá encontrar en la sección de Instalación. 

- Ejecutar la carpeta en un IDE con el JDK 18. 


# Uso de la aplicación 

Al abrir la aplicación, nos encontraremos con el siguiente menú:  

 ![menu](https://i.imgur.com/Vabj8A4.png)

Desde allí podremos acceder a las siguientes funcionalidades de la aplicación 

## Agregar Contacto 

Para Agregar un Contacto, ha de dar click en “Añadir”, y se nos abrirá el siguiente menú: 

 ![agregar](https://i.imgur.com/v4N3RPg.png) 

Allí, insertamos los datos del contacto que queremos insertar en la Agenda y damos click en “Añadir” 

 ![anadido](https://i.imgur.com/YdgOlEe.png) 
 

## Buscar Contacto 

Para “Buscar Contactos”, desde el menú principal damos click en “Buscar”, y en la siguiente interfaz, clickamos en “Buscar Contacto” e insertamos en ID o Nombre del usuario que estamos buscando. 

 ![buscar](https://i.imgur.com/PGVPw4t.png) 

## Borrar Contacto 

Desde la pestaña de “Buscar” podemos dar click en la tabla para seleccionar a uno de los Contactos, y damos click en Eliminar para Borrar ese registro, aqui la demostración con el usuario "Test". 

 ![tablaborrar](https://i.imgur.com/E2Q3Hdi.png) 
 ![borrar](https://i.imgur.com/BcH43zf.png) 

## Actualizar Contacto 

Para actualizar un Contacto, desde “Buscar” hacemos click en el Contacto que queremos que sea actualizado y desde ahí modificamos los datos que aparecen a la izquierda de la pantalla, y tras ello damos click en “Guardar” para salvar los datos que han sido modificados. 


 ![actualizar](https://i.imgur.com/yTfVcvJ.png) 
 

Los registros modificados saldrán como duplicados, pero al salir de ahí con “Volver” el registro duplicado será eliminado y solamente quedará el usuario actualizado. 