prog 	START 0
s	JSUB stackinit
	
	. push 12
	LDA =12
	STA @stackptr
	JSUB stackpush

	. push 42
	LDA =42
	STA @stackptr
	JSUB stackpush

	. load from stack
	CLEAR A
	JSUB stackpop
	LDA @stackptr

	. push 33
	LDT =33
	STT @stackptr
	JSUB stackpush

halt	J halt

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
