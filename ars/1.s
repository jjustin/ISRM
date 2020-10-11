.code
     .org 0x80
     addui r30, r0, #0x4FC
     addui r24, r0, #8
     addui r25, r0, #4
     addui r1, r0, #3
     push r1
     call r31, PR1(r0)
     pop r1
     halt
PR1: push r31 
     push r29
     add r29, r0, r30
     push r25
     push r24
     call r31, PR2(r0)
     add r24, r0, r28
     lw r25, 12(r29)
     call r31, PR2(r0)
     pop r24
     pop r25
     pop r29
     pop r31
     j 0(r31)
PR2: push r24
    add r28, r0, r0
LOOP:add r28, r28, r25 
    subui r24, r24, #4
     bne r24, LOOP
     pop r24
     j 0(r31)