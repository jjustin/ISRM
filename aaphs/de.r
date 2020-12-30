#install.packages("DEoptim")
#library("DEoptim")
install.packages("DEoptimR")
library("DEoptimR")

#We will try to find the minimum value of Rastrigin function
Rastrigin <- function(x) {
  sum(x^2 - 10 * cos(2 * pi * x)) + 10 * length(x)
}

plotRastrigin <- function(){
  #Plot 2D Rastrigin function
  x <- seq(-5, 5, 0.1)
  y <- x
  z <- matrix(rep(0, length(x)*length(y)), nrow = length(x))
  for (i in 1:length(x)){
    for (j in 1:length(y)){
      z[i,j] <- Rastrigin(c(x[i], y[j]))
    }
  }
  #Graph coloring
  #install.packages("lattice")
  library(lattice)
  jet.colors <- colorRampPalette( c("blue", "red") ) 
  nbcol <- 100
  color <- jet.colors(nbcol)
  nrz <- nrow(z)
  ncz <- ncol(z)
  zfacet <- z[-1, -1] + z[-1, -ncz] + z[-nrz, -1] + z[-nrz, -ncz]
  facetcol <- cut(zfacet, nbcol)
  #Plot
  persp(x,y,z, phi = 30, col = color[facetcol])
}

plotRastrigin()

#Define 30 dimensional Rastrigin function
set.seed(1234)
dimension <- 30
#Set the domain bounds
lower <- rep(-5.12, dimension)
upper <- rep(5.12, dimension)

#Default values of parameters
#JDEoptim(lower, upper, fn,                                     initial parameter bounds (lower, upper) and function (fn) to be optimized
#         constr = NULL, meq = 0, eps = 1e-05,                  constr: additional constraints, meq: number of equality constraints eps: tolerance for equality constraints
#         NP = 10*d, Fl = 0.1, Fu = 1,                          NP: number of candidate solutions Fl,Fu: minimal and maximal scalar value
#         tau1 = 0.1, tau2 = 0.1, tau3 = 0.1,                   probabilites that some internal parameters are updated, which are scaling factor F, crossover probaility CR and mutation probability Pf, respectivly
#         jitter_factor = 0.001,                                
#         tol = 1e-15, maxiter = 200*d, fnscale = 1,            tol: tolerance for the stopping criterion, maxiter: maximum number of iteration, fnscale: scaling factor for function fn
#         FUN = c("median", "max"),                             which function should be used for stopping criterion
#         add_to_init_pop = NULL, trace = FALSE, triter = 1,    add_to_init_pop: inital population values, trace: trace log on off, triter: how often should trace be printed
#         details = FALSE, ...)                                 details: if TRUE results will contain the parameters and fn values

res <- JDEoptim(lower, upper, Rastrigin, trace = TRUE, triter = 100)
res

#Example using constraints

fcn <- 
  list(obj = function(x) {
    35*x[1]^0.6 + 35*x[2]^0.6  #function to be optimized
  },
  eq = 2,  #number of equality constraints
  con = function(x) {
    x1 <- x[1]; x3 <- x[3]
    c(600*x1 - 50*x3 - x1*x3 + 5000,  #acutal constraints
      600*x[2] + 50*x3 - 15000)
  })

JDEoptim(c(0, 0, 100), c(34, 17, 300),
         fn = fcn$obj, constr = fcn$con, meq = fcn$eq,
         tol = 1e-7, trace = TRUE, triter = 50)

#Exercise 1
#a) maximize function: -1*(5 - (x[1] - 2)^2 - 2*(x[2]-1)^2)           Solution:(2,1)
f <- function(x){
  -1*(5 - (x[1] - 2)^2 - 2*(x[2]-1)^2)
}
lower <- rep(-10, 2)
upper <- rep(10, 2)
res <- JDEoptim(lower, upper, f, trace = TRUE, triter = 100)
res

#b) add constraint x1 + 4*x2 = 3                                      Solution:(5/3, 1/3)
con <- function(x){
  c(x[1] + 4*x[2] -3)
}
res <- JDEoptim(lower, upper, f, constr=con, meq = 1, trace = TRUE, triter = 100)
res

#Exercise 2
#Minimize function: x1 + x2 + x3^2
#Add two constraints:
#x1 = 1
#x1^2 + x2^2 = 1                                                      Solution:(1, 0, 0)
f <- function(x){
  x[1] + x[2] + x[3]^2  
}
con <- function(x) {
  c(x[1] - 1,
    x[1] ^ 2 + x[2]^2 -1)
}
lower <- rep(-10, 3)
upper <- rep(10, 3)
res <- JDEoptim(lower, upper, f, trace = TRUE, triter = 100, constr = con, meq = 2)
res

#Exercise 3
#You are given griewank function. Find the optimal values of paramters F and CR
#so that the function evaluates minimum number of times.
#Use 10 dimensional function bounded by [-500, 500]
#Note: package DEoptimR is not suitable for this exercise since it is dynamically ajusting parameters F and CR
griewank <- function(x) {
  1 + crossprod(x)/4000 - prod(cos(x/sqrt(seq_along(x))))
}

