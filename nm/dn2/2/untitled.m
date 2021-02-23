format long; figure; hold on; grid on;


in = fscanf(fopen('podatki.txt','r'),'%f,%f', [2, Inf]);

A = zeros(length(in), 5);
for i = 1:length(in)
    A(i,:) = [in(1,i).^2, in(1,i).*in(2,i), in(2,i).^2, in(1,i), in(2,i)];
end
[Q,R] = qr(A);
b = Q(:,1:5)' * ones(length(in),1);
x = R(1:5,1:5)\b

% scatter(in(1,:), in(2,:),'');
A = x(1);
B = x(2);
C = x(3);
D = x(4);
E = x(5);
f = @(x,y)  A.*x.^2 + B.*x.*y + C.*y.^2 + D.*x + E.*y - 1; 

% g = @(x,y) abs(f(x,y) - )

[X, Y] = meshgrid(-3:0.005:5.5, -3:0.005:5);
Z = f(X,Y);
contour(X, Y, Z, [0,0],'LineColor','r');

okElements = abs(Z)<0.004;
xe = X(okElements)';
ye = Y(okElements)';

xcenter = (2*C*D-B*E)/(B^2-4*A*C);
ycenter = (2*A*E-B*D)/(B^2-4*A*C);
% xe = xe - xcenter;
% ye = ye - ycenter;
% plot(xe,ye,'o');

distances = sqrt((xcenter - xe).^2 + (ycenter - ye).^2);

[a,imax] = max(distances);
xmax = xe(imax);
ymax = ye(imax);
plot([xcenter, xmax], [ycenter, ymax]);

[b,imin] = min(distances);
plot([xcenter, xe(imin)], [ycenter, ye(imin)]);
c = sqrt(a^2 - b^2);
v = [-xcenter+xmax, -ycenter+ymax]*c/a;
F1 = [xcenter, ycenter] + v
plot(F1(1),F1(2),'o');
F2 = [xcenter, ycenter] - v
plot(F2(1),F2(2),'o');

r = 2*a;

count = 0;
points = zeros(length(in), 2);
for i = 1:length(in)
    l1 = sqrt( (in(1,i)-F1(1))^2 + (in(2,i)-F1(2))^2 );
    l2 = sqrt( (in(1,i)-F2(1))^2 + (in(2,i)-F2(2))^2 );
    if(l2 + l1 < r)
        count = count + 1;
        points(count,:) = [in(1,i),in(2,i)];
    end
end
count
plot(points(1:count,1), points(1:count,2), 'o')
% okElements = abs(Z)<0.001;
% xe = X(okElements)';
% xe = xe.^2;
% ye = Y(okElements)';
% ye = ye.^2;
% % plot(xe', ye', 'o')
% 
% g1 = @(x) sqrt((x(1)-xe)^2 + (x(2)-ye)^2) + sqrt((x(3)-xe)^2 + (x(4)-ye)^2)
% g = @(x) sum(abs( max(g1(x)) - min(sqrt((x(1)-xe)^2 + (x(2)-ye)^2) + ((x(3)-xe)^2 + (x(4)-ye)^2)));
