scrn 	START 0
basics	
	LDA 	=76 	.L
	STCH 	@screenStrPtr
	LDA	=77	.M
	STCH 	@screenFrstLnEndPtr
	LDA	=65	.A
	STCH 	@screenLastLnStrPtr
	LDA 	=79	.O
	STCH 	@screenEndPtr

	LDA 	=71	.G
	STCH 	@screenMidPtr
	JSUB 	incMid
	LDA 	=79	.O
	STCH 	@screenMidPtr
	JSUB 	incMid
	LDA 	=84	.T
	STCH 	@screenMidPtr
	JSUB 	incMid
	LDA 	=84	.T
	STCH 	@screenMidPtr
	JSUB 	incMid
	LDA 	=69	.E
	STCH 	@screenMidPtr
	JSUB 	incMid
	LDA 	=77	.M
	STCH 	@screenMidPtr
	JSUB 	incMid

halt 	J 	halt

incMid	LDX screenMidPtr
	TIX =0
	STX screenMidPtr
	RSUB

screenStrPtr WORD 47104 . start of text
screenFrstLnEndPtr WORD 47183 . end of first line
screenMidPtr WORD 48100 . middle of screen
screenLastLnStrPtr WORD 49024 . start of last line
screenEndPtr WORD 49103 . end of screen
