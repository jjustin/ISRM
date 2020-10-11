#include <stdio.h>

#define ulong unsigned long
int main(int argc, char const *argv[])
{
    ulong squareOfSums = 101 * 50 * 101 * 50;
    ulong sumOfSquares = 0;
    for(int i = 0; i <= 100; i++) sumOfSquares += i*i;
    printf("(Ea)^2: %lu \n E(a^2): %lu \n", squareOfSums, sumOfSquares);
    printf("%lu\n", squareOfSums-sumOfSquares);
    return 0;
}
