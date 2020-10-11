#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>

    /* Gledamo, ce obstaja i pri katerem bo beli v vsakem primeru lahko zmagal, neglede na to kako bo igral crni ( i je stevilo kamnov, ki jih vzame beli)
    * Igra se zaklucji, ko je sestevk vseh elementov na novim elementov enak n.
    * lahko simuliramo igro za i = 2, n =6 in k =3:
    * B 2
    * C 1          2    3  
    * B 1    2 3/  1 2/ 1/
    * C 1 2/ 1/    1/
    * B 1/
    * 
    * za vsako drevo (i) lahko narisemo podrevo v kateri simuliramo igro za tak i.
    * zgoraj je primer ko je i =2
    * primer, ko je i = 3
    * B 3
    * C 1    2 3/
    * B 1 2/ 1/
    * C 1/
    * 
    * opzaimo, da za i = 3 lahko crni zakluci igro tako da igra 3 in zmaga
    * 
    * za i=2 pa crni ne more zakljuciti igre takoj. Gledamo za vsak primer, ki ga lahko igra crni posebej(i je od sedaj naprej stevio kamnov, ki jih vzame crni)
    * Ce je i=1 izgleda igra tako:
    * C 1
    * B 1    2 3/
    * C 1 2/ 1/ 
    * B 1/
    * vidimo, da obstaja poteza za belega, kjer bo za ziher zmagal (iBel=3)
    * pri i=2
    * C 2
    * B 1 2/
    * C 1/
    * bel igra 2 in je konec
    * podobno za i=3
    * 
    * SKLEP: Vsaki potezi lahko priredimo min(n,k) podiger, v katerih gledamo ce je za vsako crno potezo mogoce da beli zmaga
    * oz. ce obravnavamo za vsako vrstico posebej:
    * pri B vrsticah gledamo, ce je obstaja bela poteza, pri kateri bo neglede na to kaj igra crni zmagala.
    * pri C vrsticah gledamo, ce lahko crni zakljuci igro.
    * 
    * Sklep => koda: Za vsako potezo ki jo naredi belo gledamo vse mozne podigre, in preverimo, ce obstaja igra, kjer beli zmaga, ce se pravilno odloca.
    * za vsako potezo ki jo naredi crni gledamo, ce zakljuci igro. Ce jo zakljuci pomeni, da je prejsnja poteza belega neuporabna => gledamo naslednjo mozno belo potezo.
    */

// vrne true, ce trenutno igro ob pravilnih potezah zmaga beli
// false, ce crni lahko zakljuci igro
bool igra(int n, int k, int poteza){
    // ce je pri naslednji potezi n == 0 pomeni, da je oseba, ki je bila prejsno rundo na potezi zmagala
    if(n == 0){ 
        if(poteza%2 == 0){ // zmagal je beli
            return true;
        }else{ // zmagal je crni
            return false;
        }
    }

    bool izid; 
    if(poteza%2 == 1){ // na potezi je beli
        izid = false;
        for(int i=1; i <= k && i <= n; i++){
            izid = izid || igra(n-i, k, poteza+1); // ce je vsaj ena izmed podiger prejsnje poteze usepsna vrni true
        }
        return izid;
    }
    // na potezi je crni
    izid = true;
    for(int i=1; i <= k && i <= n; i++){
        izid = izid && igra(n-i, k, poteza+1); // ce vsaj v eni izmed podiger prejsenje poteze lahko zmaga crni vrni false 
    }
    return izid;

}

int main(){
    int n, k;
    scanf("%d %d", &n, &k);
    if(igra(n,k,1)) printf("%d", 1);
    else printf("%d", 0);

}