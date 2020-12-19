std	START	0
program	LDA #print
	JSUB echostr
	JSUB echoln
	LDA num
	JSUB echonum
	JSUB echoln
halt	J	halt
print 	BYTE C'Poljubni String' 
	BYTE 00
num	WORD 1

. print from A
echoch	WD stdout
	RSUB

. print \n
echoln	STCH tmp
	LDCH newline
	STL tmp2
	JSUB echoch
	LDL tmp2
	LDCH tmp
	RSUB
tmp BYTE 0
tmp2 WORD 0
newline BYTE X'0A'

. print string potined at from A
echostr STL retTo .store potiner to return to
	RMO A, X .store location pointer in X
	STX next .set next to start of string
loop	LDCH @next .next specifies from where to load data
	AND #255 .compare only last byte
	COMP #0
	JEQ endloop .end if \0 is found
	JSUB echoch
	TIX #0 .increment X
	STX next .store X to next location, so A loads next character
	J loop 
endloop	LDL retTo
	RSUB

. print decimal representation of A
echonum STL retTo
	LDX #numDec
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
	JSUB echoch
	COMPR T, X
	JLT numLoopPrint
	.reload pointers
	LDL retTo
	RSUB

next 	WORD 0 
stdout 	BYTE X'01'
retTo 	WORD 0

numDec 	RESB 8 . 16777215 is max -> at most 8 decimal places
