# Project 3

Language: Java 21

IDE: Eclipse

## Hashing

The project implements a custom hash table for storing strings using linear probing for collision resolution. The hash table is designed to test different resizing strategies and analyze performance based on load factor.

### Key Features
- Hash Function: Uses Java;s built-in hashCode() method and takes modulus with the table size to compute the index.
- Linear Probing: In case of a collision, the algorithm checks the next available slot until it finds an empty spot or the string.
- Duplicate Handling: If a string exists in the table, it is not inserted again.
- Dynamic Resizing: The table resizes itself when the load factor exceeds the specified maximum.
  - Doubling Strategy: The table size is double.
  - Addition Strategy: The table size increases by 10,000.
- Load Factor: Calculated as (number of items) / (table size). When this exceeds the max load factor, the table resizes and rehashes all stored values.


## Conclusions

### Graphs and their characteristics
The graphs clearly illustrate the theoretical and empirical number of probes versus the load factor for both successful and unsuccessful searches.

![Successful Search](time_succ.png)

- Linear Porbing: This curve starts low and rises steeply as λ approaches 1. It shows that when the table gets more full, successful search becomes significantly slower, especially beyond λ=0.7.
- Double Hashing: This curve increases more gradually, demostrating improved performance under high load.
- Q x Empirical Time: This curve represents actual measured timings for successful searches, scaled by a constant Q to align with theory. It tracks the linear probing theoretical curve closely, validating our implementation.

![Unsuccessful Search](plots/time_fail.png)

- Linear Probing: This curve grows faster than in the success case, indicating failed searches require more effort as the table fills.
- Double Hashing: A sharp curve but still generally better than linear probing at high load.
- Q x Empirical Time: The empirical curve again follows the theoretical linear probing shape, especially at moderate load factors. However, discrepancies can grow slightly at λ>0.85 due to real-world timing noise and collisions.
