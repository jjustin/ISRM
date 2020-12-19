figure;hold on;format long;
% function definitions
B = @(x,y) [x.^3-5 1; -y y.^2+2*x];
f = @(x) b(x,x);
g = @(x) AF(b(x,x)) - A1(b(x,x));
h = @(x) Ainf(b(x,x));
% get root point aproximation
[rootsAPR, x ,y] =getZerosInterval(g, -2,2);
% % plot functions
% plot(x,y);
% graph(@(x) 0, -2,2);
% graph(h, -2,2);


possibleX = zeros(1, length(rootsAPR));
possibleY = zeros(1, length(rootsAPR));
% get 
for i = 1:length(possibleX)
    possibleX(i) = fzero(g, rootsAPR(i));
    possibleY(i) = h(possibleX(i));
end

[~, ix] = max(possibleY);
possibleX(ix)

function n = AF(A)
    n = norm(A, 'fro');
end

function n = A1(A)
    n = norm(A, 1);
end

function n = Ainf(A)
    n = norm(A, 'inf');
end

function [x,y] = graph(fun, a, b)
    x = a:0.1:b;
    y = zeros(1, length(x));
    for i = 1:length(x)
        y(i) = fun(x(i));
    end
    plot(x,y);
end

function [roots,x,y] = getZerosInterval(fun, a ,b)
    x = a:0.1:b;
    y = zeros(1,length(x));
    roots = [];
    for i = 1:length(x)
        y(i) = fun(x(i));
        if i ~= 1 
            if sign(y(i)) ~= sign(y(i-1))
                roots = [roots (x(i-1)+x(i))/2];
            end
        end
    end
end