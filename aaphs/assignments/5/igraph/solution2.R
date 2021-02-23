library(GenSA)
library(igraph)
set.seed(1234)

# resetTimes();run();mean(ngt1);mean(ngt2);mean(neigTimesAdd);mean(et1)


#############################################
# Load/save/graph manipulation
#############################################
load<-function(){
  results <<- list(organic=list(cost=Inf, solution=c()),plastic=list(cost=Inf,solution=c()),paper=list(cost=Inf,solution=c()))
  
  readData <- read.delim(sprintf("Problem%d.txt", problemIx), header = FALSE, fill = TRUE, sep = ",")
  nOfSites <<- readData[1, 1]
  maxWeight <<- readData[1, 2]
  vertices <- data.frame(
    v=c(1:nOfSites), 
    x=c(readData[1:nOfSites+1,2]), 
    y=c(readData[1:nOfSites+1,3]), 
    organic=c(readData[1:nOfSites+1,4]), 
    plastic=c(readData[1:nOfSites+1,5]), 
    paper=c(readData[1:nOfSites+1,6])
  )
    
  connsRaw <- readData[(nOfSites+2):length(readData[[1]]),1:6]
  from <- c()
  to <- c()
  len <- c()
  weight <- c()
  j <- 1
  
  for(i in 1:length(connsRaw[[1]])){
    f <- connsRaw[i,1]
    t <- connsRaw[i,2]
    # edges better than current
    betterOrEqual <- c()# which(from==f & to==t & len<=connsRaw[i, 3] & weight>=connsRaw[i, 5])
    if(length(betterOrEqual)==0){
      # edges worse than current
      worse <- c()#which(from==f & to==t & len>=connsRaw[i, 3] & weight<=connsRaw[i, 5])
      from[j] <- f
      to[j] <- t
      len[j] <- connsRaw[i, 3]
      weight[j] <- connsRaw[i, 5]
      from[worse]<- NA
      to[worse]<- NA
      len[worse]<- NA
      weight[worse]<- NA
      j <- j+1
    }
    
    betterOrEqual <- which(from==t & to==f & len<=connsRaw[i, 3] & weight>=connsRaw[i, 5])
    if(length(betterOrEqual)==0 && connsRaw[i,4] == 0){
      worse <- which(from==t & to==f & len>=connsRaw[i, 3] & weight<=connsRaw[i, 5])
      from[j] <- t
      to[j] <- f
      len[j] <- connsRaw[i, 3]
      weight[j] <- connsRaw[i, 5]
      from[worse]<- NA
      to[worse]<- NA
      len[worse]<- NA
      weight[worse]<- NA
      j <- j+1
    }
  }
  from <- from[!is.na(from)]
  to <- to[!is.na(to)]
  len <- len[!is.na(len)]
  weight <- weight[!is.na(weight)]
  gr <<- graph.data.frame(data.frame(from=from, to=to, weight=weight, len=len), vertices = vertices)

  allEdges<<-list()
  for(v in vertices$v){
    print(v)
    e <- E(gr)[from(v)]
    for(w in 1:maxWeight){
      allEdges[[v]] <<- e[weight>=w]
    }
  }
}

save <- function(){
  writeLines(c(results[[1]]$solution,results[[2]]$solution,results[[3]]$solution), sprintf("solution%d.txt", problemIx))
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
  Q <- V(gr)
  dist <- rep(Inf, nOfSites)
  weig <- rep(Inf, nOfSites)
  prev <- rep(NA, nOfSites)
  dist[from] <- 0
  weig[from] <- w
  
  t1 <- 0
  
  while(length(Q) != 0){
    u <- which.min(dist)
    w <- weig[u]
    Q <- Q[Q!=u]
    
    # to was found
    if(u == to){
      out <- c(u)
      i <- 2;
      while(u != from){
        u <- prev[u]
        out[i] <- u
        i <- i+1
      }
      et1 <<- append(et1, t1)
      return(rev(out))
    }
    
    # go over all neighbours
    eds <- e(u, w, Q)
    for(ed in eds){
    t <- Sys.time()
      v <- head_of(gr, ed)
      alt <- dist[u] + get.edge.attribute(gr, "len", ed)
      if(w+trash[u] <= maxWeight){
        w <- w+trash[u]
      }
      if(alt < dist[v]){
        dist[v] <- alt
        prev[v] <- u
        weig[v] <- w
      }
    t1 <- t1 + Sys.time() - t
    }
    
    dist[u] <- Inf
  }
  if(to != Inf){
    warning("No path found: ", from, to, w)
  }
  return(prev)
}

e <- function(v, w, q=V(gr)){
 allEdges[[v]][to(q)][weight >= w]
}

#############################################
# Helpers
#############################################
pathOfTruck <- function(truckIx, sq, cols){
  v <- sq[(4 + (truckIx-1)*cols):(3+truckIx*cols)]
  v[v != -1]
}
getTrash <- function(){
  trash <- get.vertex.attribute(gr, trashType)
}
#############################################
# Timing
#############################################
resetTimes <- function(){
  neigTimes <<- double()
  neigTimesAdd <<- double()
  neigTimesSwitch <<- double()
  ngt1 <<- double()
  ngt2 <<- double()
  et1 <<- double()
  et2 <<- double()
  costTimes <<- double()
}

#############################################
# Cost
#############################################
cost <- function(sq){
  start_time <- Sys.time()
  
  nOfTrucks <- sq[1]
  nOfTrucksNew <- 0
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
      # warning("Last or first stop is not firm")
      return(Inf)
    }
    
    for(j in 2:length(route)){
      r <- route[j]
      rprev <- route[j-1]
      # move
      if(!are.connected(gr, rprev,r)){
        # warning("NonExistent connection used: ", rprev, "->", r)
        return(Inf)
      }
      len <- E(gr, P=c(rprev,r))[weight>=w]$len
      if(length(len)==0){
        # warning("NonExistent connection used: ", rprev, "->", r)
        return(Inf)
      }
      roadLen <- roadLen + min(len)
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
      # warning("Nothing picked up: ", i)
    }
    c <- c + roadLen * 0.1
    t <- t + roadLen/50 # 50 kmh
    if(t>8){
      c <- c + 20*(t-8) + 80
    }else{
      c <- c + 10*t
    }
  }
  
  end_time <- Sys.time()
  costTimes <<- c(costTimes, end_time-start_time)
  
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
  cols <- 3*rows
  out <- c(rows, rows, cols, rep(-1, rows*cols)) # nOfTrucks, rows, cols
  tree <- shortestPath(1, Inf, 0, trash = rep(0, length(trash)))
  todo <- which(tree==1)
  for(i in 1:(nOfSites-1)){
    j <- todo[1]
    if(length(todo) == 1){
      todo <- which(tree==j)
    }else{
      todo <- c(todo[2:length(todo)], which(tree==j)) # remove first element and append sites reachable from j
    }
    toj <- shortestPath(1,j,0)
    v <- c(toj[1:(length(toj)-1)], shortestPath(j,1, trash[j]))
    startIx <- 4+cols*(i-1)
    endIx <- startIx + length(v) -1
    out[startIx:endIx] <- v
  }
  neigRemUnused(out)
}

neigRemUnused <- function(sq){
  nOfTrucks <- sq[1]
  nOfTrucksNew <- sq[1]
  rows <- sq[2]
  cols <- sq[3]
  trash <- getTrash()
  
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
      sq[ixStart:ixEnd] <- rep(NA, ixEnd-ixStart+1)
    }
  }
  # removed truck paths now have NA fields - ommit them and add that many -1 to the end
  sq <- sq[!is.na(sq)]
  sq[1] <- nOfTrucksNew
  if(length(sq) != length(sq)){
    sq[(length(sq)+1):length(sq)] <- -1
  }
  sq
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
    neigTimesAdd <<- append(neigTimesAdd, end_time-start_time)
  }else{
    sqn <- genSwitch(sq)
    end_time <- Sys.time()
    neigTimesSwitch <<- append(neigTimesSwitch, end_time-start_time)
  }
  sqn
}
genAddStop <- function(sq){
  nOfTrucks <- sq[1]
  nOfTrucksNew <- sq[1]
  
  time1 <- 0
  time2 <- 0
  rows <- sq[2]
  cols <- sq[3]
  trash <- getTrash()
  
  switchTruck <- sample(1:nOfTrucks,1) # which truck takes extra turn
  route <- pathOfTruck(switchTruck, sq, cols)
  extraStop <- sample(1:(length(route)-1),1) # on which stop to make an extra turn
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
      start_time <- Sys.time()
      edgs <- e(route[extraStop], w)
      if(extraStop != 1){
        edgs <- edgs[head_of(gr, edgs)!=route[extraStop-1]] # not the same as prev
      }
      edgs <- edgs[head_of(gr,edgs)!=route[extraStop+1]] # not the same as next
      if(length(edgs) == 0){
        edgs <- c(route[extraStop-1]) # if there is only one neighbour
      }
      nextStop <- sample(edgs,1) # which road we choose as next
      
      start_time2 <- Sys.time()
      path <- shortestPath(head_of(gr, nextStop), route[extraStop+1], w) # find shortest path from new next to old next
      time2 <- time2 + Sys.time() - start_time2
      
      route <- append(route, path[1:(length(path)-1)], after=extraStop)
      
      if(length(route) >= cols){
        return(sq)
      }else{
        ixStart <- 4 + cols*(truckIx-1)
        ixEnd <- ixStart + length(route) -1
        sq[ixStart:ixEnd] <- route
      }
      time1 <- time1 + Sys.time() - start_time
      
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
      sq[ixStart:ixEnd] <- rep(NA, ixEnd-ixStart+1)
    }
  }
  # removed truck paths now have NA fields - ommit them and add that many -1 to the end
  sq <- sq[!is.na(sq)]
  sq[1] <- nOfTrucksNew
  len <- (3+rows*cols)
  if(length(sq) != len){
    sq[(length(sq)+1):len] <- -1
  }
  ngt1 <<- append(ngt1, time1)
  ngt2 <<- append(ngt2, time2)
  sq
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
  sq
}

#############################################
# running
#############################################
run <- function(){
  for(i in c("organic","plastic","paper")){
    trashType <<- i
    cat("Generating starting soltuion\n")
    sq <- generateStarting()
    for(j in 1:params[[problemIx]]$retries){
      cat("Starting optim for trashType",i,"loop",j,"/",params[[problemIx]]$retries,"\n")
      res <- optim(sq, cost, genNeigh, method = "SANN", 
                   control = list(maxit = params[[problemIx]]$maxit, temp = params[[problemIx]]$temp, trace = FALSE, 
                                  tmax = params[[problemIx]]$tmax, REPORT = 1))
      cat("Found solution with cost", res$value,"for trash type",i,"\n")
      if(results[[i]]$cost > res$value){
        results[[i]]$cost <<- res$value
        results[[i]]$solution <<- parseSq(res$par, i)
        results[[i]]$sq <<- res$par
      }
    }
  }
}

params <- list(
  list(retries=1, maxit=100, tmax=20, temp=10), #1
  list(retries=50, maxit=300, tmax=20, temp=10), #2
  list(retries=50, maxit=2500, tmax=20, temp=10), #3
  list(retries=50, maxit=3000, tmax=20, temp=10), #4
  list(retries=30, maxit=2500, tmax=20, temp=10), #5
  list(retries=10, maxit=2000, tmax=20, temp=10), #6
  list(retries=3, maxit=2500, tmax=30, temp=10), #7
  list(retries=3, maxit=2000, tmax=30, temp=10), #8
  list(retries=3, maxit=1500, tmax=30, temp=10), #9
  list(retries=10, maxit=5000, tmax=20, temp=10) #10
)

resetTimes()
problemIx <<- 1
load()
#plot(gr, edge.label=E(gr)$len)
run()
results[[1]]$cost+ results[[2]]$cost+results[[3]]$cost
save()
saveStates()

best <- rep(Inf, 10)
for(k in 4:5){
  problemIx <<- k
  load()
  run()
  c<- results[[1]]$cost+ results[[2]]$cost+results[[3]]$cost
  cat(k,"solution cost:",c,"\n")
  if(c < best[k]){
    best[k] <- c
    save()
  }
}

