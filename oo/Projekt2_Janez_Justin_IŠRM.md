# Scroobly

[https://www.scroobly.com/](https://www.scroobly.com/)

Scroobly omogoča animiranje čačk v realnem času. Uporabnik za animiranje uporablja svojo mimiko in kretnje. Kamera igra vlogo vhodne naprave. Uporabnikovi premiki se preko strojno naučenih modelov v realnem času pretvorijo v animacijo izbrane čačke. Tako uporabnik postane umetnik brez posebnih orodij, ki bi bila potrebna za izdelavo takih animiacj.

Scroobly uporablja TensorFlow-ov strojno naučen model PoseNet za zaznavanje kretenj in Facemesh za prepoznavanje premikov obraza. Ta modela podata 3D koordinate v prostoru, ki jih potem aplikacija uporabi za pravilen prikaz animacije.

Vmesnik je preprost in zasnovan za prijetno uporabo na telefonu in računalniku. Uporablja Material Design elemete.  
Ob prvem pristanku v spletno aplikacijo smo postavljeni pred prikupno čačko. Ob prvem pomiku miškinega koleščka nam aplikacija razkrije in opiše svoje delovanje. Iz začetne strani poženemo aplikacijo s klikom na gumb "Start". Ta uporabika skozi prikupne animacije, ki razložijo uporabo aplikacije popelje do uporabniškga vmesnika za pripravo animacij.
Po pregledu animacij smo postavljeni na zaslon, kjer se na sredini pojavi naša kamera. Naš obraz na kameri prekriva izbrana čačka, ki ves čas posnema našo mimiko in kretnje.  
Pod prikazom nas čakata gumb za snemanje in repertoar čačk. Možnost imamo tudi dodati novo čačko, ki jo narišemo sami v intuitivno zasnovanem oknu. Čačko povežemo na točke skeleta in obraza, kar apikacija potem uporabi za pravilno preslikovanje čačke na kamero.

Aplikacija ponuja možnost snemanja, rezanja in prenosa posnetka. S tem je uporabniku dana možnost, da ustvarja svoje kreacije, ki jih potem lahko tudi shrani in deli z ostalimi.

Komentar, ki bi ga podal na vmesnik je ta, da "start" in "next" gumbi niso dovolj dobro definirani. Mislim, da uporabnik, ki ni vajen ploskega oblikovalskega vzroca porabi preveč časa, da zazna gumb za zagon aplikacije.  
Samo delovanje je tekoče in v skladu s pričakovnaji. Opazil pa sem, da je zaznavanje kretenj včasih počasno in povzroči zaostanek animiacije za dejanskim premikom.

# Quick, Draw!

[https://quickdraw.withgoogle.com/](https://quickdraw.withgoogle.com/)

Qucik, Draw! je aplikacija, kjer računalnik ugiba kaj rišemo. Aplikacija je zasnovana kot enoigralska igra. Izvede se v šestih krogih. V vsakem krogu uporabnik riše v naprej podano besedo oz. stvar, računalnik pa ugiba kaj je uporabnik narisal, dokler ugib ni pravilen. Vsak krog traja 20 sekund.

Esenca aplikcije se skriva v strojnem učenju. Vsaka narisana risba pripomore k grajenju nevronske mreže, ki jo računalnik uporablja v ozadju za prepoznavanje risb. Več risb kot narišemo, točneje bo računalik razumel kaj se skriva na risbah. Za vhod v učni model je uporabljena tehnika prepoznavanja rokopisa.

Uporabniški vmesnik je pregleden in jedernat. Pisava uporabljena za glavne poudarke imitira ročno pisano besedilo, kar se motivno sklada z namenom aplikacje. Preostali tekstovni elementi uporabljajo preprosto in čisto pisavo. Vmesnik poleg sivih barv uporablja primarno rumeno in sekundarno zeleno barvo.  
Uporabniku je prikazano minimalno število gumbov, tako ni zmeden kako nadaljevati z uporabo aplikacije. Premike po aplikaciji spremljajo kratka in jederanata navodila.  
Ob končani igri se prikažejo naši narisani izdelki. S klikom na posamezno risbo je prikazana podrobnejša analiza tega, kaj je račuanlnik zaznal na risbi. Prikazane so besede za katere računalnik misli, da smo jih risali. Razporejene so po zaupanju, da so le-te bile risane. Poleg drugih besed nam je predstavljena tudi množica risb, ki so račuanlniku pomagale, da se je naučil kako izgledajo risbe danega pojma.

Na vmesnik in delovanje nimam komentarja. Predlagal bi edino opozorilo, ki uporabnika spodbudi, da aplikacijo za boljšo izkušnjo odpre na telefonu (če jo je odprl na računalniku) saj je tam risanje lažje. Tudi pridobljeni podatki menim, da bi bili boljši na risbah narejenih na telefonu.

Mislim, da ima projekt veliko potenciala za uporabo izven eksperimenta. Ena izmed možnih uporab bi bila učenje risanja za ljudi, ki so motorično manj izurjeni. Druga ideja, ki jo imam, je račuanlniško generirane risbe. Na podlagi zbranih podatkov bi moral račuanlnik biti sposoben zgenerirati nove risbe, mogoče celo z združevanjem večih izbranih besed v eno risbo.

# Infinite Bad Guy

[https://billie.withyoutube.com/](https://billie.withyoutube.com/)

Infinite Bad Guy omogoča sinhronizirano predvajanje in premikanje med več deset tisočih Youtube videoposnetki, ki vsebujejo različne izvedbe pesmi Bad Guy ameriške pevke Billie Eilish. Tako aplikacija omogoča virtualno sodelovanje poljubno mnogo avtorjev med sabo.  
Z menjavami izvedb uporabnik dobi vsakič drugačno izkušnjo pesmi. Hkrati pa lahko odkrije nove izvajalce, ki bi mu bilo potencialno všeč.

Aplikacija uporablja strojno učenje za pretvorbo vseh izvedb v enak tempo, za tekoče prehajanje med izvedbami. Vsak videoposnetek je tudi označen s kategorijami katerim ustreza (npr. trumphet, acustic, quartet...), po katerih lahko uporabnik tudi razvršča videoposnetke.

Vmesnik daje kibernetski efekt. Zgrajen je na neon zeleni in pa črni barvi. V odzadju je prikazano "vesolje" vseh videov, ki so zbrani v aplikaciji, ki se premikajo ob preletu z miško.  
Vmesnik se drži oblike klasičnega video predvajalninka; v centru pozornosti je predvajana vsebina - predvajani video skupaj z dvema drugima videa iste oznake, na katera je mogoče preklopiti iz trenutno predvajanega videa. Pod predvajano vsebino je predvajaj/ustavi gumb in časovni drsink, ki prikazuje predvajani/preostali čas glasbe.
Nad časovnim drsnikom teče seznam oznak videov med katerimi lahko uporabnik izbira in gumb za izbor naključne oznake. Pod časovnim drsnikom se nahaja "^" gumb, ki ponudi večji izbor video oznak.  
Ustavljen predvajalnik omogoča pregled statistike in zgodovino ogledanih videov.

Aplikacijo je delovala zgolj na računalniku, tako da se vsi komentarji nanašajo na to izvedbo.  
Vmesnik se mi zdi preveč natlačen. Video "vesolje" daje občutek nereda in jemlje pozornost, ker se sličice premikajo same od sebe. Ob menjavi oznake se dogaja preveč stvari naenkrat, kar uporabnika zmede. Nepredvajana videa bi lahko bila postavljena vertikalno namesto horizontalno, tako bi imel predvajani video več prostora in posledično večji poudarek.  
Pogrešam tudi drsink za glasnost predvajanja.
