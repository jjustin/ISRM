import math
A_SIZE = 26
DIFF_THRESHOLD = 30


def encrypt(b, k):
    if len(b) % 2 == 1:
        b += "A"
    b = toIntArr(b)
    kMat = toKeyMatrix(k)
    if math.gcd(det(kMat), A_SIZE) != 1:
        raise Exception(f"gcd(Det(matrix({k})), A_SIZE) != 1")

    res = []
    for i in range(0, len(b), 2):
        res += multiply(kMat, b[i:i+2])
    return toString(res)


def decrypt(c, k):
    kMat = toKeyMatrix(k)
    return decryptWithMatrix(c, kMat)


def decryptWithMatrix(c, kMat):
    if len(c) % 2 != 0:
        raise Exception(f"2 !| len(c)")
    c = toIntArr(c)
    if math.gcd(det(kMat), A_SIZE) != 1:
        raise Exception(f"gcd(Det(matrix({k})), A_SIZE) != 1")
    kInvMat = inverse(kMat)

    res = []
    for i in range(0, len(c), 2):
        res += multiply(kInvMat, c[i:i+2])
    return toString(res)


def toString(intArr):
    return bytes([i+65 for i in intArr]).decode()


def toIntArr(text):
    return [c - 65 for c in str.encode(text)]


def toKeyMatrix(k):  # len(k) should be 4
    k = [c-65 for c in str.encode(k)]
    return [[k[0], k[1]], [k[2], k[3]]]  # 2x2 matrix


def keyMatrixToString(mat):
    return toString(mat[0]+mat[1])


def multiply(matrix, vector):  # matrix*vector
    res = []
    for i in range(len(matrix)):
        scalar = 0
        for j in range(len(vector)):
            scalar += matrix[i][j] * vector[j]
        res.append(scalar % A_SIZE)
    return res


def det(matrix):
    return (matrix[0][0] * matrix[1][1] - matrix[0][1]*matrix[1][0]) % A_SIZE


def inverse(matrix):
    matrixDetInv = pow(det(matrix), -1, A_SIZE)  # inverse of det(matrix) in Zn
    a = matrixDetInv * matrix[1][1] % A_SIZE
    b = - matrixDetInv * matrix[0][1] % A_SIZE
    c = - matrixDetInv * matrix[1][0] % A_SIZE
    d = matrixDetInv * matrix[0][0] % A_SIZE

    return [[a, b], [c, d]]


def nextMatrix(mat):  # return next invertible 2x2 matrix
    mat = incMatrix(mat)
    while(mat != None and math.gcd(det(mat), A_SIZE) != 1):
        mat = incMatrix(mat)
    return mat


def incMatrix(mat):  # increment one field of matrix
    a = mat[0][0]
    b = mat[0][1]
    c = mat[1][0]
    d = mat[1][1]
    a += 1
    if(a == A_SIZE):  # owerflow to b
        a = 0
        b += 1
        if(b == A_SIZE):  # owerflow to c
            b = 0
            c += 1
            if(c == A_SIZE):  # owerflow to d
                c = 0
                d = (d + 1) % A_SIZE
                if d != 0:
                    print(f"{round(d/A_SIZE* 100)}%")
    if(a == 0 and b == 0 and c == 0 and d == 0):
        return None
    return [[a, b], [c, d]]


def findKeys(c):
    res = {}
    mat = nextMatrix([[0, 0], [0, 0]])
    while(mat != None):
        b = decryptWithMatrix(c, mat)

        diff = evaluateFreq(b)
        k = keyMatrixToString(mat)
        if(diff < DIFF_THRESHOLD):
            res[k] = diff  # store current result

        mat = nextMatrix(mat)
    return res


frequencies = [8.2, 1.5, 2.8, 4.3, 13, 2.2, 2, 6.1, 7, 0.15, 0.77, 4,
               2.4, 6.7, 7.5, 1.9, 0.095, 6, 6.3, 9.1, 2.8, 0.98, 2.4, 0.15, 2, 0.074]


def evaluateFreq(b):
    current_freq = [0]*A_SIZE
    for char in str.encode(b):
        n = (char-65)
        current_freq[n] = current_freq[n]+1
    l = len(b)
    current_freq = [n/l * 100 for n in current_freq]
    freq_change = sum([abs(frequencies[i] - current_freq[i])
                       for i in range(A_SIZE)])
    return freq_change


if __name__ == "__main__":
    # 1
    k = "HAAD"  # len == 4, gcd(det(toKeyMatrix(k)), A_SIZE) == 1
    b = "LETUSGOTHENYOUANDIWHENTHEEVENINGISSPREADOUTAGAINSTTHESKY"
    c = encrypt(b, k)
    print(f"encrypt(\"{b}\", \"{k}\") = {c}")
    b = decrypt(c, k)
    print(f"decrypt(\"{c}\", \"{k}\") = {b}")

    # 2
    c = "STSQALWTCJMIJMTHNFEBWZTVJWMRNNHPMFICJFNWSZSXGWPFHHAJFBNTWZTVTHIRMRCGVRJTAFXBWDIVMFWSNSTVLXIRACANWLYSIYVPJQMQNFLNMRPXSBHMWNJTIYNSZNHPHPIMNZDRWBPPNSHMSBUJMUHZXJHMWPSQHHJBMHHMWMJTAFXBWDICVETVLXIRANXFVETVUDWUHBWHEBMBSXHMWEEEHMANWUJUWWHAWWSNWZMLJXVXHWTVJTZZICACHHJTNWWTZRHWWTIYJSSUWSNSTVLWWWWHHPNSTVSNWWIYNSSOPFHMWEWHMHHMWNJTIYNSXPCQJTOQYFPBQKHMWEWHMHHMWNACHRNWHMWBSZWSIOGIICVETVLWWWWHHXANZRVZYWXUMVWZHDJHXAANHRUQZZOUNBTZTJFNSBUUMBVZSTTLHZXNWDTZELTVPPAJWTICVETVNNHPMFVZYWXUTVXBAJSQIUWWMHHMWNACHTGCTJIRGFCGVGSBYAPQITSDWISVPPNNZMWCIRMSFRSXHMWZEENFGDVBMHSYOYJHPBHLANXNNZVOSUSANTCVTVUMPSIATHYFAHEGCSPBWKNZMFWUYFIKXBMHHMWAAZWGJJAHSSWKVJANANXFVMAFSENLHMWBLZNDHMSBUJMNALWUFRSXWDMFWSVBTHLLJTYOSQWHYAGJHDJTXNNSTVMXTVJH"
    keys = findKeys(c)
    # order keys by evaluated fequency
    keys = dict(sorted(keys.items(), key=lambda item: item[1]))
    for k in keys:
        t = decrypt(c, k)
        if (input(f"\n{t}\nAli je ta tekst smiselen? (Y/N) ").capitalize() == "Y"):
            print(f"Kljuc: {k} / {toKeyMatrix(k)}\nBesedilo: {t}")
            quit()
    print("Ključ ni bil najden")
