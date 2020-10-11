#include <stdio.h>
#include <math.h>
#include <stdlib.h>

int f(int x) {
    return x * x;
}
int inverz(int y, int a, int b) {
    int min = abs(f(a) - y);
    int out = a;
    for(int x = a; x <= b; x++){
        int fx = f(x);
        int c = abs(fx-y);
        printf("x = %d ; f(x) = %d ; c = %d ; min = %d \n",x, fx, c, min);
        if(c < min){
            min = abs(fx-y);
            out = x;
        }
    }
    return out;
}
int main() {
    printf("%d\n", inverz(42, 1, 100));
    printf("%d\n", inverz(43, 1, 100));
    return 0;
}