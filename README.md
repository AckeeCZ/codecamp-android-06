# Domácí úkol

Domaci ukol neni povinny a je spis dobry trenink pro vas, nebudu nejak presne definovat podminky splneni. Ale ten ukol dost dobre simuluje realnou situaci, podobnou funkcionalitu implementujeme skoro v kazde apce. Zadani kombinuje v sobe praci s databazi a networking.

Cilem ukolu je vytvoreni aplikace, ktera bude mit dve obrazovky:

1. Seznam filmu, ktery bude zobrazen hned po startu apky.
2. Detail filmu, na ktery se da prejit kliknutim na polozku v seznamu.

Aplikace musi:

1. Pri startu stahnout z API seznam filmu a ulozit ten seznam do databaze. 
2. Pri kazdem vytvoreni obrazovky (aktivity) s seznamem vytahnout filmy z databaze a zobrazit uzivateli.
3. Pri prechodu na detail stahnout detail z API a rovnou zobrazit uzivateli.

(Poznamka: v pripade obrazovky s seznamem API se zavola jen jednou, pri prvnim vytvoreni, pak se ta data ziskavaji jen z databaze. V pripade detailu pracujete rovnou s API. Ta podminka ukazuje rozdil tech dvou pristupu.)

Data k zobrazeni:

1. Kazda polozka seznamu by mela zobrazovat nazev filmu, obrazek a jeho hodnoceni.
2. Detail by mel zobrazovat navic popis filmu, zanr a mozna dalsi informace podle vase chuti.

Design je uplne na vas.

API:

+ Pouzivame https://www.themoviedb.org, je to realna databaze filmu, ktera ma public API. Pro komunikaci s API potrebujete ale specialni klic. K tomu se potrebujete tam zaregistrovat a pozadat o klic. Ale zabere to 5 minut a je to zdarma pro nekomercni ucely.

+ Dokumentace je na Apiary. Je tam spousta ruznych endpointu, ale vy potrebujete dva:
  + Stahnout seznam filmu: http://docs.themoviedb.apiary.io/#reference/movies/moviepopular/get (muzete pouzit i jiny, je tam nekolik)
  + Stahnout detail filmu: http://docs.themoviedb.apiary.io/#reference/movies/movieid/get
  
Mozne rozsireni ukolu:
+ Pridani strankovani do seznamu (k tomu se pouziva atribut "page". Viz apiary)
+ Moznost pridat film do "oblibenych" (s ukladanim do databaze po uspesne odpovedi ze serveru) a nejak to ukazovat v seznamu/detailu. K tomu ale potrebujete implementovat i autentifikaci.
  + Informace: https://www.themoviedb.org/documentation/api/sessions
  + Autentifikace: http://docs.themoviedb.apiary.io/#reference/authentication/authenticationtokennew/get
  + Endpoint: http://docs.themoviedb.apiary.io/#reference/account/accountidfavorite/post
