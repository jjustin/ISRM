import math
import cProfile
import search

class RobotRescue:
    def __init__(self, n, xyz):
        # 0 - empty space
        # 1 - obstacle
        self.n = n
        self.xyz = xyz

    def solved(self):
        return 1/self.xyz[0] + 1/self.xyz[1] + 1/self.xyz[2] == self.n

    def cost(self,move):
        return 1/(self.xyz[0] + move[0]) + 1/(self.xyz[1] + move[1]) + 1/(self.xyz[2] + move[2]) - self.n
    
    def move(self, move):
        self.xyz[0] += move[0]
        self.xyz[1] += move[1]
        self.xyz[2] += move[2]

    def undo_move(self, move):
        self.xyz[0] -= move[0]
        self.xyz[1] -= move[1]
        self.xyz[2] -= move[2]

    def generate_moves(self): 
        dx = [1,0,0]
        dy = [0,1,0]
        dz = [0,0,1]
        moves = []
        cx = self.cost(dx)
        cy = self.cost(dy)
        cz = self.cost(dz)
        if cx >= 0:
            moves.append((dx, cx))
        if cy >= 0:
            moves.append((dy, cy))
        if cz >= 0:
            moves.append((dz, cz))
        return moves

    def ID(self):
        return tuple(self.xyz)

    def evaluate(self):
        return self.cost([0,0,0])

xyz = [1,1,1]
rr = RobotRescue(4/17, xyz)
resitev = search.A(rr)
for move, _ in resitev :
    xyz[0] += move[0]
    xyz[1] += move[1]
    xyz[2] += move[2]
xyz.sort()
print(xyz)

