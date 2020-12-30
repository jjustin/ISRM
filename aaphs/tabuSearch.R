# A simple example

library(tabuSearch)  # load the library to R (install.packages(tabuSearch) if you haven't done that yet)
evaluateSimple <- function(th)return(1) # a simple objective function that always returns 1 (all solutions are equal)
result <- tabuSearch(size = 20, iters = 100, objFunc = evaluateSimple)
plot(result)

#Example:
#We will use tabu search for feature selection on artificial data

# simulate 10-dimensional data: 150 samples from 3 bivariate normals and 8 noise variables.
# Feature selection should recover the first two variables
library(MASS)
NF <- 10 #number of features
G <- 3 #number of classes
NTR <- 50 #number of training examples
NTE <- 50 #number of testing examples
#cluster centers
muA <- c(1,3)
SigmaA <- matrix(c(0.2, 0.04, 0.04, 0.2), 2, 2)
muB <- c(1.2,1)
SigmaB <- matrix(c(0.1, -0.06, 0.004, 0.2), 2, 2)
muC <- c(3,2)
SigmaC <- matrix(c(0.2, 0.004, 0.004, 0.2), 2, 2)
train <- rbind(mvrnorm(NTR, muA, SigmaA), mvrnorm(NTR, muB, SigmaB), mvrnorm(NTR, muC, SigmaC))
test <- rbind(mvrnorm(NTE, muA, SigmaA), mvrnorm(NTE, muB, SigmaB), mvrnorm(NTE, muC, SigmaC))
train <- cbind(train, matrix(runif(G * NTR * (NF - 2), 0, 4), nrow = G * NTR, ncol = (NF-2)))
test <- cbind(test, matrix(runif(G * NTE * (NF - 2), 0, 4), nrow = G * NTE, ncol = (NF-2)))
wtr <- as.factor(rep(1:G, each = NTR))
wte <- as.factor(rep(1:G, each = NTE))
pairs(train, col = wtr)  # draw the constructed dataset

library(e1071)
#define evaluation function
#evaluation funcion is based on the accuracy of support vecotr machines and penalty based on the number of selected features
evaluate <- function(th){
  if (sum(th) == 0)return(0)
  model <- svm(train[ ,th==1], wtr , gamma = 0.1)
  pred <- predict(model, test[ ,th==1])
  csRate <- sum(pred == wte)/NTE
  penalty <- (NF - sum(th))/NF
  return(csRate + penalty)
}

#find the best combination of features using tabu search
res <- tabuSearch(size = NF, iters = 50, objFunc = evaluate, config = matrix(1,1,NF),
                  listSize = 5, nRestarts = 4)
plot(res)
plot(res, "tracePlot")
summary(res, verbose = TRUE)




# Exercises

# As last time solve the 0-1 backpack problem but this time using tabu search
# Size of the problem
n <- 50
# item prices
prices <- runif(n, 10, 40)
# item weights
weights <- runif(n, 5, 10)
# Maxsimum weight
maxWeight <- sum(weights)*0.6
sq <- sample(c(rep(0, n/2), rep(1, n/2))) #starting sequence



#Find minimum vertex cover for a randomly generated graph using tabu search and simulated annealing
library(igraph)

create_graph <- function(v, e){
  from <- sample(1:v, e, replace = T)
  to <- sample(1:v, e, replace = T)
  t <- cbind(from = from, to = to) 
  e <- as.vector(t(t))
  g <- make_empty_graph(directed = F) 
  g <- add_vertices(g, v, color = "red")
  g <- g + edges(e)
  g
}
plot_solution_index <- function(g, solution){
  g <- set_vertex_attr(g, "color", index = V(g), "yellow")
  g <- set_vertex_attr(g, "color", index = solution, "green")
  g <- set_edge_attr(g, "color", index = E(g), "red")
  g <- set_edge_attr(g, "color", index = E(g)[inc(solution)], "green")
  plot(g)
}
plot_solution <- function(g, solution){
  plot_solution_index(g, which(solution == 1))
}

g <- create_graph(30, 100)
sol <- sample(0:1, 30, replace = TRUE, prob = c(0.7, 0.3))

plot(g)
plot_solution(g, sol)


