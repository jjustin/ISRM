;
; mytest
;
        .data 
	.org 0x400
niz1:   .asciiz "Dober dan"
niz2:	.ascii "Lep dan"
;	.align 4
niz3:   .space 8
stev1:  .word 512,1,65537,123456789
stev2:	.word16 1,512,65534
stev3:  .word16 0x7fe
stev4:  .word64 1,0x1234567890123456
stev5:  .double -1.78901234567E-10,1.1
naslov: .word rutina2
	
        .text
	.org 0
;LOOP: beq r0, LOOP	
	mover r22
	addu r1,r31,r15
	lw r1,niz1(r0)
	lw r2,stev1(r0)
	lw r3,stev2(r0)
	sw niz3(r0),r3
	trap #2
        halt

rutina1: addi r3,r3,#1
	mover r4
	lw r5,naslov(r0) ;rutina2
	movre r5
	rfe

rutina2: movre r4
	lw r8,stev5(r0)
	addi r8,r8,#1
	rfe

	.org 0xFFFFFF08
	.word	rutina1,rutina2
