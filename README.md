# IAPPReclamos


## �ndice

+ [API REST](#API)
+ [Diagrama de arquitectura de Aplicaci�n](#app)
+ [Spike tecnol�gico](#spike)



## Servicio de Gesti�n de Reclamos

El Servicio de gesti�n de Reclamos se encarga del procesamiento de Reclamos de Pedidos de Tienda Online, su tracking y la notifcaci�n a los clientes que reclaman. 

<a name="API"/>
## API REST

Toda la informaci�n se encuentra en nuestro Portal de [API-DOC](https://google.com.ar).

<a name="app"/>
## Diagrama de arquitectura

![Diagrama](./docs/arquitectura.jpg)</br>

## Servidor FTP

Diariamente el Servicio de Gesti�n de Reclamos buscar� el lote de pediods del d�a anterior para poder realizarle un reclamo en caso que sea requerido.
El formato del nombre del archivo ser� el siguiente:

> PEDIDOS_{{$datetime}}.csv

> Ejemplo:
> PEDIDOS_20180924.csv

Y el contenido tendr� los siguientes datos:


|IdPedido  	|FechaEstimada	|MontoCompra|DNICliente	|NombreCliente	|MailCliente	|idProducto	|DescripcionProducto|
|:----------|:--------------|:----------|:----------|:--------------|:--------------|:----------|:------------------|
|000001		|2018-09-15		|180.00		|18345672	|Arturo Sampala	|arty@yahoo.com	|18976AT65	|Mouse para gamer	|


[Ver ejemplo adjunto](./docs/example.csv)

<a name="spike"/>
## SPIKE Tecnol�gico

+ Backend: JAVAEE 7
+ Frontend: HTML6 + CCS3 + Javascript
+ Framework: Springboot + JHipster
+ Base de datos: Mysql Server

