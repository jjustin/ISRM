#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

typedef struct _Vhod { 
    int a;
    int b;
    int n;
    int i;
    int state;
} Vhod;

Vhod* stck[10000];
int stackN=0;

void stackAdd(Vhod* v){
    stck[stackN] = v;
    stackN++;
}

Vhod* stackGet(){
    stackN--;
    return stck[stackN - 1];
}

Vhod* newVhod(int a, int b, int n){
    Vhod *v = (Vhod*)malloc(sizeof(Vhod));
    v->a = a;
    v->b = b;
    v->n = n;
    v->i = 0;
    v->state = 1;
    return v;
}

void it(int a, int b, int n){
    Vhod *v = newVhod(a,b,n);
    Vhod *vn;
    stackAdd(v);
    // stanja:
    // 0 -> konec
    // 1 -> zacetek
    // 2 -> izpisi a
    // 3 -> izpisi b
    // 4 -> izpisi c
    // 5 -> prvi del zanke v c
    // 6 -> drugi del zanke v c
    while(stackN != 0){
        switch (v->state) {
            case 0:
                v = stackGet();
                break;
            case 1:
                if(v->a <= 0){
                    v->state = 2;
                    break;
                    }
                if(v->b <= 0){
                    v->state = 3;
                    break;
                }
                v->state = 5;
                break;
            case 2:
                printf("A");
                v->state = 0;
                break;
            case 3:
                printf("B");
                v->state = 0;
                break;
            case 4:
                printf("C");
                v->state = 6;
                break; 
            case 5:
                if(v->i == v->n){
                    v->state = 0;
                    break;
                }
                v->state = 4;
                vn = newVhod(v->a/2, v->b-1, v->i);

                stackAdd(vn);
                v = vn;

                break;
            case 6:
                v->state = 5;

                vn = newVhod(v->a-2, v->b/3, v->i);
                v->i++;
                stackAdd(vn);
                v = vn;

                break;
        }
    }
}

void f(int a, int b, int n) {
    if (a <= 0) {
        printf("A");
    } else if (b <= 0) {
        printf("B");
    } else {
        for(int i=0; i<n; i++){
            f(a / 2, b - 1, i);
            printf("C");
            f(a - 2, b / 3, i);
        } 
    }
}
int main(){
    f(7,6,5);
    printf("\n");
    it(7,6,5);
}