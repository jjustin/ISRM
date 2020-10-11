#include <stdio.h>

int main(int argc, char const *argv[]){
    int a = getchar();
    if (a == '+' || a == '-') {
        a = getchar();
        if(((a < '0' || a > '9') && a != '\n') || a=='\n'){
            printf("0\n");
            return 0;
        }
    }
    
    if(a == '0'){
        if(getchar() == '\n'){
            printf("1\n");
            return 0;
        }else{
            printf("0\n");
            return 0;
        }
    }

    while(a != '\n'){
        if(a < '0' || a > '9'){
            printf("0\n");
            return 0;
        }
        a = getchar();
    }

    printf("1\n");
    return 0;
}

