# snapsack-EA
I naive evolutionary algorithm to solve the snapasack problem
There is a single evolve function in the user namespace that accepts a map of
options.
Consinder the following map as an example. Entries are pretty much self-explanatory

  {:ratings [2 9 3 8 10 6 4 10 2 3 4 5 1]
    :weights [1 2 4 3 3 1 5 10 3 3 1 9 4]
    :restriction 25
    :population-number 30
    :mutation-rate 0.1
    :max-generations 1000})

Running evolve with the above map, a solution will be printed in about
4 generations


Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
