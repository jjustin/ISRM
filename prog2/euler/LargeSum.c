#include <stdio.h>

unsigned long long a[100][5], sums[5];

int main(int argc, char const *argv[]){
    for(int i = 0; i < 100;i++)
    for(int j = 0; j < 5; j++){
        long long unsigned num = 0;
        for(int k = 0; k<10;k++){
            num = num*10 + (getchar() - '0');
        }
        a[i][j] = num;
    }    

    for(int j = 0; j < 5; j++){
        unsigned long long sum = 0;
        for(int i = 0; i < 100; i++){
            sum += a[i][j];
        }
        sums[j] = sum;
    }

    for(int j = 4; j > 0; j--) sums[j-1] += sums[j] / 10000000000;

    printf("%llu\n", sums[0]/100); // sums[4] is 12 characters long - cut last 2 away

    return 0;
}
