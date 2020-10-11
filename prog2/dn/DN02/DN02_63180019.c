#include <stdio.h>

int main() {
    int ukaz = getchar() - '0';  //1 ali 2
    //ukaz = 1 -> izpisi rezultat kodiranja
    //ukaz = 2 -> izpisi rezultat dekodiranja vhodnega niza
    getchar();
    int znak = getchar();
    int dolzinaNizaIstihZnakov = 1;
    int novZnak = 0;
    switch(ukaz) {
        case 1:
            while(znak != ('\n')) {
                novZnak = getchar();
                if (novZnak == znak) {
                    //stejem dolzino niza istega znaka
                    while (novZnak == znak) {
                        dolzinaNizaIstihZnakov++;
                        novZnak = getchar();
                    }
                    if (znak == ('#')) {
                        printf("##%d#", dolzinaNizaIstihZnakov);
                    }
                    else {
                        if (dolzinaNizaIstihZnakov <= 4) {
                            for (int i = 1; i <= dolzinaNizaIstihZnakov; i++) {
                                printf("%c", znak);
                            }
                        }
                        else if (dolzinaNizaIstihZnakov >= 5) {
                            printf("#%c%d#", znak, dolzinaNizaIstihZnakov);
                        }
                    }
                }
                else {
                    if (znak == '#') {
                        printf("##%d#", dolzinaNizaIstihZnakov);
                    }
                    else {
                        printf("%c", znak);
                    }    
                }
                znak = novZnak;  //ker je ze v prejsnjem while prebran
                dolzinaNizaIstihZnakov = 1;
            }
            printf("\n");
            break;
       // case 2:

         //   break; 
    }

}