import math
import matplotlib.pyplot as plt 

size = 43
l = 40
sizeT =[]
lT =[]
cT = []
x=[]
prev = 0
info = 600
pot = 0

def f(n):
    global l, size, sizeT, lT, prev, pot, info, prev2, info2
    c = 1
    if l >= size:
        size = size*1.1
        c = l + 1
    l+=1

    cc = 11*l - 10*size
    info += c + cc - prev
    cT.append(info)
    prev = cc
    
    sizeT.append(size)
    lT.append(l)
    x.append(n)
    return c

t = 0
for n in range(10000):
    t += f(n)
plt.plot(x, sizeT, color='green') 
plt.plot(x, cT, color='black') 
plt.plot(x, lT, color='magenta') 
plt.plot([30,10000], [0,0])
plt.show() 
