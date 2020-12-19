function x = regulaFalsi(f, a, b, iMax)
    for step = 1:iMax
        a2 = f(a);
        b2 = f(b);
        
        k = (a2-b2)/(a-b); % linear eq
        n = a2 - a*k;   % linear eq
        x = -n/k; % x = kx+n; y=0
        
        d = f(x);
        
        if sign(d) == sign(a2)
            a = x;
        elseif sign(d) == sign(b2)
            b = x;
        end
    end
end



