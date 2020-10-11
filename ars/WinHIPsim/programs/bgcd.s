;
; Nassir Hussain
; Binary GCD algorithm. Knuth Vol. 2
;
        .data 
	.org 0x400
u:	.word 1197622676
v:	.word 95118632

        .text
	.org 0
        lw r2,u(r0)
        lw r3,v(r0)

B1:     andi r6,r2,1
        andi r7,r3,1
        bne  r6,B2           ; note jumps to different destinations
        bne  r7,B2a
        srli r2,r2,1
        srli r3,r3,1
        addi r5,r5,1         ; r5=2^loops
        j B1(r0)

B2:     sub  r4,r0,r3
        j B4(r0)

B2a:    add  r4,r0,r2

B3:     srai r4,r4,1         ; r4=t
B4:     andi r9,r4,1
        slt r10,r4,r0        ; useful way to fill a stall !
        beq  r9,B3

B5:     beq  r10,here
        sub  r3,r0,r4 
        j B6(r0)

here:   add r2,r4,r0
B6:     sub r4,r2,r3
        bne r4,B3
        sll r10,r2,r5        ; r=u << r5

        halt
