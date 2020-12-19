function x = navadna_iteracija(g, x0, tol, maxiter)
        x = zeros(maxiter+1, 1);
        x(1)=x0;
        for n = 1:maxiter
            x(n+1) = g(x(n));
            if abs(x(n+1) - x(n)) < tol
                x = x(1:n+1);
                return
            end
        end
        warning('Napaka - najvecje dovoljeno stevilo korakov');
    end
