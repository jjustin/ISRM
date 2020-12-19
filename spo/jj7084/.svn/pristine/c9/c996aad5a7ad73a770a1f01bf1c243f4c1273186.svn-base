p 	START 0
pp	JSUB stackinit
	JSUB prepareTerms
	JSUB drawCoord
	LDX  =-40
loop	JSUB calc
	DIV quotx
	STA y
	STX x
	JSUB drawPoint
	TIX =40
	JLT loop
halt 	J halt
.......................Parameters............................
. yscale should divide xscale
yscale  WORD 1
xscale 	WORD 1
polynom WORD 	-3 .x ^4
	WORD	-1 . x^3
	WORD 	2 . x^2
	WORD 	3 . x^1
polycons	WORD 	0 . x^0
polyend EQU *

........................Term preperator......................
prepareTerms
	STL @stackptr
	JSUB stackpush

	. calculate number of terms
	LDA #polyend
	SUB #polynom
	SUB =1
	DIV =3

	JSUB pow .calulate power of polynom
	DIV yscale 
	STA quotx . was divided to avoid multipling and then dividing
	MUL yscale

	. iterate over all entries and multiply each with 8 time less
	LDS #polycons
	STS polytmp
prepTermsLoop 
	LDT @polytmp
	MULR A, T
	STT @polytmp
	DIV xscale
	
	.inc counter
	LDT #polynom
	COMPR S, T
	JEQ endPrepTermsLoop
	LDT =3
	SUBR T, S
	STS polytmp
	
	J prepTermsLoop
endPrepTermsLoop
	JSUB stackpop
	LDL @stackptr
	RSUB

. loads xscale^A into A
pow 	CLEAR X
	STA powVar
	LDA =1
powloop	MUL xscale
	TIX powVar
	JLT powloop
	RSUB

powVar	WORD 0
quotx	WORD 0 . quotient by which we divide

.........................CALCULATOR.....................

. calculates p(x) with x provieded in X. Output is returned in A
calc	STL @stackptr
	JSUB stackpush
	STT @stackptr
	JSUB stackpush
	STS @stackptr
	JSUB stackpush

	LDS #polynom . init
	STS polyNext
	LDA @polyNext 
	
calloop	. callloop gets next polynom term constant and executes one step
	. of honer algorithm on it
	. S is used as pointer to terms
	. T is used for incrementing S and storing current term

	. increment polynext
	LDT =3
	ADDR T, S . increment polyNext
	STS polyNext .here polynext points to next polynom term
	. check if polynext is out of bounds
	LDT #polyend
	COMPR S, T . check if polynext is out of bounds
	JEQ calendloop . polynext is out of bound -> return result
	. get next term
	LDT @polyNext . T contains current term

	. horner is done here
	MULR X, A
	ADDR T, A
	J calloop
calendloop
	. restore registers
	JSUB stackpop
	LDS @stackptr
	JSUB stackpop
	LDT @stackptr
	JSUB stackpop
	LDL @stackptr
	RSUB

polytmp WORD 	0
polyNext WORD 	0


...........................SCREEN...........................
. #Columns = 80
. #Rows = 25

. drawn point on screen
drawPoint
	STA @stackptr
	. move xy coordss to screen coords
	LDA x
	ADD =40
	STA x
	LDA y
	MUL =-1
	ADD =12
	STA y
	. calculate point
	LDA y
	MUL =80
	ADD x
	ADD screen
	.check if we are on screen
	COMP screen
	JLT drawPointEnd
	COMP screenend
	JGT drawPointEnd
	.drawing
	STA scrpos
	LDA =42
	STCH @scrpos
drawPointEnd
	LDA @stackptr
	RSUB

. draws coord system
drawCoord
	. draw down
	LDA screen
	ADD =39
	CLEAR X
drawCoordLoop1
	STA scrpos
	LDA =124 . |
	STCH @scrpos
	LDA scrpos
	ADD =80
	TIX =25
	JLT drawCoordLoop1
	. across
	LDA screen
	ADD =961
	CLEAR X
drawCoordLoop2
	STA scrpos
	LDA =45 .
	STCH @scrpos
	LDA scrpos
	ADD =1
	TIX =80
	JLT drawCoordLoop2

	RSUB

screen 	WORD 47103 . B800
screenend WORD 49104
x	WORD 0
y	WORD 0
scrpos	WORD 0

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
