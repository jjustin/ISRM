#include <stdio.h>

#define ulong unsigned long

int isPrime(ulong n){
    for(int i = 2; i <= n/2; i++){
        if(n%i==0) return 0;
    }
    return 1;
}

int main(int argc, char const *argv[]){
    int i = 1;
    ulong n = 1;
    while(i != 10001){
        n += 2;
        if(isPrime(n)) i++;
    }
    printf("%lu", n);
    return 0;
}
