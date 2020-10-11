;**********************************************
;*** winMIPS64 Lab0  //sum.s//   C=A+B   ******
;*** (c) 2003 CA226, DCU                 ******
;**********************************************

      .data
A:    .word 10
B:    .word 8
C:    .word 0

      .text
main:
      lw r4,A(r0)
      lw r5,B(r0)
      add r3,r4,r5
      sw r3,C(r0)
      halt
