figure; hold on;

f1 = @(x,y) x.^5 + y.^5 - 5.*x.*y - 1;
f2 = @(x,y) x.^2 + y .* exp(y.^2) - 1;

[X, Y] = meshgrid(-2:0.01:2, -2:0.01:2);
Z = f1(X,Y);
contour(X, Y, Z, [0,0],'ShowText','on','LineColor','r');
Z = f2(X,Y);
contour(X, Y, Z, [0,0],'ShowText','on','LineColor', 'g');

x0 = [0.5; -0.5];
optix = [1;0];
out = navadna_iteracija(x0, 10^-5, 10, optix);
plot(out(1,:), out(2,:), 'm');
out(:, end)

[~, V] = eig(J(x0));
if (max(max(abs(V)))>=1)
    fprintf('Pogoj spektralnega radija ni izpolnjen\n');
else
    fprintf('Pogoj spektralnega radija je izpolnjen\n');
end

out = newt(x0, 10^-5, 10, optix);
out(:, end)

plot(out(1,:), out(2,:), 'b');

function out = f(in)
    x = in(1);
    y = in(2);
    
    out = [x.^5 + y.^5 - 5.*x.*y - 1; 
           x.^2 + y .* exp(y.^2) - 1];
end

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

function out = J(in)
    x = in(1);
    y = in(2);

    out = [ 5.*x^4 - 5.*y,  5.*y^4 - 5.*x;
            2.*x,  (2*y^2+1).* exp(y.^2)];
end

function [it, n] = newt(x0, tol, maxiter, optix)
    it = zeros(size(x0,1), maxiter);
    it(:,1) = x0;

    for n = 1:maxiter
        dit = linsolve(J(it(:,n)),-f(it(:,n)));
        it(:,n+1) = it(:,n) + dit;
        if abs(norm(it(:,n+1),2)-norm(optix,2)) < tol
            it = it(:,1:n+1);
            return
        end
    end
    warning('Napaka - najvecje dovoljeno stevilo korakov');
end
