#include <stdio.h>

int nextFibonacci(int a, int b){
    return a + b;
}
int main(int argc, char const *argv[]){
    int out = 0;
    int a = 1;
    int b = 2;
    while(a <= 4000000){
        if(a % 2 == 0){
            out+= a;
        }
        int tmp = b;
        b = nextFibonacci(a, b);
        a = tmp;
        printf("%d\n", out);
    }
    printf("%d\n", out);
    return 0;
}

