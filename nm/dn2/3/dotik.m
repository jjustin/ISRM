format long; figure; hold on;

f12 = @(x,y) x.^5 + y.^5 - 5.*x.*y;
f22 = @(x,y) x.^2 + y .* exp(y.^2);

[X, Y] = meshgrid(-4:0.01:4, -2:0.01:2);
Z = f12(X,Y);
contour(X, Y, Z, [1,1], 'ShowText','on','LineColor','r');
Z = f22(X,Y);
% contour(X, Y, Z, 0:0.5:5, 'ShowText','on');

x0 = [-0.5;0.4;0.7]; % priblizna vrednost pobrana iz grafa
out = newt(x0, 10^-12, 50);
plot(out(1,:), out(2,:), 'm');
r = out(3, end)
contour(X, Y, Z, [r,r], 'ShowText','on');

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
