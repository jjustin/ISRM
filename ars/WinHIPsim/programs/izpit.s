		.data
		.org 0x400
TAB:	.byte 2,4,6
		.align 4

		.data
		.org 0x40000080
		.byte 2,3,4,5,6
		.align 4

		.code
		.org 0x0

		LHI		r2,TAB
		ADDUI	R2,r2,TAB
		ADDUI	R4,R0,#0
		ADDUI	R4,R4,#1
		BNE	R4,SUB1
		LB		R3,0(r2)
		ADDUI	R3,R0,#2
DUM:	SUB	R4,R4,R0
SUB1:	LHI	R1,#0x40000000
		ADDUI 	R1,R1,#0x0080
LOOP:	LB		R5,0(R1)
		SUBI	R3,R3,#1
		SUBUI	R5,R5,#1
		BNE	R3, LOOP
		SB		0(R1),R5
		ADDUI 	R1,R1,#1
		halt
