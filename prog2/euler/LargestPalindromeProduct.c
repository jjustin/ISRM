#include <stdio.h>

int isPalindrome(int a){
    char str[12];
    sprintf(str, "%d", a);

    for(int i = 0; i <= 3 ; i++){
        if(str[i] != str[5-i]) return 0;
    }

    return 1;
}
/*

 'abccba' = 100000*a + 10000*b + 1000*c + 100*c + 10*b + a = 11( nekaj ) = p*q
 p in q sta  faktorja 100 <= p,q <= 999
 eden izmed p,q mora imeti mora imeti prafaktor 11
 zacnemo z p = 990 (deljiv z 11 - torej je faktor 11 notr)
 gledamo za kaksen q imamo palindromsko obliko stevila
 nato iscemo pri kateri kombinaciji p,q imamo najvecje stevilo
*/

int main(int argc, char const *argv[])
{
    int max = 0;

    for( int p = 990; p > 99; p -= 11){
        for(int q = 999; q > 99; q--){

            int c = p * q;
            
            if(max < c && isPalindrome(c)){
                max = c;
                break;
            } else if(max > c){ //zmonzek je manjsi, ce manjsamo q ne dobimo vecje cifre
                break;
            }
        }
    }
    printf("%d", max);
    return 0;
}

