format long

f = @(x)(3.*x.^3-exp(sin(x)));
rf = regulaFalsi(f, 0, 2, 8) 
bs = bisekcija(f, 0, 2, 8)
tocno = fzero(f, 1)

x = [1:20];
y = [1:20];
figure;hold on;
for i=1:size(x,2)
    y(i) = regulaFalsi(f,0,2,x(i));
end
plot(x, y);

for i=1:size(x,2)
    y(i) = bisekcija(f,0,2,x(i));
end
plot(x, y);
plot(x, tocno*ones(20,1));