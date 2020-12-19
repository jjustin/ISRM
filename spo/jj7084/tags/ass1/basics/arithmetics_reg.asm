primer  START   0
second  
. Load registers
        LDA     y
        LDS     x

. Sum
        RMO     S,T
        ADDR    A,T
        STT     sum

. Substract
        RMO     S,T
        SUBR    A,T
        STT     diff

. Multiply
        RMO     S,T
        MULR    A,T
        STT     prod

. Divide
        RMO     S,T
        DIVR    A,T
        STT     quot

. Module
        MULR    A,T
        SUBR    T,S
        STS     mod
halt    J       halt

x       WORD    27
y       WORD    5
sum     WORD    0
diff    WORD    0
prod    WORD    0
quot    WORD    0
mod     WORD    0
        END     second
