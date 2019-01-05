from sys import argv

def egcd(a, b):
    if a == 0:
        return (b, 0, 1)
    g, y, x = egcd(b%a,a)
    return (g, x - (b//a) * y, y)

def modinv(a, m):
    g, x, y = egcd(a, m)
    if g != 1:
        raise Exception('No modular inverse')
    return x%m



n = int(argv[1])
e = int(argv[2])
i = 2
factors = []
while i * i <= n:
    if n % i:
      i += 1
    else:
      n //= i
      factors.append(i)
if n > 1:
    factors.append(n)
    print("n and q are :")
print(factors)
p = factors[0]
q = factors[1]

# Compute phi(n)
phi = (p - 1) * (q - 1)

d = modinv(e, phi)
print( "so d:  " + str(d) );

