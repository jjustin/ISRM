poly    START   0
horner 
. Load
    . A = current div
    . X = x
    . T = current poly term
        LDX     x
        LDA    #1 . First term

        MULR    X,A
        ADD    #2

        MULR    X,A
        ADD    #3

        MULR    X,A
        ADD    #4

        MULR    X,A
        ADD    #5

        STA     out
halt    J       halt

x       WORD    2
out     WORD    0
