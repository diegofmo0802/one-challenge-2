# Conversor de monedas

## Herramientas usadas para el consumo del api
- Se uso el api de exchangerate-api.com para conversion de monedas;
- Se uso HTTPClient, HTTPRequest, HTTPResponse para la comunicación con el api
- Se uso GSON para la conversion de archivos Json a java classes

## Herramientas usadas para el historial de búsquedas del usuario
- Se uso un modulo [com.mysaml.csv](https://github.com/diegofmo0802/com.mysaml.csv) para el manejo del archivo del historial

## Puntos a resaltar durante la creación.
- No había consumido api´s en java
- Fue un proyecto que no se me hubiese ocurrido hacer

## Problemas enfrentados en el desarrollo
- Intentar mostrar el historial:
  Esto debido a que quería usar el método toString para
  mostrar el historial, al final tome la decision de tomar
  el método toString de CSVResult y modificarlo para
  obtener el resultado deseado.
- EL modulo [com.mysaml.csv](https://github.com/diegofmo0802/com.mysaml.csv) usaba un Set para los resultados de las query en CSV por lo que no se veían en orden:
  por lo que se opto por modificarlo usando un List en lugar de Set

## Conclusion
Fue una experiencia entretenida, divertida y de la que saque algunos nuevos conocimientos.
Mejoro mi comprensión del consumo de api´s en java y me dio una noción básica de que cosas podría hacer en el futuro