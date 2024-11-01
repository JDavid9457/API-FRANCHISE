**API FRANCHISE**



![Java Version](https://img.shields.io/badge/Java-17(LTS)-red?logo=java)
![Spring Boot](https://img.shields.io/badge/Spring&nbsp;Boot-3.3.2-success?logo=springboot)
![Gradle](https://img.shields.io/badge/Gradle-8.2.1-success?logo=Gradle)

## Soluccion del problema
De acuerdo con el problema,se abordó la construcción un servicio de backen, utilizando la tecnología Java y spring boot webFlux, 
para ello se implementó una arquitectura hexagonal adaptada a las necesidades del problema, obteniendo una aplicación 
para registro de franquicias y control.



## MongoDB
Este proyecto utiliza una base de datos no relacional en MongoDB, que permite almacenar y gestionar información de manera 
eficiente mientras el aplicativo está en ejecución.


Para interactuar con la base de datos, utiliza el siguiente enlace de conexión, que conecta a una instancia 
local de MongoDB y accede directamente a la base de datos nequiFranchisedb.


```sh
mongodb://localhost:27017/nequiFranchisedb
```
una vez aquí configuramos el ingreso, colocando  la url, usuario y contraseña
```sh
spring:
  data:
    mongodb:
      uri: mongodb://localhost:27017/nequiFranchisedb
```
colocando estos datos nos permitirá el ingreso.
## Swagger
Este proyecto  tiene configurado un Swagger, el cual permite la ejecución de los endpoints disponibles,
para acceder ingresamos a la  siguiente ruta:

```sh
http://localhost:8080/swagger-ui/index.html
```
aqui nos permite interactuar de forma grafica con los endpoints

## Rutas
Estos enlaces nos permite interactuar con el aplicativo:


```sh
ingresar Guardar franquicia
http://localhost:8080/franchise

```
```sh
ingresar Guardar una sucursal en una franquicia
http://ttp://localhost:8080/franchise/{id}/branch
```
```sh
ingresar Guardar un producto en una sucursal
http://localhost:8080/franchise/{id}/branch/{id}/product
```
```sh
 Listar franquicias
http://localhost:8080/apifranchise/list
```

```sh
buscar por id: devuelve los datos de las franquicia
http://localhost:8080/franchise/{id}
```
```sh
Cambiar nombre de franquicia
http://localhost:8080/apifranchise/{id}/name
```
```sh
Cambiar nombre de sucursal
http://localhost:8080/apifranchise/{id}/branchs/{id}/name
```

```sh
Cambiar nombre de producto
http://localhost:8080/apifranchise/{id}/branches/{id}/products/{id}/name
```

## Respuesta principales del servicio:
Se ingresamos el objeto, para guardarlo
```JSON
{
  "nameFranchise": "ArinaPan",
  "branches": [
    {
      "name": "Tringo",
      "products":[
        {
          "name":"Maiz",
          "stock":1000
        }
      ]
    }
  ]
  

}
```
respuesta es exitosa devuelve lo siguiente:
```JSON
{
  "data": {
    "id": "6724d514f28ef41d4619b339",
    "nameFranchise": "ArinaPan",
    "branchDTOList": [
      {
        "id": "6724d514f28ef41d4619b337",
        "name": "Tringo",
        "products": [
          {
            "id": "6724d514f28ef41d4619b338",
            "name": "Maiz",
            "stock": 1000
          }
        ]
      }
    ]
  },
  "status": 201,
  "message": "Franquicia creado exitosamente!."
}
```
Se ingresamos el objeto, para guardarlo
```JSON
{
  "name": "New Branch Aguila",
  "products": [
    {
      "name": "negra",
      "stock": 10
    }
  ]
}
```
respuesta cuando guardar una sucursal en una franquicia:
```JSON
{
  "data": {
    "id": "6724d514f28ef41d4619b339",
    "nameFranchise": "ArinaPan",
    "branchDTOList": [
      {
        "id": "6724d514f28ef41d4619b337",
        "name": "Tringo",
        "products": [
          {
            "id": "6724d514f28ef41d4619b338",
            "name": "Maiz",
            "stock": 1000
          }
        ]
      },
      {
        "id": "6724d711f28ef41d4619b33a",
        "name": "New Branch Aguila",
        "products": [
          {
            "id": "6724d711f28ef41d4619b33b",
            "name": "negra",
            "stock": 10
          }
        ]
      },
      {
        "id": "6724d7b5f28ef41d4619b33c",
        "name": "New Branch Aguila",
        "products": [
          {
            "id": "6724d7b5f28ef41d4619b33d",
            "name": "negra",
            "stock": 10
          }
        ]
      }
    ]
  },
  "status": 201,
  "message": "Se ha regsitrado una sucursal en una franquicia"
}
```
``
Se ingresamos el objeto, para guardarlo
```JSON
{
  "name": "Lulu",
  "stock": 100
}
```
respuesta cuando guardar una sucursal en una franquicia:
```JSON
{
  "data": {
    "id": "6724d514f28ef41d4619b339",
    "nameFranchise": "ArinaPan",
    "branchDTOList": [
      {
        "id": "6724d514f28ef41d4619b337",
        "name": "Tringo",
        "products": [
          {
            "id": "6724d514f28ef41d4619b338",
            "name": "Maiz",
            "stock": 1000
          },
          {
            "id": "6724d916f28ef41d4619b33e",
            "name": "Lulu",
            "stock": 100
          }
        ]
      },
      {
        "id": "6724d711f28ef41d4619b33a",
        "name": "New Branch Aguila",
        "products": [
          {
            "id": "6724d711f28ef41d4619b33b",
            "name": "negra",
            "stock": 10
          }
        ]
      },
      {
        "id": "6724d7b5f28ef41d4619b33c",
        "name": "New Branch Aguila",
        "products": [
          {
            "id": "6724d7b5f28ef41d4619b33d",
            "name": "negra",
            "stock": 10
          }
        ]
      }
    ]
  },
  "status": 201,
  "message": "Se ha regsitrado una sucursal en una franquicia"
}
```
Para cambiar el nombre de la framquicia
```JSON
{
  "name": "Soya"
}
```
respuesta cuando se actualiza nombre de la franquicia
```JSON
{
  "data": {
    "id": "6724d514f28ef41d4619b339",
    "nameFranchise": "Soya",
    "branchDTOList": [
      {
        "id": "6724d514f28ef41d4619b337",
        "name": "Tringo",
        "products": [
          {
            "id": "6724d514f28ef41d4619b338",
            "name": "Maiz",
            "stock": 1000
          },
          {
            "id": "6724d916f28ef41d4619b33e",
            "name": "Lulu",
            "stock": 100
          }
        ]
      },
      {
        "id": "6724d711f28ef41d4619b33a",
        "name": "New Branch Aguila",
        "products": [
          {
            "id": "6724d711f28ef41d4619b33b",
            "name": "negra",
            "stock": 10
          }
        ]
      },
      {
        "id": "6724d7b5f28ef41d4619b33c",
        "name": "New Branch Aguila",
        "products": [
          {
            "id": "6724d7b5f28ef41d4619b33d",
            "name": "negra",
            "stock": 10
          }
        ]
      }
    ]
  },
  "status": 200,
  "message": "Product with max stock found successfully."
}
```
Para cambiar el nombre de la sucursal
```JSON
{
  "name": "Arroz"
}
```
respuesta cuando se actualiza nombre de la sucursal
```JSON
{
  "data": {
    "id": "6724d514f28ef41d4619b339",
    "nameFranchise": "Arroz",
    "branchDTOList": [
      {
        "id": "6724d514f28ef41d4619b337",
        "name": "Tringo",
        "products": [
          {
            "id": "6724d514f28ef41d4619b338",
            "name": "Maiz",
            "stock": 1000
          },
          {
            "id": "6724d916f28ef41d4619b33e",
            "name": "Lulu",
            "stock": 100
          }
        ]
      },
      {
        "id": "6724d711f28ef41d4619b33a",
        "name": "New Branch Aguila",
        "products": [
          {
            "id": "6724d711f28ef41d4619b33b",
            "name": "negra",
            "stock": 10
          }
        ]
      },
      {
        "id": "6724d7b5f28ef41d4619b33c",
        "name": "New Branch Aguila",
        "products": [
          {
            "id": "6724d7b5f28ef41d4619b33d",
            "name": "negra",
            "stock": 10
          }
        ]
      }
    ]
  },
  "status": 200,
  "message": "Product with max stock found successfully."
}
```
Para cambiar el nombre del producto
```JSON
{
  "name": "blanca"
}
```
respuesta cuando se actualiza nombre del producto
```JSON
{
  "data": {
    "id": "6724d514f28ef41d4619b339",
    "nameFranchise": "Arroz",
    "branchDTOList": [
      {
        "id": "6724d514f28ef41d4619b337",
        "name": "Tringo",
        "products": [
          {
            "id": "6724d514f28ef41d4619b338",
            "name": "Maiz",
            "stock": 1000
          },
          {
            "id": "6724d916f28ef41d4619b33e",
            "name": "Lulu",
            "stock": 100
          }
        ]
      },
      {
        "id": "6724d711f28ef41d4619b33a",
        "name": "New Branch Aguila",
        "products": [
          {
            "id": "6724d711f28ef41d4619b33b",
            "name": "blanca",
            "stock": 10
          }
        ]
      },
      {
        "id": "6724d7b5f28ef41d4619b33c",
        "name": "New Branch Aguila",
        "products": [
          {
            "id": "6724d7b5f28ef41d4619b33d",
            "name": "negra",
            "stock": 10
          }
        ]
      }
    ]
  },
  "status": 200,
  "message": "Product with max stock found successfully."
}
```



