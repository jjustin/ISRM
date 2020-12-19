output  START 0
sicxe   
. load from dev to A, skip 1 to get to output and read output
        LDX #out
        STX next
        . load A, output last, inc X and store to next
loop    LDCH @next
        WD dev
        TIX #endout
        STX next
        JLT loop
halt J halt


dev BYTE X'BB'
out BYTE C'SIC/XE'
newline BYTE 10
endout EQU *
next BYTE 0
    END sicxe 

