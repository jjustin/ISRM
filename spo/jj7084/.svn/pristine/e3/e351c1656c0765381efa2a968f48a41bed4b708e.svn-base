subs START   0
multiple
        LDA #in
        STA next
loop    LDX @next
        JSUB horner
        LDX hout
        STX @next
        ADD #3
        COMP #lastin
        STA next
        JLT loop
halt    J   halt

. in:
    . X = x
. uses:
    . A = current div
    . T = current poly term
horner  STA    ha 
        STT    ht
        STX    hx

        LDA    #1 . First term

        MULR    X,A
        ADD    #2

        MULR    X,A
        ADD    #3

        MULR    X,A
        ADD    #4

        MULR    X,A
        ADD    #5

        STA     hout

        LDA     ha
        LDT     ht
        LDX     hx
    RSUB

in  WORD    2
    WORD    5
    WORD    42
lastin EQU *
len    EQU lastin-in
next   WORD 0

. horner
. store registers
ha  WORD    0
ht  WORD    0
hx  WORD    0
. output
hout WORD    0

