prog 	START 0

. Input example: "1 2 3 4 5 6 \n" (note the space before \n)
fact	JSUB stackinit
readNex CLEAR T
	. read data from stdin
read	CLEAR A
	RD stdin
	COMP =32 .if space
	JEQ endRead
	COMP =10 .if newline
	JEQ halt
	SUB =48
	ADDR T, A
	MUL =10
	RMO A, T
	J read
endRead
	. A contains read number
	RMO T, A
	DIV =10
	. calculate factorial
	JSUB factorial
	. output caluculated factorial
	JSUB echonum
	JSUB echoln
	J readNex
halt 	J halt

factorial 	
	. if A == 1 return 1
	COMP =1
	JGT factrec
	RSUB . return if A == 1
factrec	STL @stackptr . store L to stack
	JSUB stackpush
	STA @stackptr . store A to stack
	JSUB stackpush

	.calulate for smaller problem
	SUB =1 . n -> n-1 problem reduction
	JSUB factorial . calculate for n-1

	JSUB stackpop
	LDT @stackptr . set T to current iterartion problem
	JSUB stackpop
	LDL @stackptr . load L from stack

	. calculate current iteration factorial
	MULR T, A
	RSUB

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

stdin  BYTE X'00'

..................... Number output .......................
echonum LDX #numDec
	STX next .next is pointer to where to store data
	.each iteration gets last decimal from A and stores it in numDec
numLoop	
	. extract last decimal place
	RMO A, T . T = A
	DIV =10
	RMO A, S . S = A //store A in S to use in next iter
	MUL =10
	SUBR A,T . T = T - A
	RMO T, A . A = T
	STCH @next . store last decimal place
	. increment next location
	TIX =0
	STX next
	. check if this was last decimal
	RMO S, A . A = S // load A/10 into A and use it in next iteration
	COMP =0
	JGT numLoop
	LDS =1
	LDT #numDec
numLoopPrint .loop back throught numDec and print it out
	SUBR S, X 
	STX next
	. print next
	LDCH @next
	ADD =48 . 60 = 0 in ASCII
	WD stdout
	COMPR T, X
	JLT numLoopPrint

	RSUB

. print \n
echoln	STCH tmp
	LDCH newline
	STL tmp2
	WD stdout
	LDL tmp2
	LDCH tmp
	RSUB
tmp BYTE 0
tmp2 WORD 0
newline BYTE X'0A'


next 	WORD 0 
stdout 	BYTE X'01'

numDec 	RESB 8 . 16777215 is max -> at most 8 decimal places
