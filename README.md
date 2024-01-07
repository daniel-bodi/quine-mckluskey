# Quine-McLuskey implementation in Java
- Daniel Bodi
- Computer Science Engineer BSc - 2023/24
- John Von Neumann University

## Usage
1. Build project
```bash
./gradlew build
```
2. Run (example)
```bash
java -jar ./build/libs/quine-mcluskey-1.0-SNAPSHOT.jar -c 4 -m 0,1,5,7,8,10,14,15
```

## Arguments
### -c, --variable-count
- Required argument
- Count of variables
### -m, --minterms
- Required argument
- Comma separated list of minterm numbers
### -d, --dont-cares
- Optional argument
- Comma separated list of "don't care" minterms

## Example
- Input:
```
java -jar ./build/libs/quine-mcluskey-1.0-SNAPSHOT.jar -c 5 -m 2,5,12,22,28 -d 4,6,10,11,13,16,18,20,21,26,27,29
```
- Output:
```
Variable count: 5
Minterms to consider: [2, 5, 12, 22, 28]
Dont cares: [4, 6, 10, 11, 13, 16, 18, 20, 21, 26, 27, 29]

Prime implicants:
2,6,18,22 (4,16)
2,10,18,26 (8,16)
4,6,20,22 (2,16)
16,18,20,22 (2,4)
10,11,26,27 (1,16)
4,5,12,13,20,21,28,29 (1,8,16)

Prime implicants for simplest form:
4,5,12,13,20,21,28,29 (1,8,16)
2,6,18,22 (4,16)

Function (simplest form):
Q=(C/D)+(/BD/E)

Function (without hazards):
Q=(/BD/E)+(/CD/E)+(/BC/E)+(A/B/E)+(B/CD)+(C/D)

Function (without hazards):
Q=(/A/B/C)+(/B/C/D)+(/A/CD)+(A/B/D)+(/ABD)+(AC/D)+(BCD)+(ABC)
```