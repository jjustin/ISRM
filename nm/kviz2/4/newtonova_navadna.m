figure; hold on;

function out = g(in)
    syms x y
    g1 = nthroot(1 + 5.*x.*y - y.^5, 5);
    g2 = (1-x.^2)/exp(y.^2);
    
    out = [g1; g2];
    out = subs(out, x, in(1));
    out = subs(out, y, in(2));    
end


function x = navadna_iteracija(x0, tol, maxiter, optix)
    x = zeros(size(x0,1), maxiter+1);
    x(:,1)=x0;
    for n = 1:maxiter
        x(:, n+1) = g(x(:,n))';
        if abs(norm(x(:,n+1),2) - norm(optix,2)) < tol
            x = x(:,1:n+1);
            return
        end
    end
    warning('Napaka - najvecje dovoljeno stevilo korakov');
end

function out = f(in)
    syms x y r
    f1 = x.^5 + y.^5 - 5.*x.*y - 1;
    f2 = x.^2 + y .* exp(y.^2) - r;
    f3 = det(jacobian([ f1, f2 ], [x,y]));
    
    out = [f1; f2; f3];
    out = subs(out, x, in(1));
    out = subs(out, y, in(2));
    out = subs(out, r, in(3));

end

function out = J(in)
    syms x y r
    f1 = x.^5 + y.^5 - 5.*x.*y - 1;
    f2 = x.^2 + y .* exp(y.^2) - r;
    f3 = det(jacobian([ f1, f2 ], [x,y]));
    jac = jacobian([f1 ,f2 , f3], [x,y,r]);

    out = subs(jac, x, in(1));
    out = subs(out, y, in(2));
    out = subs(out, r, in(3));
end

function [it, n] = newt(x0, tol, maxiter)
    it = zeros(size(x0,1), maxiter);
    it(:,1) = x0;

    for n = 1:maxiter
        dit = linsolve(J(it(:,n)),-f(it(:,n)));
        it(:,n+1) = it(:,n) + dit;
        if abs(norm(it(:,n+1),2)-norm(it(:,n),2)) < tol
            it = it(:,1:n+1);
            return
        end
    end
    warning('Napaka - najvecje dovoljeno stevilo korakov');
end
