a = 1+0.014898; b = 1.5+0.014456; h = 0.016463;

function r = thomas(a, b, c, r)
    n = length(a);
    % in-place LU
    for k = 1:n-1
        c(k) = c(k) / a(k);
        a(k+1) = a(k+1) - c(k)*b(k);
    end
    
    % forward substitution
    for k = 2:n
        r(k) = r(k) - c(k-1)*r(k-1);
    end
    
    % backwards substitution
    r(n) = r(n) / a(n);
    for k = n-1:-1:1
        r(k) = (r(k) - b(k)*r(k+1)) / a(k);
    end
end
