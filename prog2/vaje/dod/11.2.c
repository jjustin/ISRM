#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>

typedef struct A A;
typedef struct B B;
typedef struct C C;
struct A {
    int p;
    B* b; 
};
struct B {
    char* q;
    C* c;
};

struct C {
    bool r;
    A* a;
    B* b; 
};

int izpisiC(C*, char*);
int izpisiB(B*, char*);
int izpisiA(A*, char*);

int izpisiC(C* c, char* cilj){
    if(c == NULL){
        sprintf(cilj,"NULL");
        return 4;
    }
    
    int l;
    *cilj = '{';
    cilj++;

    if(c->r){
        sprintf(cilj, "true");
        l =4;
    }else{
        sprintf(cilj, "false");
        l = 5;
    }
    cilj += l;

    // ", "
    sprintf(cilj, ", ");
    cilj += 2;

    int la = izpisiA(c->a, cilj);
    cilj += la;

    // ", "
    sprintf(cilj, ", ");
    cilj += 2;

    int lb = izpisiB(c->b, cilj);
    cilj += lb;

    *cilj = '}';

    return 1 + l + 2 + la + 2 + lb;
}


int izpisiB(B* b, char* cilj){
    if(b == NULL){
        sprintf(cilj, "NULL");
        return 4;
    }
    // "{"
    *cilj = '{';
    cilj++;

    //q
    char* q = b->q;
    sprintf(cilj,"%s", q);
    int l = (int)strlen(q);
    cilj += l;

    // ", "
    sprintf(cilj, ", ");
    cilj += 2;

    // "c}"
    int lc = izpisiC(b->c, cilj);
    cilj += lc;
    *cilj = '}';

    return l + lc + 4; // +2 zaradi {}, 2 zaradi ", "
}



int izpisiA(A* a, char* cilj){
    if(a == NULL){
        sprintf(cilj,"NULL");
        return 4;
    }
    int l = 0;
    int p = a->p;
    sprintf(cilj, "{%d, ", p);
    if(p<0) l++; // ce je negativno pristej predznak
    // dolzina inta
    while(p != 0){
        l++;
        p = p/10;
    }
    cilj += l + 3;

    int lb = izpisiB(a->b, cilj);
    cilj += lb;

    *cilj = '}';
    cilj++;
    *cilj = '\0';

    return l + lb + 4; // +4 zaradi " { } , presledek"
}

char ci[1000];

int main(int argc, char const *argv[])
{
    A *a = &(A){42, &(B){"Dober", &(C){true, &(A){-15, NULL}, &(B){"dan", &(C){false, NULL, NULL}}}}};

    izpisiA(a, ci);
    printf("%s", ci);
    return 0;
}
