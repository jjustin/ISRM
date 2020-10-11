#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>

int main(int argc, char const *argv[])
{
    // brei stevilo elementov na polju
    int n;
    scanf("%d", &n);
    
    // naredi tabelo z elementi polja dolzine n in globino 3
    int **polje = (int**)malloc(n*sizeof(int*));
    for(int i = 0; i < n;i++)
        polje[i] = (int*)malloc(3*sizeof(int));

    // preberi tabelo emlementov polja.
    for(int i = 0; i < n;i++){
        //printf("i:%d ", i);
        scanf("%d %d %d\n", &polje[i][0], &polje[i][1], &polje[i][2]);
        //printf("(%d, %d) %d\n", polje[i][0], polje[i][1], polje[i][2]);
    }
    
    // preberi stevilo potez, ki jih opravi kaca
    int stPotez;
    scanf("%d", &stPotez);

    // Ustvari tabelo z zgodovino kace dolzine stPotez in globino 2 -(x,y) koordinate kace
    int **kaca = (int**)malloc(stPotez*sizeof(int*));
    for(int i = 0; i < stPotez;i++)
        kaca[i] = (int*)malloc(2*sizeof(int));

    int dolzina = 1; //dolzina kace
    int premikGor = 1; // 1 = premik gor, -1 premik dol, 0 stoj
    int premikDesno = 0; // 1 = premik desno, -1 premik levo, 0 stoj
    bool crash = false; // se je kaca zaletela?
    bool sadez = false; // je kaca zadnjo potezo pojedla sadez?
    for(int poteza = 1; poteza <= stPotez; poteza++){
        // ce je pojedla sadez se dolzina poveca za ena
        if(sadez == true){
            dolzina++;
            sadez = false;
        }

        int glavax =kaca[0][0] + premikDesno;
        int glavay =kaca[0][1] + premikGor;

        
        //pojdi cez vsa polje in glej, ce se je kaca zaletela
        for(int i = dolzina-1; i>0; i--){
            // preglej ce se prekriva
            if(kaca[i][0]==glavax&& kaca[i][1]==glavay && i != dolzina-1) crash = true;
            // pomakni za eno polje naprej
            kaca[i][0] = kaca[i-1][0]; 
            kaca[i][1] = kaca[i-1][1];
        }
        // izracunaj trenutno lokacijo glave
        kaca[0][0] += premikDesno;
        kaca[0][1] += premikGor;
        
        // JOOOOJ, kaca ni naredila vozniskega izpita, konec igre
        if(crash){
            dolzina = 0;
            break;
        }

        for(int i = 0; i<n; i++){
            // ce pride na polje naredi kar pise na polju
            if(kaca[0][0] == polje[i][0] && kaca[0][1] == polje[i][1]){
                int tmp;
                switch (polje[i][2])
                {
                    case 1:
                        sadez=true;
                        break;
                    case 2: //black magic
                        tmp = premikDesno;
                        premikDesno = -premikGor;
                        premikGor = tmp;
                        break;
                    case 3: //black magic #2
                        tmp = premikGor;
                        premikGor = -premikDesno;
                        premikDesno = tmp;
                        break;
                    default: // za vsak slucaj
                        printf("Err");
                        break;
                }
                break;
            }
        }
    }

    // for(int i =0; i < dolzina;i++){
    //     printf("(%d %d)\n", kaca[i][0], kaca[i][1]);
    // }

    printf("%d %d %d", dolzina, kaca[0][0], kaca[0][1]);
    return 0;
}
