function [L, U] = lu_razcep(A)
    n = size(A, 1);
    L = eye(n);
    for j = 1:n-1
       for i = j+1:n
           L(i, j) = A(i, j) / A(j, j);
           for k = j+1:n
               A(i, k) = A(i, k) - L(i, j)*A(j, k);
           end
       end
    end
    U = triu(A);
end