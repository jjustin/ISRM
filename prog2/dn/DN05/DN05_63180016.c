#include <stdio.h>
#include <stdint.h>
#include <string.h>
/*
Pozdravljeni!

V sledeci nalogi bom obravnaval prevajanje c programa. 
Najprej bi se rad zahvalil svoji druzini, prijateljem, še posebej Gregorju Brantusi, ki mi je dve uri šepetal na uho kaj delam narobe.
Ne bi mi uspelo tudi brez podpore alkohola in trdih drog. Kot vedno pa je bila na čelu uspeh marihuana. Kaj vec ne upam reci.

V tej nalogi sem spoznal veliko novega, razsiril svoja obzorja in tako zrastel kot clovek. 

Ta uboga naloga me je prikrajšala velike kolicine spanca.

                                                        _________________________________\                                       Z             
      _____|~~\_____      _____________                                                   \                            Z                   
  _-~               \    |    \                                                            \            .,.,        z           
  _-    | )     \    |__/   \   \                                                           \         (((((())    z             
  _-         )   |   |  |     \  \                                                           >       ((('_  _`) '               
  _-    | )     /    |--|      |  |                                                         /        ((G   \ |)                 
 __-_______________ /__/_______|  |_________                                               /        (((`   " ,                  
(                |----         |  |                                                       /          .((\.:~:          .--------------.    
 `---------------'--\\\\      .`--'                     _________________________________/           __.| `"'.__      | \              |     
                              `||||                                                     /         .~~   `---'   ~.    |  .             :     
                                                                                                 /                `   |   `-.__________)     
                                                                                                |             ~       |  :             :   
                                                                                                |                     |  :  |              
                                                                                                |    _                |     |   [ ##   :   
                                                                                                \    ~~-.            |  ,   oo_______.'   
                                                                                                `_   ( \) _____/~~~~ `--___              
                                                                                                | ~`-)  ) `-.   `---   ( - a:f -         
                                                                                                |   '///`  | `-.                         
                                                                                                |     | |  |    `-.                      
                                                                                                |     | |  |       `-.                   
                                                                                                |     | |\ |                             
                                                                                                |     | | \|                             
                                                                                                `-.  | |  |                             
                                                                                                    `-| '

Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.

Moram reci da mi je studij na fakulteti za racunalnistvo in informatiko na Univerzi v Ljubljani zares vsec. Ce bi se ponovno odlocal za studij bi definitivno izbral enako. Mogoce bi izbral vss program, saj imam na uni programu obcutek kot da sem na pocitnicah. Vedno nekaj oddajamo, sef me klice, ne da mi miru. Upam na vecno sreco in veselje. AMEN. Kakor je rekel ze kekec: "V eni roki nosim sonce, v drugi roki zlati smeh", pa ga je se vedno vseeno pojedel bedanec v upanju da bo ujel novega bivola za jahanje po alpskih klancinah, v upanju da bo vsec teti Pehti, vendar ga je ta vseeno zavracala in se ozirala po sosedovemu fantu Kosobrinu.
Izrazil bi tudi svoje prepricanje o tem kako nepotreben je esej na maturi iz slovensine. TO JE ZLOCIN! Upam da se Vi, Gospod popravljalec te naloge, zmozni kontatirati drzavni izpitni center, ki nas prisiljujeo v branje povsem nerazuljivih knjig, ki jih:
PRVIC) NE razumemo
DRUGIC) NOCEMO brati
TRETJIC) SO KR NEKI
HVALA

in jim povedali, da hocemo zanimive in uporabne knjige, ki jih je mogoce interpretirati na vec kot en sam nacin. HVala

Pa dober tek!
*/
int main(int argc, char const *argv[]){
    char next = '\0';
    char str[10000];

    while(!feof(stdin)){
        char z;
        if(next != '\0'){ z = next; next = '\0';}
        else z = getchar();

        if(z == '/'){
            next = getchar();
            if(next == '/'){
                while(getchar() != '\n') ;
                next = '\0';
                continue;
            }
            if(next == '*'){
                z= getchar();
                next = getchar();
                while(z != '*' || next != '/'){
                    z = next;
                    next = getchar();
                }
                next = '\0';
                continue;
            }
        }
        // Locila
        if(z == '(' ||z == ')' || z == '{' || z == '}' || z == ',' || z == ';' ||z == '[' || z == ']') printf("locilo[%c]\n", z);
        // Niz
        else if(z == '"'){
            printf("niz[");
            z= getchar();
            while(z != '"'){
                putchar(z);
                z = getchar();
            }
            printf("]\n");
        // Stevilo
        } else if( '0' <=z&&z<= '9'){
            int a = z - '0';
            z = getchar();
            while('0'<=z&&z<='9'){
                a = a*10 + (z - '0');
                z = getchar();
            }
            next = z;
            printf("stevilo[%d]\n", a);
        // Operatorji
        } else if(z=='+' || z=='-' || z=='*' || z=='/' || z=='=' || z=='>' || z=='<' || z=='+' || z=='-' || z=='*' || z=='/' || z=='=' || z=='>' || z == '<'){
            printf("operator[%c", z);
            if(next=='\0')
                next = getchar();
            if(next == '=') {
                printf("%c]\n", next);
                next = '\0';
            }
            else printf("]\n");
        // Imena / Rezerviranke
        } else if (('A' <= z && z <= 'Z') || z=='_' ||  ('a' <= z && z <= 'z') || ('0' <=z && z<= '9')) {
            str[0] = z;
            int i = 1;
            z = getchar();
            while(('A' <= z && z <= 'Z') || z=='_' ||  ('a' <= z && z <= 'z') || ('0' <=z && z<= '9')){
                str[i] = z;
                z = getchar();
                i++;
            }
            next = z;
            str[i] = '\0';
            if(strcmp("char", str) == 0 || strcmp("else", str) == 0 || strcmp("for", str) == 0 || strcmp("if", str) == 0 || strcmp("int", str) == 0 || strcmp("return", str) == 0 || strcmp("while", str) == 0)
                printf("rezerviranka[%s]\n", str);
            else printf("ime[%s]\n", str);
        } 
    }
    return 0;
}
