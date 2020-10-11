#include <stdio.h>

int main(int argc, char const *argv[]){
    int d;
    scanf("%d#", &d);
    printf("%d\n", d);
    int znak = getchar();
    while(znak != '\n'){
        putchar(znak);
        znak = getchar();
    }
    return 0;
}
