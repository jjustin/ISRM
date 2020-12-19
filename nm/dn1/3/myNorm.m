    function nrm=myNorm(x, n)
        nrm = 0;
        if (n == inf)
            nrm = abs(max(x));
            return 
        end
        for i=1:size(x)
            nrm = nrm + abs(x(i)).^n;
        end
        nrm = nthroot(nrm, n);
    end

    x = complex(rand(10, 1), rand(10, 1));
    myNorm(x, 1) - norm(x, 1)
    myNorm(x, 2) - norm(x, 2)
    myNorm(x, inf) - norm(x, inf)
    
    A = [2, 1, 3; 5, 4, 1; -2, -1, 2];
    
    AF = sqrt(sum(A(:).^2));
    AF - norm(A,'fro')
    
    A1 = max(sum(abs(A)));
    A1 - norm(A, 1)
    
    Ainf = max(sum(abs(A), 2));
    Ainf - norm(A, inf)

    AN = max(abs(A(:)))
