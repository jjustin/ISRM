;
; strcpy
;
        .data 
	.org 0x400
niz1:   .asciiz "Dober dan"
niz2:   .space 10

        .text
	.org 0
	lw r7,niz1(r0)
	add r2,r0,r0
        add r3,r0,r0
        call r31,STRCPYa(r0)
	nop
	nop
        halt
;
; razlicica za procesor, kjer se pri skokih predpostavlja neizpolnjen pogoj
;
STRCPYa: lb   r4,niz1(r2)
        addi r2,r2,#1
        addi r3,r3,#1
        sb   niz2(r3),r4
        bne  r4,STRCPYa
        j 0(r31)

;
; razlicica za procesor, kjer se pri skokih uporabljajo zakasnjeni skoki
;
STRCPYb: lb   r4,niz1(r2)
        addi r2,r2,#1
        bne  r4,STRCPYb
        sb   niz2(r3),r4
        addi r3,r3,#1
        j 0(r31)
	nop
	nop
