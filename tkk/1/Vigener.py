import math
import sys
MAX_KEY_LEN = 50
A_SIZE = 26


def encrypt(b, k):  # encrypt b using key k
    b = toIntArr(b)
    k = toIntArr(k)
    kLen = len(k)
    bLen = len(b)
    for i in range(bLen):
        b[i] = (b[i] + k[i % kLen]) % A_SIZE
    return toString(b)


def decrypt(c, k):  # decrypt c using k
    c = toIntArr(c)
    k = toIntArr(k)
    kLen = len(k)
    cLen = len(c)
    for i in range(cLen):
        c[i] = (c[i] - k[i % kLen]) % A_SIZE
    return toString(c)


def lengths(c):  # returns pair [lengths, occurances(length)] of subsequences in c
    occurances = [0]*MAX_KEY_LEN
    for i in range(len(c)-3):
        x = c[i:i+3]
        j = i
        while(j != -1):
            j = c.find(x, j+1)
            if(j == -1):
                break
            diff = j-i
            ds = divisors(diff)
            for d in ds:
                if(d < MAX_KEY_LEN):
                    occurances[d] = occurances[d]+1
        continue
    return [sort_index(occurances), occurances]


def divisors(n):  # returns list of divisors of n
    factors = [n]
    for i in range(2, int(n/2)+1):
        if n % i == 0:
            factors.append(i)
    return factors


# returns permutation of elements in l that produces non-ascending list l # e.g. l = [3,2,4] -> [2,0,1]
def sort_index(l):
    li = []

    for i in range(len(l)):
        li.append([l[i], i])
    li.sort(reverse=True)
    sort_index = []

    for x in li:
        sort_index.append(x[1])

    return sort_index


# https://en.wikipedia.org/wiki/Letter_frequency
frequencies = [8.2, 1.5, 2.8, 4.3, 13, 2.2, 2, 6.1, 7, 0.15, 0.77, 4,
               2.4, 6.7, 7.5, 1.9, 0.095, 6, 6.3, 9.1, 2.8, 0.98, 2.4, 0.15, 2, 0.074]


def find_key(c, key_len):  # finds key based on character frequency
    bases = [0]*key_len
    for i in range(key_len):
        best_freq_change = sys.maxsize

        current_freq = [0]*A_SIZE
        x = c[i::key_len]
        # count occurances in x
        for char in str.encode(x):
            n = (char-65)
            current_freq[n] = current_freq[n]+1
        # calculate frequencies
        l = len(x)
        current_freq = [n/l * 100 for n in current_freq]

        for base in range(A_SIZE):
            freq_change = sum([abs(frequencies[i] - current_freq[(i + base) % A_SIZE])
                               for i in range(A_SIZE)])
            if(best_freq_change > freq_change):
                bases[i] = base
                best_freq_change = freq_change
    return toString(bases)


def toString(intArr):
    return bytes([i+65 for i in intArr]).decode()


def toIntArr(text):
    return [c - 65 for c in str.encode(text)]


if __name__ == "__main__":
    # 1
    c = encrypt("BESEDILO", "KLJUC")
    b = decrypt(c, "KLJUC")
    print(f"ecrypt(\"BESEDILO\", \"KLJUC\") = {c}")
    print(f"decrypt(\"{c}\", \"KLJUC\") = {b}\n")

    # 2
    c = encrypt("LETUSGOTHENYOUANDIWHENTHEEVENINGISSPREADOUTAGAINSTTHESKYLIKEAPATIENTETHERIZEDUPONATABLELETUSGOTHROUGHCERTAINHALFDESERTEDSTREETSTHEMUTTERINGRETREATSOFRESTLESSNIGHTSINONENIGHTCHEAPHOTELSANDSAWDUSTRESTAURANTSWITHOYSTERSHELLSSTREETSTHATFOLLOWLIKEATEDIOUSARGUMENTOFINSIDIOUSINTENTTOLEADYOUTOANOVERWHELMINGQUESTIONOHDONOTASKWHATISITLETUSGOANDMAKEOURVISIT",
                "KLJUCEK")
    [i, _] = lengths(c)
    kljuc_len = i[0]
    print(f"Dolzina kljuca: {kljuc_len}")

    # 3
    c = "UTAHELHUSBXLZAZYMVXXGELAUOGDTEMOQRTUKGHCQRGTQNMUATMVASMYANZMARMOQLBIQRMPQSHMUTLWQOISQCTUNELADOGNQNHBSHMVYABUFABUUTLLJILAQNVLUNZYQAMLYEKNQNVPQSHUFHBZBOBUFTALBRXZQNMYQBXSXIHUNRHBSHMVGRKLBUUSUCMVMSXCQRXAQSMHZDMOQPKLEIWLZTBHXEELOTBVZOVJGRKPZGBUDEZBXAKJAUKZQDNYUNZATEKLNEESUOGHPDXKZOMHXIMAXEMVFHXZFRTPZTALETKPREHMFHXLXEVAUOGPEBNATUFHZNTAGRXWDAVAUCTSXYTWBLBLPTHATEYHOTLPZTALOALL"
    [indexes, _] = lengths(c)
    for key_len in indexes:
        k = find_key(c, key_len)
        t = decrypt(c, k)
        if (input(f"\n{t}\nAli je ta tekst smiselen? (Y/N) ").capitalize() == "Y"):
            print(f"Kljuc: {k}\nBesedilo: {t}")
            quit()
    print("Ključ ni bil najden")
