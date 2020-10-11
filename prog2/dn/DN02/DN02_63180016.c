#include <stdio.h>

int main(int argc, char const *argv[]){
    int encode = getchar()-'0'; //1==compress 2== decode
    getchar();
    if(encode == 1){
        int trenutno = getchar();
        while(trenutno != '\n'){
            int naslednje = getchar();
            
            if(trenutno == naslednje){
                //prestej stevilo znakov
                int stevec = 2;
                int znak = getchar();
                while(znak == trenutno){
                    stevec++;
                    znak = getchar();
                }
                // ##k#
                if(trenutno == '#') printf("##%d#", stevec);
                // #ck#
                else if(stevec >= 5) printf("#%c%d#", trenutno, stevec);
                // cccc
                else for(int i = 1; i <= stevec; i++) printf("%c", trenutno);
    
                trenutno = znak;


            }else{
                if(trenutno == '#') printf("##1#");
                else printf("%c",trenutno);
                trenutno = naslednje;
            }
        }
    }else
    {
        int naslednjiZank = getchar();
        while(naslednjiZank != '\n'){
            if(naslednjiZank!='#') putchar(naslednjiZank);
            else{
                int znak = getchar();
                int steviloZankov;
                scanf("%d#",&steviloZankov);
                for(int i = 1; i <= steviloZankov; i++) printf("%c", znak);
            }
            naslednjiZank = getchar();
        }
    }
    
    return 0;
}

