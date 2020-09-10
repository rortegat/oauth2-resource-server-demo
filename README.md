# oauth2-resource-server-demo

Demostración de endpoints protegidos por roles con el uso del protocolo de autenticación y autorización OAuth2

## Requisitos del sistema

Se requiere contar con Maven 3.3 o superior y el JDK 8 (puede ser de Oracle u OpenJDK)

## Instrucciones

Clona el proyectorio en tu directorio local y ábrelo desde la consola. Una vez dentro del proyecto ejecutar el comando:

```bash
mvn clean install
```

Esto instalará todas las dependencias necesarias para poder compilar el proyecto, posteriormente se ejecuta con:

```bash
mvn spring-boot:run
```

Es necesario modificar el archivo properties.yml para que apunte a nuestro Authentication Server.
