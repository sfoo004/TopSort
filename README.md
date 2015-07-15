# TopSort
Topological sorting algorithm that will show cycles

Assignment #1
Write a program that either outputs a topological ordering or indicates that there is a cycle (in which case it produces the cycle). Your program should use the individual file names as given on the command line. For instance, given the following three files:

file1.txt
a b
c b
a c


file2.txt
mad2104 cop3530 
cop3337 cop3530 
cop2210 cop3337


file3.txt
x y
y z
z x

The result of running
  java Assign1 file1.txt file2.txt file3.txt
would be something like:

file1.txt: a, c, b
file2.txt: cop2210, mad2104, cop3337, cop3530
file3.txt: graph has a cycle: x->y->z->x

Note that the output for file2.txt could swap mad2104 and cop3337 and the cycle could be represented in several ways. Your algorithm must run in linear time, even if there is a cycle.

Submit an appropriately named zip file containing your source code in a complete Netbeans project folder, and the output for the data files I will provide. Make sure you include the output, and place it in an easy-to-find location in the zip file. Your program should include code to time how long it takes to solve each file and this should be included in the output.
