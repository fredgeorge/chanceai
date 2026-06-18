# chanceai

An implementation of the Chance class via AI

## Purpose

Create a Chance class and tests. Chance understands the likelihood of something occurring.

## Organization

Code should in the engine project. Tests should go 
in the tests project.

## Requirements

Use all available skills.

1. Implement equality and hash code for Chance. Ensure that
   equality works when the probabilities are very, very close.
   If two Chances are equal, the hash codes must be the 
   same. Test with a variety of values, including zero and one.
   Avoid using 0.5 as a test value.
2. Implement not() on a Chance. Test behavior. Use operator
   overloading. Test with and without the operator overloading.
3. Implement and() on a Chance. Test behavior.
4. Implement or() on a Chance using DeMorgan's law.
5. Ensure that Chance is always constructed with a number
   between 0.0 and 1.0, inclusive.
6. Add common constants for IMPOSSIBLE and CERTAIN.
