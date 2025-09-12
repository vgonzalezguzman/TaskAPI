# Llegeix-me si necessites ajuda per executar el projecte:
Aquesta aplicació web ha sigut desenvolupada utilitzant IntellIJ IDEA i MongoDB Compass. També he fet servir Postman per fer request. <br>
Primer, descarrega el repositori a la branca Master. <br>
Una vegada descarregat, descomprimeix l'arxiu descarregat i obre el projecte amb el teu IDE preferit. <br>
En aquest tutorial explicaré com executar i obrir el projecte a IntellIJ IDEA i a Eclipse. <br>
## IntellIJ:
Dins el programa, al menú superior fes clic a "File -> Open", t'obrirà l'explorador de fitxers. Navega a la carpeta descomprimida del projecte i el projecte a la carpeta on vegis l'arxiu ".project". <br>
Si l'IDE et demana en quina configuració obrir-ho (en cas que ho hagis obert en un altre IDE), selecciona l'opció "Maven project". 
Posteriorment, et demanarà en quina finestra obrir-lo, no és importat, i posteriorment et demanarà si confies en l'aplicació, per evitar problemes d'execució has de clicar el botó "Trust this project" o la variació adient. <br>
Si l'has obert amb un altre IDE és possible que el projecte no et deixi executar, en aquest cas t'hauria de sortir un avís a la part inferior dreta preguntant si vols carregar les dependències Maven, accepta que les carregui.<br>
Els fitxers rellevants a la funcionalitat de la API són a la ruta "TaskAPI/src/main/java/com.testowc.taskapi/".<br>
Per executar el projecte, fes clic al botó <img width="24" height="24" alt="image" src="https://github.com/user-attachments/assets/060780c9-52ac-46fa-b4c9-95383cb6d368" /> situat a la part superior central de la finestra.<br>
Els tests es troben a la ruta "TaskAPI/src/test/java/com.testowc.taskapi/controller/". Per executar-los, fes clic dret al fitxer "TaskControllerTest" dins la ruta esmentada i selecciona la opció "Run 'TaskControllerTest'".<br>
Espera que acabi l'execució del test i a la finestra que s'ha obert a la part inferior hauries de veure això: <br>
<img width="623" height="244" alt="image" src="https://github.com/user-attachments/assets/0df65da5-732f-4af7-9648-7ea58bffda68" /><br>
## Eclipse
A l'aplicació Eclipse el procediment es similar a IntellIJ.<br>
Obrim l'IDE, cliquem el botó "Browse..." i busquem qualsevol carpeta menys la del projecte, després, al menú superior de l'IDE cliquem el botó "File->Open Projects from File System". <br>
Això ens obrirà una finestra en la qual clicarem el text "Show other specialized import wizards" i escollim "Maven->Existing Maven Projects".<br>
Posteriorment fem clic al botó "Next >" i busquem el directori del projecte clicant el botó "Browse...". Una vegada seleccionat el projecte cliquem el botó "Finish".<br>
Quan el projecte carregui el podem executar anant a la ruta "/src/main/java/com/testowc/taskapi/" i clicant el fitxer anomenat "TaskApiApplication.java" i clicant el botó 
<img width="19" height="20" alt="image" src="https://github.com/user-attachments/assets/ca8ca34f-f29a-4417-908d-64433b94ec08" />
situat a la part superior de la finestra:<br>
<img width="1010" height="759" alt="image" src="https://github.com/user-attachments/assets/29f4f4b0-b17b-4581-9ab4-6ba0a0cc9f69" /><br>
Per a executar els tests anem a la ruta "/src/test/java/com/testowc/taskapi/controller/" i seleccionem el fitxer "TaskControllerTest.java" i clicant el botó d'execució.
## MongoDB Compass
En aquesta aplicació haurem de crear 2 bases de dades noves: una anomenada "toDoApp" i una altra anomenada "toDoApp_test". L'aplicació s'encarrega de definir les coleccions i les columnes.<br>
També es poden crear les coleccions de la BDD via MongoSH via aquestes comandes:
```
use toDoApp
db.createCollection("tasks")
use toDoApp_Test
db.createCollection("tasks")
```
Per afegir entrades a la colecciò ens podem ajudar d'aquesta comanda (recomano només afegir dades a la taula toDoApp i no a la taula toDoApp_test perquè els tests eliminen totes les entrades cada vegada que s'executen):
```
db.tasks.insertMany([{name: "Test name 1", description: "Test description 1", completed: false, dueDate: Date()},{name: "Test name 2", description: "Test description 2", completed: true, dueDate: Date()},{name: "Test name 3", description: "Test description 3", completed: false, dueDate: Date()}])
```
O si només vols afegir una entrada:
```
db.tasks.insetOne({name: "Test name", description: "Test description", completed: false, dueDate: Date()})
```
<br>

## Swagger
Possiblitat d'utilitzar Swagger d'OpenAPI executant l'API i accedint a la url `http://localhost:8080/swagger-ui/index.html#/`