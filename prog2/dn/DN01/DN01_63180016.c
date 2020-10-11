#include <stdio.h>

int main(int argc, char const *argv[])
{
    unsigned long long a, b, tempB, tempA, desetkeB, desetkeA, odstej;
    // Preberi ta temačna števila!
    scanf("%llu%llu", &a, &b);

    // poisci dolzino B-ja :(
    int dolzinaB = 0;
    for(tempB = b; tempB!=0; tempB /= 10) dolzinaB++;

    // Rabmo tud dolzina A-ja :(
    int dolzinaA = 0;
    for(tempA = a; tempA!=0; tempA /= 10) dolzinaA++;

    // destekeB = pow(10, dolzinaB-1) (to vrne double, ki je premajhen :( #That's what she said)
    desetkeB = 1;
    for(int i = dolzinaB-1 ; i != 0; i--)desetkeB *= 10;
    
    // Enako strimo še za a :(
    desetkeA = 1;
    for(int i = dolzinaA ; i != 0; i--)desetkeA *= 10;

    // Udarmo en lotr cez dolzino b-ja
    for(; desetkeB != 0; desetkeB /= 10){
        // OJOJ, to vrne prvo cifro v b-ju :O
        int dolzina = b / desetkeB;

        // ŠMENT! To rabimo odstet od b-ja, da b ne bo predolg :(
        odstej = desetkeB * dolzina;
        b -= odstej;

        // WTF IS GOING ON!
        // posicemo point na katerem odrezemo a, na dolzino b-ja. #moderna romeo in julija
        for(int i = dolzina; i != 0; i--) desetkeA /= 10;
        int tmpA = a/desetkeA;
        printf("%d\n", tmpA);

        // izpisano odstranimo od zacetka a-ja. Kaksan tragican zgodba. Ce bi bil shakespare še živ, bi jo zihre napisu :(
        odstej = desetkeA * tmpA;
        a -= odstej;
    }
    return 0;
}
