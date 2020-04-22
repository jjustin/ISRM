# Izracunaj koliko drugi placa drugemu glede na odlocitve
def drugiPrvemu(prvi, drugi, x1, x2, x3, y1, y2, y3):
    if(prvi == 2):
        if(drugi == 2):
            return placila[prvi+x1-1][drugi+y1-1]
        elif(drugi == 3):
            return placila[prvi+x1-1][drugi+y2-1]
        else:
            return placila[prvi+x1-1][drugi+y3-1]
    elif(prvi == 3):
        if(drugi == 2):
            return placila[prvi+x2-1][drugi+y1-1]
        elif(drugi == 3):
            return placila[prvi+x2-1][drugi+y2-1]
        else:
            return placila[prvi+x2-1][drugi+y3-1]
    else:
        if(drugi == 2):
            return placila[prvi+x3-1][drugi+y1-1]
        elif(drugi == 3):
            return placila[prvi+x3-1][drugi+y2-1]
        else:
            return placila[prvi+x3-1][drugi+y3-1]


mat = []
placila = [[0, 2, 0, 1, -4],
           [-2, 0, -3, 1, 0],
           [0, 3, 0, -1, -2],
           [-1, -1, 1, 0, 2],
           [4, 0, 2, -2, 0]]

# napolni matriko
for i in range(2, 5):
    for x1 in range(-1, 2):
        for x2 in range(-1, 2):
            for x3 in range(-1, 2):
                mat.append([])
                for j in range(2, 5):
                    for y1 in range(-1, 2):
                        for y2 in range(-1, 2):
                            for y3 in range(-1, 2):
                                ix = 27*(i-2) + (x1+1)*9 + (x2+1) * 3 + x3+1
                                mat[ix].append(drugiPrvemu(
                                    i, j, x1, x2, x3, y1, y2, y3))
print("Placinlna matrika:")
for i in range(0, 81):
    print(mat[i])
