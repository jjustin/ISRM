figure; hold on;

f1 = @(x,y) x.^5 + y.^5 - 5.*x.*y - 1;
f2 = @(x,y) x.^2 + y .* exp(y.^2);

[X, Y] = meshgrid(-4:0.01:4, -2:0.01:2);
Z = f1(X,Y);
contour(X, Y, Z, [0,0], 'ShowText','on','LineColor','r');
Z = f2(X,Y);
contour(X, Y, Z, 0:0.5:5, 'ShowText','on');
contour(X, Y, Z, [1.2,1.2], 'ShowText','on');

x0 = [-0.9;0.35]; % priblizna vrednost pobrana iz grafa
out = newt(x0, 10^-8, 50);
x1 = out(:, end)
plot(out(1,:), out(2,:), 'm');
plot(x1(1), x1(2), 'ro');

x0 = [-0.25;0.7]; % priblizna vrednost pobrana iz grafa
out = newt(x0, 10^-8, 50);
x2 = out(:, end)
plot(out(1,:), out(2,:), 'm');
plot(x2(1), x2(2), 'ro');

x0 = [1.1;0.1]; % priblizna vrednost pobrana iz grafa
out = newt(x0, 10^-8, 50);
x3 = out(:, end)
plot(out(1,:), out(2,:), 'm');
plot(x3(1), x3(2), 'ro');


function out = f(in)
    x = in(1);
    y = in(2);
    
    out = [x.^5 + y.^5 - 5.*x.*y - 1; 
           x.^2 + y .* exp(y.^2) - 1.2];
end

function out = J(in)
    x = in(1);
    y = in(2);

    out = [ 5.*x^4 - 5.*y,  5.*y^4 - 5.*x;
            2.*x,  (2*y^2+1).* exp(y.^2)];
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
