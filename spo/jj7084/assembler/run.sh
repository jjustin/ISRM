if test -z $1; then
    echo "No asm file specified. Please use \"./run.sh <file to compile>\""
else
    if test ! -f $1; then
        echo "Error: file $1 does not exits"
    else 
        javac sic/*.java
        java sic.Main $1
    fi
fi

