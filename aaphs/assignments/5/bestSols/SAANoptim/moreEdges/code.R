library(GenSA)
library(rlist)
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
    for(u in 1:nOfSites){
    #   # u -> v connection
    #   conns1 <- connsRaw
    #   rev <- which(conns1$V1==v & conns1$V2 == u & conns1$V4 == 0)
    #   conns1[rev,] <- conns1[rev,][c(2,1,3:5)]
    #   conns1 <- conns1[conns1$V1 == u & conns1$V2 == v,]
    #   toKeep <- rep(T, nrow(conns1))
    #   for(i in 1:nrow(conns1)){
    #     e <- conns1[i,]
    #     c <- conns1[-i,]
    #     c <- c[c$V2==e[1,2] & c$V1==e[1,1],]
    #     better <- (c[,3]<e[1,3] & c[,5]>=e[1,5])
    #     toKeep[i] <- !any(better)
    #   }
    #   
    #   conns[[u]] <<- conns1[toKeep,]
    }
  }
}
setTrashType <- function(i){
  sites <<- readData[(1:nOfSites)+1 ,c(2:3,3+i)]
}
loadState <- function(trashType){
 unlist(read.delim(sprintf("seed%d_%d.txt", problemIx,i), header = FALSE, fill = TRUE, sep = ","),use.names=FALSE)
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
resetTimes <- function(){
  neigTimes <<- double()
  neigTimesAdd <<- double()
  neigTimesSwitch <<- double()
  nt1 <<- double()
  nt2 <<- double()
  spt1 <<- double()
  et1 <<- double()
  costTimes <<- double()
}
printTimes <- function(){
  cat("neigTimes",mean(neigTimes),"\n")
  cat("neigTimesAdd",mean(neigTimesAdd),"\n")
  cat("neigTimesSwitch",mean(neigTimesSwitch),"\n")
  cat("nt1",mean(nt1),"\n")
  cat("nt2",mean(nt2),"\n")
  cat("spt2",mean(spt1),"\n")
  cat("et1",mean(et1),"\n")
  cat("costTimes",mean(costTimes),"\n")
}
getTrash <- function(){
  trash <- sites[,3]
}

#############################################
# Cost
#############################################
cost <- function(sq){
  time <- 0
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
      start_time <- Sys.time()
      edgs <- E(rprev,w)
      time <- time + Sys.time() - start_time 
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
  
  costTimes <<- c(costTimes, time)
  
  if(sum(trash) != 0){
    # warning("Not all trash was collected: ", trash)
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
# Neighborhood
#############################################
genNeigh <- function(sq){
  start_time <- Sys.time()
  selector <- sample(1:100, 1)
  if(selector <= 45){
    sqn <- genAddStop(sq)
    end_time <- Sys.time()
    neigTimesAdd <<- c(neigTimesAdd, end_time-start_time)
  }else{
    sqn <- genSwitch(sq)
    end_time <- Sys.time()
    neigTimesSwitch <<- c(neigTimesSwitch, end_time-start_time)
  }
  sqn
}
genAddStop <- function(sq){
  sq2 <- sq
  nOfTrucks <- sq[1]
  nOfTrucksNew <- sq[1]
  rows <- sq[2]
  cols <- sq[3]
  time1 <- 0;
  time2 <- 0;
  trash <- getTrash()
  
  switchTruck <- sample(1:nOfTrucks,1) # which truck takes extra turn
  extraStop <- sample(1:(length(pathOfTruck(switchTruck, sq, cols))-1),1) # on which stop to make an extra turn
  
  
  c <- 0 #cost
  for(truckIx in 1:nOfTrucks){
    w <- 0 # weight of current truck
    route <- pathOfTruck(truckIx, sq, cols)
    if(truckIx == switchTruck){
      # calculate how much has been collected
      for(j in 1:extraStop){
        r <- route[j]
        if(trash[r] != 0){ 
          newW <- w + trash[r]
          if(newW <= maxWeight){
            w <- newW
            trash[r] <- 0
          }
        }
      }
      
      # construct new path
      e <- E(route[extraStop], w)$V2
      if(extraStop != 1){
        e <- e[e!=route[extraStop-1]] # not the same as prev
      }
      e <- e[e!=route[extraStop+1]] # not the same as next
      if(length(e) == 0){
       return(sq) # if there is only one neighbour
      }
      nextStop <- sample(e,1) # which road we choose as next
      t <- Sys.time()
      path <- shortestPath(nextStop, route[extraStop+1], w) # find shortest path from new next to old next
      time1 <- time1 + Sys.time()-t
      
      route <- append(route, path, after=extraStop)
      if(length(route) >= cols){
        return(sq)
      }else{
        ixStart <- 4 + cols*(truckIx-1)
        ixEnd <- ixStart + length(route) -1
        sq2[ixStart:ixEnd] <- route
      }
      
      # calculate how much has been collected after extra stop
      for(j in (extraStop+1):length(route)){
        r <- route[j]
        if(trash[r] != 0){ 
          newW <- w + trash[r]
          if(newW <= maxWeight){
            w <- newW
            trash[r] <- 0
          }
        }
      }
    }else{
      # simulate current truck
      for(j in 2:length(route)){
        r <- route[j]
        if(trash[r] != 0){ 
          newW <- w + trash[r]
          if(newW <= maxWeight){
            w <- newW
            trash[r] <- 0
          }
        }
      }
    }
    
    # if no trash was collected, remove current truck
    if(w == 0){
      nOfTrucksNew <- nOfTrucksNew-1
      ixStart <- 4 + cols*(truckIx-1)
      ixEnd <- ixStart + cols -1
      sq2[ixStart:ixEnd] <- rep(NA, ixEnd-ixStart+1)
    }
  }
  # removed truck paths now have NA fields - ommit them and add that many -1 to the end
  sq2 <- sq2[!is.na(sq2)]
  sq2[1] <- nOfTrucksNew
  if(length(sq2) != length(sq)){
    sq2[(length(sq2)+1):length(sq)] <- -1
  }
  nt1<<-append(nt1, time1)
  sq2
}
neigRemUnused <- function(sq){
  sq2 <- sq
  nOfTrucks <- sq[1]
  nOfTrucksNew <- sq[1]
  rows <- sq[2]
  cols <- sq[3]
  time1 <- 0;
  time2 <- 0;
  trash <- sites[,3]
  
  c <- 0 #cost
  for(truckIx in 1:nOfTrucks){
    w <- 0 # weight of current truck
    route <- pathOfTruck(truckIx, sq, cols)
    
    # simulate current truck
    for(j in 2:length(route)){
      r <- route[j]
      rprev <- route[j-1]
      
      if(trash[r] != 0){ 
        newW <- w + trash[r]
        if(newW <= maxWeight){
          w <- newW
          trash[r] <- 0
        }
      }
    }
    
    # if no trash was collected, remove current truck
    if(w == 0){
      nOfTrucksNew <- nOfTrucksNew-1
      ixStart <- 4 + cols*(truckIx-1)
      ixEnd <- ixStart + cols -1
      sq2[ixStart:ixEnd] <- rep(NA, ixEnd-ixStart+1)
    }
  }
  # removed truck paths now have NA fields - ommit them and add that many -1 to the end
  sq2 <- sq2[!is.na(sq2)]
  sq2[1] <- nOfTrucksNew
  if(length(sq2) != length(sq)){
    sq2[(length(sq2)+1):length(sq)] <- -1
  }
  sq2
}
genSwitch <- function(sq){
  idx <- sort(sample(1:sq[1], 2))
  startIx1 <- 4 + sq[3]*(idx[1]-1)
  startIx2 <- 4 + sq[3]*(idx[2]-1)
  endIx1 <- startIx1 + sq[3] -1
  endIx2 <- startIx2 + sq[3] -1
  v1 <- sq[startIx1:endIx1]
  v2 <- sq[startIx2:endIx2]
  sq[startIx1:endIx1] <- v2
  sq[startIx2:endIx2] <- v1
  neigRemUnused(sq)
}

#############################################
# running
#############################################
run <- function(){
  for(i in 1:3){
    cat("Generating starting soltuion \n")
    setTrashType(i)
    sq <- generateStarting()
    for(j in 1:params[[problemIx]]$retries){
      resetTimes()
      
      currentState <- c(params[[problemIx]]$maxit, params[[problemIx]]$temp, params[[problemIx]]$tmax,.Random.seed)
      cat("Starting optim for trashType",i,"loop",j,"/",params[[problemIx]]$retries,"\n")
      res <- optim(sq, cost, genNeigh, method = "SANN", 
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
  list(retries=1, maxit=1000, tmax=20, temp=10), #5
  list(retries=4, maxit=800, tmax=20, temp=10), #6
  list(retries=3, maxit=2500, tmax=30, temp=10), #7
  list(retries=3, maxit=2000, tmax=30, temp=10), #8
  list(retries=3, maxit=1500, tmax=30, temp=10), #9
  list(retries=1, maxit=2000, tmax=20, temp=10) #10
)
resetTimes()
problemIx <<- 6
load()
run()
results[[1]]$cost+ results[[2]]$cost+results[[3]]$cost
save()
saveStates()
