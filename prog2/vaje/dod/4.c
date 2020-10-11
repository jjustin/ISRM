#include <stdio.h>
int returnZero(){
    printf("0\n");
    return 0;
}
int main(int argc, char const *argv[])
{
    int a = getchar();

    //[1,9]  
    if(a >= '1' && a <= '9'){
        a = getchar();
        while(a != '\n'){
            if(a < '0' || a > '9') return returnZero();
            a = getchar();
        }
    }
    //0
    else if(a == '0'){
        a = getchar();
        //0x
        if(a == 'x'){
            a = getchar();
            if(a == '\n')  return returnZero();
            while(a != '\n'){
                if(a < '0' || a > 'F') return returnZero();
                a = getchar();
            }
        }
        //0b
        if(a == 'b'){
            a = getchar();
            if(a == '\n')  return returnZero();
            while(a != '\n'){
                if(!(a=='1' || a=='0')) return returnZero();
                a = getchar();
            }
        }

        //[0, 7]
        while(a != '\n'){
            if(a < '0' || a > '7') return returnZero();
            a = getchar();
        }

    }else return returnZero();
    printf("1\n");
    return 0;
}
