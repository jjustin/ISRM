griewank <- function(x) {
  1 + crossprod(x)/4000 - prod( cos(x/sqrt(seq_along(x))))
}

DE <- function(lower, upper, fn, Ff=1, CR=0.9, popSize = 100, tol = 1e-10, maxIter = 5){
  #generate initial population
  pop <- vector()
  for(p in 1:popSize){
    pop <- rbind(pop, apply(cbind(lower, upper), 1, function(x) runif(1,x[1], x[2])))
  }
  #save current best solution
  bestSolution <- rep(0, length(lower))
  bestValue <- Inf
  
  stoppingCriterion <- FALSE
  i <- 0
  
  while(!stoppingCriterion){
    j <- 0
    #find current best solution
    for(k in 1:popSize){
      t <- fn(pop[k,])
      if(t < bestValue){
        bestValue <- t
        bestSolution <- pop[k,]
      }
    }
    # print(bestValue)
    while(j < popSize){
      #choose a random dimension
      R <- sample(1:length(lower), 1)
      
      #pick vector x, a, b and c 
      #note: x can be just the example with index j so that each example is picked exactly once
      pickXabc <- sample(1:popSize, 4, replace = FALSE)
      x <- pop[pickXabc[1],]
      a <- pop[pickXabc[2],]
      b <- pop[pickXabc[3],]
      c <- pop[pickXabc[4],]
      
      #calculate new candidate
      candidate <- rep(0, length(lower))
      for(d in 1:length(lower)){
        if(d == R || runif(1,0,1) < CR){
          candidate[d] <- a[d] + Ff*(b[d]-c[d])
        }else{
          candidate[d] <- x[d]
        }
      }
      #replace the solution if it is better
      #print("new version")
      #print(fn(x))
      #print(fn(candidate))
      if(fn(x) > fn(candidate)){
        pop[pickXabc[1],] <- candidate
      }
      j <- j + 1
    }
    i <- i + 1
    if(i > maxIter) {
      stoppingCriterion <- TRUE
    }
  }

  return(list(Value = bestValue, sol = bestSolution, population = pop))
}

#DE(rep(-500, 10), rep(500, 10), griewank, F = 1, CR = 0.7, popSize = 20)
res <- vector
for(cr in seq(0, 1, 0.1)){
  t <- vector()
  r <- matrix(nrow = 10)
  for(ff in seq(0.1, 1, 0.1)){
    set.seed(1234)
    re <- DE(rep(-500, 10), rep(500, 10), griewank, F = ff, CR = cr, popSize = 100)
    t <- append(t, re$Value)
    r <- cbind(r, re$sol)
    # cat(sprintf("%f, %f: %f\n",
    #             cr, ff, val
    # ))
  }
  best = c(cr, (which.min(t)) * 0.1, min(t));
  print(best)
  print(r[,which.min(t) + 1])
  res <- append(res, best)
}

