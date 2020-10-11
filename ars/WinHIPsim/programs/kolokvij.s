        .code
		.org 0x0000
		addui r3,r0,#0x4000
		addui r4,r0,#3
		addui r5,r0,#0x1234
		call r31,HIGH_ADD(r0)
		nop
		nop
		halt


		.code
		.org 0x1000
HIGH_ADD:
		lh r1,0(r3)
		add r1,r1,r5
		sh 0(r3),r1
		addui r3,r3,#0x1000
		subui r4,r4,#1
		bne r4,HIGH_ADD
		nop
		nop
		j 0(r31)
		nop
		nop
