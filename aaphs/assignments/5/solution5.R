library(GenSA)
set.seed(1234)

#############################################
# Load/save/graph manipulation
#############################################
load<-function(){
  results <<- list(list(cost=Inf, solution=c()),list(cost=Inf,solution=c()),list(cost=Inf,solution=c()))
  
  readData <<- read.delim(sprintf("Problem%d.txt", problemIx), header = FALSE, fill = TRUE, sep = ",")
  maxWeight <<- readData[1, 2]
  nOfSites <<- readData[1, 1]
  connsRaw <<- readData[(nOfSites+2):length(readData[[1]]),1:5]
  conns <<- list()
  for(v in 1:nOfSites){
    # find reversed
    conns1 <- connsRaw
    rev <- which(connsRaw[,2]==v & connsRaw[,4]==0)
    conns1[rev,] <- connsRaw[rev,][c(2,1,3:5)]
    conns1 <- conns1[conns1$V1==v,]
    toKeep <- rep(T, nrow(conns1))
    for(i in 1:nrow(conns1)){
      e <- conns1[i,]
      c <- conns1[-i,]
      c <- c[c$V2==e[1,2] & c$V1==e[1,1],]
      better <- (c[,3]<e[1,3] & c[,5]>=e[1,5])
      toKeep[i] <- !any(better)
    } 
    conns[[v]] <<- conns1[toKeep,]
  }
}
setTrashType <- function(i){
  sites <<- readData[(1:nOfSites)+1 ,c(2:3,3+i)]
}
save <- function(){
  writeLines(c(results[[1]]$solution,results[[2]]$solution,results[[3]]$solution), sprintf("solution%d.txt", problemIx))
}
saveStates <- function(){
  writeLines(paste(results[[1]]$state, collapse = ","), sprintf("seed%d_1.txt", problemIx))
  writeLines(paste(results[[2]]$state, collapse = ","), sprintf("seed%d_2.txt", problemIx))
  writeLines(paste(results[[3]]$state, collapse = ","), sprintf("seed%d_3.txt", problemIx))
}

parseSq <- function(sq, trashType){
  out <- c()
  cols <- sq[3]
  nOfTrucks <- sq[1]
  for(truck in 1:nOfTrucks){
    out <- c(out, paste(c(trashType, pathOfTruck(truck, sq, cols)), collapse = ","))
  }
  out
}


#############################################
# Graph functions
#############################################
shortestPath <- function(from, to, w, trash=getTrash()){
  Q <- 1:nOfSites
  dist <- rep(Inf, nOfSites)
  weig <- rep(Inf, nOfSites)
  prev <- rep(NA, nOfSites)
  dist[from] <- 0
  weig[from] <- w
  
  time1 <- 0;
  while(length(Q) != 0){
    u <- which.min(dist)
    w <- weig[u]
    Q <- Q[Q!=u]
    
    if(u == to){
      out <- c()
      while(u != from){
        u <- prev[u]
        out <- c(u, out)
      }
      spt1 <<- append(spt1, time1)
      return(out)
    }
    
    neighs <- E(u, w) 
    neighs <- neighs[neighs[,2] %in% Q,]
    l <- nrow(neighs)
    if(l >0){
      for(i in 1:l){
        e <- neighs[i,]
        v <- e[1,2]
        
        alt <- dist[u] + e[,3]
        if(w+trash[u] <= maxWeight){
          w <- w+trash[u]
        }
        
        if(alt < dist[v]){
          dist[v] <- alt
          prev[v] <- u
          weig[v] <- w
        }
      }
    }
      #   t1 <- Sys.time()
      # time1 <- time1 +  Sys.time() -t1

    dist[u] <- Inf
  }
  if(to != Inf){
    warning("No path found: ", from, to, w)
  }
  return(prev)
}

E <- function(v, w=0){
  t <- Sys.time()
  if(w==0){
    return(conns[[v]])
  }
  c <- conns[[v]]
  out<- c[c[,5]>=w,]
  et1 <<- append(et1, Sys.time() - t)
  return (out)
  }

#############################################
# Helpers
#############################################
pathOfTruck <- function(truckIx, sq, cols){
  v <- sq[(4 + (truckIx-1)*cols):(3+truckIx*cols)]
  v[v != -1]
}
getTrash <- function(){
  trash <- sites[,3]
}

#############################################
# Cost
#############################################
cost <- function(sq){
  time <- 0
  start_time <- Sys.time()
  nOfTrucks <- sq[1]
  rows <- sq[2]
  cols <- sq[3]
  
  trash <- getTrash()
  
  c <- 0 #cost
  for(i in 1:nOfTrucks){
    c <- c+10
    w <- 0 # weight of current truck
    roadLen <- 0 #length of ride
    t <- 1/2 #time (unload counted in)
    route <- pathOfTruck(i, sq, cols)
    if(route[length(route)] != 1 && route[1] != 1){
      warning("Last or first stop is not firm")
      return(Inf)
    }
    
    for(j in 2:length(route)){
      r <- route[j]
      rprev <- route[j-1]
      # move
      edgs <- E(rprev,w)
      edgs <- edgs[edgs$V2==r,]
      if(nrow(edgs) == 0 ){
        warning("NonExistent connection used: ", rprev, "->", r)
        return(Inf)
      }
      roadLen <- roadLen + min(edgs$V3)
      if(trash[r] != 0){ # dont count coming back and already picked up
        newW <- w + trash[r]
        if(newW <= maxWeight){
          w <- newW
          trash[r] <- 0
          t <- t + 1/5
        }
      }
    }
    if(w == 0){
      warning("Nothing picked up: ", i)
    }
    c <- c + roadLen * 0.1
    t <- t + roadLen/50 # 50 kmh
    if(t>8){
      c <- c + 20*(t-8) + 80
    }else{
      c <- c + 10*t
    }
  }
  
  time <- time + Sys.time() - start_time 
  costTimes <<- c(costTimes, time)
  
  if(sum(trash) != 0){
    warning("Not all trash was collected: ", trash)
    return(Inf)
  }
  return(c)
}
#############################################
# Starting
#############################################
generateStarting <- function(){
  trash <- getTrash()
  rows <- length(trash)-1
  cols <- 10*rows
  out <- c(rows, rows, cols) # nOfTrucks, rows, cols
  tree <- shortestPath(1, Inf, 0)
  todo <- which(tree==1)
  for(i in 2:length(trash)){
    j <- todo[1]
    if(length(todo) == 1){
      todo <- which(tree==j)
    }else{
      todo <- c(todo[2:length(todo)], which(tree==j)) # remove first element and append sites reachable from j
    }

    v <- c(shortestPath(1,j,0), shortestPath(j,1, trash[j]),1)
    out <- c(out, v, rep(-1, cols - length(v)))
  }
  out
}

#############################################
# running
#############################################
run <- function(n=3){
  for(i in 1:n){
    cat("Generating starting soltuion \n")
    setTrashType(i)
    sq <- generateStarting()
    for(j in 1:params[[problemIx]]$retries){
      currentState <- c(params[[problemIx]]$maxit, params[[problemIx]]$temp, params[[problemIx]]$tmax,.Random.seed)
      cat("Starting optim for trashType",i,"loop",j,"/",params[[problemIx]]$retries,"\n")
      res <<- optim(sq, cost, genNeigh, method = "SANN", 
                   control = list(maxit = params[[problemIx]]$maxit, temp = params[[problemIx]]$temp, trace = TRUE, 
                                  tmax = params[[problemIx]]$tmax, REPORT = 1))
      cat("Found solution with cost", res$value,"for trash type",i,"\n")
      if(results[[i]]$cost > res$value){
        results[[i]]$cost <<- res$value
        results[[i]]$solution <<- parseSq(res$par, i)
        results[[i]]$sq <<- res$par
        results[[i]]$state <<- currentState
      }
    }
  }
}

params <- list(
  list(retries=1, maxit=100, tmax=20, temp=10), #1
  list(retries=1, maxit=500, tmax=20, temp=10), #2
  list(retries=1, maxit=700, tmax=20, temp=10), #3
  list(retries=50, maxit=3000, tmax=20, temp=10), #4
  list(retries=30, maxit=2500, tmax=20, temp=10), #5
  list(retries=20, maxit=4000, tmax=20, temp=10), #6
  list(retries=3, maxit=2500, tmax=30, temp=10), #7
  list(retries=3, maxit=2000, tmax=30, temp=10), #8
  list(retries=3, maxit=1500, tmax=30, temp=10), #9
  list(retries=10, maxit=5000, tmax=20, temp=10) #10
)
resetTimes()
problemIx <<- 3
load()
run()
results[[1]]$cost+ results[[2]]$cost+results[[3]]$cost
save()
saveStates()
