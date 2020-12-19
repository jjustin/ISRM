primer  START   0
second  LDA     a
        ADD     b
        STA     sum

        LDA     a
        SUB     b
        STA     diff

        LDA     a
        MUL     b
        STA     prod

        LDA     a
        DIV     b
        STA     quot

        MUL     b
        STA     mod
        LDA     a
        SUB     mod
        STA     mod
halt    J       halt

a       WORD    12
b       WORD    5
sum     WORD    0
diff    WORD    0
prod    WORD    0
quot    WORD    0
mod     WORD    0
        END     second
