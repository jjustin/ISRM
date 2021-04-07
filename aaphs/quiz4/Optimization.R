#########################################################################################
#
#Examples of using simulated annealing
#
#########################################################################################

#R already has a package for using general simulated annealing. We will use package GenSA
#install.packages("GenSA")
library(GenSA)

#Define Rastrigin function fow which we will try to find minimum value
#Rastrigin function is continious and "jagged", meaning it has many 
#local maxima and minima
Rastrigin <- function(x) {
  sum(x^2 - 10 * cos(2 * pi * x)) + 10 * length(x)
}
#Compute Rastigin function for two dimensional vector
# on domain x = y = [-5, +5]
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
ncz <- ncol(z)
nrz <- nrow(z)
zfacet <- z[-1, -1] + z[-1, -ncz] + z[-nrz, -1] + z[-nrz, -ncz]
facetcol <- cut(zfacet, nbcol)
#3D graph of Rastrigin function
persp(x,y,z, phi = 30, col = color[facetcol])

#For the next example we will use 30-dimensional Rastrigin function
#Set the same seed so we get the exact same results
set.seed(1234)
#Set the number of dimensions and the value of global minima (known in advance)
dimension <- 30
global.min <- 0
#Tolerance
tol <- 1e-13
#Set the search domain Xi = [-5.12, 5.12] for i = 1...30
lower <- rep(-5.12, dimension)
upper <- rep(5.12, dimension)
#Run simulated annealing
out <- GenSA(lower = lower, upper = upper, fn = Rastrigin, control=list(threshold.stop=global.min+tol,verbose=TRUE))
#Results
out[c("value","par","counts")]

# Combinatorial optimation of traveling salesman problem
library(stats)
#eurodist incudes the data about distances between european cities
#we can transform distances into matrix
eurodistmat <- as.matrix(eurodist)

#create initial solution
sq <- c(1:nrow(eurodistmat), 1) 


#function to compute the distance of the cycle given by sq
distance <- function(sq) {  # Target function
  sq2 <- embed(sq, 2)
  sum(eurodistmat[cbind(sq2[,2], sq2[,1])])
}

#function that generates neighbors (starts and ends in city with index 1)
genseq <- function(sq) { 
  #generate city indices
  idx <- seq(2, NROW(eurodistmat)-1)
  #select two indices
  changepoints <- sample(idx, size = 2, replace = FALSE)
  #swap the selected cities
  tmp <- sq[changepoints[1]]
  sq[changepoints[1]] <- sq[changepoints[2]]
  sq[changepoints[2]] <- tmp
  sq
}


#distance of the inital solution
distance(sq)

#find the 2d coordinates
loc <- -cmdscale(eurodist, add = TRUE)$points #PCA
x <- loc[,1]; y <- loc[,2]
s <- seq_len(nrow(eurodistmat))
tspinit <- loc[sq,]
#draw the cities and the inital solution
plot(x, y, type = "n", asp = 1, xlab = "", ylab = "",
     main = "initial solution of traveling salesman problem", axes = FALSE)
arrows(tspinit[s,1], tspinit[s,2], tspinit[s+1,1], tspinit[s+1,2],
       angle = 10, col = "green")
text(x, y, labels(eurodist), cex = 0.8)

set.seed(1234)
#optim function (from stats package) can also run simulated annealing
#it needs inital solution, criterion function and a neighbourhood function
res <- optim(sq, distance, genseq, method = "SANN", control = list(maxit = 30000, temp = 2000, trace = TRUE, tmax = 20, REPORT = 500))
res  #found solution

tspres <- loc[res$par,]
plot(x, y, type = "n", asp = 1, xlab = "", ylab = "",
     main = "optim() 'solving' traveling salesman problem", axes = FALSE)
arrows(tspres[s,1], tspres[s,2], tspres[s+1,1], tspres[s+1,2],
       angle = 10, col = "red")
text(x, y, labels(eurodist), cex = 0.8)

#generation of all neigbours (global variables in R)
#adopt the previous example so that it generates all the neighbours
#deterministic function to generate all the neighbrours
firstcol <- vector()
secondcol <- vector()
for(i in 2:(nrow(eurodistmat)-1)){
  firstcol <- c(firstcol,rep(i, nrow(eurodistmat) - i)) 
  secondcol <- c(secondcol, seq(i+1, nrow(eurodistmat)))
}
#matrix of all posible swaps
indmat <- matrix(c(firstcol, secondcol), ncol = 2)
sosed <<- 1
gendetseq <- function(sq) { 
  changepoints <- indmat[sosed,]
  #swap two cities
  tmp <- sq[changepoints[1]]
  sq[changepoints[1]] <- sq[changepoints[2]]
  sq[changepoints[2]] <- tmp
  sosed <<- sosed + 1
  if(sosed > nrow(indmat))
    sosed <<- 1
  return(sq)
}
#run with the new neighbourhood function
sosed <<- 1 #each run needs to set the global variable
res <- optim(sq, distance, gendetseq, method = "SANN", control = list(maxit = 30000, temp = 2000, trace = TRUE, tmax = nrow(indmat), REPORT = 5))
res  #dobljena re???itev

tspres <- loc[res$par,]
plot(x, y, type = "n", asp = 1, xlab = "", ylab = "",
     main = "optim() 'solving' traveling salesman problem", axes = FALSE)
arrows(tspres[s,1], tspres[s,2], tspres[s+1,1], tspres[s+1,2],
       angle = 10, col = "red")
text(x, y, labels(eurodist), cex = 0.8)

# Exercise 1
# Using simulated annealing solve the 0-1 backpack problem
# Size of the problem
n <- 50
# Item prices
prices <- runif(n, 10, 40)
# Item weights
weights <- runif(n, 5, 10)
# Maximum weight
maxWeight <- sum(weights)*0.6
sq <- sample(c(rep(0, n/2), rep(1, n/2)))
# Complete the function to compute the value of a backpack
objectiveFunction <- function(sq){
  
}

# Exercise 1 a)
# Find a simple neighbourhood and generate "random" neighbours

# Exercise 1 b)
# Generate all the neighbours

# Exercise c)
# Select a wider neighbourhood (add or remove 2,3,4,..., n elements at once)
