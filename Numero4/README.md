#SHERBROOKE

# JdG2019
## Competition Académique Info - Numero 4

RSA crack.

`start.sh 7 1037` is the expected format

### Required to launch
Il faut avoir python d'installer sur la machine. Puisqu'il était installer sur la machine de développement, nous assumons que le correcteur aura accès à la même version que nous.

1.Exécuter start.bat
2.Enjoy!

### Explanation
Nous utilisons la théorie des modulos pour factoriser le nombre modulo donner. de cette facon nous trouvons les facteur p et q. avec ceux-ci nous trouvons phi. en trouvant phi, on peux donc par la suite trouver d en faisant linverse  (euclide) avec un nombre plus petit que n (gain efficacité). on trouve donc d car  d⋅e ≡ 1 (mod φ(n)).