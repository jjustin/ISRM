#include <stdio.h>
#include <math.h>

int nOfDivisors(unsigned long long n){
    int x = 0;
    for(int i = 1; i <= sqrt(n); i++){
        if(n%i == 0){
            x++;
            if(n/i!=i) x++;
        }
    }
    return x;
}

int main(int argc, char const *argv[]){
    unsigned long long i = 0;
    unsigned long long n = 0;
    while(nOfDivisors(n) <= 500){
        i++;
        n += i;
    }

    printf("%llu", n);
    return 0;
}

