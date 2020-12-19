poly    START   0
classic 
. Load
    . A = current term value
    . X = x value
    . T = current polynomial calculated value
        LDA    #1
        LDX     x
        LDT    #0

. x^4
        MULR X,A
        MULR X,A
        MULR X,A
        MULR X,A
        
        ADDR A,T 

. 2x^3
        LDA  #1
        MULR X,A
        MULR X,A
        MULR X,A
        MUL  #2

        ADDR A,T

. 3 x^2
        LDA  #1
        MULR X,A
        MULR X,A
        MUL  #3

        ADDR A,T

. 4x
        RMO  X,A
        MUL  #4

        ADDR A,T
. 5
        LDA #5
        ADDR A,T
        STT out
halt    J       halt

x       WORD    2
out     WORD    0
