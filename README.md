role uzivatleu, sprong security
ctenar hled aknihy viid ritng vii jeslti jeknih apujcena le nveidi kym

pridat elastic search, nejaky timer pridava do indexu knihy z d. samostant aplikace? soucasti stystemu?


knihovni vidi knihy a akym je puejcna a vsehcny uzivatele a ejjich knihy

pujcka knihy dela knihvnik  vyzbere uzivatle e apa pak vyber eknihy a ty mu pujci

vraceni kniyky

uzivatel se muze podivat jaky knizky ma pujceny, senam knih, kter ejsou pujcene filtr

filtrovnai knih podle pujceni, raitngu atd, jak udelat ui?

spring mvc nebo vystavit rest

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

//todo multuthtaing timer a colvnai notfy musi oetrit aby se nestal rpoblem s vlkny, immutabel jak to vyrsti
databaze atd

//todo vsude v entitach Date

//todo vsude strib buildery u to string

test na timer, soring ev tools, h2 console

multuthreaing kvuli temru muzou bejt entity immutable? hibenrat stjnee tam dava proxies,
pridt synchronised? kdy muze nstast kolize? kdy se muze pracovt v jendu chvili s s jednou enittiu
nebo spirng componentuou atd? jk vyresit deadlock straviton atd?




db on exit false k cemu to je?

polymorphismus nejen knihy le i casopisy atd
misto jap repository napr enotty amnage rpresisit atd

pridat u poujceni knih vytovreni ezpiraiton

priciintni lduhu kazdy den, opet timer? nebo ten smay timer?
npr timer se ssutit  a nekam ulozi datum poseldni psustnei itemru, pri dlasim spustnei acte
dautm poslendiho posustnei spocita kolik dni pulynulo, uplynulo, timer se posuti jedenkrat denne
porjede vsechny uzivatele a podiva se na vsechny knihy nebo jednoudseji podiva se na pujcene knihy a
pricte dluh?

ulzonei expirace pri pujcnei knih - udela expiraitons service, library jen zavola epxir service
stjen tak pri vraceni knih,
 a ntiifkactioan serivce muze iskat seznam dnesnich knih  abudoucnoch kni taky od exiriton service


rpi pujcovani knih se pusti expiraiton service a ta vyhledava v ezpir repository a pak uklada Expiraiton a zas dalsi kniha vyhleda
takze pro kazdou knihu se vyhleda  2* expiraion, to se upravi a zse ulozi
a dalsi knha hleda treba to samy expiraiton. Hibernate diky 1st level cache
nedotazuje DB pokazde, ale jen to taha z cache a pokud se to ulozi, tak jen do cache
a do DB se to promitne az na konci session. Udelat celou session v ramci 1 metody jde pomoci
transactional.
https://stackoverflow.com/questions/13531122/multiple-transactions-in-a-single-hibernate-session-with-spring?noredirect=1&lq=1
https://stackoverflow.com/questions/13531122/multiple-transactions-in-a-single-hibernate-session-with-spring
https://stackoverflow.com/questions/25709976/spring-boot-spring-data-how-are-hibernate-sessions-managed

je otazka na thread safe kdyz se pujcuje a vola se expiraitonservice a udela se session a zarovne
MCV spusti daalsi volani tak to muze byt v jinem vlakne? sesson nen i thread safe





https://stackoverflow.com/questions/11881479/how-do-i-update-an-entity-using-spring-data-jpa?rq=1
kdyz je metoda trasnacitonal tak se zmeny entity samy ukladaji na konci metody do DB?
Neni treba volat repository save?

//todo v enittach co s metou set List? mela by byt private, aby se list nemohl zmenit i list dkelarovan
final
ale hibenrat epotebuje mit getery a setery? nebo si to umi udelat reflexi sam? nebo muze prisutpovat i
k privatnimu seteru?

//pri vraceni knih a odebirani expiraton pokud jsou fuutr ebooks a toaday books prazdny tak vymaz
expiraiton
zkontorlvat v testu

//todo pridat tes u library system pri vraceni ze se avola remove na e ri service
//u pujcovani pridat do testu nastaveni data
//jaky format data?

u vracnei prid odebrani data pujceno na false

//test timeru, pripprofilu te tpsustnei kazdych x sekund, v oralnim prfioilu jendou denne

//toddo prjemenovat v expiraiton a v book future a today na neco srozumitlenejsiho napriklad expiraiotn a earlyNotificaitn nebo NotificOn Expiry a Notif InAdvance

podivat se jeslti funguje  1st evel cache hibernat epzpanout sql a dival se jestli uklada nekolikrat tu samou entitu nebo
na konci

vytvorit metodu main, ktera bude zvlast bude it spingovou kongfiguraci, vezme si enttiy atd a vygeneruje
sql skripty pro vytvoreni schemat v db. tyo skripty pak pustit gradle update pomoci liquibase rpi nasazeni na produkcu

pri deployi napred psutit grdle udpate a pak pri psuteni aplikace hibernate validate?

------------------
TIMER
1. pouzit @Scheduled(cron = "${cron.expression}")
nacist z app pro, por tes tjine nez pro produkci.
prepdokladam, ze timer se spusti  vnovem vlakne, to zavola epxir service, to bude se pripjivat
k repository, je repo thred safe? hibernate jak to dela kdzy se vola z vice vlaekn? jak se chova trnsasctional
ve vic vlaknemch?
connection pool?
nepromicha se neco?

jak otestovat timer?
spusti se test a s tim i context timer bude napr kazdy tri sekundy firovat,
bude se hledat v expir repository bude rapddnza nic, pusti se test expir integr
ta ulozi do epxir repository a v testu se zavola expir serivce chck expir a ta se poidvat do realnyu expr repo
a najde ale mezitim se pusti schduelrem taky metody expir service, bude konflikt
proto mit jenom unit test na expir service a zamockovanou expir repo
a mit jen jedn integracni test kterej bude mit realny expir repo, realnou expir service a mock emailu
a na pocatku se jen ulozyi do expir repo, pak test pocka a scheudler se sam vyapli
zavola epir service, ta zavola relanou expr repo, nacte, zavola email mock,
test to overi.
pro test bude v app properties cron experssion a pro prod bude jiy cron exproession



architkerura bud mt vedle sbe v resources vsechny pofily prod dev test nebo test dat do test resources
kdyz posuti gradle spring context proc nebere s main resources ale z test resources?

//todo refactor v testech persisovtavni obejtu, delegovat to na pomocnou em todu TesDtaG


//todo v tesetech uvest db do vychoziho stavu. do kadych teardown ale mezi jednotlivymi nebo az po vsech testech?

//rozlisit unit testy a integracni testy nejak v gradlu, napr pustit gradlem jen unit testy a integracni
pomoci profulu nebo group?
