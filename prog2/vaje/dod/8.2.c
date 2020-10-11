#include <stdio.h>
#include <stdlib.h>
#include <string.h>

/*
 * OjA! Rekurzija
 * t - tabela stringov, iz katerih se gradi outpute
 * out - tabela dolzine n kamor se hrani outpute
 * len - trenutna dolzina out-a
 * n - dolzina stringov, ki jih hocemo
 */
void output(char** t, char* out, int len, int n){
    if(len == n){ // ce sta enaka je konec rekurzije
        printf("%s\n", out);
        return;
    }

    len += 1; // dodamo eno crko v out
    for(int i = 0; i < strlen(t[len-1]); i++){ // ponavljamo za vse crke v stringu na (len-1) mestu v tabeli t 
        out[len-1] = t[len-1][i]; // vzemi i-to crko in jo postavi na (len-1) mesto v out
        output(t, out, len, n); // izpisi vse mogoce kombinacije za vse nadalnje stringe v t tabeli
        // v naslenjem loopu se pogleda vse kombinacije za naslednjo crko
    }
}


int main(int argc, char const *argv[])
{
    int n; // dolzina
    scanf("%d", &n); 
 
    char** t = malloc(n*sizeof(char*)); // 2d tabela inputov

    // beremo stringe
    for(int i = 0; i < n; i++){
        char* tmpT = malloc(43*sizeof(char));
        scanf("%s", tmpT);
        t[i] = tmpT;
    }

    char* out = malloc((n+1) * sizeof(char)); // tu hranimo outpute, ki jih izpisujemo na stdout
    out[n] = '\0'; // rabimo zakljucit string

    output(t, out, 0, n); // izpisi vse kombinacije

    return 0;
}


