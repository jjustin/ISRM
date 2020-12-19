format long;
fu = @(x) x^3+x+2.5;
dfu = @(x) 3.*x^2;
ddfu = @(x) 6.*x;

x0 = 0.5;
fun = @(x) x.^2 + log(x);
z = fzero(fun, x0);
x = navadna_iteracija(@(x) exp(-(x.^2)), x0, 10^-5, 50);
x-z

x0 = -1;
z = fzero(fu, x0);
x = tangentna(fu, dfu, x0, 10^-8, 50)
x-z

x = sekantna(fu, x0, x0-0.3, 10^-8, 50)
x-z

x = fff(fu, dfu, ddfu, x0, 10^-8, 50)
x-z

x = halley(fu, dfu,ddfu, x0, 10^-8, 50)
x-z

function x = navadna_iteracija(g, x0, tol, maxiter)
        x = zeros(maxiter+1, 1);
        x(1)=x0;
        for n = 1:maxiter
            x(n+1) = g(x(n));
            if abs(x(n+1) - x(n)) < tol
                x = x(1:n+1);
                return
            end
        end
        warning('Napaka - najvecje dovoljeno stevilo korakov');
    end

function [it, n] = tangentna(f, df, x0, tol, maxiter)
    it = x0;

    for n = 1:maxiter
        it_prev = it;
        it = it - f(it)/df(it);
        if abs(it-it_prev) < tol
            return
        end
    end
    warning('Napaka - najvecje dovoljeno stevilo korakov');
end

function [x_curr, n] = sekantna(f, x0, x1, tol, maxiter)
    x_prev = x0;
    x_curr = x1;
    f_prev = f(x_prev);
    f_curr = f(x_curr);


    for n = 1:maxiter
        tmp = x_curr;
        x_curr = x_curr - (f_curr.*(x_curr-x_prev))/(f_curr - f_prev);
        x_prev = tmp;
        f_prev = f_curr;
        f_curr = f(x_curr);
        if abs(x_curr-x_prev) < tol
            return
        end
    end
    warning('Napaka - najvecje dovoljeno stevilo korakov');
end

function [it, n] = fff(f, df, ddf, x0, tol, maxiter)
    it = x0;

    for n = 1:maxiter
        fv = f(it);
        dfv = df(it);
        ddfv = ddf(it);
        it_prev = it;
        it = it - fv/dfv - ddfv.*(fv.^2)/(2*dfv.^3);
        if abs(it-it_prev) < tol
            return
        end
    end
    warning('Napaka - najvecje dovoljeno stevilo korakov');
end

function [it, n] = halley(f, df, ddf, x0, tol, maxiter)
    it = x0;

    for n = 1:maxiter
        fv = f(it);
        dfv = df(it);
        ddfv = ddf(it);
        it_prev=it;
        it = it - 2.*fv*dfv/(2.*dfv.^2-fv*ddfv);
        if abs(it-it_prev) < tol
            return
        end
    end
end
