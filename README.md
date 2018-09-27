role uzivatleu, sprong security
ctenar hled aknihy viid ritng vii jeslti jeknih apujcena le nveidi kym

knihovni vidi knihy a akym je puejcna a vsehcny uzivatele a ejjich knihy

pujcka knihy dela knihvnik  vyzbere uzivatle e apa pak vyber eknihy a ty mu pujci

vraceni kniyky

uzivatel se muze podivat jaky knizky ma pujceny, senam knih, kter ejsou pujcene filtr

filtrovnai knih podle pujceni, raitngu atd, jak udelat ui?

zacit se soring seurity, podivat se naspring webflow

nap ocatku musi byt uziatel vytvoreni?

uzivatel admin ma pravo vvytorit uzivatel kniovnik  uzivatel ctenar.

uzivatel se zologuje a


zatim be rpsing security

//todo pri pujcovnai se overi jestli uzivatel nema propadle clenstvi, kdyz ano vyahdoi se vyjimka,
//todo jak se to preda do View?


nemel jsem anotace many to one, hubernate hazel cchybu

opridat one to many atd anotace, kdyz entita ma odkaz na jinou entitu

https://docs.oracle.com/javaee/6/tutorial/doc/bnbqa.html
u many to one je vlastnici strana vzdy many. a ta vlastnena musi mit mappedBy, vlastnici ne.

delam integracni test se srig contextme, repository.
@spring boot test, ale potom musim sputit test pomoci spring runner. jde tpustit spock se
spring runner?
neni treba, stacilo prida tdo dependency spring spock a test ve spocku anotovat psirgbntotest a context se da autowirivoat


chyba rpri ukladani knih neni ulozen autor, takze metoda pujcni knh musi napred ullozit autotra
pak persistovanyho autora dat do knih, pak ulozit knihu a library meember a teprve pak ulzi tmmebere do
knih  aknhy do meberea? v realu u vsichni budou persiotvani kdyz se zavola metoda borrow protoze na weu se vybira jen
z ulozenych knih a clenu.
pridat teda do testu save? nebo to pridat do metody borrow? nebo pridat do borrow overeni ze jsou vsechny enitty persisovaten?
nebo jde udelat persistovani pomoci cascade?

cascade neni k nicemu, nefunugje tak, ze by se do session pridala child entita na zaklade attachovani
parent entity, cascading slouzi kdyz uz jsou jsou paretna i chidl entity attachovany v sesison, tj persistvoanya
a delaji se zmeny.

v relau to bude tka, ze bude strnaka na pridani knihy a strnaka na pridani library meneber,
takze kdyz se pujcuje kniha z db se vytahnout uz persisovany entity. proot ojse pridla do testu
napred ulzoeni do db a tepvrv epotom volani metody pujci tknihy.

dalsi chyba lazy inti ecxception no session. musim pridat @transcitonal nad metodu kde se ctou entity. staci do borrow nebo
i do testu?

je nedke ve sprigbn bootu treba pridat enabel transcitons?

prdat namd many to ne lazy fethcing, pri dotazu na ctenare se nanathujou vsehcny pujcnee knihy hend, ale az kdyz je poreba
potom je ale potreba  to delat v ramci session, hibernat to dothane a session se udel apomoci trancitonal

pridla sem transcitonal nad tridu s testem a nad metodu borrow a test funguje


//todo rozsiritelnost, nejen knihy, ale i casopisy, cd, spolecny interface nebo abstraktni trida


//todo system hodnoceni


todo upozorneni na konec vypujcky

todo borrow book vraci membera nebo void? objekt memeber se upravi metodou

//todo je potrbea u knihy mit boorrowed boolena a bororowed by user neni tduplicivta?
kdyz me zajimaji knihy k pujcnei tak staci tam kde borowed by je null

//integracni test udelat pujceni, vraceni, pujceni a zokotorlvat stav db a v unit testy zkonorlvat i stav objetku member  abook , v interaci staci jen db

pujcneo d kdyz pridt do testu jak se nastavi pujceno do nejaky proifle porerty por test nastaiv tna krakto a
overit ze se posle mail uzivatleum udelat integracni test, zamockovat posilani mailu a nechat test bezet par minut
a pocakt ze se psusti timer a vovzla se metda poslan mail, v tom testu se nastavi pujceno do na par sekund a interval kontorly se nastavi na par sekund a okud to vzprsi dirve nez za xz sekudnt tak se posle