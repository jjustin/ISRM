#include <stdio.h>

int main(){
    long a = 600851475143;
    long b = 2;
    while(a > b){
        if(a % b == 0){
            a = a / b;
            // b = 2;
        }else{
            b++;
        }
    }
    printf("%ld\n", b);
}