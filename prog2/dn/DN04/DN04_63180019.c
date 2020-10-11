#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>

int main() {

    //vzorcni niz
    static char vzorcniNiz[101];
    scanf("%s\n", vzorcniNiz);
    int stTestnihNizov;
    scanf("%d", &stTestnihNizov);
    //printf("%d", stTestnihNizov);

    //vsak niz vsebuje vsaj 1 in najvec 100 znakov; vsi iz malih crk
    char niz[101];  //1 za \0
    bool seUjemata = false;
    int stUjemajocih = 0;  //to iscemo
    int dolzinaNiza = 0;
    bool vprasaj = false;
    bool zvezdica = false;

    int dolzinaVzorca = 0;
    while (*(vzorcniNiz+dolzinaVzorca) != '\0') {  //!!!!!
        dolzinaVzorca++;
    }

    int stIstihZnakov = 0;
    char* pVzorec = vzorcniNiz;
    for (int i = 0; i < stTestnihNizov; i++) {
    
        scanf("%s\n", niz);
        dolzinaNiza = strlen(niz);
        char* pNiz = niz;

        //grem preverjat, ce je prebrani niz podoben nasemu
        while (*pNiz != '\0') {
            //printf("%c \n", *pNiz);
            if (dolzinaNiza < dolzinaVzorca) {
                seUjemata = false;
                break;
            }
            if (*pNiz == *pVzorec) {
                pNiz++;
                pVzorec++;
                stIstihZnakov++;
            }
            else if (*pVzorec == '?') {
                vprasaj = true;
                pNiz++;
                pVzorec++;
                stIstihZnakov++;
            }
            else if (*pVzorec == '*') {
            //potem je dolzina niza lahko vecja od dolzine vzorcnega niza
                zvezdica = true;
                while (*niz != '\0' && *pNiz != *pVzorec) {
                    pNiz++;
                    pVzorec++;
                }
            }
            else {
                seUjemata = false;
                break;
            }
        }
        //printf("%d \n", stIstihZnakov);
        if (dolzinaNiza < dolzinaVzorca) {
            seUjemata = false;
        } 
        if (dolzinaNiza == dolzinaVzorca && zvezdica == false && vprasaj == false &&
                stIstihZnakov == dolzinaVzorca) {
            //seUjemata = true;
            stUjemajocih++;
        }
        else if (zvezdica == false && vprasaj == true && dolzinaNiza == dolzinaVzorca && 
                stIstihZnakov == dolzinaVzorca) {
            //seUjemata = true;
            stUjemajocih++;
        }    
       /*  if (seUjemata) {
            stUjemajocih++;
        } */
        zvezdica = false;
        vprasaj = false;
        seUjemata = false;
        stIstihZnakov = 0;
        pVzorec = vzorcniNiz;

    }
 
    printf("%d\n", stUjemajocih);
    //printf("%s \n", vzorcniNiz);
    return 0;
}