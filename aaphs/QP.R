#quadratic programming
#install.packages("quadprog")
library(quadprog)

#min a - 2b + 4c + a^2 + 2b^2 + 3c^2 + ac
#subject to:
#3a + 4b - 2c <= 10
#-3a + 2b + c >= 2
#2a + 3b + 4c = 5
#0 <= a <= 5
#1 <= b <= 5
#0 <= c <= 5

#Package quadprog optimizes the follwing function
#min(-d^T b + 1/2 b^T D b)
#subject to:
#A^T b >= b_0

#First constraints can be equalites and the following constraits are all >=.

#Rewriting and reordining our constraints gives us
#2a + 3b + 4c = 5
#-3a - 4b + 2c >= -10
#-3a + 2b + c >= 2
#a >= 0
#-a >= -5
#b >= 1
#-b >= -5
#c >= 0
#-c >= -5

#The function call is defined as
#solve.QP(Dmat, dvec, Amat, bvec, meq)
#dvec = d
#Dmat = D
#bvec = b_0
#Amat = A
#meq is the number of equalities in constraints

#min a - 2b + 4c + a^2 + 2b^2 + 3c^2 + ac
#optimization variables
Dmat <- 2*matrix(c(1, 0, 0.5, # quad terms
                    0, 2, 0,
                    0.5, 0, 3), ncol = 3, byrow = TRUE)

dvec <- -c(1, -2, 4) # linear terms

#constraints
#2a + 3b + 4c = 5
#-3a - 4b + 2c >= -10
#-3a + 2b + c >= 2
#a >= 0
#-a >= -5
#b >= 1
#-b >= -5
#c >= 0
#-c >= -5
Amat <- matrix(c(2, 3, 4,
                 -3, -4, 2,
                 -3, 2, 1,
                 1, 0, 0,
                 -1, 0, 0,
                 0, 1, 0,
                 0, -1, 0,
                 0, 0, 1,
                 0, 0, -1
                 ), ncol = 3, byrow = TRUE)
Amat <- t(Amat) # transpose
bvec <- c(5, -10, 2, 0, -5, 1, -5, 0, -5)


dvec
Dmat

Amat
bvec

res <- solve.QP(Dmat, dvec, Amat, bvec, meq = 1) # meq -> number of equalities in Amat
res

#manual check
a <- res$solution[1]
b <- res$solution[2]
c <- res$solution[3]
a - 2*b + 4*c +a*a + 2*b*b+3*c*c+a*c

#Exercise

#Find the solution to the following problem:
#Minimize:  x + y + 2x^2 + 2xy + 2y^2
#x - y >= 3
#x + 2y >= 6
#3x - 4y = 10 
#4 <= x <= 20
#2 <= y <= 22


# 3x - 4y = 10 
#  x - y >= 3
# x + 2y >= 6
# -x >= -20
# x >= 4
# y >= 2
# -y >= -22

Dmat <- 2*matrix(c(2, 1,
                   1, 2), ncol=2, byrow = TRUE) 
dvec = -c(1,1)
Amat <- t(matrix(c(3, -4,
                   1, -1,
                   1, 2,
                   -1, 0,
                   1, 0,
                   0, 1,
                   0, -1
), ncol = 2, byrow = TRUE))
bvec <- c(10, 3, 6, -20, 4, 2, -22)


res <- solve.QP(Dmat, dvec, Amat, bvec, meq = 1) # meq -> number of equalities in Amat
res

