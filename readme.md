## ICS 340 Programming Project, Deliverable B

-----------------
# Specification:
Start with your Java program “prog340”, ideally it should be working for Deliverable A.
Perform Depth First Search (DFS) on the graph, starting from the node with value “S”. When you have
the choice of two or more unexplored nodes to visit next, choose the nearest one. Break ties
alphabetically by name. If your depth first search terminates without visiting every node in the graph,
start again from the first node in the graph in alphabetical order by name that hasn’t already been visited.

After the DFS is completed, print the following:
1. The discovery and finish times of each node in the graph.
2. The classification of each edge in the graph. (_Tree, Forward, Back, or Cross_.)

-----------------
> As will always be the case in this class, the program must be written in Java and must run on the
University Windows computer systems.
Output:
Please see the test files and corresponding output files in D2L under Deliverable B/Test Files. Here is a
graph of test file b0 for your reference.
