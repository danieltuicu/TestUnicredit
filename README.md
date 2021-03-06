# RESTful Web Services Application – Vanzare Bilete
#### Tuicu Daniel-George

##### Tehnologii folosite:
<p> Aplicatia a fost implementata in Java 1.8 cu ajutorul Intellij IDEA. Proiectul foloseste Maven, Java Spark si Gson. </p>

##### Descrierea claselor si a structurilor de date:
<p> 	Au fost folosite 4 clase de baza: Ticket, Error, Response si clasa Main.</p> 
<p> Clasa Ticket are 3 elemente importante: un id unic, numele evenimentului pentru care este luat biletul si un generator care asigura unicitatea id-ului. ID-urile incep de la 100. <p>
<p> Clasa Response este folosita la raspunsul serverului in caz de succes dupa executarea unui request. </p>
<p> Clasa Error mosteneste Response si este folosita pentru request-uri esuate.</p>
<p> Clasa Main contine toate endpoint-urile cerute, plus inca 3 pe care le-am considerat importante. Datele sunt salvate intr-un HashMap pentru bilete, respectiv intr-un LinkedList pentru biletele anulate de unde pot fi refolosite pentru alte evenimente, fara a genera mereu un alt bilet cu un ID nou. Programul se deschide automat cu 5 evenimente disponibile, retinute intr-un Arraylist. </p>
<p> Evenimentele implicite sunt: "Metallica", "Queen", "Slipknot", "Nightwish", "Dragonforce". </p>

##### Rularea programului si a comenzilor:
<p> Pentru executarea efectiva a programului se poate folosi urmatoare comanda in folderul radacina din cmd:<br> <b>mvn clean package exec:java</b> </p>



<p> Request-urile de tip GET pot fi facute intr-un browser, dar pentru cele de tip POST este nevoie de un client. Eu pentru testare am folosit extensia pentru Google Chrome: Advanced REST client. Request-urile trebuie scrise asemenea unor link-uri web (de exemplu http://localhost:4567/:endpoint) in timp ce aplicatia ruleaza. </p>

##### Endpoints:
* /buy_ticket/:name  ->  Endpoint de tip POST. Adauga un bilet in baza de date doar daca parametrul <name> este un eveniment care se afla in lista de evenimente si intoarce un mesaj de succes. In caz de esec, intoarce un mesaj de eroare.  
*<p> Exemplu de folosire: localhost:4567/buy_ticket/Queen </p>*

* /list_events  ->  Endpoint de tip GET. Afiseaza lista evenimentelor pentru 
care exista bilete in format Json.
*<p> Exemplu de folosire: localhost:4567/list_events</p>*

* /ticket_details/:id  ->  Endpoint de tip GET. Intoarce datele de pe biletul cu ID-ul introdus sau un mesaj de eroare in caz ca nu este inregistrat. 
*<p> Exemplu de folosire: localhost:4567/ticket_details/105 </p>*

* /cancel_ticket/:id  ->  Endpoint de tip POST. Anuleaza biletul cu ID-ul introdus. 
*<p> Exemplu de folosire: localhost:4567/cancel_ticket/105 </p>*

##### Extra
<p> Acestea au fost endpoint-urile cerute. Pe langa ele am mai adaugat 3 pe care le-am considerat importante. Toate acestea necesita si parola "pass123", deoarece modifica sau cer acces la bazele de date.</p>

* /list_tickets/:password  ->  Endpoint de tip GET. Intoarce lista tuturor biletelor cumparate in format Json.
*<p> Exemplu de folosire: localhost:4567/list_tickets/pass123 </p>*

* /add_event/:password/:name  ->  Endpoint de tip POST. Adauga in lista evenimentul <name>, daca nu exista deja.
*<p> Exemplu de folosire: localhost:4567/add_event/pass123/Aerosmith </p>*

* /remove_event/:password/:name -> Endpoint de tip DELETE. Sterge un eveniment din lista daca exista.
