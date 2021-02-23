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
setSites <- function(i){
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
  cat("neigTimes",mean(neigTimes),sum(neigTimes),"\n")
  cat("neigTimesAdd",mean(neigTimesAdd),sum(neigTimesAdd),"\n")
  cat("neigTimesSwitch",mean(neigTimesSwitch),sum(neigTimesSwitch),"\n")
  cat("nt1",mean(nt1),sum(nt1),"\n")
  cat("nt2",mean(nt2),sum(nt2),"\n")
  cat("spt2",mean(spt1),sum(spt1),"\n")
  cat("et1",mean(et1),sum(et1),"\n")
  cat("costTimes",mean(costTimes),sum(costTimes),"\n")
}
getTrash <- function(){
  trash <- sites[,3]
}

#############################################
# Cost
#############################################
cost <- function(sq){
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
# Neighborhood
#############################################
genNeigh <- function(sq){
  selector <- sample(1:100, 1)
  if(selector <= 20){
    sqn <- genAddStop(sq)
  }else{
    sqn <- genSwitch(sq)
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
    setSites(i)
    sq <- generateStarting()
    it <- params[[problemIx]]$it # after how many of no change iterations to quit
    tmax <- params[[problemIx]]$tmax # iterations until reset is made
    t <- params[[problemIx]]$temp # starting temp 
    lambda <- params[[problemIx]]$lambda # starting temp
    for(j in 1:params[[problemIx]]$retries){
      cat("Starting optim for problem",problemIx,"trashType",i,"loop",j,"/",params[[problemIx]]$retries,"\n")
      Temp <- t
      s_b <- s_c <- sq
      c_b <- c_c <- cost(sq)
      cat("Starting solution cost:", c_c,"\n")
      noChange <- 0;
      noBetter <- 0;
      cooli <- 0;
      while(T){
        noChange <- noChange+1;
        noBetter <- noBetter+1;
        cooli <- cooli + 1;
        s_m <- genNeigh(s_c)
        c_m <- cost(s_m)
        if(c_m != Inf){
          if(c_m < c_b){
            s_b <- s_m
            c_b <- c_m
            noBetter<-0;
          }
          if(c_m < c_c){
            s_c <- s_m
            c_c <- c_m
            noChange<-0;
          }else{
            a <- runif(1)
            p <- exp((c_m - c_c)/Temp)
            
            if(a <= p){
              s_c <- s_m
              c_c <- c_m
              noChange<-0;
            }
          }
          if(noChange >= tmax){
            cat("No change found for",noChange,"iterations. Restoring to best\n")
            s_c <- s_b
            c_c <- s_c
          }
          if(noBetter >= it){
            cat("No better found for",noBetter,"iterations \n")
            break;
          }
        }
        if(cooli%%reportRate == 0){
          cat("iter:",cooli,"cost:", c_b,"teperature",Temp,"\n")
        }
        
        Temp <- t/log(cooli+1)
      }
      
      cat("Found solution with cost", c_b ,"for trash type",i,"\n")
      if(results[[i]]$cost > c_b){
        results[[i]]$cost <<- c_b
        results[[i]]$solution <<- parseSq(s_b, i)
        results[[i]]$sq <<- s_b
      }
    }
  }
  cat("Found solution with cost:",results[[1]]$cost+ results[[2]]$cost+results[[3]]$cost)
}

params <- list(
  list(retries=20, it=100, tmax=20, temp=20), #1
  list(retries=50, it=100, tmax=20, temp=5), #2
  list(retries=30, it=300, tmax=20, temp=10), #3
  list(retries=100, it=100, tmax=1, temp=20), #4
  list(retries=5, it=2500, tmax=20, temp=10), #5
  list(retries=5, it=4000, tmax=20, temp=10), #6
  list(retries=1, it=1000, tmax=30, temp=60), #7
  list(retries=2, it=2000, tmax=30, temp=10), #8
  list(retries=2, it=1500, tmax=30, temp=10), #9
  list(retries=5, it=5000, tmax=20, temp=10) #10
)
reportRate <<- 50
resetTimes()
problemIx <<- 3
load()
run()
results[[1]]$cost+ results[[2]]$cost+results[[3]]$cost
save()
saveStates()
