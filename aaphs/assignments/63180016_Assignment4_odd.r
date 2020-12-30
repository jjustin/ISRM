#U is universal set (usually 1:n)
#n - number of desired subsets
#minSize = minimum subset size
#maxSize = maximum subset size (last set can be an exception)
generateSubsets <- function(U, n, minSize = 1, maxSize = sqrt(length(U))){
  Subsets <- list()
  leftovers <- U
  for(i in 1:(n-1)){
    items <- sample(1:length(U), sample(minSize:maxSize,1))
    Subsets[[i]] <- U[items]
    indexes <- which(leftovers %in% U[items])
    if(length(indexes) != 0){
      leftovers <- leftovers[-which(leftovers %in% U[items])]
    }
  }
  if(length(leftovers) == 0){
    items <- sample(1:length(U), sample(minSize:maxSize,1))
    Subsets[[n]] <- U[items]
  }else{
    #Add all remaning elements if needed
    Subsets[[n]] <- leftovers 
    if(length(Subsets[[n]]) < maxSize) {
      candidates <- U[-which(Subsets[[n]] %in% U)]
      items <- sample(1:length(candidates), sample(1:(maxSize-length(Subsets[[n]])), 1))
      Subsets[[n]] <- c(Subsets[[n]], candidates[items])
    }
  }
  Subsets
}

calculateCosts <- function(S){
  costs <- c()
  for (si in S){
    costs <- append(costs, length(si))
  }
  costs
}

SCPgreedy <- function(U, S){
  costs <- calculateCosts(S)
  l <- length(S)
  # storage for current best solution
  bestR <- rep(1, l);
  costBestR <- Inf;

  # cost in first iteration of the inner loop is always the same for all elements
  # check for all possible choosings of the first subset to include
  for(firstIx in 1:l){
    r <- rep(0, times=l); # result - indexes of subsets
    r[firstIx] = 1
    C <- S[[firstIx]]; # coverage - elements already in result
    
    for (i in 2:l){
      # calculate cost effectiveness
      a <- c()
      for (sIx in 1:length(S)){
        subset <- S[[sIx]]
        a <- append(a, costs[sIx]/length(subset[!subset %in% C]))
      }
      
      # choose random from those with optimal cost effectivness
      bestIx <- which.min(a) 
      
      # apply best found subset
      r[bestIx] <- 1
      subset <- S[[bestIx]]
      C <- append(C, subset[!subset %in% C])
      
      # check if full coverage has been found
      if (length(U[!U %in% C]) == 0){
        break
      }
    }
    
    # check if full coverage has been found
    if (length(U[!U %in% C]) != 0){
      warning("Full coverage not found")
      break
    }

    #check if current solution is better than previous
    cost = sum(r*costs)
    if (cost < costBestR){
      costBestR <- cost
      bestR <- r
      
      # cant be more optimized than cost being equal to number of all elements
      if(cost == length(U)){ 
        break;
      }
    }
  }
  list(result=bestR, cost=sum(costBestR))
}

SCPlinear <- function(U, S){
  require(lpSolveAPI)
  nOfVariables = length(S)
  nOfConstrains = length(U)
  # Set an empty linear problem
  linProgram <- make.lp(nrow = 0, ncol = nOfVariables)
  # Set the linear program as a minimization
  lp.control(linProgram, sense="min")
  
  # Set type of decision variables
  set.type(linProgram, 1:nOfVariables, type=c("binary"))
  # Set objective function to costs of subsets
  costs=calculateCosts(S)
  set.objfn(linProgram, costs)
  
  x = integer()
  for(i in 1:nOfConstrains){
    constrain = integer()
    for(subset in S){
      constrain = append(constrain, length(subset[subset==i])) # 1 if xi is in subset, 0 if not
    }
    add.constraint(linProgram, constrain, ">=", 1)
  }
  solve(linProgram)
  r = get.variables(linProgram)
  list(result=r, cost=sum(r*costs))
}

n <- 20
k <- 10
U <- 1:n
S <- generateSubsets(U, k)
S
SCPgreedy(U, S)
SCPlinear(U,S)
