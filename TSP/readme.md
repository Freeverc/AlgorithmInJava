## Report
#### Introduction
In this experiment, genetic algorithm is used to solve TSP problem. Genetic algorithm is a computational model that simulates the natural selection and genetic mechanism of Darwinian biological evolution, and searches for the optimal solution by simulating the natural evolution. Genetic algorithm starts from a population that represents the potential solution set of the problem. After the generation of the first generation of population, according to the principle of survival of the fittest and survival of the fittest, generation by generation evolution produces better and better approximate solutions. In each generation, individuals are selected according to the fitness of individuals in the problem domain, and are combined, crossed and mutated with the help of genetic operators of natural, and generate a population that represents the new solution set. This process will lead to the population of the later generation, like the natural evolution, more suitable for the environment than the previous generation. The optimal individuals in the last generation can be decoded as the approximate optimal solution of the problem.
In this TSP problem, a city sequence is an individual of the genetic algorithm, and a series of city sequences compose a population of genetic algorithm. Through the evolution of population, the final convergence to the final solution. The final solution is not necessarily optimal, but close to the global optimal solution.
#### Dara Structure and Variables
Important static variables of the tspsover class:
* Maxgeneration: maximum algebra
* Populationsize: population number
* Crossover probability: probability of crossover
* Mutation probability: the probability of gene mutation
* Dist [] []: adjacency matrix
* Population: population
* bestIndividual: Best individual
* bestDist: the best total distance
* Sumdistances; distance between individuals in the current population
* Fitness values: adaptability of current population
* Survival: probability of current population of surival

#### Algorithm
The basic operation process of genetic algorithm is as follows:



1. Initialization: set the evolution algebra counter t = 0, set the maximum evolution algebra T, crossover probability, mutation probability, randomly generate m individuals as the initial population p
2. Individual evaluation: calculate the fitness of each individual in population p
3. Selection operation: the selection operator is applied to the group. On the basis of individual fitness, select the best individual to directly inherit to the next generation or generate a new individual through pairing crossover to inherit to the next generation
4. Crossover operation: under the control of crossover probability, individuals in the group are crossed
5. Mutation operation: under the control of mutation probability, two individuals in a population are mutated, that is, the gene of an individual is randomly adjusted
6. After selection, crossover and mutation, the next generation population P1 was obtained.
Repeat the above 1-6 until the genetic algebra is t. take the individual with the maximum fitness obtained in the evolution process as the optimal solution output, and terminate the calculation.
#### Experiments
Many of given data has been tested.Here are some records.
1.	For data  ‘C110_5.TXT’：
Initial Distance: 271623.217564 
best Distance: 6667.578362(1000 generaitons)
 
2.	The process(for data ‘RC11010.TXT’) (1000 generaitons)
 
>Distances: 263007.851113
>len : 1001
>size: 1001 1001
>generation: 1  bestDistance: 199670.1  current distance: 199670.1
>generation: 2  bestDistance: 165893.77  current distance: 165893.77
>generation: 3  bestDistance: 139175.56  current distance: 139175.56
>generation: 4  bestDistance: 128790.27  current distance: 128790.27
>generation: 5  bestDistance: 114291.95  current distance: 114291.95
>generation: 6  bestDistance: 104755.055  current distance: 104755.055
>generation: 7  bestDistance: 100533.01  current distance: 100533.01
>generation: 8  bestDistance: 93684.64  current distance: 93684.64
>generation: 9  bestDistance: 89960.375  current distance: 89960.375
>generation: 10  bestDistance: 86422.984  current distance: 86422.984
>   ...
>generation: 995  bestDistance: 12363.626  current distance: 12363.626
>generation: 996  bestDistance: 12363.103  current distance: 12363.103
>generation: 997  bestDistance: 12363.103  current distance: 12690.348
>generation: 998  bestDistance: 12363.103  current distance: 12432.966
>generation: 999  bestDistance: 12363.103  current distance: 12406.376
>generation: 1000  bestDistance: 12363.103  current distance: 12503.437
>Distances: 12363.118452
