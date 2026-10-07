# DAA Assignment 2 - Data Structures

## Overview

This project implements three data structures from scratch for the Design and Analysis of Algorithms course:

* DynamicArray
* MyLinkedList
* MinHeap

The project also includes automated tests, performance benchmarks, operation counters and experimental results.

Java collection classes such as `ArrayList`, `LinkedList` and `PriorityQueue` are not used inside the implemented data structures.

## Requirements

* Java 17
* Maven
* IntelliJ IDEA

## Build and Test

To compile the project and run all tests:

```bash
mvn clean test
```

The project uses JUnit 5 for testing.

The tests check:

* basic correctness
* empty structures
* one-element structures
* duplicate values
* first and last indexes
* invalid indexes
* heap property
* sorted output of `extractMin()`

## Running the Benchmark

Run the following class from IntelliJ IDEA:

```text
benchmark.BenchmarkRunner
```

The benchmark generates:

```text
results/results.csv
```

The benchmark contains four workloads:

### W1 - Random Access

The structures are filled with `n` elements and then 10,000 random `get(index)` operations are performed.

Structures:

* DynamicArray
* MyLinkedList

### W2 - Search

The benchmark performs 1,000 `contains(x)` queries.

Half of the queries use values that are present in the structure and half use values that are not present.

Structures:

* DynamicArray
* MyLinkedList

### W3 - Insert & Remove

The benchmark performs 1,000 insertions and 1,000 removals.

Two variants are tested:

* `head` - index 0
* `middle` - index `n / 2`

Structures:

* DynamicArray
* MyLinkedList

### W4 - Priority Processing

The benchmark:

1. Inserts `n` values into MinHeap.
2. Extracts the minimum value `n` times.
3. Checks that the extracted values are in non-decreasing order.

Structure:

* MinHeap

## Tested Input Sizes

The benchmark uses:

```text
n = 100
n = 1000
n = 10000
n = 100000
```

The same reproducible random seed is used:

```text
Random(42)
```

Each benchmark case has:

* 1 warm-up run
* 5 measured runs

The median of the five measured runs is saved to the CSV file.

## Metrics

The benchmark records three operation counters:

### Steps

One array cell read or one move to the next linked-list node.

### Moves

One array element shift or one pointer/link update.

### Comparisons

One comparison between two elements.

The counters are updated directly inside the data structure operations.

## Results

Benchmark results are stored in:

```text
results/results.csv
```

Charts are stored in:

```text
results/plots/
```

The plots show performance for the four workloads and compare the implemented data structures.

## Project Structure

```text
DAA-Assignment2
├── pom.xml
├── README.md
├── REPORT.md
├── results
│   ├── results.csv
│   └── plots
│       ├── w1_time.png
│       ├── w1_steps.png
│       ├── w2_time.png
│       ├── w2_operations.png
│       ├── w3_head_time.png
│       ├── w3_head_moves.png
│       ├── w3_middle_time.png
│       ├── w3_middle_moves.png
│       ├── w4_time.png
│       └── w4_operations.png
└── src
    ├── main
    │   └── java
    │       ├── benchmark
    │       ├── metrics
    │       └── structures
    └── test
        └── java
            └── structures
```

## Data Structures

### DynamicArray

DynamicArray uses a primitive `int` array.

When the array becomes full, its capacity is doubled and the existing elements are copied to the new array.

Indexed access is constant time because the element can be accessed directly by its index.

### MyLinkedList

MyLinkedList is a singly linked list.

Each node contains:

* an integer value
* a reference to the next node

The list is efficient for operations at the head, but indexed access requires traversal from the first node.

### MinHeap

MinHeap is an array-based binary min-heap.

The minimum value is stored at the root.

Insertion uses bubble-up and `extractMin()` uses bubble-down to maintain the heap property.

## Complexity Summary

| Structure    | Operation     | Complexity     |
| ------------ | ------------- | -------------- |
| DynamicArray | add(x)        | O(1) amortized |
| DynamicArray | add(index, x) | O(n)           |
| DynamicArray | remove(index) | O(n)           |
| DynamicArray | get(index)    | O(1)           |
| DynamicArray | contains(x)   | O(n)           |
| MyLinkedList | add(x)        | O(n)           |
| MyLinkedList | add(index, x) | O(n)           |
| MyLinkedList | remove(index) | O(n)           |
| MyLinkedList | get(index)    | O(n)           |
| MyLinkedList | contains(x)   | O(n)           |
| MinHeap      | insert(x)     | O(log n)       |
| MinHeap      | peekMin()     | O(1)           |
| MinHeap      | extractMin()  | O(log n)       |

## Report

A detailed analysis of the implementation, complexity, loop invariants, benchmark methodology and experimental results is provided in:

```text
REPORT.md
```



## Bonus Tasks

### JOL Memory Footprint

The project includes a JOL-based memory benchmark for DynamicArray, MyLinkedList and MinHeap.

Results are saved to:

results/memory.csv

The benchmark compares the memory footprint of the three structures for n = 100, 1000, 10000 and 100000.

### Floyd BuildHeap

MinHeap includes an additional Floyd bottom-up buildHeap operation with O(n) construction time.

The project compares Floyd BuildHeap with repeated insert operations.

Results are saved to:

results/floyd.csv

## Git

The project uses the following main branches:

```text
main
feature/array
feature/list
feature/heap
feature/metrics
```

## GitHub Repository

https://github.com/diassabit1/DAA-Asik2


The final submission is tagged:

```text
v1.0
```
