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
