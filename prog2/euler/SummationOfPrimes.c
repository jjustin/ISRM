#include <stdio.h>
#include <math.h>

int isPrime(int n){
    for(int i = 2; i <= sqrt((double)n); i++){
        if(n%i==0) return 0;
    }
    if(n == 2) printf("aa");
    if(n == 1) return 0;
    return 1;
}

int main(int argc, char const *argv[])
{
    unsigned long sum = 2; //start @ 2, because 2 is the only even prime and we skip it in for loop
    for(int i = 1; i <= 2000000; i+=2){
        if(isPrime(i)) sum += i;
    }
    printf("%lu", sum);
    return 0;
}
