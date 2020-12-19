    function [it, n] = tangentna(f, df, x0, tol, maxiter)
        it = x0;
        prev = 0;
        for n = 1:maxiter
            if abs(it-prev) < tol
                return
            end
            prev = it;
            it = it - f(it)/df(it)
        end
    end