#include <stdio.h>

int main(int argc, char const *argv[])
{
    int limit = 32; // sqrt(1000)
    int a, b, c = 0;

    for(a = 1;a <= 333; a++){
        for(b = a + 1; b <= (1000 - a); b++){
            for(c = 1000 - a - b ; c <= (1000 - b -a); c++){
                if(a*a + b*b == c*c){
                    printf("%d, %d, %d, %d", a, b, c, a*b*c);
                    return 0;
                }
            }
        }
        
    }
    printf("no luck :(");
    return 0;
}
