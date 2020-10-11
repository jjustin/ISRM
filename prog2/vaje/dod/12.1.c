#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>

int main(int argc, char const *argv[]){
    int n = (int) strtol(argv[2], (char **)NULL , 10); // vzame drugi argument in ga pretvori v int
    char const *fname = argv[1];

    char ch;
    int nOfLines = 0;

    FILE *f = fopen(fname, "r");

    // prestej vrstice
    while(!feof(f)){
        ch = fgetc(f);
        if(ch == '\n') nOfLines++;
    }

    fclose(f);
    f = fopen(fname, "r");

    int nToSkip = nOfLines - n;

    // preskoci nToSkip vrstic
    int skipped = 0;
    while(skipped < nToSkip && !feof(f)){
        ch = fgetc(f);
        if(ch == '\n') skipped++;
    }

    while(!feof(f)){
        putchar(fgetc(f));
    }

    fclose(f);
    return 0;
}
