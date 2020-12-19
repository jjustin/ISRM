format long;
% calculated by hand from [x,y]B(x,y)[x,y]^T
f = @(x,y) x.^5 - 5.*x.^2 - (y.^2) .* x + x .* y + y.^4 + 2.*y.*x.*y;

[X, Y] = meshgrid(-2:0.01:2, -2:0.01:2);
Z = f(X, Y);
figure; % 'ShowText','on'
surf(X, Y, Z, 'EdgeColor', 'none');
figure; % second window
contour(X, Y, Z, [-5,-3,-1,1,3,5], 'ShowText','on');
% is not connected for x >= 1

sum(Z < 1, 'all') % z is matrix - 'all' sums all elements