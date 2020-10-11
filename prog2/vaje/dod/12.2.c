#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>

int main(int argc, char const *argv[]){
    char const *fnamein = argv[1];
    char const *fnameout = argv[2];
    int zamik = 0;
    bool start = true;
    char ch;
    char prevCh;

    FILE *f = fopen(fnamein, "r");
    FILE *fout = fopen(fnameout, "w");

    // prepisovanje datoteke
    while(!feof(f)){
        prevCh = ch;
        ch = fgetc(f);
        if(start){
            if(ch == ' ' || ch == '\t') continue; //presledek na zacetku -> preskoci
            if(ch =='}') zamik--;
            for(int i=0; i < zamik;i++){
                fputc('\t', fout);
            }
            start = false;
        }
        if(ch == '\n') {
            start = true;
            if(prevCh == '{') zamik++;
        }

        fputc(ch, fout);
    }
    return 0;
}
