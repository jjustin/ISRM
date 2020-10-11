#include <stdio.h>
#include <stdbool.h>
#include <stdlib.h>

int main(int argc, char const *argv[])
{
    char niz[101];
    char zacetniNiz[101];
    int n;
    scanf("%s\n%d", zacetniNiz, &n);
    int kulStevec = 0;
    for(int i = 0; i < n; i++){
        scanf("%s\n", niz);
        bool ok = true;
        int j = 0; 
        int k = 0; // premik po 
        while(*(niz+j) != '\0' && ok){
            if(*(zacetniNiz+k) == '?'){}
            else if(*(zacetniNiz+k) == '*'){
                char *next = zacetniNiz+k+1;
                k++;
                while(*next != '\0' && *next != '*'){
                    next++;
                    k++;
                }
                if(*next == '*') { // ce se trenutno nahajamo na zvezdici
                    next--; 
                    k--;
                }
                int dolzina =next - start+1
                int l = 0;
                while((*(niz+j+l) != '\0')) {
                    printf("c: %c\n",*(niz+j+l));
                    if(*(niz+j+l) == *(start + f)) f++;
                    else f=0;
                    l++;
                }
                j += l;

            }else if(*(niz+j) != *(zacetniNiz+k)){
                ok = false;
            }
            j++;
            k++;
        }
        if(*(niz+j) != '\0' || *(zacetniNiz+k) != '\0') ok = false;
        if(ok) kulStevec++;
    }
    printf("%d", kulStevec);
    return 0;
}
