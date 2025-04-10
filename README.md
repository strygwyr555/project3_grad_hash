# Priject 3

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
