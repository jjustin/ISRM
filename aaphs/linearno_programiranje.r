#
#Linear programming
#
#A company makes two products A and B. The sales from product A must be at least 80% of all sales (A+B).
#The market is saturated with 100 products of type A. To produce one product A the
#company needs 2 kg of material and to product a product B it needs 4kg. 
#The company has 240kg available material. Profit form product A is 20$ and for
#product B it is 50$. How to maximize the company profit given these constraints. 

#Rewrite text into equations

#Maxsimize 20A + 50B 

#The text gives us the following constraints

#A >= 0.8(A+B)
#A <= 100
#2A + 4B <= 240
#A, B >= 0

#We will use a package lpSolve, due to this we have to change the form 
#of the first constraint

#0.8(A+B) - A <= 0
#0.8B - 0.2A <= 0

#optimization function
f.opt <- c(20, 50)

#constraints
f.con <- matrix(c(-0.2, 0.8, 
                  1, 0, 
                  2, 4, 
                  1, 0, # A >= 0
                  0, 1  # B >= 0
                  ), ncol = 2, byrow = TRUE)

#operators
f.dir <- c("<=", "<=", "<=", ">=", ">=")

#right hand side of constraints
f.rhs <- c(0, 100, 240, 0, 0)

#run the linear program with lpSolve

library(lpSolve)
#function lp returns the value of the best solution
rez <- lp("max", f.opt, f.con, f.dir, f.rhs)

#We can find the exact values of A and B
rez$solution

#rez Displays the max optimization value
rez

#-------------------------------------------------------------------------------
#Same exercise using lpSolveAPI

# Load lpSolveAPI
require(lpSolveAPI)

# Set an empty linear problem
linProgram <- make.lp(nrow = 0, ncol = 2)
# Set the linear program as a maximanization
lp.control(linProgram, sense="max")


# Set type of decision variables
set.type(linProgram, 1:2, type=c("real"))
# Set objective function coefficients vector C
set.objfn(linProgram, c(20, 50))

# Add constraints
add.constraint(linProgram, c(-0.2, 0.8), "<=", 0)
add.constraint(linProgram, c(1, 0), "<=", 100)
add.constraint(linProgram, c(2, 4), "<=", 240)
add.constraint(linProgram, c(1, 0), ">=", 0)
add.constraint(linProgram, c(0, 1), ">=", 0)



# Display the LPsolve matrix
linProgram

# Solve problem
solve(linProgram)

# Get the variables
get.variables(linProgram)
# Get the value of the objective function
get.objective(linProgram)

#---------------------------------------------------------------------------------

#Exercise 1
#Maximal flow

#You have a graf G(V,E), where each edge (u,v) of E has a positive flow c(u,v) >= 0.
#You also have a starting vertex s and terminal vertex t. Find the maximal flow from 
#s to t. 

#The graf consists of vertices V = {s,t,v1,v2,v3,v4,v5} and capacities:
#c(s, v1) = 20
#c(s, v4) = 7
#c(v1, v2) = 15
#c(v1, v3) = 5
#c(v2, t) = 12
#c(v2, v5) = 5
#c(v3, v4) = 2
#c(v3, v5) = 4
#c(v4, v1) = 3
#c(v4, v5) = 8
#c(v5, t) = 17
#find the maximal flow

# max koliko gre iz s / gre v t
# omejitve na:
# na povezavi je lahko max c(x,y)
# povezava vecja od 0
# v vozlisce = iz vozlisca

linProgram <- make.lp(nrow = 0, ncol = 11)
lp.control(linProgram, sense="max")
set.objfn(linProgram, c(1,1,rep(0, times=9)))

# v1
v = c(1,0,-1,-1,0,0,0,0,1,0,0)
add.constraint(linProgram, v, "=", 0)
#v2
v = c(0,0,1,0,-1,-1,0,0,0,0,0)
add.constraint(linProgram, v, "=", 0)
#v3
v = c(0,0,0,1,0,0,-1,-1,0,0,0)
add.constraint(linProgram, v, "=", 0)
#v4
v = c(0,1,0,0,0,0,1,0,-1,-1,0)
add.constraint(linProgram, v, "=", 0)
#v5
v = c(0,0,0,0,0,1,0,1,0,1,-1)
add.constraint(linProgram, v, "=", 0)
#c(s, v1) = 20
v = c(1,rep(0, times=10))
add.constraint(linProgram, v, "<=", 20)
add.constraint(linProgram, v, ">=", 0)
#c(s, v4) = 7
v = c(rep(0, times=1), 1,rep(0, times=9))
add.constraint(linProgram, v, "<=", 7)
add.constraint(linProgram, v, ">=", 0)
#c(v1, v2) = 15
v = c(rep(0, times=2), 1,rep(0, times=8))
add.constraint(linProgram, v, "<=", 15)
add.constraint(linProgram, v, ">=", 0)
#c(v1, v3) = 5
v = c(rep(0, times=3), 1,rep(0, times=7))
add.constraint(linProgram, v, "<=", 5)
add.constraint(linProgram, v, ">=", 0)
#c(v2, t) = 12
v = c(rep(0, times=4), 1,rep(0, times=6))
add.constraint(linProgram, v, "<=", 12)
add.constraint(linProgram, v, ">=", 0)
#c(v2, v5) = 5
v = c(rep(0, times=5), 1,rep(0, times=5))
add.constraint(linProgram, v, "<=", 5)
add.constraint(linProgram, v, ">=", 0)
#c(v3, v4) = 2
v = c(rep(0, times=6), 1,rep(0, times=4))
add.constraint(linProgram, v, "<=", 2)
add.constraint(linProgram, v, ">=", 0)
#c(v3, v5) = 4
v = c(rep(0, times=7), 1,rep(0, times=3))
add.constraint(linProgram, v, "<=", 4)
add.constraint(linProgram, v, ">=", 0)
#c(v4, v1) = 3
v = c(rep(0, times=8), 1,rep(0, times=2))
add.constraint(linProgram, v, "<=", 3)
add.constraint(linProgram, v, ">=", 0)
#c(v4, v5) = 8
v = c(rep(0, times=9), 1,rep(0, times=1))
add.constraint(linProgram, v, "<=", 8)
add.constraint(linProgram, v, ">=", 0)
#c(v5, t) = 17
v = c(rep(0, times=10), 1)
add.constraint(linProgram, v, "<=", 17)
add.constraint(linProgram, v, ">=", 0)

solve(linProgram)
# Get the variables
get.variables(linProgram)
# Get the value of the objective function
get.objective(linProgram)

#Exercise 2

#Write a function that will return the shortest path for graphs generated by make_my_graph()
library(tidyverse)
library(lpSolveAPI)
library(igraph)

make_my_graph <- function(v, e){
  from <- sample(2:(v+1), e, replace = T)
  to <- sample(2:(v+1), e, replace = T)
  t <- bind_cols(from = from, to = to) %>% distinct() %>% filter(from != to)
  print(t)
  e <- as.vector(t(t))
  make_empty_graph() %>%
    add_vertices(1, color = "green") %>%
    add_vertices(v, color = "red") %>%
    add_vertices(1, color = "green") %>%
    add_edges(c(1,sample(2:v, 1), e,sample(2:(v-1),1), v+2)) %>%
    set_edge_attr("cost", value = c(1,runif(length(e)/2, 0.1, 2), 1))
}

g <- make_my_graph(30, 55)
is_connected(g)
as_data_frame(g)

plot(g)

#Finish the following function
return_shortest_path <- function(g){
  
}