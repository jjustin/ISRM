#include <stdio.h>
#include <limits.h>

unsigned long long memory[1844674407370];

unsigned long long collatz(unsigned long long n){
    // printf("%llu\n",n);
    if(n == 1) return 1;
    if(memory[n-1] != 0) return memory[n-1];
    unsigned long long a;
    if(n % 2 == 0) {
        // printf("Sending %llu from %llu\n", n/2, n);
        a = collatz(n/2) + 1;
    }
    else {
        // printf("Sending %llu from %llu\n", 3*n+1, n);
        a = collatz((3*n + 1)/2) + 2;
    }
    memory[n-1] = a;
    return a;
}

int main(int argc, char const *argv[]){
    for(int i = 1; i < 1000000;i++) {
        collatz(i);
    }
    int max = 0;
    for(int i = 500000; i < 1000000;i++) if(memory[i] > memory[max]) max = i;
    printf("max: %d\n", max+1);
    return 0;
}


