#!/bin/bash
trap "exit" INT

if [ $# -ne 2 ]; then
   printf "Usage: ./test.sh <timeout> <java file without .java suffix>\nExample: ./test.sh 3s Naloga1\n";
   exit;
fi

TIMEFORMAT=$' [ %Es ]'
TIMEOUT=$1
JAVA_FILE=$2

printf "Compiling $JAVA_FILE.java\n"
javac $JAVA_FILE.java

I=$((0));
for file in $(ls -1 I_*.txt); do
	printf "Test file $file"
	$(time timeout ${TIMEOUT} java $JAVA_FILE $file R${file:1})
	if [ $? == 124 ]
	then
		printf " \e[34mTimeout\033[0m\n"
		continue
	fi

	diff -w <(sort O${file:1}) <(sort R${file:1}) > D${file:1}
	if [ -s D${file:1} ]
	then
		printf " \e[31mX\033[0m\n"
		continue
	fi
	printf " \e[32m+\033[0m\n"
done

htmlname="prikaz.html"
red="rgb(230, 70, 40)"
    #zgornji del
    echo "<!DOCTYPE html><html><head>
        <meta name="viewport" content="width=device-width, initial-scale=1">
         <style>
        h3 {font-size: 30px}
        a {color:inherit; font-size: 20px; padding:10px}
        a:hover{color: green}
        div {font-size: 15px;}
        </style>
        <title>Primerjava</title></head><body style=\"position: absolute; background-color:rgb(40,40,50); color:rgb(240,240,240); left:5%; top:5%;\">" > $htmlname

for file in $(ls -1 R?_*.txt); do
    if [ -s D${file:1} ]
    then
        echo "<span class=\"grow\" style=\"color:$red\"><a href=\"#$file\">$file      </a></span>">>$htmlname
    else
        echo "<span class=\"grow\"><a href=\"#$file\">$file</a>      </span>">>$htmlname
    fi
done

for file in $(ls -1 R?_*.txt); do
    if [ -s D${file:1} ] 
    then
        echo "<h3 style=\"color:$red\" id="$file" >$file</h3>">>$htmlname
        echo "<div style=\"color:$red\">In:<pre>" >>$htmlname
    else
        echo "<h3 id="$file" >$file</h3>">>$htmlname
        echo "<div>In:<pre>" >>$htmlname
    fi
    if [ -f I${file:1} ] 
    then
        cat I${file:1} >> $htmlname
    fi
    echo "</pre>Res:<pre>" >> $htmlname
    cat $file >> $htmlname
    if [ -s D${file:1} ] 
    then
        echo "</pre>Out:<pre>" >> $htmlname
        cat O${file:1} >> $htmlname
    fi    
    echo "</pre></div>" >> $htmlname
    echo "_________________________<br>" >> $htmlname
done

echo "    </body></html>" >> $htmlname