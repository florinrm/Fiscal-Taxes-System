====================== Tema POO - Sistem de facturi fiscale ===================

	Nume: Mihalache Florin-Razvan
	Grupa: 323CC
	Timp de lucru: o saptamana

	Pentru a rula aplicatia, se ruleaza main-ul din clasa AppStart

	A se loga cu:
	username: florin.mihalache
	parola: 69420

	In cadrul acestei teme, am implementat urmatoarele clase:

	1) AppStart - o clasa GUI, care se ocupa cu logarea utilizatorului si lansarea
aplicatiei. Fereastra construita in cadrul acestei clase (utilizand facilitatile
Swing) contine:
	- camp text pentru username
	- camp text pentru parola - caracterele sunt secrete
	- buton de logare - daca nu sunt toate fisierele pe care le vom prelucra
in radacina proiectului se va lansa o pagina de upload de fisiere (produse.txt,
taxe.txt, facturi.txt), altfel e lansata aplicatia propriu - zisa
	- buton de creare de cont nou
	- buton de schimbare de parola - se lanseaza o fereastra noua pentru a fi
schimbata parola utilizatorului
	Daca parola sau username sunt gresite sau nu exista contul cu username-ul
respectiv, va fi afisat jos un mesaj de avertizare (in cazul logarii). In
cazul crearii unui cont nou, se va afisa un mesaj sub butoane daca contul
exista deja sau daca a fost creat cu succes. Evidenta utilizatorilor este tinuta
in fisierul login.txt, unde pe prima coloana este username si pe a doua este
parola criptata cu SHA-256 a utilizatorului.

	2) ChangePassword - o clasa GUI, care se ocupa cu schimbarea parolei
utilizatorului. Fereastra construita in cadrul acestei clase contine:
	- camp text pentru username - JTextField
	- camp text pentru parola veche - text secret
	- camp text pentru parola noua - text secret
	- buton pentru salvarea setarilor
	- buton de inchidere a ferestrei
	Daca parola veche sau username-ul sunt gresite, va fi afisat un mesaj
de avertizare sub butoane. In cazul in care ambele sunt corecte, parola veche
criptate va fi inlocuita de noua parola criptata, in login.txt.

	3) UploadFiles - o clasa GUI, care se ocupa cu incarcarea fisierelor - o
fereastra ce apare in caz ca nu sunt toate fisierele ce trebuiesc parsate in 
radacina proiectului, dupa ce user-ul este logat cu succes si inainte de lansarea
aplicatiei propriu zise. Fereastra contine 4 butoane (3 de upload si inca unul
pentru start aplicatie)

	4) WelcomePage - clasa GUI, fereastra aplicatiei propriu zise. Aceasta contine
un JTabbedPane cu 4 taburi:
	- primul este legat de user-ul logat, cu 3 butoane (inchidere aplicatie, restart
aplicatie - apare fereastra de login cu informatiile userului, adica nume si parola,
si delogare user)
	- al doilea este o pagina de fisiere, similara cu UploadFiles, cu 3 butoane de
incarcare, un buton de realizare a gestiunii si un buton de afisare a fisierului
out.txt (in caz ca se doreste inlocuirea unui fisier)
	- al treilea este o pagina legata de produse, unde am un JList unde afisez
lista de produse, butoane de adaugare / editare / stergere / cautare produs si 
mai multe JTextField-uri folosite la adaugarea / stergerea / editatea produselor,
plus JLabel-uri care vor afisa mesaje corespunzatoare JTextField-urilor completate
de user si de actiunile intreprinse de acesta + buton de instructiuni
	- al patrulea - pagina de statistica a magazinelor, folosind un JPanel pentru
magazinul si factura cu cele mai mari costuri per total si 2 JListuri pentru
magazinele cu cele mai mari costuri pe tari si categorii

	5) DateProdus - fereastra ce afiseaza lista de magazine in care se gaseste 
un produs cautat (prin apasarea butonului cauta produs din WelcomePage - tabul 3)

	6) Main - clasa in care este realizata gestiunea produselor din magazine

	7) FileParsing - clasa in care am metode de parsare a fisierelor si construire
a structurilor de date folosite in realizarea gestiunii

	8) Produs - clasa ceruta in enuntul temei

	9) Factura - clasa ceruta in enuntul temei + implementeaza Comparable pentru
sortarea in privinta gestiunii

	10) IMagazin - interfata ceruta in enuntul temei

	11) Magazin + clasele mostenitoare - cele cerute in enunt

	12) Gestiune - aici realizez toString-ul care a afisat in out.txt

	13) Instructions - clasa GUI (fereastra) ce descrie instructiunile pentru
pagina de produse

	P.S. Am folosit IntelliJ deoarece este un IDE de mare ajutor (autocomplete,
vezi ce metode au fost folosite sau nu in cadrul proiectului, autosugestie) +
imi place interfata acestuia, fiind obisnuit sa lucrez cu AndroidStudio, care
e derivat din IntelliJ
	