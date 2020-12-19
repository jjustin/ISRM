rtns	START 0
test	JSUB stackinit
	. RD stdin
	. JSUB printch
	LDA =69
	JSUB scrfill
	JSUB scrclear
halt  	J    halt
stdin 	BYTE X'00'

. fills the screen with last byte in A
scrfill	. store data
	STL @stackptr
	JSUB stackpush
	STX @stackptr
	JSUB stackpush
	.prepare screen
	LDX screen
fillloop . output A to all bytes of screen
	STX scrpos
	STCH @scrpos
	TIX scrend
	JLT fillloop .if x is out of screen

	JSUB stackpop
	LDX @stackptr
	JSUB stackpop
	LDL @stackptr
	RSUB
	
scrclear
	. store needed data
	STL @stackptr
	JSUB stackpush
	STA @stackptr
	JSUB stackpush

	. fill screen with 0
	CLEAR A
	JSUB scrfill

	. restore registers
	JSUB stackpop
	LDA @stackptr
	JSUB stackpop
	LDL @stackptr
	RSUB

printch 
	. reset from stack
	STL @stackptr
	JSUB stackpush
	STX @stackptr
	JSUB stackpush

	LDX scrpos
	TIX scrend
	JEQ printchreset
	STX scrpos
	STCH @scrpos

printchreset .reset from stack
	JSUB stackpop
	LDX @stackptr
	JSUB stackpop
	LDL @stackptr
	RSUB

screen 	WORD 47103 . B800
scrcols	WORD 80
scrrows	WORD 25
scrend	WORD 49104 . first byte out of screen
scrpos	WORD 47103

........................... STACK ..........................
stackinit 
	. store values
	STA stacktmp

	. init stackptr value
	LDA #stack
	STA stackptr

	. restore values
	LDA stacktmp
	RSUB

stackpush
	. store values
	STA stacktmp

	. move pointer up
	LDA stackptr
	ADD =3
	STA stackptr

	. restore values
	LDA stacktmp
	RSUB
	
stackpop
	. store values
	STA stacktmp

	. move pointer down
	LDA stackptr
	SUB =3
	STA stackptr
	
	. restore values
	LDA stacktmp
	RSUB

stacktmp WORD 0
stackptr WORD 0
stack 	RESW 50
