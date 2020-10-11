#include <stdio.h>

int main(int argc, char const *argv[])
{
    int next = getchar() -'0';
    int prvaEnkaNajdena = 0, sameNicle = 1, stevec = 0;
    
    while(next == 1 || next == 0){
        if(prvaEnkaNajdena == 1 && next == 1) sameNicle = 0;
        if(next == 1) prvaEnkaNajdena = 1;
        if(prvaEnkaNajdena == 1) stevec++;
        next = getchar() - '0';
    }

    printf("%d\n", stevec - sameNicle);
    return 0;
}
