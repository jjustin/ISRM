figure; hold on;format long;
c = 2.6323592462254095;

f1 = @(x,y) (x+1).^5+y.^5-3.*x.*y-1;
f2 = @(x,y) x.^2+y.*exp(y.^2)-c;

[X, Y] = meshgrid(-2:0.01:0, 0:0.01:1);
Z = f1(X,Y);
contour(X, Y, Z, [0,0],'ShowText','on','LineColor','r');
Z = f2(X,Y);
contour(X, Y, Z, [0,0],'ShowText','on','LineColor', 'g');

x0 = [-0.9;0.2];
optix = [-1.5;0.25];
out = navadna_iteracija(x0, 10^-5, 2, optix)
plot(out(1,:), out(2,:), 'm');
sum(abs(out(:, end)))

x0 = [-0.9,0.2];
gg = @(x) [(x(1)+1).^5+x(2).^5-3.*x(1).*x(2)-1 , x(1).^2+x(2).*exp(x(2).^2)-c];
x=fsolve(gg,x0)
plot(x(1),x(2), '+');
sum(abs(x))

function out = g(in)
    c = 2.6323592462254095;

    syms x y
    g1 = (y.^5+(x+1).^5-1)/(3.*y);
    g2 = (c-x.^2)/(exp(y.^2));
    
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
