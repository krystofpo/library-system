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
https://vladmihalcea.com/the-best-way-to-use-entity-inheritance-with-jpa-and-hibernate/
https://stackoverflow.com/questions/35226891/how-to-design-jpa-polymorphic-relationships-in-java
https://books.google.cz/books?id=JohFDAAAQBAJ&pg=PA99&lpg=PA99&dq=polymorphism+persistence+spring&source=bl&ots=3jvGctxodL&sig=0PMCHnzgZDyfeGBaYbry4NbSjzE&hl=en&sa=X&ved=2ahUKEwj-r9Dh2dvdAhWHKFAKHeFIDjwQ6AEwCHoECAAQAQ#v=onepage&q=polymorphism%20persistence%20spring&f=false
https://medium.com/@nagillavenkatesh1234/design-patterns-for-jpa-and-hibernate-aca40fb19e2c


//todo system hodnoceni


todo upozorneni na konec vypujcky

todo borrow book vraci membera nebo void? objekt memeber se upravi metodou

//todo je potrbea u knihy mit boorrowed boolena a bororowed by user neni tduplicivta?
kdyz me zajimaji knihy k pujcnei tak staci tam kde borowed by je null

//integracni test udelat pujceni, vraceni, pujceni a zokotorlvat stav db a v unit testy zkonorlvat i stav objetku member  abook , v interaci staci jen db

pujcneo d kdyz pridt do testu jak se nastavi pujceno do nejaky proifle porerty por test nastaiv tna krakto a
overit ze se posle mail uzivatleum udelat integracni test, zamockovat posilani mailu a nechat test bezet par minut
a pocakt ze se psusti timer a vovzla se metda poslan mail, v tom testu se nastavi pujceno do na par sekund a interval kontorly se nastavi na par sekund a okud to vzprsi dirve nez za xz sekudnt tak se posle


spirng scheldueler
v kokamziku pujceni se vytovir timery pro kazdkou pujcenou jednotku
timer dostane refernci na membera, kvuli emailu, nebo jenom email (nekdo uprednostnuje poslani dopisu, takze notikace mue bud
posalt email nebo poslat odpis nebo oboje)
takze referenci na membera
kdy se ma iter supsutiti, ted plus pujocni doba ta se vezme z knihonvi jednotky
jak vyresti : na produkci chci poslat uziateli jen email jednou denne kde se vzpisou ten den prochazejici jednotky
ale v testu chci otestovat ze upozorneni se spustio v cas spusteni testu plus treba 5 sekund
googlit how to tst timers integraiton test

spring quartz

knihovni jendotka ma vypujcni dobu ve dnech, hondota je v app properties, v konsturktoru  book je @value, takze aplikace bezi
a kdyz chtjei zkratit vzpujcni dobu, jen zmeni applproperties.
najit kdy se ctou app prop vpris tstau apliakce nebo kdyz je hodnota opravdu ctena?
kdyz se egisturje do db nova kniha, tak se vytavari nova entita kniha a ctes se ze souboru app prop? muzu okouset za behu klidne.
udelam jakro a upravim a behu ap prorp a pak vytovirm noovo uknihu a jestli se hodnoa nacte ze souboru

kdyz se vola emtod abooow book tak se vmze dnes, pricte se ondota z book respektie je vyhondejsi aby se hodntoa value cetla
proi opujcovani knihy ne pri conskturkoru, ziska se datum, zaokralouhl ise na den, z db se nacte entita sposutec,
a to tak, ze ze entita ma id a ma index na slpupci kterej obsahuje stirng datum se dny, , najde se podle honoty ten obekt spostec
objetk spustec pak obshauje list toho, co se m spustit, to je nejaky objekt.
do listu se prida novy objekt, ulozi se

pak se vyzpocte doba kdy se posle upozroneni s predstihem, pokud je s predstihem mensi nez dnes tak nic pokud je v buducnu alepson zitra
tak se vyztovir uplne stjne.

takze v db budou listy a ty byudou obsahovt objekty obsahujici uadje. mozna jeste kdyz se vrati ten list objetktu obshaujci upozotneni,
tak se porjdou vsechna upozorneni a vyberou se jen ta, ktera obsajuhu librar membera, a do nej se prida upozorneni
zas to je list a do nej se prida dalsi zaznam.

jeste se nacte z app roperties ten predstih, a jskym se ma pslat mail pred vyprsenim

potom bude psiorng quart scheudlera persisovny v db a ten se psusit jendo  denne, ale to kdyz se psustu bude nejak parmaetirzovani
a v testu pujde urzict, ze se to psusti napr za dest sekudn od  ted
ten timer pootm spocia jaky je dnes den a najde podle toho stirungu  db ten objekt pro ten den  a pak vezme list vsech pozorneni
kazdy elemte je pro jednoho uzivatel a pro kazdy obejt posle upozorneni tomu uzivateli. potom vymaze z db dnesni den a a vymaze ten list obejktu.

v tstu se nastvi napr ze vypucni odba je 2 dny a predstih je 1 den cili se vytovir obejt s datem dnes, pak se nastavi quat aby se spsutil
za deesetk sekund a mel by ted razjet ten proces a v tom procesu bde mock email sluzby
jen se overi, ze se to zavolalo

https://stackoverflow.com/questions/48940203/spring-quartz-triggering-job-programatically

http://forum.spring.io/forum/spring-projects/container/44781-spring-s-quartz-integration-how-to-schedule-new-jobs-programmatically


timer ktery se spopusti denne napred zpracuje dnes zaicnajici zpozdne, posle mail, pak zpracuje
zpozdne ktere se blizi, osle mail, ak muze dostat jede uzivate l dva ruzne maily v jeden den, je to lepsi
nez kdby dostal jeden souhrnyy mail, je to prehlednejsi

posilac mailu dostanu uz zformatovana data, komu, predmet, text zpravy, to by mel udelat mail
formater, mail fomater na nem budou dve meotdy
jedn abudouci zpozdne a druha dnesni zpozdne podle oho udela text predmetu a text zpravy

tabluka zpozdneho, kde je id, sloupec obsahujici datum zaokrouhleno na dny bud string 2018-09-28 nebo Date kde je cas 00:00:00, jak prevadi hibernate Date javovsky na Date h2 specific?  a potom
sloupec typu Book projde dnes a slouepc typu Book predstih. Jde o to aby se kdyz vyberu radek teto entity
a dam get list books aby se sql neprojizdely vsechny knihy ale jen ten vysek kde je id z tabu;lky Zpozdne,
cili pres join table?

nebo jak funguje join kdyz to neni one to one ? bude to jen unidireitonal kniha nebude mit referenci na
Zpozdne,

udelat group by uz v db? jak se del gorup by pomoci jpa repository?

jak tudelat baych nemusel drzet vpoameti celej senzam vsech knih dnes porlsych a iterva t apor kazdou
zpracovat ale abych dotahl jednu z databae zpracoval, vymzal refeneci  vjave tim uvolnim pamet
a zas dalsi z db? lazy fetchin? ale i tak kdyz je refeence v jave, envim

nechat si vpasta sql dotaz co dela hubernat udelat lazy fetchung a podvat se do db schematu jak jsou udelany
PK a FK

je treba pustit test EmailSenderSpec s JVM argumenty -Dspring.mail.username=xxxx -Dspring.mail.password=xxxx -Dspring.mail.to=<REALNA-EMAIL-ADRESA>
kde xxx jsou realne prihlasovaci udaje k gmail uctu

