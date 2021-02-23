library(data.table)
library(rlist)
#############################################
# Load/save/graph manipulation
#############################################
load<-function(){
  results <<- list(list(cost=Inf, solution=c()),list(cost=Inf,solution=c()),list(cost=Inf,solution=c()))
  
  readData <<- read.delim(sprintf("Problem%d.txt", problemIx), header = FALSE, fill = TRUE, sep = ",")
  nOfSites <<- readData[1, 1]
  maxWeight <<- readData[1, 2]
  connsRaw <<- readData[(nOfSites+2):length(readData[[1]]),1:5]
  conns <<- list()
  for(v in 1:nOfSites){
    # find reversed
    conns1 <- connsRaw
    rev <- which(connsRaw[,2]==v & connsRaw[,4]==0)
    conns1[rev,] <- connsRaw[rev,][c(2,1,3:5)]
    conns1 <- conns1[conns1$V1==v,]
    conns[[v]] <<- conns1
  }
  sols <<- list(list(), list(), list())
  con = file(sprintf("solution%d.txt", problemIx), "r")
  colsAll <- c(1,1,1)
  while ( TRUE ) {
    line = readLines(con, n = 1)
    if ( length(line) == 0 ) {
      break
    }
    l <- as.integer(unlist(strsplit(line,",")))
    sols[[l[1]]] <<- list.append(sols[[l[1]]], l[2:length(l)])
    colsAll[l[1]] <- max(length(l)-1,colsAll[l[1]])
  }
  close(con)
  
  sol <<- list()
  for(i in 1:3){
    s <- sols[[i]]
    rows <- length(s)
    cols <- colsAll[i]
    v <- c(rows,rows,cols)
    
    for(j in 1:rows){
      start <- length(v)+1
      end <- start+length(s[[j]])-1
      v[start:(start+cols-1)] <- rep(-1, cols)
      v[start:end] <- s[[j]]
    }
    sol[[i]] <<- v
  }
}
setTrashType <- function(i){
  sites <<- readData[(1:nOfSites)+1 ,c(2:3,3+i)]
}

#############################################
# Graph functions
#############################################
E <- function(v, w=0){
  if(w==0){
    return(conns[[v]])
  }
  c <- conns[[v]]
  out<- c[c[,5]>=w,]
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
        warning("NonExistent connection used: ", rprev, "->", r, " w: ",w)
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

run <- function(){
  setTrashType(1)
  c <- cost(sol[[1]])
  setTrashType(2)
  c <- c + cost(sol[[2]])
  setTrashType(3)
  c <- c + cost(sol[[3]])
  cat("Cost of problem",problemIx,":",c,"\n")
}
for (i in 1:10){
  problemIx <- i
  load()
  run()
}
