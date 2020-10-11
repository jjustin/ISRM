#include <stdio.h>

int main(int argc, char const *argv[])
{
    int a = 0;
    int minusa = 0;
    int next = getchar() - '0';
    if (next == '-' - '0')
    {
        minusa = 1;
        next = getchar() - '0';
    }
    do
    {
        a = a * 10 + next;
        next = getchar() - '0';
    } while (next != ' ' - '0');

    int b = 0;
    int minusb = 0;
    next = getchar() - '0';
    if (next == '-' - '0')
    {
        minusb = 1;
        next = getchar() - '0';
    }
    do
    {
        b = b * 10 + next;
        next = getchar() - '0';
    } while (next != '\n' - '0');

    if (minusa == 1)
        a = -a;
    if (minusb == 1)
        b = -b;

    printf("%d\n", a + b);

    return 0;
}
