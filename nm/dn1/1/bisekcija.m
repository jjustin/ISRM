function x = bisekcija(f, a, b, iMax)
    for step = 1:iMax
        a2 = f(a);
        b2 = f(b);

        x = (a+b)/2; % x = kx+n; y=0
        
        d = f(x);
        
        if sign(d) == sign(a2)
            a = x;
        elseif sign(d) == sign(b2)
            b = x;
        end
    end
end



