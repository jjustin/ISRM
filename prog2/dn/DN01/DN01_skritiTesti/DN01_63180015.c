#include <stdio.h>

long potenca(int st)
{
    long i = 1;
    for (int j = 0; j < st; j++)
    {
        i *= 10;
    }
    return i;
}

long dolzinaSt(long st)
{
    long stevec = 0;
    while (st > 10)
    {
        st /= 10;
        stevec++;
    }
    return stevec;
}

long stevka(long st, int parameter)
{
    while (st >= potenca(parameter))
    {
        st /= 10;
    }
    return st % 10;
}

int main()
{
    long stevilo, dolzine;
    scanf("%ld %ld", &stevilo, &dolzine);
    long temp;
    int kolikokrat, kjeSem = 1;

    //izracunam koliko stevk ima stevilo dolzin in tolikokrat grem cez loop
    int dolzinaLoopa = dolzinaSt(dolzine) + 1;

    for (int i = 0; i < dolzinaLoopa; i++)
    {   
        // kolikokrat nam pove, koliko stevk mora biti izpisanih
        kolikokrat = (int)stevka(dolzine, 1);
        //nato izbrisem to stevko
        dolzine -= potenca(dolzinaSt(dolzine)) * kolikokrat;
        
        //temp je stevilo, katerega bomo printali sproti
        temp = 0;
        for (int j = 0; j < kolikokrat; j++)
        {
            //ce stevilo ze ima stevko, pomnozim z deset, nato naslednjo pristejem zraven
            if (temp != 0) temp *= 10; 
            temp += stevka(stevilo, kjeSem);
            //kjeSem pove, na katerem mestu prvotnega stevila smo (s tisto stevko operiramo)
            kjeSem++;
        }
        //sprintamo trenutno stevilo
        printf("%ld\n", temp);
        
    }

    return 0;
}
