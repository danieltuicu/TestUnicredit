# TestUnicredit
RESTful Web Services Application – Vanzare Bilete
Tuicu Daniel-George

  	Tehnologii folosite:
	Aplicatia a fost implementata in Java 1.8. Proiectul foloseste Maven, Java
Spark si Gson.

	Descrierea claselor si a structurilor de date:
	Au fost folosite 2 clase de baza: clasa Ticket si clasa Main.
	Clasa Ticket are 3 elemente importante: un id unic, numele evenimentului
pentru care este luat biletul si un generator care asigura unicitatea id-ului.
ID-urile incep de la 100.
	Clasa Main contine toate endpoint-urile cerute, plus inca 3 pe care le-am
considerat importante. Datele sunt salvate intr-un HashMap pentru bilete, res-
pectiv intr-un LinkedList pentru biletele anulate de unde pot fi refolosite
pentru alte evenimente, fara a genera mereu un alt bilet cu un ID nou. Progra-
mul se deschide automat cu 5 evenimente disponibile, retinute intr-un Arraylist.
Evenimentele sunt: "Metallica", "Queen", "Slipknot", "Nightwish", "Dragonforce".

	Rularea programului si a comenzilor:
	Request-urile de tip GET pot fi facute intr-un browser, dar pentru cele de
tip POST este nevoie de un client. Eu pentru testare am folosit extensia pentru
Google Chrome: Advanced REST client. Request-urile trebuie scrise asemenea unor
link-uri web (de exemplu http://localhost:4567/<endpoint>) in timp ce aplicatia
ruleaza.

	Endpoints:
> /buy_ticket/<name>  ->  Endpoint de tip POST. Adauga un bilet in baza de date
doar daca parametrul <name> este un eveniment care se afla in lista de eveni-
mente si intoarce un mesaj de succes impreuna cu ID-ul biletului. In caz de 
esec, intoarceun mesaj de eroare.  
Exemplu de folosire: localhost:4567/buy_ticket/Queen

> /list_events  ->  Endpoint de tip GET. Afiseaza lista evenimentelor pentru 
care exista bilete in format Json.
Exemplu de folosire: localhost:4567/list_events

> /ticket_details/<ID>  ->  Endpoint de tip GET. Intoarce datele de pe biletul
cu ID-ul introdus sau un mesaj de eroare in caz ca nu este inregistrat. 
Exemplu de folosire: localhost:4567/ticket_details/105

> /cancel_ticket/<ID>  ->  Endpoint de tip POST. Anuleaza biletul cu ID-ul in-
trodus. Exemplu de folosire: localhost:4567/cancel_ticket/105

	Acestea au fost endpoint-urile cerute. Pe langa ele am mai adaugat 3 pe ca-
re le-am considerat importante. Toate acestea necesita si parola "pass123", de-
oarece modifica sau cer acces la bazele de date.

> /list_tickets/<password>  ->  Endpoint de tip GET. Intoarce lista tuturor bi-
letelor cumparate in format Json.
Exemplu de folosire: localhost:4567/list_tickets/pass123

> /add_event/<password>/<name>  ->  Endpoint de tip POST. Adauga in lista eve-
nimentul <name>, daca nu exista deja.
Exemplu de folosire: localhost:4567/add_event/pass123/Aerosmith

> /remove_event/<password>/<name> -> Endpoint de tip POST. Sterge un eveniment
din lista daca exista.
