o = newt([-0.9; 0.2], 10^-8, 3)
sum(abs(o(:, end)))

function out = f(in)
d = 2.9292636231872278; s = 2.3499837659848088;

    syms x y
    f1 = (x+s/10)^5 + y^5 -3*x*y -1;
    f2 = x^2 + y*exp(y^2) - d;
    
    out = [f1; f2];
    out = subs(out, x, in(1));
    out = subs(out, y, in(2));

end

function out = J(in)
d = 2.9292636231872278; s = 2.3499837659848088;

    syms x y
    f1 = (x+s/10)^5 + y^5 -3*x*y -1;
    f2 = x^2 + y*exp(y^2) - d;
    jac = jacobian([f1 ,f2 ], [x,y]);

    out = subs(jac, x, in(1));
    out = subs(out, y, in(2));
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
