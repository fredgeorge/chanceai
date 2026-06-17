# chanceai

An implementation of the Chance class via AI

## Purpose

Create a Chance class and tests. Chance understands the likelihood of something occurring.

## Organization

Code should in the engine project. Tests should go 
in the tests project.

## Requirements

1. Implement equality and hash code for Chance. Ensure that
   equality works when the probabilities are very, very close.
   If two Chances are equal, the hash codes must be the 
   same. Test with a variety of values, including zero and one.
   Avoid using 0.5 as a test value.
2. Implement not() on a Chance. Test behavior. Use operator
   overloading. Test with and without the operator overloading.
