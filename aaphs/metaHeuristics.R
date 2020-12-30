#install.packages("metaheuristicOpt")
library(metaheuristicOpt)

#We will try to find the minimum value of Rastrigin function
Rastrigin <- function(x) {
  sum(x^2 - 10 * cos(2 * pi * x)) + 10 * length(x)
}

## Define control variable
control <- list(numPopulation=50, maxIter=1000)

rangeVar <- matrix(c(-5.12,5.12), nrow=2)

#Artificial Bee Colonly Optimization
#best.variable <- metaOpt(Rastrigin, optimType="MIN", algorithm="ABC", 30, rangeVar, control)
#best.variable

#Bat Algorithm
#best.variable <- metaOpt(Rastrigin, optimType="MIN", algorithm="BA", 30, rangeVar, control)
#best.variable

#Black Hole Optimization
#best.variable <- metaOpt(Rastrigin, optimType="MIN", algorithm="BHO", 30, rangeVar, control)
#best.variable

#Clonal Selection ALGorithm
#best.variable <- metaOpt(Rastrigin, optimType="MIN", algorithm="CLONALG", 30, rangeVar, control)
#best.variable

#Cuckoo Search Algorithm
#best.variable <- metaOpt(Rastrigin, optimType="MIN", algorithm="CS", 30, rangeVar, control)
#best.variable

#Cat Swarm Optimization
#best.variable <- metaOpt(Rastrigin, optimType="MIN", algorithm="CSO", 30, rangeVar, control)
#best.variable

#Dragonfly Algorithm
#best.variable <- metaOpt(Rastrigin, optimType="MIN", algorithm="DA", 30, rangeVar, control)
#best.variable

#Differential Evolution
best.variable <- metaOpt(Rastrigin, optimType="MIN", algorithm="DE", 30, rangeVar, control)
best.variable

#FireFly Algorithm
#best.variable <- metaOpt(Rastrigin, optimType="MIN", algorithm="FFA", 30, rangeVar, control)
#best.variable

#Genetic Algorithm
best.variable <- metaOpt(Rastrigin, optimType="MIN", algorithm="GA", 30, rangeVar, control)
best.variable

#Gravitational Based Search Algorithm
#best.variable <- metaOpt(Rastrigin, optimType="MIN", algorithm="GBS", 30, rangeVar, control)
#best.variable

#Grasshopper Optimisation Algorithm
#best.variable <- metaOpt(Rastrigin, optimType="MIN", algorithm="GOA", 30, rangeVar, control)
#best.variable

#Grey Wolf Optimizer
best.variable <- metaOpt(Rastrigin, optimType="MIN", algorithm="GWO", 30, rangeVar, control)
best.variable

#Harmony Search Algorithm
#best.variable <- metaOpt(Rastrigin, optimType="MIN", algorithm="HS", 30, rangeVar, control)
#best.variable

#Krill-Herd Algorithm
#best.variable <- metaOpt(Rastrigin, optimType="MIN", algorithm="KH", 30, rangeVar, control)
#best.variable

#Moth Flame Optimizer
#best.variable <- metaOpt(Rastrigin, optimType="MIN", algorithm="MFO", 30, rangeVar, control)
#best.variable

#Particle Swarm Optimization
best.variable <- metaOpt(Rastrigin, optimType="MIN", algorithm="PSO", 30, rangeVar, control)
best.variable

#Sine Cosine Algorithm
#best.variable <- metaOpt(Rastrigin, optimType="MIN", algorithm="SCA", 30, rangeVar, control)
#best.variable

#Shuffled Frog Leaping Algorithm
#best.variable <- metaOpt(Rastrigin, optimType="MIN", algorithm="SFL", 30, rangeVar, control)
#best.variable

#Whale Optimization Algorithm
best.variable <- metaOpt(Rastrigin, optimType="MIN", algorithm="WOA", 30, rangeVar, control)
best.variable


algorithms <- c("ABC", "ALO", "BA", "BHO", "CLONALG", "CS", "CSO", "DA", "DE", "FFA", "GA", "GBS", "GOA",
                "GWO", "HS", "KH", "MFO", "PSO", "SCA", "SFL", "WOA")

optimums <- vector()
times <- vector()
control <- list(numPopulation=50, maxIter=100) 
for(a in algorithms){
  print(paste("Working on", a))
  res <- metaOpt(Rastrigin, optimType="MIN", algorithm=a, 30, rangeVar, control)
  optimums <- c(optimums, res$optimumValue)
  times <- c(times, res$timeElapsed[1])
}

df <- data.frame(alg = algorithms, value = optimums, time = times)

plot(1:21, df$value, t = "l", ylab = "Optimization value", xlab = "")
points(1:21, df$value, pch = 1, col = "red")
text(1:21, df$value + 20, df$alg, col = "blue")

plot(1:21, df$time, t = "l", ylab = "Running time", xlab = "")
points(1:21, df$time, pch = 1, col = "red")
text(1:21, df$time + 1, df$alg, col = "blue")

plot(1:21, sort(df$value), t = "l", ylab = "Optimization value with time", xlab = "")
points(1:21, sort(df$value), pch = 1, col = "red")
text(1:21, sort(df$value + 20)+c(0,20,40), df$alg[order(df$value)], col = "blue")
lines(10*(df$time[order(df$value)]), col = "green")
points(10*(df$time[order(df$value)]), pch = 1, col = "red")



