.PHONY: clean run

hello: hello.o dep.o  
	gcc -o $@ $^

hello.o: hello.c dep.h
	gcc -c $<

%.o: %.c
	gcc -c $^

run: hello
	./hello
	
clean:
	rm -rf hello *.o